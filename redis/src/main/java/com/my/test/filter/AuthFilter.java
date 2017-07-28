package com.my.test.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.my.test.redis.JedisClient;
import com.my.test.redis.JedisClientCluster;


public class AuthFilter implements Filter {

	
	private JedisClient jedisClient;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		chain.doFilter(request, response);
		response.setContentType("application/json;charset=UTF-8");
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();


	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// 从servlet上下文获取Spring容器
		WebApplicationContext app = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		jedisClient = app.getBean("jedisClientCluster",
				JedisClientCluster.class);
		System.out.println(jedisClient);

	}

}
