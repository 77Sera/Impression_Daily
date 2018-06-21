package com.example.impressiondaily.Interceptor;

import com.example.impressiondaily.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception{
        String uri = request.getRequestURI();
        User sessionUser = (User)request.getSession().getAttribute("session_user");
        User sessionAdmin = (User)request.getSession().getAttribute("session_admin");
        System.out.println(uri); //测试语句

        if(uri.equals("/error")){
            response.sendRedirect("/404");
            return true;
        }

        if(uri.equals("/")){
            response.sendRedirect("/index");
            return false;
        }

        if(sessionAdmin != null){
            return true;
        }

        // 这里如果使用 uri == "xx"的话，会无法正确判断相等，所以用.equals
        if( uri.equals("/index") || uri.equals("/admin/index") ){
            if( sessionUser == null ){
                response.sendRedirect("/login");
                return false;
            }
        }else if( uri.equals("/login") || uri.equals("/register") ){
            if(sessionUser != null){
                response.sendRedirect("/index");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object o,
                           ModelAndView modelAndView) throws Exception{

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object o,
                                Exception e) throws Exception{

    }
}
