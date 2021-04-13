package com.dhsr.smartid.util.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.transaction.annotation.Transactional;

import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.SysTerminal;
import com.dhsr.smartid.peizhiguanli.domain.SystemLog;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.tingchexitong.dao.TingcheMapper;
import com.dhsr.smartid.tingchexitong.domain.CarType;
import com.dhsr.smartid.tingchexitong.domain.ParkInfo;
import com.dhsr.smartid.tingchexitong.domain.PassRules;
import com.dhsr.smartid.util.dao.UtilMapper;

import net.sf.json.JSONObject;

/**
 * 公用方法Service层，一些公用方法
 * 
 * @author qidong
 *
 */
public class UtilServiceImpl implements UtilService {

	@Resource
	private UtilMapper utilMapper;

	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;

	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private TingcheMapper tingcheMapper;


	@Override
	@Transactional
	public boolean remoteColumn(Integer InnerId, String Column, String ColumnName) throws IOException {
		// 读取配置文件
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("remote.properties");
		Properties p = new Properties();
		p.load(inputStream);
		// 获取配置文件的值
		String[] ColumnNames = p.getProperty("ColumnNames").split(",");
		String[] InnerIdNames = p.getProperty("InnerIdNames").split(",");
		String[] TableNames = p.getProperty("TableNames").split(",");
		String InnerIdName = "";
		String TableName = "";
		// 定义控制标记
		int Flag = 0;
		// 寻找列名对应的表名和主键名
		for (int i = 0; i < ColumnNames.length; i++) {
			if (ColumnNames[i].equals(ColumnName)) {
				InnerIdName = InnerIdNames[i];
				TableName = TableNames[i];
				Flag = 1;
				break;
			}
		}
		if (Flag == 0) {
			// 未寻找到列名对应的表名和主键名，返回失败
			return false;
		} else {
			String str[] = Column.split("'");
			Column = "";
			for(int i=0;i<str.length;i++){
				Column = Column+str[i];
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("InnerId", InnerId);
			map.put("Column", Column);
			map.put("ColumnName", ColumnName);
			map.put("TableName", TableName);
			map.put("InnerIdName", InnerIdName);
			Integer nameSize = utilMapper.remoteColumn(map);
			if (nameSize > 0) {
				// 数据库有相同数据，返回失败
				return false;
			} else {
				// 数据库没有相同数据，返回成功
				return true;
			}
		}
	}

	@Override
	@Transactional
	public String delete(Integer InnerId, String TableName, HttpServletRequest request) throws IOException {
		// 读取配置文件
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("delete.properties");
		Properties p = new Properties();
		p.load(inputStream);
		// 获取配置文件的值
		String[] InnerIdNames = p.getProperty("InnerIdNames").split(",");
		String[] TableNames = p.getProperty("TableNames").split(",");
		String InnerIdName = "";
		// 定义控制标记
		int Flag = 0;
		// 寻找表名对应的主键名
		for (int i = 0; i < TableNames.length; i++) {
			if (TableNames[i].equals(TableName)) {
				InnerIdName = InnerIdNames[i];
				Flag = 1;
				break;
			}
		}
		JSONObject jo = new JSONObject();
		if (Flag == 0) {
			// 未寻找到表名对应的主键名，返回失败
			jo.put("code", 500);
			jo.put("msg", "删除失败！");
		} else {
			SystemLog systemLog = deleteCheck(InnerId, TableName);
			if (systemLog.getOperatorInnerId() == 200) {
				// 数据库删除操作
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("InnerId", InnerId);
				map.put("TableName", TableName);
				map.put("InnerIdName", InnerIdName);
				Integer result = utilMapper.delete(map);
				if (result > 0) {
					// 插入操作日志
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
					systemLog.setOperatorInnerId(operator.getOperatorInnerId());
					systemLog.setLogDatetime(sdf.format(new Date()));
					Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);
					if (result1 > 0) {
						jo.put("code", 200);
						jo.put("msg", "删除成功！");
					} else {
						jo.put("code", 500);
						jo.put("msg", "删除失败！");
					}
				} else {
					jo.put("code", 500);
					jo.put("msg", "删除失败！");
				}
			} else {
				jo.put("code", 500);
				jo.put("msg", systemLog.getLogDatetime());
			}
		}
		return jo.toString();
	}

