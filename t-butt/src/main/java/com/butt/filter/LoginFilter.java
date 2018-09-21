package com.butt.filter;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: JavaTansanlin
 * @Description: 登陆拦截器
 * @Date: Created in 9:01 2018/9/7
 * @Modified By:
 */
//@WebFilter(filterName = "loginFilter",urlPatterns = {"/sys/*"})
public class LoginFilter implements Filter {


    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/sys/login","/system/login.html",".css",".js",".png",".jpg",".ico"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        System.out.println("filter url:"+uri);
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);

        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if(session!=null){
                Object o = session.getAttribute("user");
                if (o!=null){
                    filterChain.doFilter(request, response);
                }else {
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    response.sendRedirect(request.getContextPath()+"/system/login.html");
                    return;
                }
            }else{
                //重定向到登录页(需要在static文件夹下建立此html文件)
                response.sendRedirect(request.getContextPath()+"/system/login.html");
                return;
            }
        }
    }

    /**
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     * @param uri
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(uri.endsWith(includeUrl)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
