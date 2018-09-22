package com.butt.service.impl;

import com.butt.dao.BankcardDao;
import com.butt.dao.MemberDao;
import com.butt.dao.MoneydetailDao;
import com.butt.dao.WithdrawDao;
import com.butt.entity.Bankcard;
import com.butt.entity.Member;
import com.butt.entity.Moneydetail;
import com.butt.entity.Withdraw;
import com.butt.service.UserService;
import com.butt.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 用户的相关业务实现类
 * @Date: Created in 0:58 2018/9/15
 * @Modified By:
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /** 用户dao */
    @Autowired
    MemberDao memberDao;

    /** 用户金额明细dao */
    @Autowired
    private MoneydetailDao moneydetailDao;

    /** 银行卡dao */
    @Autowired
    BankcardDao bankcardDao;

    /** 提现dao */
    @Autowired
    WithdrawDao withdrawDao;

    /** 根据用户oid查询用户信息 */
    @Override
    public Map<String, Object> findUserByOid(String oid) {
        Map<String ,Object> result = new HashMap<>();
        result.put("code" ,1);
        result.put("msg" ,"请用微信授权登陆！！");
        if (oid==null || "".equals(oid.trim())){
            return result;
        }
        //查询该用户
        Member mem = memberDao.findMemByOid(oid);
        if (mem==null){
            return result;
        }
        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        result.put("member" ,mem);
        return result;
    }

    /** 用户提现 */
    @Override
    public Map<String, Object> userWithdraw(String oid, double withdrawMoney, int carId) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || withdrawMoney<=0 || carId<=0){
            result.put("code" ,1);
            result.put("msg" ,"参数错误");
            return result;
        }
        //查询出该用户并且上锁
        Member user = memberDao.findMemByOidAndLock(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //判断该用户提现的余额是否大于本身的余额
        if (user.getMoney()<withdrawMoney){
            result.put("code" ,4);
            result.put("msg" ,"请输入正确的提现金额");
            return result;
        }
        //查询该银行卡信息是否存在
        Bankcard card = bankcardDao.findCardByUidAndID(carId, user.getId());
        if (card==null){
            result.put("code" ,5);
            result.put("msg" ,"银行卡错误");
            return result;
        }
        //减去用户余额
        memberDao.mimuMoney(withdrawMoney ,user.getId());
        //插入资金明细
        Moneydetail moneydetail = new Moneydetail();
        moneydetail.setUId(user.getId());
        moneydetail.setMoney(-withdrawMoney);
        moneydetail.setCuMoney(user.getMoney());
        moneydetail.setRemarke("参与促销消费");
        moneydetailDao.insertOne(moneydetail);
        //增加提现记录
        Withdraw withdraw = new Withdraw();
        withdraw.setUId(user.getId());
        withdraw.setState(1);
        withdraw.setWMoney(withdrawMoney);
        withdraw.setCode(card.getCode());
        withdraw.setName(card.getName());
        withdraw.setPhone(card.getPhone());
        withdraw.setArea(card.getArea());
        withdrawDao.insertOne(withdraw);

        result.put("code",3);
        result.put("msg" ,"提现成功");
        return result;
    }

    /** 绑定手机号 */
    @Override
    public Map<String, Object> bandPhone(HttpSession session, String oid, String code, String phone) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || code==null){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //判断session中有没有验证码
        String mm = (String) session.getAttribute(oid);
        String mphone = (String) session.getAttribute(oid+"-phone");
        if (mm==null || mphone==null){
            result.put("code" ,2);
            result.put("msg" ,"请先获取验证码");
            return result;
        }
        if (!mm.equals(code) || !mphone.equals(phone)){
            result.put("code" ,4);
            result.put("msg" ,"验证码不正确");
            return result;
        }
        //查询出该用户并且上锁
        Member user = memberDao.findMemByOid(oid);
        if (user==null){
            result.put("code" ,5);
            result.put("msg" ,"用户错误");
            return result;
        }
        user.setPhone(phone);
        memberDao.updatePhone(user);
        result.put("code" ,3);
        result.put("msg" ,"绑定成功");
        return result;
    }

    /** 查询所有的用户资料，有用户名条件 ，按注册时间排序*/
    @Override
    public PageInfo<Member> findAll(String name , Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<Member> all = memberDao.findAllByName(name);
        return new PageInfo<Member>(all);
    }

    /** 查看今日注册数和总用户数 */
    @Override
    public Map<String, Object> findCount() {
        //获取开始时间
        String start = DateUtil.getStringDate(DateUtil.getStartTime());
        //获取结束时间
        String end = DateUtil.getStringDate(DateUtil.getEndTime());
        //查询总用户数
        Integer allUCount = memberDao.findAllUCount(null, null);
        //查询今日用户数
        Integer todayCount = memberDao.findAllUCount(start, end);
        Map<String ,Object> result = new HashMap<>();
        result.put("code" ,3);
        result.put("all" ,allUCount);
        result.put("today" ,todayCount);
        return result;
    }

    /** 插入一个用户,判断逻辑 */
    @Override
    public void inserMem(Member member) {
        //根据oid判断该用户是否存在
        Member mem = memberDao.findMemByOid(member.getOid());
        if (mem==null){//无用户，插入
            memberDao.insertOne(member);
        }else {
            memberDao.updateNameAndImg(member);
        }
    }
}
