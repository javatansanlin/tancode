package com.butt.service;

import com.butt.entity.Goods;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 商品的业务接口
 * @Date: Created in 23:46 2018/9/13
 * @Modified By:
 */
public interface GoodsService {
    /** 增加商品 **/
    Map<String ,Object> addGoods(int type ,String name ,double price ,String remarke ,MultipartFile[] file);
    /** 查询所有的商品列表，以类型为条件 */
    List<Goods> findAllGoods(int type);
    /** 根据id进入商品详情 **/
    Goods findGoodsByid(int id);
}
