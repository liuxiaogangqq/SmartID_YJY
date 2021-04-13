package com.dhsr.smartid.tingchexitong.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dhsr.smartid.base.web.BaseController;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.tingchexitong.domain.CarInfo;
import com.dhsr.smartid.tingchexitong.domain.CarType;
import com.dhsr.smartid.tingchexitong.domain.Holiday;
import com.dhsr.smartid.tingchexitong.domain.ParkInfo;
import com.dhsr.smartid.tingchexitong.domain.PassRules;
import com.dhsr.smartid.tingchexitong.service.TingcheService;
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.util.HttpClintUtil;
import com.dhsr.smartid.util.LogInfo;

@Controller
public class TingcheController extends BaseController {
	
	@Resource
	private TingcheService tingcheService;
	
	private Boolean checkJurisdiction(Operator operator, String pageRightId) {
		Boolean result = false;
		if(operator.getOperatorId().equals("admin")){
			return true;
		}else{
			String[] PageList = operator.getPageList().split(",");
			for (String Page : PageList) {
				if (Page.indexOf(pageRightId) == 0) {
					result = true;
					break;
				}
			}
			return result;
		}
	}
	
	@RequestMapping(value = "/tccmain", method = RequestMethod.GET)
	public String rsywmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "8");
			if (result) {
				return "/tcxt/tccmain";
			} else {
				model.put("noSystem", "您没有进入停车管理系统的权限");
				return "/index";
			}
		} else {
			model.put("backLogin", "login.html");
			return "/tcxt/tccmain";
		}
	}

	@RequestMapping(value = "/tccright", method = RequestMethod.GET)
	public String rsywright() {
		return "/tcxt/tccright";
	}

	@RequestMapping(value = "/tccsidebar", method = RequestMethod.GET)
	public String rsywsidebar() {
		return "/tcxt/tccsidebar";
	}

	@RequestMapping(value = "/tcctop", method = RequestMethod.GET)
	public String rsywtop() {
		return "/tcxt/tcctop";
	}
	
	// 停车场管理界面
	@RequestMapping(value = "/parkInfo", method = RequestMethod.GET)
	public String parkInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "801");
			if (result) {
				return "/tcxt/parkInfo";
			} else {
				model.put("noPage", "您没有进入停车场 管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	// 车辆信息界面
	@RequestMapping(value = "/userCarInfo", method = RequestMethod.GET)
	public String userCarInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "802");
			if (result) {
				return "/tcxt/carInfo";
			} else {
				model.put("noPage", "您没有进入车辆信息管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	// 车辆信息导入界面
	@RequestMapping(value = "/carExcel", method = RequestMethod.GET)
	public String carExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "802");
			if (result) {
				return "/tcxt/carExcel";
			} else {
				model.put("noPage", "您没有进入车辆信息管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	// 车辆类型界面
	@RequestMapping(value = "/carType", method = RequestMethod.GET)
	public String carType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "803");
			if (result) {
				return "/tcxt/carType";
			} else {
				model.put("noPage", "您没有进入车辆类型管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 车辆类型界面
	@RequestMapping(value = "/passRules", method = RequestMethod.GET)
	public String passRules(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "804");
			if (result) {
				return "/tcxt/passRules";
			} else {
				model.put("noPage", "您没有进入通行规则管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 节假日管理界面
	@RequestMapping(value = "/holiday", method = RequestMethod.GET)
	public String holiday(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "805");
			if (result) {
				return "/tcxt/holiday1";
			} else {
				model.put("noPage", "您没有进入节假日管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 预约车管理界面
	@RequestMapping(value = "/yuyueche", method = RequestMethod.GET)
	public String yuyueche(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "810");
			if (result) {
				return "/tcxt/yuyueche";
			} else {
				model.put("noPage", "您没有进入预约车管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 固定车通行记录
	@RequestMapping(value = "/gudingchejilu", method = RequestMethod.GET)
	public String gudingchejilu(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "806");
			if (result) {
				return "/tcxt/gudingchejilu";
			} else {
				model.put("noPage", "您没有进入固定车通行记录的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 预约车通行记录
	@RequestMapping(value = "/yuyuechejilu", method = RequestMethod.GET)
	public String yuyuechejilu(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "807");
			if (result) {
				return "/tcxt/yuyuechejilu";
			} else {
				model.put("noPage", "您没有进入预约车通行记录的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	// 特殊车通行记录
	@RequestMapping(value = "/teshuchejilu", method = RequestMethod.GET)
	public String teshuchejilu(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "809");
			if (result) {
				return "/tcxt/teshuchejilu";
			} else {
				model.put("noPage", "您没有进入特殊车通行记录的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	// 异常记录查询界面
	@RequestMapping(value = "/yichangjilu", method = RequestMethod.GET)
	public String yichangjilu(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "808");
			if (result) {
				return "/tcxt/yichangjilu";
			} else {
				model.put("noPage", "您没有进入异常车辆记录的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 异常记录查询界面
	@RequestMapping(value = "/xiaofeiTJ", method = RequestMethod.GET)
	public String xiaofeiTJ(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "813");
			if (result) {
				return "/tcxt/conLog";
			} else {
				model.put("noPage", "您没有进入预约消费记录的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 所有车辆通行记录界面
	@RequestMapping(value = "/chargeRecord", method = RequestMethod.GET)
	public String chargeRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "811");
			if (result) {
				return "/tcxt/chargeRecord";
			} else {
				model.put("noPage", "您没有进入车辆收费记录的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	
	// 消费记录统计界面
	@RequestMapping(value = "/contcStatistics", method = RequestMethod.GET)
	public String conStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "812");
			if (result) {
				return "/tcxt/conStatistics";
			} else {
				model.put("noPage", "您没有进入消费记录统计的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	// 消费记录统计界面
	@RequestMapping(value = "/parkNumber", method = RequestMethod.GET)
	public String parkNumber(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (operator != null) {
			Boolean result = checkJurisdiction(operator, "813");
			if (result) {
				return "/tcxt/parkNumber";
			} else {
				model.put("noPage", "您没有进入车位数量管理的权限！");
				return "/tcxt/tccmain";
			}
		} else {
			return "redirect:tccmain.html";
		}
	}
	
	/**
	 *  查询停车场方法，在前台显示
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkInfo/select", method = RequestMethod.POST)
	public void selectParkInfo(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectParkInfo(dgm, parkInfo);
		renderText(response, result);
	}
	
	/**
	 * 新增停车场信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkInfo/insert", method = RequestMethod.POST)
	public void insertParkInfo(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertParkInfo(parkInfo);
		renderText(response, result);
	}
	
	/**
	 * 更新停车场信息
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/parkInfo/update", method = RequestMethod.POST)
	public void updateParkInfo(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateParkInfo(parkInfo);
		renderText(response, result);
	}
	
	/**
	 * 查询车辆信息
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/userCarInfo/select", method = RequestMethod.POST)
	public void selectuserCarInfo(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectuserCarInfo(dgm,request);
		renderText(response, result);
	}
	
	/**
	 * 查询车辆类型，界面显示
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/carType/select", method = RequestMethod.POST)
	public void selectCarType(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectCarType(dgm,request);
		renderText(response, result);
	}
	
	/**
	 * 新增车辆类型
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/carType/insert", method = RequestMethod.POST)
	public void insertCarType(HttpServletRequest request, HttpServletResponse response, CarType carType,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertCarType(carType);
		renderText(response, result);
	}
	
	/**
	 * 修改车辆类型
	 * @param request
	 * @param response
	 * @param carType
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/carType/update", method = RequestMethod.POST)
	public void updateCarType(HttpServletRequest request, HttpServletResponse response, CarType carType,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateCarType(carType);
		renderText(response, result);
	}
	
	/**
	 * 查询车辆类型，在下拉框显示
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/carTypeBox/select", method = RequestMethod.POST)
	public void selectCarTypeBox(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectCarTypeBox(dgm,request);
		renderText(response, result);
	}
	
	/**
	 * 新增车辆信息
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/carInfo/insert", method = RequestMethod.POST)
	public void insertCarInfo(HttpServletRequest request, HttpServletResponse response, CarInfo carInfo,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertCarInfo(carInfo,request);
		renderText(response, result);
	}
	
	/**
	 * 修改车辆信息
	 * @param request
	 * @param response
	 * @param carInfo
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/carInfo/update", method = RequestMethod.POST)
	public void updateCarInfo(HttpServletRequest request, HttpServletResponse response, CarInfo carInfo,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateCarInfo(carInfo,request);
		renderText(response, result);
	}
	
	/**
	 * 新增通行规则
	 * @param request
	 * @param response
	 * @param passRules
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/passRules/insert", method = RequestMethod.POST)
	public void insertPassRules(HttpServletRequest request, HttpServletResponse response, PassRules passRules,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertPassRules(passRules,request);
		renderText(response, result);
	}
	
	/**
	 * 修改通行规则
	 * @param request
	 * @param response
	 * @param passRules
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/passRules/update", method = RequestMethod.POST)
	public void updatePassRules(HttpServletRequest request, HttpServletResponse response, PassRules passRules,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updatePassRules(passRules,request);
		renderText(response, result);
	}
	
	/**
	 * 查询通行规则
	 * @param request
	 * @param response
	 * @param dgm
	 * @param modelMap
	 * @param parkInfo
	 * @throws IOException
	 */
	@RequestMapping(value = "/passRules/select", method = RequestMethod.POST)
	public void selectPassRules(HttpServletRequest request, HttpServletResponse response, DataGridModel dgm,
			ModelMap modelMap, ParkInfo parkInfo) throws IOException {
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectPassRules(dgm,request);
		renderText(response, result);
	}
	
	/**
	 * 查询节假日
	 * @param request
	 * @param dgm
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/attendanceHoliday/select",method=RequestMethod.POST)
	public void attendanceHolidayInfo(HttpServletRequest request, DataGridModel dgm,HttpServletResponse response, ModelMap model){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectAttendanceHoliday(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "attendanceHoliday/select", e, "查询考勤节假日异常");
		}
		
	}
	/**
	 * 插入节假日
	 * @param request
	 * @param dgm
	 * @param attendance_Holiday
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/attendanceHoliday/insert",method=RequestMethod.POST)
	public void attendanceGroupInsert(HttpServletRequest request, DataGridModel dgm,Holiday holiday,HttpServletResponse response, ModelMap model){
		
		
		
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertAttendanceHoliday(request, holiday);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "attendanceGroup/insert", e, "添加考勤节假日异常");
		}
		
	}
	/**
	 * 修改节假日
	 * @param request
	 * @param dgm
	 * @param attendance_Holiday
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/attendanceHoliday/update",method=RequestMethod.POST)
	public void attendanceHolidayUpdate(HttpServletRequest request, DataGridModel dgm,Holiday holiday,HttpServletResponse response, ModelMap model){

		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateAttendanceHoliday(request, holiday);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "attendanceHoliday/update", e, "修改考勤节假日异常");
		}
		
	}
	/**
	 * 删除考勤组
	 * @param request
	 * @param dgm
	 * @param GroupInnerId
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/attendanceHoliday/delete",method=RequestMethod.POST)
	public void attendanceHolidayDelete(HttpServletRequest request, DataGridModel dgm,Integer HolidayInnerId,HttpServletResponse response, ModelMap model){

		response.setCharacterEncoding("utf-8");
		String result = tingcheService.delelteAttendanceHoliday(request, HolidayInnerId);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "attendanceHoliday/delete", e, "删除考勤节假日异常");
		}
	}
	
	/**
	 * 存储车辆进出记录
	 * @param request
	 * @param response
	 * @param model
	 */
	/*@RequestMapping(value = "/carRecord",method=RequestMethod.POST)
	public void insertCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertCarRecord(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "carRecord", e, "接收推送的通行记录");
		}
	}*/
	
	/**
	 * 查询预约车辆信息
	 * @param request
	 * @param response
	 * @param model
	 * @param dgm
	 */
	@RequestMapping(value = "/ReservationCar/select",method=RequestMethod.POST)
	public void selectReservationCar(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectReservationCar(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "ReservationCar", e, "查询预约车辆信息");
		}
	}
	
	/**
	 * 查询固定车通行记录
	 * @param request
	 * @param response
	 * @param model
	 * @param dgm
	 */
	@RequestMapping(value = "/GDCarRecord/select",method=RequestMethod.POST)
	public void selectGDCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectGDCarRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "ReservationCar", e, "查询预约车辆信息");
		}
	}
	
	/**
	 * 查询特殊车通行记录
	 * @param request
	 * @param response
	 * @param model
	 * @param dgm
	 */
	@RequestMapping(value = "/TSCarRecord/select",method=RequestMethod.POST)
	public void selectTSCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectTSCarRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "ReservationCar", e, "查询特殊车辆通行记录信息");
		}
	}
	
	/**
	 * 查询固定车通行记录
	 * @param request
	 * @param response
	 * @param model
	 * @param dgm
	 */
	@RequestMapping(value = "/YYCarRecord/select",method=RequestMethod.POST)
	public void selectYYCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectYYCarRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "ReservationCar", e, "查询预约车辆信息");
		}
	}
	
	
	@RequestMapping(value = "/CarRecord",method=RequestMethod.POST)
	public void CarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectYYCarRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "ReservationCar", e, "查询预约车辆信息");
		}
	}
	
	@RequestMapping(value = "/StopCharging/update",method=RequestMethod.POST)
	public void StopCharging(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.StopCharging(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "提前停止预约");
		}
	}
	
	@RequestMapping(value = "/YCCarRecord/select",method=RequestMethod.POST)
	public void selectYCCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectYCChargeRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "异常收费记录查询");
		}
	}
	
	@RequestMapping(value = "/YCchargeRecordInfo/update",method=RequestMethod.POST)
	public void updateYCchargeRecordInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateYCchargeRecordInfo(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/CarconLog/select",method=RequestMethod.POST)
	public void selectCarconLog(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectCarconLog(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/SYYCarRecord/select",method=RequestMethod.POST)
	public void selectSYYCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectSYYCarRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/SLSCarRecord/select",method=RequestMethod.POST)
	public void selectSGDCarRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectSLSCarRecord(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/carStopShengyu/select",method=RequestMethod.POST)
	public void selectcarStopShengyu(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectcarStopShengyu(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/CarInfoExcel/select",method=RequestMethod.POST)
	public void selectCarInfoExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectCarInfoExcel(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/YuyuecheExcel/select",method=RequestMethod.POST)
	public void selectYuyuecheExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectYuyuecheExcel(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "处理异常收费记录");
		}
	}
	
	@RequestMapping(value = "/Visitor_OrderRecord/insert",method=RequestMethod.POST)
	public void insertVisitor_OrderRecord(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.insertVisitor_OrderRecord(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "StopCharging", e, "新增临时车预约记录");
		}
	}
	
	@RequestMapping(value = "/chargeRecordInfo/select",method=RequestMethod.POST)
	public void selectChargeRecordInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectChargeRecordInfo(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "查询收费记录");
		}
	}
	
	@RequestMapping(value = "/carInfoTime/update",method=RequestMethod.POST)
	public void updateCarInfoTime(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateCarInfoTime(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "批量修改车辆时间信息");
		}
	}
	
	@RequestMapping(value = "/tcConStatistics/select",method=RequestMethod.POST)
	public void selectTcConStatistics(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectTcConStatistics(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "批量修改车辆时间信息");
		}
	}
	
	@RequestMapping(value = "/showCarExcel",method=RequestMethod.POST)
	public void showCarExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.showCarExcel(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "批量修改车辆时间信息");
		}
	}
	
	@RequestMapping(value = "/chargeRecordInfoExcel/select",method=RequestMethod.POST)
	public void selectChargeRecordInfoExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectChargeRecordInfoExcel(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "导出车辆收费信息表");
		}
	}
	
	@RequestMapping(value = "/VisitorState/update",method=RequestMethod.POST)
	public void updateVisState(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateVisState(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "修改预约记录状态");
		}
	}
	
	@RequestMapping(value = "/ParkNumberDate/select",method=RequestMethod.POST)
	public void selectParkNumberDate(HttpServletRequest request, HttpServletResponse response, ModelMap model,DataGridModel dgm) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.selectParkNumberDate(request,dgm);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "查询一个月内车位数量");
		}
	}
	
	@RequestMapping(value = "/parkDateNumber/update",method=RequestMethod.POST)
	public void updateParkDateNumber(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("utf-8");
		String result = tingcheService.updateParkDateNumber(request);
		try {
			renderText(response, result);
		} catch (IOException e) {
			LogInfo.logError(TingcheController.class, "chargeRecordInfo", e, "修改车位数量");
		}
	}
	
	
}
