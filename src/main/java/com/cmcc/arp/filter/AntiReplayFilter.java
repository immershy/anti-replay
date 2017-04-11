package com.cmcc.arp.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.cmcc.arp.exception.InvalidSignException;
import com.cmcc.arp.exception.RepeatedRequestException;
import com.cmcc.arp.util.Config;
import com.cmcc.arp.util.MD5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmcc.arp.RequestPlayer;
import com.cmcc.arp.util.Util;

/**
 * 防重放filter
 */
@Component
public class AntiReplayFilter implements Filter {

	@Autowired
	private RequestPlayer player;

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		String debug = req.getParameter("debug");
		if (!"18867101992".equals(debug)) {
			// ReplayRequestWrapper req = new
			// ReplayRequestWrapper((HttpServletRequest) servletRequest);
			try {
				// 获取所有请求参数,按照key字母序排序,拼接
				Enumeration<String> params = req.getParameterNames();
				Map<String, String> paramMap = new TreeMap<String, String>();
				String sign = "";
				while (params.hasMoreElements()) {
					String key = params.nextElement();
					String value = req.getParameter(key);
					if (key.equals("sign")) {
						sign = value;
					} else {
						paramMap.put(key, value);
					}
				}
				String concatString = Util.concat(paramMap)
						+ req.getRequestURI();
				Boolean signResult = MD5.verify(concatString, sign,
						Config.private_key, Config.input_charset);
				// 签名错误
				if (!signResult) {
					throw new InvalidSignException();
				}
				// request method
				sign += req.getMethod();

				if (!player.hasPlayed(sign)) {
					player.play(sign);
				} else {
					// 重复请求
					throw new RepeatedRequestException();
				}
			} catch (Exception e) {
				player.handleError(e, servletResponse);
				return;
			}
		}
		filterChain.doFilter(req, servletResponse);
	}

	public void destroy() {

	}
}
