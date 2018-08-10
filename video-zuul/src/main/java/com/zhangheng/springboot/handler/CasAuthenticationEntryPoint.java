package com.zhangheng.springboot.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhangheng.springboot.properties.CasProperties;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/8/7.
 *
 * 自定义登录跳转逻辑
 */
@Component
public class CasAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean {

    @Autowired
    private CasProperties casProperties;

    private final static Logger logger = LoggerFactory.getLogger(CasAuthenticationEntryPoint.class);

    private ServiceProperties serviceProperties;
    private String loginUrl;
    private boolean encodeServiceUrlWithSessionId = true;

    public CasAuthenticationEntryPoint() {
    }

    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(this.loginUrl, "loginUrl must be specified");
        Assert.notNull(this.serviceProperties, "serviceProperties must be specified");
        Assert.notNull(this.serviceProperties.getService(), "serviceProperties.getService() cannot be null.");
    }

    @Override
    public  void commence(HttpServletRequest servletRequest, HttpServletResponse response, org.springframework.security.core.AuthenticationException authenticationException) throws IOException, ServletException {
        if(isAjaxRequest(servletRequest)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authenticationException.getMessage());
        }else{
            logger.info("要跳转的路径信息:"+servletRequest.getPathInfo());
            String urlEncodedService = this.createServiceUrl(servletRequest, response);
            String redirectUrl = this.createRedirectUrl(urlEncodedService);
            this.preCommence(servletRequest, response);
//            servletRequest.
//            response.sendRedirect("/login");
            PrintWriter out = response.getWriter();
            out.write(getJson(redirectUrl));
//            out.write("{code:301,redirect:"+casProperties.getCasServerLoginUrl()+"}");
            out.flush();
            out.close();
        }
    }


    protected String createServiceUrl(HttpServletRequest request, HttpServletResponse response) {
        return CommonUtils.constructServiceUrl((HttpServletRequest)null, response, this.serviceProperties.getService(), (String)null, this.serviceProperties.getArtifactParameter(), this.encodeServiceUrlWithSessionId);
    }

    protected String createRedirectUrl(String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.loginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }

    protected void preCommence(HttpServletRequest request, HttpServletResponse response) {
    }

    public final String getLoginUrl() {
        return this.loginUrl;
    }

    public final ServiceProperties getServiceProperties() {
        return this.serviceProperties;
    }

    public final void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
//
    public final void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public final void setEncodeServiceUrlWithSessionId(boolean encodeServiceUrlWithSessionId) {
        this.encodeServiceUrlWithSessionId = encodeServiceUrlWithSessionId;
    }

    protected boolean getEncodeServiceUrlWithSessionId() {
        return this.encodeServiceUrlWithSessionId;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }


    @ResponseBody
    public String getJson(String redirectUrl){
//        JSONArray jsonArray = new JSONArray();
        JSONObject jsonDate = new JSONObject();
//        for(int i = 0 ; i < list.size() ; i++){
//            Object object = list.get(i);
//            jsonArray.put(JsonMapper.toJsonString(object));
//        }
        jsonDate.put("code", 301);
        jsonDate.put("redirect", redirectUrl);
        return jsonDate.toString();
    }

}
