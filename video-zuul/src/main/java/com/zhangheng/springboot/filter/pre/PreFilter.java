package com.zhangheng.springboot.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zhangheng.springboot.controller.VUserInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/7/23.
 *
 * 请求路由之前被过滤 是否存在accessToken 存在就通过 不存在则过滤
 *
 */
public class PreFilter extends ZuulFilter{


    //日志
    private final static Logger logger = LoggerFactory.getLogger(PreFilter.class);

    /**
     * 返回一个字符代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型。
     * pre:可以在请求被路由之前调用。
     * routing:在路由请求时候被调用。
     * post:在routing和error过滤器之后被调用。
     * error:处理请求时发生错误被调用。
     * @return
     */
    @Override
    public String filterType() {

        logger.info("过滤前被调用!!!");

        return "pre";
    }

    /**
     * 通过int定义过滤器执行的顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否执行，从而该方法相当于是一个过滤器的开关。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {

            //设置不过滤该请求。并且返回错误码
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }

        return null;
    }

}
