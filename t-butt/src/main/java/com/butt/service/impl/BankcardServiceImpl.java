package com.butt.service.impl;

import com.butt.dao.BankcardDao;
import com.butt.dao.MemberDao;
import com.butt.entity.Bankcard;
import com.butt.entity.Member;
import com.butt.service.BankcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 银行卡相关业务
 * @Date: Created in 0:12 2018/9/17
 * @Modified By:
 */
@Service
@Transactional
public class BankcardServiceImpl implements BankcardService {

    /** 银行卡dao */
    @Autowired
    private BankcardDao bankcardDao;

    /** 用户dao */
    @Autowired
    private MemberDao memberDao;

    /** 根据用户oid查询用户名下所有的银行卡 */
    @Override
    public List<Bankcard> findAllByOid(String oid) {
        return bankcardDao.findAllCardByOid(oid);
    }

    /** 根据id删除银行卡 */
    @Override
    public Map<String, Object> deleCard(String oid, Integer id) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || id==null){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询出该卡
        Bankcard car = bankcardDao.findCarByOidAndID(oid, id);
        if (car==null){
            result.put("code" ,2);
            result.put("msg" ,"银行卡错误");
            return result;
        }
        bankcardDao.deleByID(car);

        result.put("code" ,3);
        result.put("msg" ,"删除成功");
        return result;
    }

    /** 增加一个银行卡 */
    @Override
    public Map<String, Object> addCard(String oid, String code, String name, String phone, String area) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || code==null || "".equals(code.trim()) || code.length()>50 || name==null || "".equals(name.trim()) || name.length()>10
                || phone==null || phone.length()!=11 || area==null || "".equals(area.trim()) || area.length()>225){
            result.put("code" ,1);
            result.put("msg" ,"请规范填写必要的信息");
            return result;
        }
        //查询出该用户
        Member user = memberDao.findMemByOid(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //执行插入
        Bankcard bankcard = new Bankcard();
        bankcard.setUId(user.getId());
        bankcard.setName(name);
        bankcard.setCode(code);
        bankcard.setPhone(phone);
        bankcard.setArea(area);
        bankcardDao.insertOne(bankcard);

        result.put("code" ,3);
        result.put("msg" ,"添加成功");
        return result;
    }

    /** 修改一个银行卡 */
    @Override
    public Map<String, Object> editCard(String oid, Integer id ,String code, String name, String phone, String area) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || code==null || "".equals(code.trim()) || code.length()>50 || name==null || "".equals(name.trim()) || name.length()>10
                || phone==null || phone.length()!=11 || area==null || "".equals(area.trim()) || area.length()>225 || id==null){
            result.put("code" ,1);
            result.put("msg" ,"请规范填写必要的信息");
            return result;
        }
        //查询出该卡
        Bankcard car = bankcardDao.findCarByOidAndID(oid, id);
        if (car==null){
            result.put("code" ,2);
            result.put("msg" ,"银行卡错误");
            return result;
        }
        //跟新信息
        car.setName(name);
        car.setCode(code);
        car.setPhone(phone);
        car.setArea(area);
        bankcardDao.updateByID(car);

        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        return result;
    }
}
