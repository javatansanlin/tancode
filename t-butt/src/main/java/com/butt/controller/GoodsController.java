package com.butt.controller;

import com.butt.entity.Goods;
import com.butt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: JavaTansanlin
 * @Description: 前端商品相关的接口
 * @Date: Created in 4:07 2018/9/14
 * @Modified By:
 */
@RestController
@RequestMapping("/mem/goods")
public class GoodsController {

    @Value("${img.goods}")
    private String imgPath;

    /** 商品的service */
    @Autowired
    private GoodsService goodsService;

    /** 查询所有的商品列表，以类型为条件 */
    @PostMapping("findAllByType")
    public List<Goods> findAllByType(int type){
        return goodsService.findAllGoods(type);
    }

    /** 根据id进入商品详情 **/
    @PostMapping("findGoodsByid")
    public Goods findGoodsByid(int id){
        return goodsService.findGoodsByid(id);
    }

    /** 根据图片名，显示商品图片 */
    @GetMapping("imgShow")
    public void imgShow(String fileName ,HttpServletResponse response){
        try {
            if (fileName != null) {
                //判断图片是否存在
                File file = new File(imgPath + fileName);
                if (file.exists()) {
                    response.setContentType("image/jpeg");
                    FileInputStream is = new FileInputStream(file);
                    if (is != null) {
                        int i = is.available(); // 得到文件大小
                        byte data[] = new byte[i];
                        is.read(data); // 读数据
                        is.close();
                        response.setContentType("image/jpeg");  // 设置返回的文件类型
                        OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                        toClient.write(data); // 输出数据
                        toClient.close();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
