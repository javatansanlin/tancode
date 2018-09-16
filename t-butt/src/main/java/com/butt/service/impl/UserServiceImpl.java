package com.butt.service.impl;

import com.butt.dao.MemberDao;
import com.butt.entity.Member;
import com.butt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
}
