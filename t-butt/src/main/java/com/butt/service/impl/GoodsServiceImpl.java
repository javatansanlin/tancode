package com.butt.service.impl;

import com.butt.util.Salt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.butt.dao.GoodsDao;
import com.butt.entity.Goods;
import com.butt.service.GoodsService;
import com.butt.util.DateUtil;

import java.io.File;
import java.util.*;

/**
 * @Author: JavaTansanlin
 * @Description: 商品的业务接口实现类
 * @Date: Created in 23:48 2018/9/13
 * @Modified By:
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    /** 获取配置文件中的文件保存路径 */
    @Value("${img.goods}")
    private String path;

    /** 商品dao */
    @Autowired
    private GoodsDao goodsDao;

    /** 增加商品 **/
    @Override
    public Map<String, Object> addGoods(int type ,String name ,double price ,String remarke ,MultipartFile file1,MultipartFile file2,MultipartFile file3) {
        Map<String ,Object> result = new HashMap<>();
        result.put("code" ,1);
        //参数判断
        if(type!=1 && type!=2){
            result.put("msg" ,"商品类型选择错误");
            return result;
        }
        if (name==null || "".equals(name.trim())){
            result.put("msg" ,"商品名称不正确");
            return result;
        }
        if (price<=0){
            result.put("msg" ,"商品价格填写有误");
            return result;
        }
        if (file1==null || file2==null || file3==null){
            result.put("msg" ,"商品图片有误");
            return result;
        }
        try {
            //进行文件处理
            String img = null;
            String imgt = null;
            String imgtt = null;
            //获取文件的后缀名
            String s = file1.getOriginalFilename();
            String suffixName1 = s.substring(s.lastIndexOf("."));
            String fileName1 = Salt.getOrderNum() +suffixName1;
            img = fileName1;
            String s2 = file2.getOriginalFilename();
            String suffixName2 = s2.substring(s2.lastIndexOf("."));
            String fileName2 = Salt.getOrderNum()+suffixName2;
            imgt = fileName2;
            String s3 = file3.getOriginalFilename();
            String suffixName3 = s3.substring(s3.lastIndexOf("."));
            String fileName3 = Salt.getOrderNum()+suffixName3;
            imgtt = fileName3;
            //判断文件夹是否存在
            File dir = new File(path);
            if  (!dir.exists()  && !dir.isDirectory()) {
                dir .mkdir();
            }
            //创建文件
            File dest = new File(path + fileName1);
            file1.transferTo(dest);
            File dest2 = new File(path + fileName2);
            file2.transferTo(dest2);
            File dest3 = new File(path + fileName3);
            file3.transferTo(dest3);

            //创建商品类
            Goods goods = new Goods();
            goods.setType(type);
            goods.setName(name);
            goods.setRemarke(remarke);
            goods.setImg(img);
            goods.setImgt(imgt);
            goods.setImgtt(imgtt);
            if (type==1){
                goods.setPrice(price);
            }else {
                goods.setIntegral(price);
            }
            //执行商品插入
            goodsDao.insertOne(goods);
            result.put("code" ,3);
            result.put("msg" ,"操作成功");
        }catch (Exception e){
            result.put("code" ,33);
            result.put("msg" ,"错误");
        }
        return result;
    }

    /** 查询所有的商品列表，以类型为条件 */
    @Override
    public List<Goods> findAllGoods(int type) {
        return goodsDao.findGoodsByType(type);
    }

    /** 根据id进入商品详情 **/
    @Override
    public Goods findGoodsByid(int id) {
        return goodsDao.findGoodsById(id);
    }

    /** 修改商品 */
    @Override
    public Map<String, Object> editGoods(Integer id, int type, String name, double price, String remarke, MultipartFile file1,MultipartFile file2,MultipartFile file3) {
        Map<String ,Object> result = new HashMap<>();
        result.put("code" ,1);
        //参数判断
        if(type!=1 && type!=2){
            result.put("msg" ,"商品类型选择错误");
            return result;
        }
        if (name==null || "".equals(name.trim())){
            result.put("msg" ,"商品名称不正确");
            return result;
        }
        if (price<=0){
            result.put("msg" ,"商品价格填写有误");
            return result;
        }
        if (file1==null || file2==null || file3==null){
            result.put("msg" ,"商品图片有误");
            return result;
        }
        //查询该商品是否存在
        Goods goods = goodsDao.findGoodsById(id);
        if (goods==null){
            result.put("code" ,2);
            result.put("msg" ,"该商品不存在");
            return result;
        }
        try {
            //进行文件处理
            String img = null;
            String imgt = null;
            String imgtt = null;
            //获取文件的后缀名
            String s = file1.getOriginalFilename();
            String suffixName1 = s.substring(s.lastIndexOf("."));
            String fileName1 = DateUtil.Date2TimeStamp(new Date())+suffixName1;
            img = fileName1;
            s = file2.getOriginalFilename();
            String suffixName2 = s.substring(s.lastIndexOf("."));
            String fileName2 = DateUtil.Date2TimeStamp(new Date())+suffixName2;
            imgt = fileName2;
            s = file3.getOriginalFilename();
            String suffixName3 = s.substring(s.lastIndexOf("."));
            String fileName3 = DateUtil.Date2TimeStamp(new Date())+suffixName3;
            imgtt = fileName3;
            //判断文件夹是否存在
            File dir = new File(path);
            if  (!dir.exists()  && !dir.isDirectory()) {
                dir .mkdir();
            }
            //创建文件
            File dest = new File(path + fileName1);
            file1.transferTo(dest);
            File dest2 = new File(path + fileName2);
            file2.transferTo(dest2);
            File dest3 = new File(path + fileName3);
            file3.transferTo(dest3);
            //创建商品类
            goods.setType(type);
            goods.setName(name);
            goods.setRemarke(remarke);
            goods.setImg(img);
            goods.setImgt(imgt);
            goods.setImgtt(imgtt);
            if (type==1){
                goods.setPrice(price);
            }else {
                goods.setIntegral(price);
            }
            //执行商品插入
            goodsDao.updateOne(goods);
            result.put("code" ,3);
            result.put("msg" ,"操作成功");
        }catch (Exception e){
            result.put("code" ,33);
            result.put("msg" ,"错误");
        }
        return result;
    }
}
