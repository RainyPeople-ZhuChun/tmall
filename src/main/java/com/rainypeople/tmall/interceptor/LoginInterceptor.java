package com.rainypeople.tmall.interceptor;

import com.rainypeople.tmall.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        HttpSession session=request.getSession();
        //获取/tmall_ssm域名
        String contextPath = session.getServletContext().getContextPath();
        //不需要登录即可访问的地址，直接放行
        String[] noNeedAuthPage=new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"
        };
        String uri=request.getRequestURI();
        //把/tmall_ssm去除
        uri=StringUtils.remove(uri,contextPath);
        //如果访问的路径是以/fore开始的
        if (uri.startsWith("/fore")){
            //把/fore前缀去除
            uri=StringUtils.substringAfterLast(uri,"/fore");
            //判断是否属于无需登录直接放行的路径，假如不是重定向登录界面，拒绝放行
            if (!Arrays.asList(noNeedAuthPage).contains(uri)){
                User user= (User) session.getAttribute("user");
                if (null==user){
                    response.sendRedirect("loginPage");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}

