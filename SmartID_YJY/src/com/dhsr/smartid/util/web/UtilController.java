package com.dhsr.smartid.util.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dhsr.smartid.base.web.BaseController;
import com.dhsr.smartid.util.service.UtilService;

/**
 * 公用方法Web层，一些公用方法
 * 
 * @author qidong
 *
 */
@Controller
public class UtilController extends BaseController {
	@Resource
	private UtilService utilService;

	// 错误提示页面
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "/common/error";
	}

	// 404提示页面
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String notFound() {
		return "/404";
	}

	// 校验数据唯一，统一调用此方法
	@RequestMapping(value = "/remoteColumn", method = RequestMethod.POST)
	public void remoteId(HttpServletRequest request, HttpServletResponse response, Integer InnerId, String Column, String ColumnName, ModelMap model) throws IOException {
		response.setCharacterEncoding("UTF-8");
		boolean result = utilService.remoteColumn(InnerId, Column, ColumnName);
		try {
			renderText(response, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 删除数据（软删除）统一调用此方法
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Integer InnerId, String TableName) throws IOException {

		response.setCharacterEncoding("utf-8");
		String result = utilService.delete(InnerId, TableName, request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
