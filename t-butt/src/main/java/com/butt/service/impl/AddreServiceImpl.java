package com.butt.service.impl;

import com.butt.dao.AddressDao;
import com.butt.dao.MemberDao;
import com.butt.entity.Address;
import com.butt.entity.Member;
import com.butt.service.AddreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: JavaTansanlin
 * @Description: 用户收货地址相关的额业务
 * @Date: Created in 16:16 2018/9/16
 * @Modified By:
 */
@Service
@Transactional
public class AddreServiceImpl implements AddreService {

    /** 用户dao */
    @Autowired
    private MemberDao memberDao;

    /** 收货地址dao */
    @Autowired
    private AddressDao addressDao;

    /** 根据用户的oid查询用户的所有收货地址 */
    @Override
    public List<Address> findAllByOid(String oid) {
        return addressDao.findAllByOid(oid);
    }

    /** 新增收货地址 */
    @Override
    public Map<String, Object> addAddre(String oid, String name, String phone, String addre) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || name==null || "".equals(name.trim()) || phone==null || "".equals(phone.trim()) || addre==null || "".equals(addre.trim()) || addre.length()>200){
            result.put("code" ,1);
            result.put("msg" ,"参数错误");
            return result;
        }
        //查询出该用户
        Member user = memberDao.findMemByOid(oid);
        if (user==null){
            result.put("code" ,2);
            result.put("msg" ,"用户错误");
            return result;
        }
        //执行插入操作
        Address address = new Address();
        address.setUId(user.getId());
        address.setName(name);
        address.setPhone(phone);
        address.setAddre(addre);
        addressDao.insertOne(address);

        result.put("code" ,3);
        result.put("msg" ,"增加成功");
        return result;
    }

    /** 修改收货地址 */
    @Override
    public Map<String, Object> editAddre(String oid, Integer id, String name, String phone, String addre) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || id==null || name==null || "".equals(name.trim()) || phone==null || "".equals(phone.trim()) || addre==null || "".equals(addre.trim()) || addre.length()>200){
            result.put("code" ,1);
            result.put("msg" ,"参数错误");
            return result;
        }
        //查询出该地址
        Address address = addressDao.findOneByOidAndID(oid, id);
        if (address==null){
            result.put("code" ,2);
            result.put("msg" ,"查找地址错误");
            return result;
        }
        //修改信息
        address.setName(name);
        address.setPhone(phone);
        address.setAddre(addre);
        addressDao.updateByID(address);

        result.put("code" ,3);
        result.put("msg" ,"修改成功");
        return result;
    }

    /** 删除收货地址 **/
    @Override
    public Map<String, Object> deleAddre(String oid, Integer id) {
        Map<String ,Object> result = new HashMap<>();
        //判断参数
        if (oid==null || id==null){
            result.put("code" ,1);
            result.put("msg" ,"参数错误");
            return result;
        }
        //查找出该记录
        Address address = addressDao.findOneByOidAndID(oid, id);
        if (address!=null){
            addressDao.deleOne(address.getId());
        }
        result.put("code" ,3);
        result.put("msg" ,"删除成功");
        return result;
    }
}