	public SystemLog deleteCheck(Integer InnerId, String TableName) {
		SystemLog systemLog = new SystemLog();
		if (TableName.equals("System_Area")) {
			// 判断此区域是否存在
			Area area = peizhiguanliMapper.selectAreaByInnerId(InnerId);
			if (area == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此区域不存在");
			} else {
				// 删除区域时验证是否有下级应用
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("AreaInnerId", InnerId);
				Integer counts = peizhiguanliMapper.selectAppTotal(map);
				if (counts > 0) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此区域下有应用，不能删除");
				} else {
					systemLog = new SystemLog(null, "System_Area", area.getAreaInnerId(), area.getAreaId(), area.getAreaName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(area)), null, 200, null, area.getRemark());
				}
			}
		} else if (TableName.equals("System_App")) {
			// 判断此应用是否存在
			App app = peizhiguanliMapper.selectAppByInnerId(InnerId);
			if (app == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此应用不存在");
			} else {
				// 删除应用时验证是否有下级设备或商户
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("AppInnerId", InnerId);
				Integer counts = peizhiguanliMapper.selectSysTerminalTotal(map);
				/*Integer counts1 = xiaofeiguanliMapper.selectMerchantTotal(map);*/
				if (counts > 0 /*|| counts1 > 0 */) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此应用下有设备或商户，不能删除");
				} else {
					systemLog = new SystemLog(null, "System_App", app.getAppInnerId(), app.getAppId(), app.getAppName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(app)), null, 200, null, app.getRemark());
				}
			}
		} else if (TableName.equals("System_UserType")) {
			// 判断此人员类型是否存在
			UserType userType = peizhiguanliMapper.selectUserTypeByInnerId(InnerId);
			if (userType == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此人员类型不存在");
			} else {
				// 删除人员类型时验证是否有人员
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("UserTypeInnerId", InnerId);
				Integer counts = renshiyewuMapper.selectUserTotal(map);
				if (counts > 0) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("有人员使用此人员类型，不能删除");
				} else {
					systemLog = new SystemLog(null, "System_UserType", userType.getUserTypeInnerId(), userType.getUserTypeId(), userType.getUserTypeName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(userType)), null, 200, null, userType.getRemark());
				}
			}
		} else if (TableName.equals("System_Terminal")) {
			// 判断此设备是否存在
			SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(InnerId);
			if (sysTerminal == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此设备不存在");
			} else {
				systemLog = new SystemLog(null, "System_Terminal", sysTerminal.getSysTerminalInnerId(), sysTerminal.getSysTerminalId(), sysTerminal.getSysTerminalName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(sysTerminal)), null, 200, null, sysTerminal.getRemark());
			}
		} else if (TableName.equals("System_Operator")) {
			// 判断此操作员是否存在
			Operator operator = peizhiguanliMapper.selectOperatorByInnerId(InnerId);
			if (operator == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此操作员不存在");
			} else {
				systemLog = new SystemLog(null, "System_Operator", operator.getOperatorInnerId(), operator.getOperatorId(), operator.getOperatorName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(operator)), null, 200, null, operator.getRemark());
			}
		} else if (TableName.equals("System_Company")) {
			// 判断此公司是否存在
			Company company = renshiyewuMapper.selectCompanyByInnerId(InnerId);
			if (company == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此公司不存在");
			} else {
				// 删除公司时验证是否有部门
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CompanyInnerId", InnerId);
				List<Department> departments = renshiyewuMapper.selectDepartment(map);
				if (departments.size() > 0) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此公司下有部门，不能删除");
				} else {
					systemLog = new SystemLog(null, "System_Company", company.getCompanyInnerId(), company.getCompanyId(), company.getCompanyName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(company)), null, 200, null, company.getRemark());
				}
			}
		} else if (TableName.equals("System_Department")) {
			// 判断此部门是否存在
			Department department = renshiyewuMapper.selectDepartmentByInnerId(InnerId);
			if (department == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此部门不存在");
			} else {
				// 删除部门时验证是否有子部门或人员
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("UpInnerId", InnerId);
				List<Department> departments = renshiyewuMapper.selectDepartment(map);
				map.put("DepartmentInnerId", InnerId);
				Integer counts = renshiyewuMapper.selectUserTotal(map);
				if (departments.size() > 0 || counts > 0) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此部门下有子部门或人员，不能删除");
				} else {
					systemLog = new SystemLog(null, "System_Department", department.getDepartmentInnerId(), department.getDepartmentId(), department.getDepartmentName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(department)), null, 200, null, department.getRemark());
				}
			}
		} else if (TableName.equals("System_UserInfo")) {
			// 判断此人员是否存在
			User user = renshiyewuMapper.selectUserByInnerId(InnerId);
			if (user == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此人员不存在");
			} else {
				// 删除人员时验证是否有卡片
				List<Integer> Marks = renshiyewuMapper.searchMarkByUser(InnerId);
				if (Marks.size() > 0) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此人员下有标识，不能删除");
				} else {
					systemLog = new SystemLog(null, "System_UserInfo", user.getCompanyInnerId(), user.getUserId(), user.getUserName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(user)), null, 200, null, user.getRemark());
				}
			}
		} /*else if (TableName.equals("Consumption_Merchant")) {
			// 判断此商户是否存在
			Merchant Merchant = xiaofeiguanliMapper.selectMerchantByInnerId(InnerId);
			if (Merchant == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此商户不存在");
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("MerchantInnerId", InnerId);
				Integer count = xiaofeiguanliMapper.selectConTerminalTotal(map);
				if (count > 0) {
					// 借用SystemLog的两个字段存储状态和提示
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("商户下有设备，不能删除");
				}else {
					systemLog = new SystemLog(null, "Consumption_Merchant", Merchant.getMerchantInnerId(), Merchant.getMerchantId(), Merchant.getMerchantName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(Merchant)), null, 200, null, Merchant.getRemark());
				}
			}
		} else if (TableName.equals("Consumption_ConType")) {
			// 判断此消费类型是否存在
			ConType ConType = xiaofeiguanliMapper.selectConTypeByInnerId(InnerId);
			if (ConType == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此消费类型不存在");
			} else {
				systemLog = new SystemLog(null, "Consumption_ConType", ConType.getConTypeInnerId(), ConType.getConTypeId(), ConType.getConTypeName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(ConType)), null, 200, null, ConType.getRemark());
			}
		} else if (TableName.equals("Consumption_Terminal")) {
			// 判断此设备是否存在
			ConTerminal conTerminal = xiaofeiguanliMapper.selectConTerminalByInnerId(InnerId);
			if (conTerminal == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此设备不存在");
			} else {
				systemLog = new SystemLog(null, "Consumption_Terminal", conTerminal.getConTerminalInnerId(), conTerminal.getConTerminalId(), conTerminal.getConTerminalName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(conTerminal)), null, 200, null, conTerminal.getRemark());
			}
		} else if (TableName.equals("TCY_MenuList")) {
			// 判断此设备结构是否存在
			MenuList menuList = xiaofeiguanliMapper.selectMenuListbyInnerId(InnerId);
			if (menuList == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此菜品不存在");
			} else {
				// 可以直接删除
				systemLog = new SystemLog(null, "Door_TerminalStructure", menuList.getFMenuInnerId(), menuList.getFMenuId(), menuList.getFMenuName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(menuList)), null, 200, null, menuList.getFRemark());
				
			}
		}else if (TableName.equals("TCY_MenuType")) {
			// 判断此设备结构是否存在
			MenuType menuType = xiaofeiguanliMapper.selectFMenuTypeNameByMenuTypeInnerId(InnerId);
			if (menuType == null) {
				// 借用SystemLog的两个字段存储状态和提示
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此菜类不存在");
			} else {
				Map<String,Object> map = new HashMap<>();
				map.put("FMenuTypeInnerId", InnerId);
				Integer selectMenuListTotal = xiaofeiguanliMapper.selectMenuListTotal(map);
				if (selectMenuListTotal > 0 ) {
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此菜单下有菜品，不能删除");
				} else {
					systemLog = new SystemLog(null, "Door_TerminalStructure", menuType.getFMenuTypeInnerId(), menuType.getFMenuTypeId(), menuType.getFMenuTypeName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(menuType)), null, 200, null, menuType.getFRemark());
				}
			}
		}*/else if(TableName.equals("Park_Info")){
			ParkInfo parkInfo = tingcheMapper.selectParkInfoByInnerId(InnerId);
			if(parkInfo != null){
				systemLog = new SystemLog(null, "Park_Info", parkInfo.getParkInfoInnerId(), parkInfo.getParkInfoId(), parkInfo.getParkInfoName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(parkInfo)), null, 200, null, parkInfo.getRemark());
			}else{
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此停车场不存在");
			}
		}else if(TableName.equals("Park_CarType")){
			CarType carType = tingcheMapper.selectCarTypeByInnerId(InnerId);
			if(carType != null){
				// 查询是否有车辆引用此类型
				Map<String,Object> map = new HashMap<>();
				map.put("CarTypeInnerId", InnerId);
				int to = tingcheMapper.selectUserCarInfoTotal(map);
				if(to > 0){
					systemLog.setOperatorInnerId(500);
					systemLog.setLogDatetime("此车辆类型再被使用，请解除使用后在进行删除");
				}else{
					systemLog = new SystemLog(null, "Park_Info", carType.getCarTypeInnerId(), carType.getCarTypeId(), carType.getCarTypeName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(carType)), null, 200, null, carType.getRemark());
				}
			}else{
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此车辆类型不存在");
			}
		}else if(TableName.equals("Park_PassRule")){
			PassRules passRule = tingcheMapper.selectPassRuleByInnerId(InnerId);
			if(passRule != null){
				systemLog = new SystemLog(null, "PassRules", passRule.getPassRulesInnerId(), passRule.getPassRulesId(), passRule.getPassRulesName(), 2, Base64.encodeBase64String(SerializationUtils.serialize(passRule)), null, 200, null, passRule.getRemark());
			}else{
				systemLog.setOperatorInnerId(500);
				systemLog.setLogDatetime("此通行规则不存在");
			}
		}



		return systemLog;
	}

}
