package com.butt.service.impl;

import com.butt.dao.*;
import com.butt.entity.*;
import com.butt.service.BuyService;
import com.butt.util.DateUtil;
import com.butt.util.Salt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 购买下单业务逻辑实现类
 * @Date: Created in 14:15 2018/9/15
 * @Modified By:
 */
@Service
@Transactional
public class BuyServiceImpl implements BuyService {

    /** 用户dao */
    @Autowired
    private MemberDao memberDao;

    /** 商品dao */
    @Autowired
    private GoodsDao goodsDao;

    /** 用户金额明细dao */
    @Autowired
    private MoneydetailDao moneydetailDao;

    /** 用户积分明细dao */
    @Autowired
    private IntegraldetailDao integraldetailDao;

    /** 地址dao */
    @Autowired
    private AddressDao addressDao;

    /** 订单dao */
    @Autowired
    private OrderinfoDao orderinfoDao;

    /** 竞猜dao */
    @Autowired
    private GuessingDao guessingDao;

    /** 点击购买商品 */
    @Override
    public Map<String, Object> buyTheShop(String oid, Integer goodsId ,int count) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || "".equals(oid.trim()) || goodsId==null || goodsId<=0 || count<=0){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询出该用户并且上锁
        Member user = memberDao.findMemByOidAndLock(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //查询出该商品
        Goods goods = goodsDao.findGoodsById(goodsId);
        if (goods==null){
            result.put("code" ,4);
            result.put("msg" ,"商品错误");
            return result;
        }
        //获取商品类型
        Integer type = goods.getType();
        //判断用户余额是否足够支付
        double price = type==1?goods.getPrice():goods.getIntegral();//支付金额
        price = price*count;
        if ( (type==1 && user.getMoney()<=price) || (type==2 && user.getIntegral()<=price) ){
            result.put("code" ,5);
            result.put("msg" ,"余额不足，请先充值");
            return result;
        }
        //扣取用户余额或者积分并且插入相关的明细
        if (type==1){
            memberDao.mimuMoney(price ,user.getId());
            Moneydetail moneydetail = new Moneydetail();
            moneydetail.setUId(user.getId());
            moneydetail.setMoney(-price);
            moneydetail.setCuMoney(user.getMoney());
            moneydetail.setRemarke("购买商品消费");
            moneydetailDao.insertOne(moneydetail);
        }else {
            memberDao.mimuIntegra(price ,user.getId());
            Integraldetail integraldetail = new Integraldetail();
            integraldetail.setUId(user.getId());
            integraldetail.setIntegral(-price);
            integraldetail.setCuIntegral(user.getIntegral());
            integraldetail.setRemarke("积分商品消费");
            integraldetailDao.insertOne(integraldetail);
        }
        //创建订单
        Orderinfo orderinfo = new Orderinfo();
        orderinfo.setUId(user.getId());//用户
        orderinfo.setCode(Salt.getOrderNum());//订单号
        orderinfo.setGId(goods.getId());//商品
        orderinfo.setConts(count);//数量
        orderinfo.setPrice(price);//价格
        orderinfo.setState(1);//状态
        orderinfoDao.insertOne(orderinfo);

        result.put("code" ,3);
        result.put("orderId" ,orderinfo.getId());
        result.put("msg" ,"购买成功");
        return result;
    }

    /**
     * 确认收货，选择收货地址
     * type-类型：2-下单后选择确认收货
     *           6-未中奖选择提货
     *           8-中奖选择提货，物品X2
     * */
    @Override
    public Map<String, Object> takeGoods(Integer orderId, String oid, Integer type, Integer addressId) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (orderId==null || oid==null || (type!=2 && type!=6 && type!=8) || addressId==null){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询出该用户
        Member user = memberDao.findMemByOid(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //查询订单是否存在，以用户为条件
        Orderinfo order = orderinfoDao.findOrderByIdAndUid(user.getId(), orderId);
        if (order==null || (order.getState()!=1 && order.getState()!=4 && order.getState()!=5) ){
            result.put("code" ,4);
            result.put("msg" ,"订单错误");
            return result;
        }
        //抽取35的运费
        if (user.getMoney()<35){
            result.put("code" ,6);
            result.put("msg" ,"余额不足以支付运费");
            return result;
        }
        memberDao.mimuMoney(35 ,user.getId());
        Moneydetail moneydetail = new Moneydetail();
        moneydetail.setUId(user.getId());
        moneydetail.setMoney(-35.0);
        moneydetail.setCuMoney(user.getMoney());
        moneydetail.setRemarke("提货运费");
        moneydetailDao.insertOne(moneydetail);
        //查询地址是否存在
        Address addre = addressDao.findAddreByUidAndID(user.getId(), addressId);
        if (addre==null){
            result.put("code" ,5);
            result.put("msg" ,"地址错误");
            return result;
        }
        //更改订单状态
        order.setState(type);//该为相对应的状态
        order.setAddre(addre.getAddre());
        order.setAddreName(addre.getName());
        order.setAddrePhone(addre.getPhone());
        orderinfoDao.updateOrderAddreAndState(order);
        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        return result;
    }

    /** 参与促销，选择大小鸡藕 */
    @Override
    public Map<String, Object> partCuxiao(Integer orderId, String oid, String guessResult) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (orderId==null || oid==null || guessResult==null || ("DXJO".indexOf(guessResult)==-1) ){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询出该用户并且上锁
        Member user = memberDao.findMemByOidAndLock(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //查询订单是否存在以及状态，以用户为条件
        Orderinfo order = orderinfoDao.findOrderByIdAndUid(user.getId(), orderId);
        if (order==null || order.getState()!=1){
            result.put("code" ,4);
            result.put("msg" ,"订单错误");
            return result;
        }
        //判断该商品是否是正常商品
        Goods goods = goodsDao.findGoodsById(order.getGId());
        if (goods.getType()!=1){
            result.put("code" ,6);
            result.put("msg" ,"该商品不能参与促销");
            return result;
        }
        //扣取价格的百分之1
        double koqu = order.getPrice() * 0.01;
        //判断余额是否足以支付
        if (user.getMoney()<koqu){
            result.put("code" ,5);
            result.put("msg" ,"参与促销需要扣取1%的手续费，你的余额不足");
            return result;
        }
        memberDao.mimuMoney(koqu ,user.getId());
        Moneydetail moneydetail = new Moneydetail();
        moneydetail.setUId(user.getId());
        moneydetail.setMoney(-koqu);
        moneydetail.setCuMoney(user.getMoney());
        moneydetail.setRemarke("参与促销消费");
        moneydetailDao.insertOne(moneydetail);

        //获取最近未开奖的竞猜
        Guessing notGuess = guessingDao.findOneNotGuess();
        //获取开奖时间
        //计算时间
        Date newSecond = DateUtil.getNewSecond(notGuess.getRegistertime(), 300);
        Long second = DateUtil.getSecond(new Date(), newSecond);
        Date date = DateUtil.getSecondDate(Integer.parseInt(second+"")+30);
        order.setState(3);
        order.setGuess(guessResult);
        order.setGuesstime(date);
        order.setGuessid(notGuess.getId());
        orderinfoDao.updateOrderGuessAndState(order);
        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        return result;
    }

    /**
     * 参与促销后兑换积分
     * type-类型：7-未中奖选择兑换积分
     *           10-中奖选择兑换积分1=100
     * */
    @Override
    public Map<String, Object> changeIntegra(Integer orderId, String oid, Integer type) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (orderId==null || oid==null || type==null || (type!=7 && type!=10) ){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询出该用户并且上锁
        Member user = memberDao.findMemByOidAndLock(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //查询订单是否存在以及状态，以用户为条件
        Orderinfo order = orderinfoDao.findOrderByIdAndUid(user.getId(), orderId);
        if (order==null || (order.getState()!=4 && order.getState()!=5) ){
            result.put("code" ,4);
            result.put("msg" ,"订单错误");
            return result;
        }
        //判断该商品是否是正常商品
        Goods goods = goodsDao.findGoodsById(order.getGId());
        if (goods.getType()!=1){
            result.put("code" ,6);
            result.put("msg" ,"该商品不能参与促销");
            return result;
        }
        //更改订单状态
        order.setState(type);
        orderinfoDao.updateOrderGuessAndState(order);

        //计算应得积分
        //double integra = type==7?order.getPrice():order.getPrice()*100;
        double integra = order.getPrice()*100;
        //执行增加积分
        memberDao.increaseIntegra(integra ,user.getId());
        //插入积分明细
        Integraldetail integraldetail = new Integraldetail();
        integraldetail.setUId(user.getId());
        integraldetail.setIntegral(integra);
        integraldetail.setCuIntegral(user.getIntegral());
        integraldetail.setRemarke("积分兑换");
        integraldetailDao.insertOne(integraldetail);
        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        return result;
    }

    /** 中奖选择兑换金额 */
    @Override
    public Map<String, Object> changeMoney(Integer orderId, String oid) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (orderId==null || oid==null){
            result.put("code" ,1);
            result.put("msg" ,"参数不正确");
            return result;
        }
        //查询出该用户并且上锁
        Member user = memberDao.findMemByOidAndLock(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //查询订单是否存在，以用户为条件
        Orderinfo order = orderinfoDao.findOrderByIdAndUid(user.getId(), orderId);
        if (order==null || order.getState()!=5){
            result.put("code" ,4);
            result.put("msg" ,"订单错误");
            return result;
        }
        //判断该商品是否是正常商品
        Goods goods = goodsDao.findGoodsById(order.getGId());
        if (goods.getType()!=1){
            result.put("code" ,6);
            result.put("msg" ,"该商品不能参与促销");
            return result;
        }
        //更改订单状态
        order.setState(9);
        orderinfoDao.updateOrderGuessAndState(order);

        double money = order.getPrice() * 1.6;
        //增加用户余额
        memberDao.increaseMoney(money ,user.getId());
        //插入资金明细
        Moneydetail moneydetail = new Moneydetail();
        moneydetail.setUId(user.getId());
        moneydetail.setMoney(money);
        moneydetail.setCuMoney(user.getMoney());
        moneydetail.setRemarke("中奖兑换所得");
        moneydetailDao.insertOne(moneydetail);

        result.put("code" ,3);
        result.put("msg" ,"操作成功");
        return result;
    }
}
