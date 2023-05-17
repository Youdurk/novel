package com.baidu.novel.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baidu.novel.entity.User;
import com.baidu.novel.server.serverImpl.UserServerImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils  {
        private  static UserServerImpl staticuserserveimpl;
        @Resource
        private UserServerImpl userServerimpl;
        @PostConstruct
        public void setUserServerimpl(){staticuserserveimpl=userServerimpl;}
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId) //将 user id保存到token里面，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时后token过期
                .sign(Algorithm.HMAC256(sign));//以password 作为token的密钥
    }
//
//    /**
//     * 获当前登录的用户信息
//     * @return
//     */
//    public static User getCurrentUser(){
//        try {
//
//            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            //获取token
//            String token=request.getHeader("token");
//            if (StrUtil.isNotBlank(token)){
//                //// 获取 token 中的 user
//                String userId=JWT.decode(token).getAudience().get(0);
//                //获取登录用户的信息
//                return   staticuserserveimpl.getById(Integer.valueOf(userId));
//            }
//        }catch (Exception e){
//            return  null;
//        }
//        return null;
//    }


}
