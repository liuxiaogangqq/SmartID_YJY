package com.dhsr.smartid.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = Logger.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("出现异常了————————————————————————————————————————————: ", ex);// 把异常信息记入日志
		System.out.println("出现异常了——————————————————————————————");
		Map<String, Object> model = new HashMap<String, Object>();
		StringPrintWriter strintPrintWriter = new StringPrintWriter();
		ex.printStackTrace(strintPrintWriter);
		System.out.println(strintPrintWriter.getString());
		model.put("ex", ex);// 将错误信息传递给view
		// return new ModelAndView(new
		// InternalResourceView("/WEB-INF/velocity/common/error.html"));
		return new ModelAndView("redirect:error.html");
		// return new ModelAndView("error.html", model);
	}
}