package com.dst.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(dispatcherTypes= {DispatcherType.REQUEST, DispatcherType.FORWARD}, urlPatterns = {"/WEB-INF/web/*","/server/*"})
public class SessionRestrictionFilter implements Filter 
{
	public static final String ATTR_SESSION = "session";
	
	public static final String VIEW1 = "/LoginService?service=login&lang=en";
	
	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
		System.out.println("init session...");
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException 
	{
		HttpServletRequest request   = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute(ATTR_SESSION) == null)
		{
			// REDIRECTION
			request.getRequestDispatcher(VIEW1).forward(request, response);
		} 
		else
		{
			// REDIRECTION AVEC SESSION
			arg2.doFilter( request, response );
		}
	}
	
	@Override
	public void destroy() 
	{
		System.out.println("destroy session...");
	}
}