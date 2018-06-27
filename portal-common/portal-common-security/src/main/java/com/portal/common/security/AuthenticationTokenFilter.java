package com.portal.common.security;

import com.portal.common.utils.StringHelper;
import com.mzlion.core.http.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 校验过滤器
 *
 * @author dcp
 */
public class AuthenticationTokenFilter extends GenericFilterBean {

    /**
     * 携带Token的HTTP头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Token工具类
     */
    @Autowired
    private AbstractTokenUtil jwtTokenUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE,PUT");
        resp.setHeader("Access-Control-Max-Age","3600");
        resp.setHeader("Access-Control-Allow-Headers","Authorization,Origin, X-Requested-With, Content-Type, Accept");
        resp.setHeader("","");resp.setHeader("","");


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if(httpRequest.getMethod().equals("OPTIONS")) {
            ((HttpServletResponse) response).setStatus(200);
            chain.doFilter(request, response);
            return;
        }



        String authHeader = httpRequest.getHeader(TOKEN_HEADER);

        if (authHeader == null || !authHeader.startsWith(AbstractTokenUtil.TOKEN_TYPE_BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        final String authToken = StringHelper.substring(authHeader, 7);
        String username = StringHelper.isNotBlank(authToken) ? jwtTokenUtil.getUsernameFromToken(authToken) : null;
        String ip = IPUtils.getClientIp((HttpServletRequest) request);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenUtil.validateToken(authToken, ip)) {
            UserDetails userDetails = jwtTokenUtil.getUserDetails(authToken, ip);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}