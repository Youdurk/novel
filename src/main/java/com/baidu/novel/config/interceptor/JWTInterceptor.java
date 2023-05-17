package com.baidu.novel.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baidu.novel.common.Constant;
import com.baidu.novel.entity.User;
import com.baidu.novel.exception.ServiceException;

import com.baidu.novel.server.serverImpl.UserServerImpl;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JWTInterceptor implements HandlerInterceptor {
    @Resource
    private UserServerImpl userServerimpl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token= request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token=request.getParameter("token");
        }
        //如果是方法
        if (handler instanceof HandlerMethod){
            //拿到方法上的AuthAccess这个注解
            AuthAccess annotation=((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            //如果这个注解不为空，则放行
            if(annotation !=null){
                return true;
            }
        }
        //执行通过
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constant.CODE_401,"无token,请重新登录");
        }
        //获取token 的userid
        String userid;
        try {
            userid= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constant.CODE_401,"token验证失败");
        }
        //根据token中的userid查询数据
        User user =userServerimpl.getById(userid);
        if (user==null){
            throw new ServiceException(Constant.CODE_401,"用户不存在，请注册");
        }
        //用户密码加签验证 token
        JWTVerifier jwtVerifier= JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw  new ServiceException(Constant.CODE_401,"token验证失败，请重新登录");
        }
        return true;
    }
}
