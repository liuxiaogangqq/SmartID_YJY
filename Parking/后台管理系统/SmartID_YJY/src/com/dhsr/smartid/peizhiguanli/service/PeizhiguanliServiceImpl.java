package com.dhsr.smartid.peizhiguanli.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.transaction.annotation.Transactional;

import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.AppType;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Configure;
import com.dhsr.smartid.peizhiguanli.domain.MarkType;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.PageRight;
import com.dhsr.smartid.peizhiguanli.domain.SysTerminal;
import com.dhsr.smartid.peizhiguanli.domain.SystemConfigure;
import com.dhsr.smartid.peizhiguanli.domain.SystemLog;
import com.dhsr.smartid.peizhiguanli.domain.TerminalType;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.util.DataGridModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 配置管理的Service层
 * 
 * @author qidong
 *
 */
public class PeizhiguanliServiceImpl implements PeizhiguanliService {
	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;
	

	@Override
	@Transactional
	public String selectConfigure(String Page) {
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Configure> ConfigureList = peizhiguanliMapper.selectConfigure(Page);
		for (Configure configure : ConfigureList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("Page", configure.getPage());
			row.put("Property", configure.getProperty());
			row.put("Enabled", configure.getEnabled());
			if (configure.getEnabled() == 0) {
				row.put("IsEnabled", "启用");
			} else {
				row.put("IsEnabled", "禁用");
			}
			row.put("EditName", configure.getEditName());
			row.put("InitialName", configure.getInitialName());
			row.put("Name", configure.getName());
			row.put("Length", configure.getLength());
			rows.add(row);
		}
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateConfigure(Configure configure) {
		Integer result = peizhiguanliMapper.updateConfigure(configure);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectSystemConfigure() {
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<SystemConfigure> systemConfigureList = peizhiguanliMapper.selectSystemConfigure();
		for (SystemConfigure systemConfigure : systemConfigureList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ConfigureId", systemConfigure.getConfigureId());
			row.put("ConfigureName", systemConfigure.getConfigureName());
			row.put("ConfigureValue", systemConfigure.getConfigureValue());
			rows.add(row);
		}
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectSystemConfigureById(String ConfigureId) {
		JSONObject jo = new JSONObject();
		SystemConfigure systemConfigure = peizhiguanliMapper.selectSystemConfigureById(ConfigureId);
		jo.put("ConfigureId", systemConfigure.getConfigureId());
		jo.put("ConfigureName", systemConfigure.getConfigureName());
		jo.put("ConfigureValue", systemConfigure.getConfigureValue());
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateSystemConfigure(SystemConfigure systemConfigure) {
		Integer result = peizhiguanliMapper.updateSystemConfigure(systemConfigure);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectArea(DataGridModel dgm, Area Area) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("AreaInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("AreaId", Area.getAreaId());
		map.put("AreaName", Area.getAreaName());
		map.put("Remark", Area.getRemark());
		map.put("StandbyA", Area.getStandbyA());
		map.put("StandbyB", Area.getStandbyB());
		map.put("StandbyC", Area.getStandbyC());
		map.put("StandbyD", Area.getStandbyD());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Area> AreaList = peizhiguanliMapper.selectArea(map);
		for (Area Area1 : AreaList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("AreaInnerId", Area1.getAreaInnerId());
			row.put("AreaId", Area1.getAreaId());
			row.put("AreaName", Area1.getAreaName());
			row.put("Remark", Area1.getRemark());
			row.put("StandbyA", Area1.getStandbyA());
			row.put("StandbyB", Area1.getStandbyB());
			row.put("StandbyC", Area1.getStandbyC());
			row.put("StandbyD", Area1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", peizhiguanliMapper.selectAreaTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectAreaBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "AreaInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Area> AreaList = peizhiguanliMapper.selectArea(map);
		for (Area Area1 : AreaList) {
			jo = new JSONObject();
			jo.put("id", Area1.getAreaInnerId());
			jo.put("text", Area1.getAreaName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertArea(Area Area, HttpServletRequest request) {
		Integer result = peizhiguanliMapper.insertArea(Area);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Area", Area.getAreaInnerId(), Area.getAreaId(), Area.getAreaName(), 0, null, Base64.encodeBase64String(SerializationUtils.serialize(Area)), operator.getOperatorInnerId(), sdf.format(new Date()), Area.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateArea(Area Area, HttpServletRequest request) {
		Area beforeArea = peizhiguanliMapper.selectAreaByInnerId(Area.getAreaInnerId());
		// String
		// str=Base64.encodeBase64String(SerializationUtils.serialize(beforeArea));
		// Object obj1=SerializationUtils.deserialize(Base64.decodeBase64(str));
		Integer result = peizhiguanliMapper.updateArea(Area);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Area afterArea = peizhiguanliMapper.selectAreaByInnerId(Area.getAreaInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Area", afterArea.getAreaInnerId(), afterArea.getAreaId(), afterArea.getAreaName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeArea)), Base64.encodeBase64String(SerializationUtils.serialize(afterArea)), operator.getOperatorInnerId(), sdf.format(new Date()), afterArea.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectApp(DataGridModel dgm, App app) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("AppInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		if (dgm.getSort() != null) {
			if (dgm.getSort().equals("AppTypeName")) {
				dgm.setSort("AppTypeInnerId");
			}
			if (dgm.getSort().equals("AreaName")) {
				dgm.setSort("AreaInnerId");
			}
			if (dgm.getSort().equals("MarkTypeNameList")) {
				dgm.setSort("MarkTypeList");
			}
			if (dgm.getSort().equals("TerminalTypeNameList")) {
				dgm.setSort("TerminalTypeList");
			}
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("AppId", app.getAppId());
		map.put("AppName", app.getAppName());
		map.put("AppTypeInnerId", app.getAppTypeInnerId());
		map.put("AreaInnerId", app.getAreaInnerId());
		map.put("Remark", app.getRemark());
		map.put("StandbyA", app.getStandbyA());
		map.put("StandbyB", app.getStandbyB());
		map.put("StandbyC", app.getStandbyC());
		map.put("StandbyD", app.getStandbyD());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<App> AppList = peizhiguanliMapper.selectApp(map);
		for (App app1 : AppList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("AppInnerId", app1.getAppInnerId());

			row.put("AppTypeInnerId", app1.getAppTypeInnerId());
			AppType appType = peizhiguanliMapper.selectAppTypeByInnerId(app1.getAppTypeInnerId());
			if (appType != null) {
				row.put("AppTypeName", appType.getAppTypeName());
			}

			row.put("AreaInnerId", app1.getAreaInnerId());
			Area area = peizhiguanliMapper.selectAreaByInnerId(app1.getAreaInnerId());
			if (area != null) {
				row.put("AreaName", area.getAreaName());
			}

			row.put("AppId", app1.getAppId());
			row.put("AppName", app1.getAppName());
			row.put("Remark", app1.getRemark());
			row.put("StandbyA", app1.getStandbyA());
			row.put("StandbyB", app1.getStandbyB());
			row.put("StandbyC", app1.getStandbyC());
			row.put("StandbyD", app1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", peizhiguanliMapper.selectAppTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectAppBox(Integer AppTypeInnerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "AppInnerId");
		map.put("start", 0);
		map.put("AppTypeInnerId", AppTypeInnerId);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<App> AppList = peizhiguanliMapper.selectApp(map);
		for (App app : AppList) {
			jo = new JSONObject();
			jo.put("id", app.getAppInnerId());
			jo.put("text", app.getAppName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertApp(App app, HttpServletRequest request) {
		Integer result = peizhiguanliMapper.insertApp(app);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_App", app.getAppInnerId(), app.getAppId(), app.getAppName(), 0, null, Base64.encodeBase64String(SerializationUtils.serialize(app)), operator.getOperatorInnerId(), sdf.format(new Date()), app.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateApp(App app, HttpServletRequest request) {
		App beforeApp = peizhiguanliMapper.selectAppByInnerId(app.getAppInnerId());
		Integer result = peizhiguanliMapper.updateApp(app);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			App afterApp = peizhiguanliMapper.selectAppByInnerId(app.getAppInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_App", afterApp.getAppInnerId(), afterApp.getAppId(), afterApp.getAppName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeApp)), Base64.encodeBase64String(SerializationUtils.serialize(afterApp)), operator.getOperatorInnerId(), sdf.format(new Date()), afterApp.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectAppTypeBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<AppType> AppTypeList = peizhiguanliMapper.selectAppType(map);
		for (AppType appType : AppTypeList) {
			jo = new JSONObject();
			jo.put("id", appType.getAppTypeInnerId());
			jo.put("text", appType.getAppTypeName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectMarkTypeBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<MarkType> MarkTypeList = peizhiguanliMapper.selectMarkType(map);
		for (MarkType markType : MarkTypeList) {
			jo = new JSONObject();
			jo.put("id", markType.getMarkTypeInnerId());
			jo.put("text", markType.getMarkTypeName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectTerminalTypeBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<TerminalType> TerminalTypeList = peizhiguanliMapper.selectTerminalType(map);
		for (TerminalType terminalType : TerminalTypeList) {
			jo = new JSONObject();
			jo.put("id", terminalType.getTerminalTypeInnerId());
			jo.put("text", terminalType.getTerminalTypeName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectOperator(DataGridModel dgm, Operator operator, HttpServletRequest request) {
		Operator operatorNow = (Operator) request.getSession().getAttribute("operatorSession");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("OperatorInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("OperatorInnerIdNow", operatorNow.getOperatorInnerId());
		map.put("OperatorId", operator.getOperatorId());
		map.put("admin", "admin");
		map.put("OperatorName", operator.getOperatorName());
		map.put("Remark", operator.getRemark());
		map.put("StandbyA", operator.getStandbyA());
		map.put("StandbyB", operator.getStandbyB());
		map.put("StandbyC", operator.getStandbyC());
		map.put("StandbyD", operator.getStandbyD());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Operator> OperatorList = peizhiguanliMapper.selectOperator(map);
		for (Operator operator1 : OperatorList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("OperatorInnerId", operator1.getOperatorInnerId());
			row.put("OperatorId", operator1.getOperatorId());
			row.put("OperatorName", operator1.getOperatorName());
			row.put("Password", operator1.getPassword());
			row.put("PageList", operator1.getPageList());
			String[] pageList = operator1.getPageList().split(",");
			StringBuilder PageRightNameList = new StringBuilder();
				for (String pageRightId : pageList) {
					if(!pageRightId.equals("")){
						String pageRightName = peizhiguanliMapper.selectPageRightByPageRightId(Integer.valueOf(pageRightId));
						PageRightNameList.append(pageRightName+",");
					}
				}
			row.put("PageRightNameList", PageRightNameList.toString());
			row.put("AreaList", operator1.getAreaList());
			row.put("AppList", operator1.getAppList());
			row.put("CompanyList", operator1.getCompanyList());
			/*row.put("DepartmentList", operator1.getDepartmentList());*/
			/*String[] departmentList = operator1.getDepartmentList().split(",");
			StringBuilder departmentNameList = new StringBuilder();
				for (String departmentInnerId : departmentList) {
					if(!departmentInnerId.equals("")){
						String departmentName = peizhiguanliMapper .selectDepartmentNameByDepartmentInnerId(Integer.valueOf(departmentInnerId));
						departmentNameList.append(departmentName+",");
					}
				}
			row.put("DepartmentNameList", departmentNameList.toString());*/
			/*String MerchantList = peizhiguanliMapper.selectMerchantListbyOperatorInnerId(operator1.getOperatorInnerId());
			if(MerchantList != null){
				String[] merchantList = MerchantList.split(",");
				StringBuilder sb = new StringBuilder();
				if(MerchantList.equals("") || MerchantList == null){
					row.put("MerchantNameList", "");
				}else{
					for (String merchantInnerId : merchantList) {
						Merchant merchant = xiaofeiguanliMapper.selectMerchantByInnerId(Integer.valueOf(merchantInnerId));
						if(merchant != null){
							sb.append(merchant.getMerchantName()+",");
						}
					}
					row.put("MerchantNameList", sb.toString());
				}
			}
			row.put("MerchantList", MerchantList);*/
			row.put("TableSize", operator1.getTableSize());
			row.put("Remark", operator1.getRemark());
			row.put("StandbyA", operator1.getStandbyA());
			row.put("StandbyB", operator1.getStandbyB());
			row.put("StandbyC", operator1.getStandbyC());
			row.put("StandbyD", operator1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", peizhiguanliMapper.selectOperatorTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectNowOperator(HttpServletRequest request) {
		Operator operatorNow = (Operator) request.getSession().getAttribute("operatorSession");
		JSONObject jo = new JSONObject();
		jo.put("OperatorInnerId", operatorNow.getOperatorInnerId());
		jo.put("OperatorId", operatorNow.getOperatorId());
		jo.put("OperatorName", operatorNow.getOperatorName());
		jo.put("Password", operatorNow.getPassword());
		jo.put("PageList", operatorNow.getPageList());
		jo.put("AreaList", operatorNow.getAreaList());
		jo.put("AppList", operatorNow.getAppList());
		jo.put("CompanyList", operatorNow.getCompanyList());
		jo.put("DepartmentList", operatorNow.getDepartmentList());
		jo.put("TableSize", operatorNow.getTableSize());
		jo.put("Remark", operatorNow.getRemark());
		jo.put("StandbyA", operatorNow.getStandbyA());
		jo.put("StandbyB", operatorNow.getStandbyB());
		jo.put("StandbyC", operatorNow.getStandbyC());
		jo.put("StandbyD", operatorNow.getStandbyD());
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectOperatorBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "OperatorInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Operator> OperatorList = peizhiguanliMapper.selectOperator(map);
		for (Operator operator : OperatorList) {
			jo = new JSONObject();
			jo.put("id", operator.getOperatorInnerId());
			jo.put("text", operator.getOperatorName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertOperator(Operator operator, HttpServletRequest request) {
		operator.setPassword(DigestUtils.md5Hex(operator.getPassword()));
		Integer result = peizhiguanliMapper.insertOperator(operator);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator1 = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Operator", operator.getOperatorInnerId(), operator.getOperatorId(), operator.getOperatorName(), 0, null, Base64.encodeBase64String(SerializationUtils.serialize(operator)), operator1.getOperatorInnerId(), sdf.format(new Date()), operator.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			/*ConOperatorPer conOperatorPer = new ConOperatorPer();
			conOperatorPer.setOperatorInnerId(operator.getOperatorInnerId());
			System.out.println(request.getParameter("MerchantInnerId"));
			conOperatorPer.setMerchantList(request.getParameter("MerchantInnerId"));
			xiaofeiguanliMapper.insertConOperatorPer(conOperatorPer);*/
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateOperator(Operator operator, HttpServletRequest request) {
		Operator beforeOperator = peizhiguanliMapper.selectOperatorByInnerId(operator.getOperatorInnerId());
		Integer result = peizhiguanliMapper.updateOperator(operator);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Operator afterOperator = peizhiguanliMapper.selectOperatorByInnerId(operator.getOperatorInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator1 = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Operator", afterOperator.getOperatorInnerId(), afterOperator.getOperatorId(), afterOperator.getOperatorName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeOperator)), Base64.encodeBase64String(SerializationUtils.serialize(afterOperator)), operator1.getOperatorInnerId(), sdf.format(new Date()), afterOperator.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			/*ConOperatorPer conOperatorPer = new ConOperatorPer();
			conOperatorPer.setOperatorInnerId(operator.getOperatorInnerId());
			conOperatorPer.setMerchantList(request.getParameter("MerchantInnerId"));
			Integer result2 =  peizhiguanliMapper.updateConOperatorPer(conOperatorPer);*/
			if (result1 > 0 ) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updatePWDOperator(HttpServletRequest request) {
		Integer OperatorInnerId = Integer.valueOf(request.getParameter("OperatorInnerId"));
		String Password = request.getParameter("Password");
		String PasswordNew = request.getParameter("PasswordNew");
		Operator beforeOperator = peizhiguanliMapper.selectOperatorByInnerId(OperatorInnerId);
		JSONObject jo = new JSONObject();
		if (DigestUtils.md5Hex(Password).equals(beforeOperator.getPassword()) || Password.equals(beforeOperator.getPassword())) {
			Operator operator = new Operator();
			operator.setOperatorInnerId(OperatorInnerId);
			operator.setPassword(DigestUtils.md5Hex(PasswordNew));
			Integer result = peizhiguanliMapper.updatePWDOperator(operator);
			if (result > 0) {
				Operator afterOperator = peizhiguanliMapper.selectOperatorByInnerId(operator.getOperatorInnerId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Operator operator1 = (Operator) request.getSession().getAttribute("operatorSession");
				SystemLog systemLog = new SystemLog(null, "System_Operator", afterOperator.getOperatorInnerId(), afterOperator.getOperatorId(), afterOperator.getOperatorName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeOperator)), Base64.encodeBase64String(SerializationUtils.serialize(afterOperator)), operator1.getOperatorInnerId(), sdf.format(new Date()), afterOperator.getRemark());
				Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
				if (result1 > 0) {
					jo.put("code", 200);
					jo.put("msg", "修改成功！");
				} else {
					jo.put("code", 500);
					jo.put("msg", "修改失败！");
				}
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败,原密码不正确！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectAreaAppTree(Integer AppTypeInnerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "AreaInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Area> AreaList = peizhiguanliMapper.selectArea(map);
		for (Area area : AreaList) {
			jo = new JSONObject();
			jo.put("id", "area" + area.getAreaInnerId());
			jo.put("text", area.getAreaName());

			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "AppInnerId");
			map.put("start", 0);
			map.put("AreaInnerId", area.getAreaInnerId());
			map.put("AppTypeInnerId", AppTypeInnerId);
			JSONArray appJa = new JSONArray();
			JSONObject appJo = new JSONObject();
			List<App> AppList = peizhiguanliMapper.selectApp(map);
			for (App app : AppList) {
				appJo = new JSONObject();
				appJo.put("id", "app" + app.getAppInnerId());
				appJo.put("text", app.getAppName());
				appJa.add(appJo);
			}
			jo.put("children", appJa);
			if (AppList.size() > 0) {
				jo.put("state", "closed");
			} else {
				jo.put("state", "");
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectUserType(DataGridModel dgm, UserType userType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("UserTypeInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("UserTypeId", userType.getUserTypeId());
		map.put("UserTypeName", userType.getUserTypeName());
		map.put("Remark", userType.getRemark());
		map.put("StandbyA", userType.getStandbyA());
		map.put("StandbyB", userType.getStandbyB());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<UserType> UserTypeList = peizhiguanliMapper.selectUserType(map);
		for (UserType userType1 : UserTypeList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserTypeInnerId", userType1.getUserTypeInnerId());
			row.put("UserTypeId", userType1.getUserTypeId());
			row.put("UserTypeName", userType1.getUserTypeName());
			row.put("AreaList", userType1.getAreaList());
			row.put("AppList", userType1.getAppList());
			row.put("Remark", userType1.getRemark());
			row.put("StandbyA", userType1.getStandbyA());
			row.put("StandbyB", userType1.getStandbyB());
			rows.add(row);
		}
		jo.put("total", peizhiguanliMapper.selectUserTypeTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectUserTypeBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "UserTypeInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<UserType> UserTypeList = peizhiguanliMapper.selectUserType(map);
		for (UserType userType : UserTypeList) {
			jo = new JSONObject();
			jo.put("id", userType.getUserTypeInnerId());
			jo.put("text", userType.getUserTypeName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertUserType(UserType userType, HttpServletRequest request) {
		Integer result = peizhiguanliMapper.insertUserType(userType);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_UserType", userType.getUserTypeInnerId(), userType.getUserTypeId(), userType.getUserTypeName(), 0, null, Base64.encodeBase64String(SerializationUtils.serialize(userType)), operator.getOperatorInnerId(), sdf.format(new Date()), userType.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateUserType(UserType userType, HttpServletRequest request) {
		UserType beforeUserType = peizhiguanliMapper.selectUserTypeByInnerId(userType.getUserTypeInnerId());
		Integer result = peizhiguanliMapper.updateUserType(userType);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			UserType afterUserType = peizhiguanliMapper.selectUserTypeByInnerId(userType.getUserTypeInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_UserType", afterUserType.getUserTypeInnerId(), afterUserType.getUserTypeId(), afterUserType.getUserTypeName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeUserType)), Base64.encodeBase64String(SerializationUtils.serialize(afterUserType)), operator.getOperatorInnerId(), sdf.format(new Date()), afterUserType.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String pageRightTree() {
		PageRight pageRightTn = new PageRight();
		pageRightTn.setUpId(0);
		List<PageRight> pageRightList = peizhiguanliMapper.selectPageRight(pageRightTn);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		for (PageRight pageRight : pageRightList) {
			jo = new JSONObject();
			jo.put("id", pageRight.getPageRightId());
			jo.put("text", pageRight.getPageRightName());
			Integer size = nextRightTree(jo, pageRight.getPageRightId());
			if (size > 0) {
				jo.put("state", "closed");
			} else {
				jo.put("state", "");
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	private Integer nextRightTree(JSONObject jo, Integer UpId) {
		PageRight pageRightTn = new PageRight();
		pageRightTn.setUpId(UpId);
		List<PageRight> pageRightList = peizhiguanliMapper.selectPageRight(pageRightTn);
		JSONArray pageJa = new JSONArray();
		JSONObject pageJo = new JSONObject();
		for (PageRight pageRight : pageRightList) {
			pageJo = new JSONObject();
			pageJo.put("id", pageRight.getPageRightId());
			pageJo.put("text", pageRight.getPageRightName());
			Integer size = nextRightTree(pageJo, pageRight.getPageRightId());
			if (size > 0) {
				pageJo.put("state", "closed");
			} else {
				pageJo.put("state", "");
			}
			pageJa.add(pageJo);
		}
		jo.put("children", pageJa);
		return pageRightList.size();
	}

	@Override
	@Transactional
	public String selectSysTerminal(DataGridModel dgm, SysTerminal SysTerminal) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("SysTerminalInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("SysTerminalId", SysTerminal.getSysTerminalId());
		map.put("SysTerminalName", SysTerminal.getSysTerminalName());
		map.put("AppInnerId", SysTerminal.getAppInnerId());
		map.put("AreaInnerId", SysTerminal.getAreaInnerId());
		map.put("TerminalTypeInnerId", SysTerminal.getTerminalTypeInnerId());
		map.put("Remark", SysTerminal.getRemark());
		map.put("StandbyA", SysTerminal.getStandbyA());
		map.put("StandbyB", SysTerminal.getStandbyB());
		map.put("StandbyC", SysTerminal.getStandbyC());
		map.put("StandbyD", SysTerminal.getStandbyD());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<SysTerminal> SysTerminalList = peizhiguanliMapper.selectSysTerminal(map);
		for (SysTerminal SysTerminal1 : SysTerminalList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("SysTerminalInnerId", SysTerminal1.getSysTerminalInnerId());
			row.put("SysTerminalId", SysTerminal1.getSysTerminalId());
			row.put("SysTerminalName", SysTerminal1.getSysTerminalName());
			row.put("AppInnerId", SysTerminal1.getAppInnerId());
			App app = peizhiguanliMapper.selectAppByInnerId(SysTerminal1.getAppInnerId());
			if (app != null) {
				row.put("AppName", app.getAppName());
			}

			row.put("AreaInnerId", SysTerminal1.getAreaInnerId());
			Area area = peizhiguanliMapper.selectAreaByInnerId(SysTerminal1.getAreaInnerId());
			if (area != null) {
				row.put("AreaName", area.getAreaName());
			}

			row.put("TerminalTypeInnerId", SysTerminal1.getTerminalTypeInnerId());
			TerminalType terminalType = peizhiguanliMapper.selectTerminalTypeByInnerId(SysTerminal1.getTerminalTypeInnerId());
			if (terminalType != null) {
				row.put("TerminalTypeName", terminalType.getTerminalTypeName());
			}

			row.put("ComId", SysTerminal1.getComId());
			row.put("ComSerials", SysTerminal1.getComSerials());
			row.put("Remark", SysTerminal1.getRemark());
			row.put("StandbyA", SysTerminal1.getStandbyA());
			row.put("StandbyB", SysTerminal1.getStandbyB());
			row.put("StandbyC", SysTerminal1.getStandbyC());
			row.put("StandbyD", SysTerminal1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", peizhiguanliMapper.selectSysTerminalTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectSysTerminalBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "SysTerminalInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<SysTerminal> SysTerminalList = peizhiguanliMapper.selectSysTerminal(map);
		for (SysTerminal SysTerminal : SysTerminalList) {
			jo = new JSONObject();
			jo.put("id", SysTerminal.getSysTerminalInnerId());
			jo.put("text", SysTerminal.getSysTerminalName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertSysTerminal(SysTerminal SysTerminal, HttpServletRequest request) {
		App App = peizhiguanliMapper.selectAppByInnerId(SysTerminal.getAppInnerId());
		SysTerminal.setAreaInnerId(App.getAreaInnerId());
		Integer result = peizhiguanliMapper.insertSysTerminal(SysTerminal);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SystemLog systemLog = new SystemLog(null, "System_Terminal", SysTerminal.getSysTerminalInnerId(), SysTerminal.getSysTerminalId(), SysTerminal.getSysTerminalName(), 0, null, Base64.encodeBase64String(SerializationUtils.serialize(SysTerminal)), operator.getOperatorInnerId(), sdf.format(new Date()), SysTerminal.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateSysTerminal(SysTerminal SysTerminal, HttpServletRequest request) {
		App App = peizhiguanliMapper.selectAppByInnerId(SysTerminal.getAppInnerId());
		SysTerminal.setAreaInnerId(App.getAreaInnerId());
		SysTerminal beforeSysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(SysTerminal.getSysTerminalInnerId());
		Integer result = peizhiguanliMapper.updateSysTerminal(SysTerminal);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			SysTerminal afterSysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(SysTerminal.getSysTerminalInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "System_Terminal", afterSysTerminal.getSysTerminalInnerId(), afterSysTerminal.getSysTerminalId(), afterSysTerminal.getSysTerminalName(), 1, Base64.encodeBase64String(SerializationUtils.serialize(beforeSysTerminal)), Base64.encodeBase64String(SerializationUtils.serialize(afterSysTerminal)), operator.getOperatorInnerId(), sdf.format(new Date()), afterSysTerminal.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
			if (result1 > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
			jo.put("code", 200);
			jo.put("msg", "修改成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "修改失败！");
		}
		return jo.toString();
	}
}
