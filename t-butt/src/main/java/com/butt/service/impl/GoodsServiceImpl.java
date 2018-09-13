package com.butt.service.impl;

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
    public Map<String, Object> addGoods(int type ,String name ,double price ,String remarke ,MultipartFile[] file) {
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
        if (file==null || file.length>3){
            result.put("msg" ,"商品图片有误");
            return result;
        }
        try {
            //进行文件处理
            String img = null;
            String imgt = null;
            String imgtt = null;
            for (int i = 0; i < file.length; i++) {
                //获取文件的后缀名
                String s = file[i].getOriginalFilename();
                String suffixName = s.substring(s.lastIndexOf("."));
                String fileName = DateUtil.Date2TimeStamp(new Date())+suffixName;
                if (i==0){
                    img = fileName;
                }else if (i==1){
                    imgt = fileName;
                }else if (i==2){
                    imgtt = fileName;
                }
                //判断文件夹是否存在
                File dir = new File(path);
                if  (!dir.exists()  && !dir.isDirectory()) {
                    dir .mkdir();
                }
                //创建文件
                File dest = new File(path + fileName);
                file[i].transferTo(dest);
            }
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


}
