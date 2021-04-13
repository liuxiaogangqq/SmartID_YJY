package com.dhsr.smartid.xiaofeiguanli.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.Area;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.SystemLog;
import com.dhsr.smartid.peizhiguanli.domain.TerminalType;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.util.ExportExcelUtils;
import com.dhsr.smartid.util.UpLoadExcel;
import com.dhsr.smartid.xiaofeiguanli.dao.XiaofeiguanliMapper;
import com.dhsr.smartid.xiaofeiguanli.domain.ConDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog;
import com.dhsr.smartid.xiaofeiguanli.domain.ConLog_User;
import com.dhsr.smartid.xiaofeiguanli.domain.ConOperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.ConStatistics;
import com.dhsr.smartid.xiaofeiguanli.domain.ConTerminal;
import com.dhsr.smartid.xiaofeiguanli.domain.ConType;
import com.dhsr.smartid.xiaofeiguanli.domain.Con_OperatorPer;
import com.dhsr.smartid.xiaofeiguanli.domain.InvoiceLog;
import com.dhsr.smartid.xiaofeiguanli.domain.MealPrice;
import com.dhsr.smartid.xiaofeiguanli.domain.MenuList;
import com.dhsr.smartid.xiaofeiguanli.domain.MenuStatistics;
import com.dhsr.smartid.xiaofeiguanli.domain.MenuType;
import com.dhsr.smartid.xiaofeiguanli.domain.Menus;
import com.dhsr.smartid.xiaofeiguanli.domain.Merchant;
import com.dhsr.smartid.xiaofeiguanli.domain.MerchantDiscount;
import com.dhsr.smartid.xiaofeiguanli.domain.TimeRule;
import com.dhsr.smartid.zhanghuyewu.dao.ZhanghuyewuMapper;
import com.dhsr.smartid.zhanghuyewu.domain.Account;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 消费管理的Service层
 * 
 * @author qidong
 *
 */
public class XiaofeiguanliServiceImpl implements XiaofeiguanliService {
	@Resource
	private XiaofeiguanliMapper xiaofeiguanliMapper;
	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;
	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private ZhanghuyewuMapper zhanghuyewuMapper;

	@Override
	@Transactional
	public String selectMerchant(DataGridModel dgm, Merchant merchant) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("MerchantInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(20);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("MerchantId", merchant.getMerchantId());
		map.put("MerchantName", merchant.getMerchantName());
		map.put("Remark", merchant.getRemark());
		map.put("StandbyA", merchant.getStandbyA());
		map.put("StandbyB", merchant.getStandbyB());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Merchant> MerchantList = xiaofeiguanliMapper.selectMerchant(map);
		for (Merchant Merchant1 : MerchantList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("MerchantInnerId", Merchant1.getMerchantInnerId());
			row.put("MerchantId", Merchant1.getMerchantId().replace("'", ""));
			row.put("MerchantName", Merchant1.getMerchantName().replace("'", ""));
			row.put("AppInnerId", Merchant1.getAppInnerId());
			row.put("AreaInnerId", Merchant1.getAreaInnerId());
			row.put("Proportion", Merchant1.getProportion());
			App app = peizhiguanliMapper.selectAppByInnerId(Merchant1.getAppInnerId());
			if (app != null) {
				row.put("AppName", app.getAppName());
			}

			row.put("AreaInnerId", Merchant1.getAreaInnerId());
			Area area = peizhiguanliMapper.selectAreaByInnerId(Merchant1.getAreaInnerId());
			if (area != null) {
				row.put("AreaName", area.getAreaName());
			}
			row.put("MerchantType", Merchant1.getMerchantType());
			if (Merchant1.getMerchantType() == 0) {
				row.put("MerchantTypeName", "文体");
			} else if (Merchant1.getMerchantType() == 1) {
				row.put("MerchantTypeName", "餐饮");
			} else {
				row.put("MerchantTypeName", "");
			}
			row.put("Remark", Merchant1.getRemark());
			row.put("StandbyA", Merchant1.getStandbyA());
			row.put("StandbyB", Merchant1.getStandbyB());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectMerchantTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectMerchantBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "MerchantInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Merchant> MerchantList = xiaofeiguanliMapper.selectMerchant(map);
		for (Merchant Merchant : MerchantList) {
			jo = new JSONObject();
			jo.put("id", Merchant.getMerchantInnerId());
			jo.put("text", Merchant.getMerchantName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertMerchant(Merchant merchant, HttpServletRequest request) {
		App app = peizhiguanliMapper.selectAppByInnerId(merchant.getAppInnerId());
		merchant.setAreaInnerId(app.getAreaInnerId());
		Integer result = xiaofeiguanliMapper.insertMerchant(merchant);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SystemLog systemLog = new SystemLog(null, "Consumption_Merchant", merchant.getMerchantInnerId(),
					merchant.getMerchantId(), merchant.getMerchantName(), 0, null,
					Base64.encodeBase64String(SerializationUtils.serialize(merchant)), operator.getOperatorInnerId(),
					sdf.format(new Date()), merchant.getRemark());
			Integer result1 = peizhiguanliMapper.insertSystemLog(systemLog);

			String OperatorInerId = request.getParameter("OperatorInerId");
			String[] OperatorList = OperatorInerId.split(",");
			for (String Operator : OperatorList) {
				Con_OperatorPer con_OperatorPer = xiaofeiguanliMapper
						.selectConOperatorPerByInnerId(Integer.valueOf(Operator));
				if (con_OperatorPer != null) {
					ConOperatorPer OperatorPer = new ConOperatorPer();
					OperatorPer.setOperatorInnerId(con_OperatorPer.getOperatorInnerId());
					if (con_OperatorPer.getMerchantList() == null) {
						OperatorPer.setMerchantList(merchant.getMerchantInnerId() + ",");
					} else {
						OperatorPer.setMerchantList(
								con_OperatorPer.getMerchantList() + merchant.getMerchantInnerId() + ",");
					}
					OperatorPer.setConTerminalList(con_OperatorPer.getConTerminalList());
					OperatorPer.setRemark(con_OperatorPer.getRemark());
					OperatorPer.setStandbyA(con_OperatorPer.getStandbyA());
					OperatorPer.setStandbyB(con_OperatorPer.getStandbyB());
					xiaofeiguanliMapper.updateConOperatorPer(OperatorPer);
				}
			}
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
	public String updateMerchant(Merchant merchant, HttpServletRequest request) {
		App app = peizhiguanliMapper.selectAppByInnerId(merchant.getAppInnerId());
		merchant.setAreaInnerId(app.getAreaInnerId());
		Merchant beforeMerchant = xiaofeiguanliMapper.selectMerchantByInnerId(merchant.getMerchantInnerId());
		Integer result = xiaofeiguanliMapper.updateMerchant(merchant);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Merchant afterMerchant = xiaofeiguanliMapper.selectMerchantByInnerId(merchant.getMerchantInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "Consumption_Merchant", afterMerchant.getMerchantInnerId(),
					afterMerchant.getMerchantId(), afterMerchant.getMerchantName(), 1,
					Base64.encodeBase64String(SerializationUtils.serialize(beforeMerchant)),
					Base64.encodeBase64String(SerializationUtils.serialize(afterMerchant)),
					operator.getOperatorInnerId(), sdf.format(new Date()), afterMerchant.getRemark());
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
	public String selectConType(DataGridModel dgm, ConType conType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ConTypeInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("ConTypeId", conType.getConTypeId());
		map.put("ConTypeName", conType.getConTypeName());
		map.put("Remark", conType.getRemark());
		map.put("StandbyA", conType.getStandbyA());
		map.put("StandbyB", conType.getStandbyB());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ConType> ConTypeList = xiaofeiguanliMapper.selectConType(map);
		for (ConType ConType1 : ConTypeList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ConTypeInnerId", ConType1.getConTypeInnerId());
			row.put("ConTypeId", ConType1.getConTypeId());
			row.put("ConTypeName", ConType1.getConTypeName());
			row.put("BeginTime", ConType1.getBeginTime());
			row.put("EndTime", ConType1.getEndTime());
			row.put("Remark", ConType1.getRemark());
			row.put("StandbyA", ConType1.getStandbyA());
			row.put("StandbyB", ConType1.getStandbyB());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectConTypeTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConTypeBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "ConTypeInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<ConType> ConTypeList = xiaofeiguanliMapper.selectConType(map);
		for (ConType ConType1 : ConTypeList) {
			jo = new JSONObject();
			jo.put("id", ConType1.getConTypeInnerId());
			jo.put("text", ConType1.getConTypeName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertConType(ConType conType, HttpServletRequest request) {
		Integer result = xiaofeiguanliMapper.insertConType(conType);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SystemLog systemLog = new SystemLog(null, "Consumption_ConType", conType.getConTypeInnerId(),
					conType.getConTypeId(), conType.getConTypeName(), 0, null,
					Base64.encodeBase64String(SerializationUtils.serialize(conType)), operator.getOperatorInnerId(),
					sdf.format(new Date()), conType.getRemark());
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
	public String updateConType(ConType conType, HttpServletRequest request) {
		ConType beforeConType = xiaofeiguanliMapper.selectConTypeByInnerId(conType.getConTypeInnerId());
		Integer result = xiaofeiguanliMapper.updateConType(conType);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			ConType afterConType = xiaofeiguanliMapper.selectConTypeByInnerId(conType.getConTypeInnerId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			SystemLog systemLog = new SystemLog(null, "Consumption_ConType", afterConType.getConTypeInnerId(),
					afterConType.getConTypeId(), afterConType.getConTypeName(), 1,
					Base64.encodeBase64String(SerializationUtils.serialize(beforeConType)),
					Base64.encodeBase64String(SerializationUtils.serialize(afterConType)),
					operator.getOperatorInnerId(), sdf.format(new Date()), afterConType.getRemark());
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
	public String selectConTerminal(DataGridModel dgm, ConTerminal conTerminal) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ConTerminalInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("ConTerminalId", conTerminal.getConTerminalId());
		map.put("ConTerminalName", conTerminal.getConTerminalName());
		map.put("Remark", conTerminal.getRemark());
		map.put("StandbyA", conTerminal.getStandbyA());
		map.put("StandbyB", conTerminal.getStandbyB());
		map.put("StandbyC", conTerminal.getStandbyC());
		map.put("StandbyD", conTerminal.getStandbyD());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ConTerminal> ConTerminalList = xiaofeiguanliMapper.selectConTerminal(map);
		for (ConTerminal conTerminal1 : ConTerminalList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ConTerminalInnerId", conTerminal1.getConTerminalInnerId());
			row.put("ConTerminalId", conTerminal1.getConTerminalId());
			row.put("ConTerminalName", conTerminal1.getConTerminalName());
			row.put("MerchantInnerId", conTerminal1.getMerchantInnerId());
			row.put("AppInnerId", conTerminal1.getAppInnerId());
			row.put("AreaInnerId", conTerminal1.getAreaInnerId());
			row.put("TerminalTypeInnerId", conTerminal1.getTerminalTypeInnerId());

			Merchant merchant = xiaofeiguanliMapper.selectMerchantByInnerId(conTerminal1.getMerchantInnerId());
			if (merchant != null) {
				row.put("MerchantName", merchant.getMerchantName());
			}

			App app = peizhiguanliMapper.selectAppByInnerId(conTerminal1.getAppInnerId());
			if (app != null) {
				row.put("AppName", app.getAppName());
			}

			row.put("AreaInnerId", conTerminal1.getAreaInnerId());
			Area area = peizhiguanliMapper.selectAreaByInnerId(conTerminal1.getAreaInnerId());
			if (area != null) {
				row.put("AreaName", area.getAreaName());
			}

			row.put("TerminalTypeInnerId", conTerminal1.getTerminalTypeInnerId());
			TerminalType terminalType = peizhiguanliMapper
					.selectTerminalTypeByInnerId(conTerminal1.getTerminalTypeInnerId());
			if (terminalType != null) {
				row.put("TerminalTypeName", terminalType.getTerminalTypeName());
			}
			row.put("ComId", conTerminal1.getComId());
			row.put("ComSerials", conTerminal1.getComSerials());
			row.put("ConTypeList", conTerminal1.getConTypeList());
			row.put("Remark", conTerminal1.getRemark());
			row.put("StandbyA", conTerminal1.getStandbyA());
			row.put("StandbyB", conTerminal1.getStandbyB());
			row.put("StandbyC", conTerminal1.getStandbyC());
			row.put("StandbyD", conTerminal1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectConTerminalTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConTerminalBox() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "ConTerminalInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		List<ConTerminal> ConTerminalList = xiaofeiguanliMapper.selectConTerminal(map);
		for (ConTerminal conTerminal : ConTerminalList) {
			jo = new JSONObject();
			jo.put("id", conTerminal.getConTerminalInnerId());
			jo.put("text", conTerminal.getConTerminalName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String insertConTerminal(ConTerminal conTerminal, HttpServletRequest request) {
		Merchant merchant = xiaofeiguanliMapper.selectMerchantByInnerId(conTerminal.getMerchantInnerId());
		conTerminal.setAppInnerId(merchant.getAppInnerId());
		conTerminal.setAreaInnerId(merchant.getAreaInnerId());
		boolean flag = false;
		try {
			flag = timeOverlaps(conTerminal.getConTypeList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jo = new JSONObject();
		if(flag){
			Integer result = xiaofeiguanliMapper.insertConTerminal(conTerminal);
			if (result > 0) {
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SystemLog systemLog = new SystemLog(null, "Consumption_Terminal", conTerminal.getConTerminalInnerId(),
						conTerminal.getConTerminalId(), conTerminal.getConTerminalName(), 0, null,
						Base64.encodeBase64String(SerializationUtils.serialize(conTerminal)), operator.getOperatorInnerId(),
						sdf.format(new Date()), conTerminal.getRemark());
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
		}else{
			jo.put("code", 500);
			jo.put("msg", "消费类型时间段重叠，请重新选择");
		}
		return jo.toString();
	}
	
	public boolean timeOverlaps(String ConTypeList) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String[] conTypeList = ConTypeList.split(",");
		boolean flag = false;
		if(conTypeList.length>1){
			for (String string : conTypeList) {
				ConType conType1 = xiaofeiguanliMapper.selectConTypeByInnerId(Integer.valueOf(string));
				String beginTime1 = conType1.getBeginTime();
				String endTime1 = conType1.getEndTime();
				long time1 = sdf.parse(beginTime1).getTime();
				long time2 = sdf.parse(endTime1).getTime();
				for (String string2 : conTypeList) {
					if(string2.equals(string)){
						continue;
					}else{
						ConType conType2 = xiaofeiguanliMapper.selectConTypeByInnerId(Integer.valueOf(string2));
						String beginTime2 = conType2.getBeginTime();
						String endTime2 = conType2.getEndTime();
						long time3 = sdf.parse(beginTime2).getTime();
						long time4 = sdf.parse(endTime2).getTime();
						if(time1 >= time4 || time2 <= time3){
							flag = true;
						}else{
							return false;
						}
					}
				}
			}
		}else{
			flag = true;
		}
		return flag;
	}
	
	@Override
	@Transactional
	public String updateConTerminal(ConTerminal conTerminal, HttpServletRequest request) {
		Merchant merchant = xiaofeiguanliMapper.selectMerchantByInnerId(conTerminal.getMerchantInnerId());
		conTerminal.setAppInnerId(merchant.getAppInnerId());
		conTerminal.setAreaInnerId(merchant.getAreaInnerId());
		ConTerminal beforeConTerminal = xiaofeiguanliMapper.selectConTerminalByInnerId(conTerminal.getConTerminalInnerId());
		boolean flag = false;
		try {
			flag = timeOverlaps(conTerminal.getConTypeList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jo = new JSONObject();
		if(flag){
			Integer result = xiaofeiguanliMapper.updateConTerminal(conTerminal);
			if (result > 0) {
				ConTerminal afterConTerminal = xiaofeiguanliMapper
						.selectConTerminalByInnerId(conTerminal.getConTerminalInnerId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
				SystemLog systemLog = new SystemLog(null, "Consumption_Terminal", afterConTerminal.getConTerminalInnerId(),
						afterConTerminal.getConTerminalId(), afterConTerminal.getConTerminalName(), 1,
						Base64.encodeBase64String(SerializationUtils.serialize(beforeConTerminal)),
						Base64.encodeBase64String(SerializationUtils.serialize(afterConTerminal)),
						operator.getOperatorInnerId(), sdf.format(new Date()), afterConTerminal.getRemark());
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
		}else{
			jo.put("code", 500);
			jo.put("msg", "消费类型时间段重叠，请重新选择");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectMerchantAppTree() {
		// 查询区域
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
			// 查询应用
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "AppInnerId");
			map.put("start", 0);
			map.put("AreaInnerId", area.getAreaInnerId());
			map.put("AppTypeInnerId", 1);
			JSONArray appJa = new JSONArray();
			JSONObject appJo = new JSONObject();
			List<App> AppList = peizhiguanliMapper.selectApp(map);
			for (App app : AppList) {
				appJo = new JSONObject();
				appJo.put("id", "app" + app.getAppInnerId());
				appJo.put("text", app.getAppName());
				// 查询商户
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("rows", 100000000);
				map1.put("order", "asc");
				map1.put("sort", "MerchantInnerId");
				map1.put("start", 0);
				map1.put("AppInnerId", app.getAppInnerId());
				JSONArray merchantJa = new JSONArray();
				JSONObject merchantJo = new JSONObject();
				List<Merchant> merchantList = xiaofeiguanliMapper.selectMerchant(map1);
				for (Merchant merchant : merchantList) {
					merchantJo = new JSONObject();
					merchantJo.put("id", "merchant" + merchant.getMerchantInnerId());
					merchantJo.put("text", merchant.getMerchantName());
					merchantJa.add(merchantJo);
				}
				appJo.put("children", merchantJa);
				if (merchantList.size() > 0) {
					appJo.put("state", "open");
				} else {
					appJo.put("state", "");
				}

				appJa.add(appJo);
			}
			jo.put("children", appJa);
			if (AppList.size() > 0) {
				jo.put("state", "open");
			} else {
				jo.put("state", "");
			}
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectConTerminalMerchantTree() {
		// 查询区域
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
			// 查询应用
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "AppInnerId");
			map.put("start", 0);
			map.put("AreaInnerId", area.getAreaInnerId());
			map.put("AppTypeInnerId", 1);
			JSONArray appJa = new JSONArray();
			JSONObject appJo = new JSONObject();
			List<App> AppList = peizhiguanliMapper.selectApp(map);
			for (App app : AppList) {
				appJo = new JSONObject();
				appJo.put("id", "app" + app.getAppInnerId());
				appJo.put("text", app.getAppName());
				// 查询商户
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("rows", 100000000);
				map1.put("order", "asc");
				map1.put("sort", "MerchantInnerId");
				map1.put("start", 0);
				map1.put("AppInnerId", app.getAppInnerId());
				JSONArray merchantJa = new JSONArray();
				JSONObject merchantJo = new JSONObject();
				List<Merchant> merchantList = xiaofeiguanliMapper.selectMerchant(map1);
				for (Merchant merchant : merchantList) {
					merchantJo = new JSONObject();
					merchantJo.put("id", "merchant" + merchant.getMerchantInnerId());
					merchantJo.put("text", merchant.getMerchantName());

					// 查询设备
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("rows", 100000000);
					map2.put("order", "asc");
					map2.put("sort", "ConTerminalInnerId");
					map2.put("start", 0);
					map2.put("MerchantInnerId", merchant.getMerchantInnerId());
					JSONArray conTerminalJa = new JSONArray();
					JSONObject conTerminalJo = new JSONObject();
					List<ConTerminal> conTerminalList = xiaofeiguanliMapper.selectConTerminal(map2);
					for (ConTerminal conTerminal : conTerminalList) {
						conTerminalJo = new JSONObject();
						conTerminalJo.put("id", "conTerminal" + conTerminal.getConTerminalInnerId());
						conTerminalJo.put("text", conTerminal.getConTerminalName());
						conTerminalJa.add(conTerminalJo);
					}
					merchantJo.put("children", conTerminalJa);
					if (conTerminalList.size() > 0) {
						merchantJo.put("state", "closed");
					} else {
						merchantJo.put("state", "");
					}

					merchantJa.add(merchantJo);
				}
				appJo.put("children", merchantJa);
				if (merchantList.size() > 0) {
					appJo.put("state", "closed");
				} else {
					appJo.put("state", "");
				}

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
	public String selectConLog(DataGridModel dgm, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String departmentInnerId = request.getParameter("DepartmentInnerId");
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		map.put("DepartmentInnerId", departmentInnerId);
		String ConTerminalInnerId = request.getParameter("ConTerminalInnerId");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String TimeType = request.getParameter("TimeType");
		String ConTypeList = request.getParameter("ConTypeList");
		String ConWay = request.getParameter("ConWay");
		String ConPattern = request.getParameter("ConPattern");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String Flag = request.getParameter("Flag");
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ConLogInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		String[] sortS = dgm.getSort().split(",");
		String[] orderS = dgm.getOrder().split(",");
		String sortString = "";
		for (int i = 0; i < sortS.length; i++) {
			if (i == sortS.length - 1) {
				sortString += sortS[i] + " " + orderS[i];
			} else {
				sortString += sortS[i] + " " + orderS[i] + ",";
			}
		}
		Con_OperatorPer operatorPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operator.getOperatorInnerId());
		if (operatorPer != null) {
			if(operatorPer.getOperatorId().equals("admin")){
				map.put("MerchantList", "");
			} else {
				if(operatorPer.getMerchantList() != null){
					map.put("MerchantList", operatorPer.getMerchantList());
				}else{
					map.put("MerchantList", "100000,");
				}
			}
		} else {
			map.put("MerchantList", "100000,");
		}
		map.put("rows", dgm.getRows());
		// map.put("order", dgm.getOrder());
		map.put("sort", sortString);
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("ConTerminalInnerId", ConTerminalInnerId);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", "ConDatetime");
		map.put("ConTypeList", ConTypeList);
		map.put("ConWay", ConWay);
		map.put("ConPattern", ConPattern);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		if(Flag == null || Flag.equals("")){
			Flag = "1";
		}
		map.put("Flag", Flag);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ConLog_User> ConLogList = xiaofeiguanliMapper.selectConLog(map);
		for (ConLog_User conLog : ConLogList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ConLogInnerId", conLog.getConLogInnerId());
			row.put("UserId", conLog.getUserId());
			row.put("Remark", conLog.getRemark());
			row.put("CompanyName", conLog.getCompanyName());
			row.put("DepartmentName", conLog.getDepartmentName());
			row.put("UserName", conLog.getUserName());
			row.put("PersonMoney", conLog.getPersonMoney() / 100.0);
			row.put("AllowanceMoney", conLog.getAllowanceMoney() / 100.0);
			/*row.put("NumberMoney", conLog.getNumberMoney() / 100.0);*/
			row.put("Money", conLog.getMoney() / 100.0);
			row.put("DiscountMoney", conLog.getDiscountMoney() / 100.0);
			row.put("Proportion", conLog.getProportion() / 100);
			row.put("ConDatetime", conLog.getConDatetime().substring(0, 19));
			row.put("UploadDatetime", conLog.getUploadDatetime().substring(0, 19));
			row.put("ConTerminalName", conLog.getConTerminalName());
			row.put("MerchantName", conLog.getMerchantName());
			row.put("AllowanceAFMoney", conLog.getAllowanceAFMoney() / 100.0);
			row.put("AllowanceBFMoney", conLog.getAllowanceBFMoney() / 100.0);
			row.put("PersonAFMoney", conLog.getPersonAFMoney() / 100.0);
			row.put("PersonBFMoney", conLog.getPersonBFMoney() / 100.0);
			/*row.put("NumberAFMoney", conLog.getNumberAFMoney() / 100.0);
			row.put("NumberBFMoney", conLog.getNumberBFMoney() / 100.0);*/
			row.put("Rules", conLog.getRules());
			row.put("StandbyA", conLog.getStandbyA());
			row.put("StandbyB", conLog.getStandbyB());
			row.put("StandbyC", conLog.getStandbyC());
			row.put("StandbyD", conLog.getStandbyD());
			rows.add(row);
		}
		Long total = xiaofeiguanliMapper.selectConLogTotal(map);
		if (total > 0 && dgm.getRows() * dgm.getPage() >= total) {
			map.put("rows", 100000000);
			map.put("start", 0);
			List<ConLog_User> ConLogList1 = xiaofeiguanliMapper.selectConLog(map);
			Long PersonMoneySum = (long) 0;
			Long AllowanceMoneySum = (long) 0;
			/*Long NumberMoneySum = (long) 0;*/
			Long MoneySum = (long) 0;
			Long DiscountMoneySum = (long) 0;
			Long Count = (long) 0;
			for (ConLog_User conLog : ConLogList1) {
				Count++;
				PersonMoneySum += conLog.getPersonMoney();
				AllowanceMoneySum += conLog.getAllowanceMoney();
				/*NumberMoneySum += conLog.getNumberMoney();*/
				MoneySum += conLog.getMoney();
				DiscountMoneySum += conLog.getDiscountMoney();
			}
			// 最后一行合计
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserId", "合计");
			row.put("PersonMoney", PersonMoneySum / 100.0);
			row.put("AllowanceMoney", AllowanceMoneySum / 100.0);
			/*row.put("FavorableMoney", NumberMoneySum / 100.0);*/
			row.put("Money", MoneySum / 100.0);
			row.put("DiscountMoney", DiscountMoneySum / 100.0);
			row.put("UserName", Count + "人");
			rows.add(row);
		}
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConLogExcel(HttpServletRequest request) throws Exception {

		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String departmentInnerId = request.getParameter("DepartmentInnerId");
		String ConTerminalInnerId = request.getParameter("ConTerminalInnerId");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String TimeType = request.getParameter("TimeType");
		String ConTypeList = request.getParameter("ConTypeList");
		String ConWay = request.getParameter("ConWay");
		String ConPattern = request.getParameter("ConPattern");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Map<String, Object> map = new HashMap<String, Object>();
		if (sort == null || sort.equals("")) {
			sort = "ConLogInnerId";
			order = "asc";
		}
		String[] sortS = sort.split(",");
		String[] orderS = order.split(",");
		String sortString = "";
		for (int i = 0; i < sortS.length; i++) {
			if (i == sortS.length - 1) {
				sortString += sortS[i] + " " + orderS[i];
			} else {
				sortString += sortS[i] + " " + orderS[i] + ",";
			}
		}
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		map.put("DepartmentInnerId", departmentInnerId);
		Con_OperatorPer operatorPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operator.getOperatorInnerId());
		if (operatorPer != null) {
			if(operatorPer.getOperatorId().equals("admin")){
				map.put("MerchantList", "");
			} else {
				if(operatorPer.getMerchantList() != null){
					map.put("MerchantList", operatorPer.getMerchantList());
				}else{
					map.put("MerchantList", "100000,");
				}
			}
		} else {
			map.put("MerchantList", "100000,");
		}
		map.put("rows", 100000000);
		// map.put("order", order);
		map.put("sort", sortString);
		map.put("start", 0);
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("ConTerminalInnerId", ConTerminalInnerId);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", TimeType);
		map.put("ConTypeList", ConTypeList);
		map.put("ConWay", ConWay);
		map.put("ConPattern", ConPattern);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);

		List<List<Object>> data = new ArrayList<List<Object>>();
		int count = 0;
		Long personSum = (long) 0;
		Long allowanceSum = (long) 0;
		Long FavorableMoneySum = (long) 0;
		Long MoneySum = (long) 0;
		Long DiscountMoneySum = (long) 0;

		List<ConLog_User> ConLogList = xiaofeiguanliMapper.selectConLog(map);
		for (ConLog_User conLog : ConLogList) {
			List<Object> rowData = new ArrayList<Object>();
			count++;
			personSum += conLog.getPersonMoney();
			allowanceSum += conLog.getAllowanceMoney();
			FavorableMoneySum += conLog.getNumberMoney();
			MoneySum += conLog.getMoney();
			DiscountMoneySum += conLog.getDiscountMoney();
			rowData.add(conLog.getUserId());
			rowData.add(conLog.getUserName());
			rowData.add(conLog.getDepartmentName());
			rowData.add(conLog.getPersonMoney() / 100.0);
			rowData.add(conLog.getPersonBFMoney() / 100.0);
			rowData.add(conLog.getPersonAFMoney() / 100.0);
			rowData.add(conLog.getAllowanceMoney() / 100.0);
			rowData.add(conLog.getAllowanceBFMoney() / 100.0);
			rowData.add(conLog.getAllowanceAFMoney() / 100.0);
			/*rowData.add(conLog.getNumberMoney() / 100.0);
			rowData.add(conLog.getNumberBFMoney() / 100.0);
			rowData.add(conLog.getNumberAFMoney() / 100.0);*/
			rowData.add(conLog.getMoney() / 100.0);
			rowData.add(conLog.getDiscountMoney() / 100.0);
			rowData.add(conLog.getProportion() / 100);
			rowData.add(conLog.getConDatetime().substring(0, 19));
			rowData.add(conLog.getUploadDatetime().substring(0, 19));
			rowData.add(conLog.getMerchantName());
			rowData.add(conLog.getConTerminalName());
			if (conLog.getRemark() != null) {
				rowData.add(conLog.getRemark());
			} else {
				rowData.add(" ");
			}
			data.add(rowData);
		}
		List<Object> rowData = new ArrayList<Object>();
		rowData.add("总计");
		rowData.add(count + "人");
		rowData.add(" ");
		rowData.add(personSum / 100.0);
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(allowanceSum / 100.0);
		rowData.add(" ");
		rowData.add(" ");
		/*rowData.add(FavorableMoneySum / 100.0);
		rowData.add(" ");
		rowData.add(" ");*/
		rowData.add(MoneySum / 100.0);
		rowData.add(DiscountMoneySum / 100.0);
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		data.add(rowData);
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/ConLog" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<String> headerList = new ArrayList<String>();
		headerList.add("消费记录表");
		headerList.add("制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date()));
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("人员编号");
		headers0.add("人员姓名");
		headers0.add("公司");
		headers0.add("现金消费");
		headers0.add("消费前现金金额");
		headers0.add("消费后现金金额");
		headers0.add("补贴消费");
		headers0.add("消费前补贴金额");
		headers0.add("消费后补贴金额");
		/*headers0.add("计次消费");
		headers0.add("消费前次数");
		headers0.add("消费后次数");*/
		headers0.add("商品原价");
		headers0.add("实际消费");
		headers0.add("折扣系数（%）");
		headers0.add("消费时间");
		headers0.add("上传时间");
		headers0.add("商户");
		headers0.add("设备");
		headers0.add("备注");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(15);
		length0.add(15);
		length0.add(20);
		length0.add(20);
		length0.add(9);
		length0.add(9);
		length0.add(9);
		length0.add(9);
		length0.add(9);
		length0.add(9);
		/*length0.add(9);
		length0.add(9);
		length0.add(9);*/
		length0.add(9);
		length0.add(9);
		length0.add(12);
		length0.add(20);
		length0.add(20);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		eeu.selectUseruserExcel(workbook, 0, "消费记录表", headerList, headers0, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertConLog(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer ConLogInnerId = Integer.valueOf(request.getParameter("ConLogInnerId"));
		Integer ErrorMoney = Integer.valueOf(request.getParameter("ErrorMoney"));
		String Remark = request.getParameter("Remark");
		Map<String,Object> map = new HashMap<>();
		map.put("ConLogInnerId", ConLogInnerId);
		ConLog beforeConLog = xiaofeiguanliMapper.selectConLogByInnerId(map);
		if (beforeConLog == null) {
			jo.put("code", 501);
			jo.put("msg", "此消费记录不存在！");
		}else {
			map.put("ErrorConLogInnerId", beforeConLog.getConLogInnerId());
			map.put("ConLogInnerId", "");
			ConLog beforeconlog = xiaofeiguanliMapper.selectConLogByInnerId(map);
			if(beforeconlog != null){
				jo.put("code", 501);
				jo.put("msg", "此消费记录已存在误消费补款记录！请勿重复补款");
				return jo.toString();
			}
			ConLog conLog = new ConLog();
			// 查询现金账户
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("AccountTypeInnerId", 1);
			map1.put("UserInnerId", beforeConLog.getUserInnerId());
			Account account = zhanghuyewuMapper.selectAccount(map1);
			// 补贴账户
			map1.put("AccountTypeInnerId", 2);
			map1.put("UserInnerId", beforeConLog.getUserInnerId());
			Account account1 = zhanghuyewuMapper.selectAccount(map1);
			if (ErrorMoney <= beforeConLog.getAllowanceMoney()) {
				conLog.setAllowanceMoney(-ErrorMoney);
				conLog.setPersonMoney(0);
				conLog.setAllowanceBFMoney(account1.getMoney());
				conLog.setAllowanceAFMoney(account1.getMoney()+ErrorMoney);
				conLog.setPersonBFMoney(account.getMoney());
				conLog.setPersonAFMoney(account.getMoney());
			} else {
				conLog.setAllowanceMoney(-beforeConLog.getAllowanceMoney());
				conLog.setPersonMoney(beforeConLog.getAllowanceMoney() - ErrorMoney);
				conLog.setAllowanceBFMoney(account1.getMoney());
				conLog.setAllowanceAFMoney(account1.getMoney()+beforeConLog.getAllowanceMoney());
				conLog.setPersonBFMoney(account.getMoney());
				conLog.setPersonAFMoney(account.getMoney()-(beforeConLog.getAllowanceMoney() - ErrorMoney));
			}

			
			conLog.setDiscountMoney(-ErrorMoney);
			conLog.setProportion(10000);
			conLog.setNumberMoney(0);
			conLog.setNumberAFMoney(0);
			conLog.setNumberBFMoney(0);
			conLog.setMoney(-ErrorMoney);
			conLog.setMarkInnerId(beforeConLog.getMarkInnerId());
			conLog.setMarkId(beforeConLog.getMarkId());
			conLog.setMarkTypeInnerId(beforeConLog.getMarkTypeInnerId());
			conLog.setUserInnerId(beforeConLog.getUserInnerId());
			conLog.setDepartmentInnerId(beforeConLog.getDepartmentInnerId());
			conLog.setCompanyInnerId(beforeConLog.getCompanyInnerId());
			conLog.setConTerminalInnerId(beforeConLog.getConTerminalInnerId());
			conLog.setTerminalTypeInnerId(beforeConLog.getTerminalTypeInnerId());
			conLog.setMerchantInnerId(beforeConLog.getMerchantInnerId());
			conLog.setAppInnerId(beforeConLog.getAppInnerId());
			conLog.setAreaInnerId(beforeConLog.getAreaInnerId());
			conLog.setConDatetime(sdf.format(new Date()));
			conLog.setUploadDatetime(sdf.format(new Date()));
			conLog.setConTypeInnerId(beforeConLog.getConTypeInnerId());
			conLog.setConPattern(beforeConLog.getConPattern());
			conLog.setConWay(beforeConLog.getConWay());
			conLog.setLimitTimes(beforeConLog.getLimitTimes());
			conLog.setOffLine(beforeConLog.getOffLine());
			conLog.setErrorConLogInnerId(beforeConLog.getConLogInnerId());
			conLog.setOperator(operator.getOperatorInnerId());
			conLog.setRemark("误消费补款");
			conLog.setStandbyA(beforeConLog.getStandbyA());
			conLog.setStandbyB(beforeConLog.getStandbyB());
			conLog.setStandbyC(beforeConLog.getStandbyC());
			conLog.setStandbyD(beforeConLog.getStandbyD());
			conLog.setRules("");
			Integer result = xiaofeiguanliMapper.insertConLog(conLog);
			if (result > 0) {
				if(conLog.getAllowanceMoney() < 0){
					map1.put("AccountInnerId", account1.getAccountInnerId());
					map1.put("Money", conLog.getAllowanceAFMoney());
					zhanghuyewuMapper.updateAccount(map1);
				}
				if(conLog.getPersonMoney() < 0){
					map1.put("AccountInnerId", account.getAccountInnerId());
					map1.put("Money", conLog.getPersonAFMoney());
					zhanghuyewuMapper.updateAccount(map1);
				}
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConStatistics(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String RowName = request.getParameter("RowName");
		String Type = request.getParameter("Type");
		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String departmentInnerId = request.getParameter("DepartmentInnerId");
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		map.put("DepartmentInnerId", departmentInnerId);
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String TimeType = request.getParameter("TimeType");
		String ConTypeList = request.getParameter("ConTypeList");
		String ConWay = request.getParameter("ConWay");
		String ConPattern = request.getParameter("ConPattern");
		if (RowName.equals("Money")) {
			// 按现金补贴只能统计金额
			Type = "sum";
		}
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		Con_OperatorPer operatorPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operator.getOperatorInnerId());
		if (operatorPer != null) {
			if(operatorPer.getOperatorId().equals("admin")){
				map.put("MerchantList", "");
			} else {
				if(operatorPer.getMerchantList() != null){
					map.put("MerchantList", operatorPer.getMerchantList());
				}else{
					map.put("MerchantList", "100000,");
				}
			}
		} else {
			map.put("MerchantList", "100000,");
		}
		map.put("UserName", UserName);
		map.put("UserId", UserId);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", TimeType);
		map.put("ConTypeList", ConTypeList);
		map.put("ConWay", ConWay);
		map.put("ConPattern", ConPattern);
		map.put("Type", Type);
		if (RowName.equals("Money")) {
			// 按现金补贴统计
			List<ConStatistics> ConStatisticsList = xiaofeiguanliMapper.selectConStatistics(map);
			if (ConStatisticsList.size() > 0) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("RowName", "现金消费");
				Long count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getPersonMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "补贴消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getAllowanceMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				/*row = new HashMap<String, Object>();
				row.put("RowName", "计次消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getNumberMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);*/

				row = new HashMap<String, Object>();
				row.put("RowName", "商品原价");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "实际消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getDiscountMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "总计");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getDiscountMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);
			}
		} else {
			map.put("RowName", RowName);
			List<ConStatistics> ConStatisticsList = xiaofeiguanliMapper.selectConStatistics(map);
			if (ConStatisticsList.size() > 0) {
				// 将第一个对象设为游标
				ConStatistics cursor = ConStatisticsList.get(0);
				Boolean flag = true;
				int port = 0;
				// 合计行
				Map<String, Object> row1 = new HashMap<String, Object>();
				row1.put("RowName", "合计");
				while (flag) {
					Map<String, Object> row = new HashMap<String, Object>();
					if (RowName.equals("ConTypeInnerId")) {
						// 消费类型
						ConType conType = xiaofeiguanliMapper
								.selectConTypeByInnerId(Integer.valueOf(cursor.getRowName()));
						row.put("RowName", conType.getConTypeName());
					} else if (RowName.equals("ConWay")) {
						// 消费途径
						if (cursor.getRowName().equals("0")) {
							row.put("RowName", "POS机消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("RowName", "智能餐盘消费");
						} else if (cursor.getRowName().equals("2")) {
							row.put("RowName", "智能餐台消费");
						} else if (cursor.getRowName().equals("3")) {
							row.put("RowName", "安卓APP消费");
						} else if (cursor.getRowName().equals("4")) {
							row.put("RowName", "IOSAPP消费");
						} else if (cursor.getRowName().equals("5")) {
							row.put("RowName", "微信APP消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("ConPattern")) {
						// 消费模式
						/*if (cursor.getRowName().equals("3")) {
							row.put("RowName", "计次消费");
						} else*/ if (cursor.getRowName().equals("1")) {
							row.put("RowName", "定额消费");
						} else if (cursor.getRowName().equals("2")) {
							row.put("RowName", "计额消费");
						} else if (cursor.getRowName().equals("4")) {
							row.put("RowName", "菜单消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("Year") || RowName.equals("Month") || RowName.equals("Day")) {
						// 按时间分组
						row.put("RowName", cursor.getRowName());
					} else if (RowName.equals("CompanyInnerId")) {
						// 公司
						Department company = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(cursor.getRowName()));
						row.put("RowName", company.getDepartmentName());
					} else if (RowName.equals("ErrorConLog")) {
						// 正常、误消费统计
						if (cursor.getRowName().equals("1")) {
							row.put("RowName", "误消费");
						} else if (cursor.getRowName().equals("0")) {
							row.put("RowName", "正常消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("LimitTimes")) {
						// 限次、不限次统计
						if (cursor.getRowName().equals("0")) {
							row.put("RowName", "不限次消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("RowName", "限次消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("OffLine")) {
						// 脱机、联机统计
						if (cursor.getRowName().equals("0")) {
							row.put("RowName", "脱机消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("RowName", "联机消费");
						} else {
							row.put("RowName", "其它消费");
						}
					}
					Long count = (long) 0;
					for (int i = port; i < ConStatisticsList.size(); i++) {
						ConStatistics conStatisticsNow = ConStatisticsList.get(i);
						if (conStatisticsNow.getRowName().equals(cursor.getRowName())) {
							if (i == ConStatisticsList.size() - 1) {
								flag = false;
							}
							Long Money = conStatisticsNow.getDiscountMoney();
							// 此对象和游标对象是同一行
							count += Money;
							row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
							if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
								row.put("app" + conStatisticsNow.getAppInnerId(),
										Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
							} else {
								row.put("app" + conStatisticsNow.getAppInnerId(), Money);
							}
							if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
								row.put("area" + conStatisticsNow.getAreaInnerId(),
										Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
							} else {
								row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
							}
							// 合计行数据
							if (row1.get("merchant" + conStatisticsNow.getMerchantInnerId()) != null) {
								row1.put("merchant" + conStatisticsNow.getMerchantInnerId(),
										Money + (Long) row1.get("merchant" + conStatisticsNow.getMerchantInnerId()));
							} else {
								row1.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
							}
							if (row1.get("app" + conStatisticsNow.getAppInnerId()) != null) {
								row1.put("app" + conStatisticsNow.getAppInnerId(),
										Money + (Long) row1.get("app" + conStatisticsNow.getAppInnerId()));
							} else {
								row1.put("app" + conStatisticsNow.getAppInnerId(), Money);
							}
							if (row1.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
								row1.put("area" + conStatisticsNow.getAreaInnerId(),
										Money + (Long) row1.get("area" + conStatisticsNow.getAreaInnerId()));
							} else {
								row1.put("area" + conStatisticsNow.getAreaInnerId(), Money);
							}
						} else {
							// 此对象和游标对象不是同一行
							// 游标下移
							cursor = conStatisticsNow;
							port = i;
							break;
						}
					}
					// 合计行数据

					if (row1.get("count") != null) {
						row1.put("count", count + (Long) row1.get("count"));
					} else {
						row1.put("count", count);
					}
					row.put("count", count);
					// 最后一行合计

					rows.add(row);
				}
				rows.add(row1);
			}
		}
		if (Type.equals("sum")) {
			// 按金额统计除100
			for (Map<String, Object> row : rows) {
				for (Map.Entry<String, Object> entry : row.entrySet()) {
					if (!entry.getKey().equals("RowName")) {
						entry.setValue(((Long) entry.getValue()) / 100.0);
					}
				}
			}
		}
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConStatisticsExcel(HttpServletRequest request) throws Exception {
		String RowName = request.getParameter("RowName");
		String Type = request.getParameter("Type");
		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String departmentInnerId = request.getParameter("DepartmentInnerId");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String TimeType = request.getParameter("TimeType");
		String ConTypeList = request.getParameter("ConTypeList");
		String ConWay = request.getParameter("ConWay");
		String ConPattern = request.getParameter("ConPattern");
		if (RowName.equals("Money")) {
			// 按现金补贴只能统计金额
			Type = "sum";
		}
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		/*StringBuilder DepartmentInnerId = new StringBuilder();*/
		map.put("DepartmentInnerId", departmentInnerId);
		/*if(operator.getOperatorId().equals("admin")){
		}else{
			if (departmentInnerId != null) {
				String departmentList = operator.getDepartmentList();
				String[] ccdepartments = departmentList.split(","); // 存储的部门权限
				String[] crdepartments = departmentInnerId.split(","); // 前台传入的要查询的部门
				for (String department : crdepartments) {
					for (String department1 : ccdepartments) {
						if (department.equals(department1)) {
							DepartmentInnerId.append(department + ",");
							break;
						}
					}
				}
				map.put("DepartmentInnerId", DepartmentInnerId.toString());
			}else{
				map.put("DepartmentInnerId", operator.getDepartmentList());
			}
		}*/
		Con_OperatorPer operatorPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operator.getOperatorInnerId());
		if (operatorPer != null) {
			if(operatorPer.getOperatorId().equals("admin")){
				map.put("MerchantList", "");
			} else {
				if(operatorPer.getMerchantList() != null){
					map.put("MerchantList", operatorPer.getMerchantList());
				}else{
					map.put("MerchantList", "100000,");
				}
			}
		} else {
			map.put("MerchantList", "100000,");
		}
		map.put("UserName", UserName);
		map.put("UserId", UserId);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", TimeType);
		map.put("ConTypeList", ConTypeList);
		map.put("ConWay", ConWay);
		map.put("ConPattern", ConPattern);
		map.put("Type", Type);
		if (RowName.equals("Money")) {
			// 按现金补贴统计
			List<ConStatistics> ConStatisticsList = xiaofeiguanliMapper.selectConStatistics(map);
			if (ConStatisticsList.size() > 0) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("统计方式", "现金消费");
				Long count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getPersonMoney();
					count += Money;
					Merchant merchant = xiaofeiguanliMapper
							.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
					row.put(merchant.getMerchantName(), Money);
				}
				row.put("合计", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("统计方式", "补贴消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getAllowanceMoney();
					count += Money;
					Merchant merchant = xiaofeiguanliMapper
							.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
					row.put(merchant.getMerchantName(), Money);
				}
				row.put("合计", count);
				rows.add(row);

				/*row = new HashMap<String, Object>();
				row.put("统计方式", "计次消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getNumberMoney();
					count += Money;
					Merchant merchant = xiaofeiguanliMapper
							.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
					row.put(merchant.getMerchantName(), Money);
				}
				row.put("合计", count);
				rows.add(row);*/

				row = new HashMap<String, Object>();
				row.put("统计方式", "商品原价");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getMoney();
					count += Money;
					Merchant merchant = xiaofeiguanliMapper
							.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
					row.put(merchant.getMerchantName(), Money);
				}
				row.put("合计", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("统计方式", "实际消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getDiscountMoney();
					count += Money;
					Merchant merchant = xiaofeiguanliMapper
							.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
					row.put(merchant.getMerchantName(), Money);
				}
				row.put("合计", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("统计方式", "总计");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getDiscountMoney();
					count += Money;
					Merchant merchant = xiaofeiguanliMapper
							.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
					row.put(merchant.getMerchantName(), Money);
				}
				row.put("合计", count);
				rows.add(row);
			}
		} else {
			map.put("RowName", RowName);
			List<ConStatistics> ConStatisticsList = xiaofeiguanliMapper.selectConStatistics(map);
			if (ConStatisticsList.size() > 0) {
				// 将第一个对象设为游标
				ConStatistics cursor = ConStatisticsList.get(0);
				Boolean flag = true;
				int port = 0;
				// 合计行
				Map<String, Object> row1 = new HashMap<String, Object>();
				row1.put("统计方式", "合计");
				while (flag) {
					Map<String, Object> row = new HashMap<String, Object>();
					if (RowName.equals("ConTypeInnerId")) {
						// 消费类型
						ConType conType = xiaofeiguanliMapper
								.selectConTypeByInnerId(Integer.valueOf(cursor.getRowName()));
						row.put("统计方式", conType.getConTypeName());
					} else if (RowName.equals("ConWay")) {
						// 消费途径
						if (cursor.getRowName().equals("0")) {
							row.put("统计方式", "POS机消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("统计方式", "智能餐盘消费");
						} else if (cursor.getRowName().equals("2")) {
							row.put("统计方式", "智能餐台消费");
						} else if (cursor.getRowName().equals("3")) {
							row.put("统计方式", "安卓APP消费");
						} else if (cursor.getRowName().equals("4")) {
							row.put("统计方式", "IOSAPP消费");
						} else if (cursor.getRowName().equals("5")) {
							row.put("统计方式", "微信APP消费");
						} else {
							row.put("统计方式", "其它消费");
						}
					}  else if (RowName.equals("ConPattern")) {
						// 消费模式
						/*if (cursor.getRowName().equals("3")) {
							row.put("RowName", "计次消费");
						} else*/ if (cursor.getRowName().equals("1")) {
							row.put("统计方式", "定额消费");
						} else if (cursor.getRowName().equals("2")) {
							row.put("统计方式", "计额消费");
						} else if (cursor.getRowName().equals("4")) {
							row.put("统计方式", "菜单消费");
						} else {
							row.put("统计方式", "其它消费");
						}
					} else if (RowName.equals("Year") || RowName.equals("Month") || RowName.equals("Day")) {
						// 按时间分组
						row.put("统计方式", cursor.getRowName());
					} else if (RowName.equals("CompanyInnerId")) {
						// 公司
						Department company = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(cursor.getRowName()));
						row.put("统计方式", company.getDepartmentName());
					} else if (RowName.equals("ErrorConLog")) {
						// 正常、误消费统计
						if (cursor.getRowName().equals("1")) {
							row.put("统计方式", "误消费");
						} else if (cursor.getRowName().equals("0")) {
							row.put("统计方式", "正常消费");
						} else {
							row.put("统计方式", "其它消费");
						}
					} else if (RowName.equals("LimitTimes")) {
						// 限次、不限次统计
						if (cursor.getRowName().equals("0")) {
							row.put("统计方式", "不限次消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("统计方式", "限次消费");
						} else {
							row.put("统计方式", "其它消费");
						}
					} else if (RowName.equals("OffLine")) {
						// 脱机、联机统计
						if (cursor.getRowName().equals("0")) {
							row.put("统计方式", "脱机消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("统计方式", "联机消费");
						} else {
							row.put("统计方式", "其它消费");
						}
					}
					Long count = (long) 0;
					for (int i = port; i < ConStatisticsList.size(); i++) {
						ConStatistics conStatisticsNow = ConStatisticsList.get(i);
						if (conStatisticsNow.getRowName().equals(cursor.getRowName())) {
							if (i == ConStatisticsList.size() - 1) {
								flag = false;
							}
							Long Money = conStatisticsNow.getDiscountMoney();
							// 此对象和游标对象是同一行
							count += Money;
							Merchant merchant = xiaofeiguanliMapper
									.selectMerchantByInnerId(conStatisticsNow.getMerchantInnerId());
							row.put(merchant.getMerchantName(), Money);
							// 合计行数据
							if (row1.get(merchant.getMerchantName()) != null) {
								row1.put(merchant.getMerchantName(),
										Money + (Long) row1.get(merchant.getMerchantName()));
							} else {
								row1.put(merchant.getMerchantName(), Money);
							}
						} else {
							// 此对象和游标对象不是同一行
							// 游标下移
							cursor = conStatisticsNow;
							port = i;
							break;
						}
					}
					// 合计行数据

					if (row1.get("合计") != null) {
						row1.put("合计", count + (Long) row1.get("合计"));
					} else {
						row1.put("合计", count);
					}
					row.put("合计", count);
					// 最后一行合计

					rows.add(row);
				}
				rows.add(row1);
			}
		}
		if (Type.equals("sum")) {
			// 按金额统计除100
			for (Map<String, Object> row : rows) {
				for (Map.Entry<String, Object> entry : row.entrySet()) {
					System.out.println(entry.getKey() + "---------" + entry.getValue());
					if (!entry.getKey().equals("统计方式")) {
						entry.setValue(((Long) entry.getValue()) / 100.0);
					}
				}
			}
		}

		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/ConsumptionStatistics" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		String header0 = "消费统计表";
		String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
		List<String> headerList = new ArrayList<String>();
		headerList.add(header0);
		headerList.add(header1);
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("统计方式");

		// 查询商户
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("rows", 100000000);
		map1.put("order", "asc");
		map1.put("sort", "MerchantInnerId");
		map1.put("start", 0);
		List<Merchant> merchantList = xiaofeiguanliMapper.selectMerchant(map1);
		for (Merchant merchant : merchantList) {
			headers0.add(merchant.getMerchantName());
		}
		headers0.add("合计");
		eeu.selectConsumptionStatisticsExcel(workbook, 0, "充减值统计表", headerList, headers0, rows, out);
		workbook.write(out);
		out.close();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConOperatorPer(DataGridModel dgm, Con_OperatorPer con_OperatorPer) {
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
		map.put("OperatorId", con_OperatorPer.getOperatorId());
		map.put("OperatorName", con_OperatorPer.getOperatorName());
		map.put("Remark", con_OperatorPer.getRemark());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Con_OperatorPer> operatorPerList = xiaofeiguanliMapper.selectConOperatorPer(map);
		for (Con_OperatorPer operatorPer : operatorPerList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("OperatorInnerId", operatorPer.getOperatorInnerId());
			row.put("OperatorId", operatorPer.getOperatorId());
			row.put("OperatorName", operatorPer.getOperatorName());
			row.put("MerchantList", operatorPer.getMerchantList());
			String MerchantNameList = "";
			if (operatorPer.getMerchantList() != null && !operatorPer.getMerchantList().equals("")) {
				String[] MerchantList = operatorPer.getMerchantList().split(",");
				for (String MerchantId : MerchantList) {
					Merchant Merchant = xiaofeiguanliMapper.selectMerchantByInnerId(Integer.valueOf(MerchantId));
					if (Merchant != null) {
						MerchantNameList += Merchant.getMerchantName() + ",";
					}
				}
			}
			row.put("MerchantNameList", MerchantNameList);
			row.put("ConTerminalList", operatorPer.getConTerminalList());
			row.put("Remark", operatorPer.getRemark());
			row.put("StandbyA", operatorPer.getStandbyA());
			row.put("StandbyB", operatorPer.getStandbyB());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectConOperatorPerTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateConOperatorPer(ConOperatorPer conOperatorPer, HttpServletRequest request) {
		Integer result = xiaofeiguanliMapper.updateConOperatorPer(conOperatorPer);
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
	public String selectMerchantDiscount(DataGridModel dgm, HttpServletRequest request) {
		String MerchantDiscountId = request.getParameter("MerchantDiscountId");
		String MerchantDiscountName = request.getParameter("MerchantDiscountName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("MerchantDiscountInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("MerchantDiscountId", MerchantDiscountId);
		map.put("MerchantDiscountName", MerchantDiscountName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<MerchantDiscount> MerchantDiscountList = xiaofeiguanliMapper.selectMerchantDiscount(map);
		for (MerchantDiscount MerchantDiscount1 : MerchantDiscountList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("BeginTime", MerchantDiscount1.getBeginTime().substring(0, 19));
			row.put("EndTime", MerchantDiscount1.getEndTime().substring(0, 19));

			row.put("MerchantDiscountInnerId", MerchantDiscount1.getMerchantDiscountInnerId());
			row.put("MerchantDiscountId", MerchantDiscount1.getMerchantDiscountId());
			row.put("MerchantDiscountName", MerchantDiscount1.getMerchantDiscountName());
			row.put("MerchantInnerId", MerchantDiscount1.getMerchantInnerId());
			Merchant Merchant = xiaofeiguanliMapper.selectMerchantByInnerId(MerchantDiscount1.getMerchantInnerId());
			if (Merchant != null) {
				row.put("MerchantName", Merchant.getMerchantName());
			}
			row.put("UserTypeList", MerchantDiscount1.getUserTypeList());
			String[] UserTypeList = MerchantDiscount1.getUserTypeList().split(",");
			String UserTypeListName = "";
			for (String string : UserTypeList) {
				UserType UserType = peizhiguanliMapper.selectUserTypeByInnerId(Integer.valueOf(string));
				if (UserType != null) {
					UserTypeListName += UserType.getUserTypeName() + ",";
				}
			}
			row.put("UserTypeListName", UserTypeListName);
			row.put("Proportion", MerchantDiscount1.getProportion() / 100.0);
			row.put("Remark", MerchantDiscount1.getRemark());
			row.put("StandbyA", MerchantDiscount1.getStandbyA());
			row.put("StandbyB", MerchantDiscount1.getStandbyB());
			row.put("StandbyC", MerchantDiscount1.getStandbyC());
			row.put("StandbyD", MerchantDiscount1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectMerchantDiscountTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertMerchantDiscount(MerchantDiscount MerchantDiscount, HttpServletRequest request) {
		MerchantDiscount.setStandbyD("1");
		Integer result = xiaofeiguanliMapper.insertMerchantDiscount(MerchantDiscount);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "添加成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateMerchantDiscount(MerchantDiscount MerchantDiscount, HttpServletRequest request) {
		Integer result = xiaofeiguanliMapper.updateMerchantDiscount(MerchantDiscount);
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
	public String deleteMerchantDiscount(MerchantDiscount MerchantDiscount, HttpServletRequest request) {
		MerchantDiscount.setStandbyD("1");
		Integer result = xiaofeiguanliMapper.deleteMerchantDiscount(MerchantDiscount);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "删除成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "删除失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectConDiscount(DataGridModel dgm, HttpServletRequest request) {
		String ConDiscountId = request.getParameter("ConDiscountId");
		String ConDiscountName = request.getParameter("ConDiscountName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ConDiscountInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("ConDiscountId", ConDiscountId);
		map.put("ConDiscountName", ConDiscountName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ConDiscount> ConDiscountList = xiaofeiguanliMapper.selectConDiscount(map);
		for (ConDiscount ConDiscount1 : ConDiscountList) {
			Map<String, Object> row = new HashMap<String, Object>();

			row.put("ConDiscountInnerId", ConDiscount1.getConDiscountInnerId());
			row.put("ConDiscountId", ConDiscount1.getConDiscountId());
			row.put("ConDiscountName", ConDiscount1.getConDiscountName());
			row.put("UserTypeList", ConDiscount1.getUserTypeList());
			String[] UserTypeList = ConDiscount1.getUserTypeList().split(",");
			String UserTypeListName = "";
			for (String string : UserTypeList) {
				UserType UserType = peizhiguanliMapper.selectUserTypeByInnerId(Integer.valueOf(string));
				if (UserType != null) {
					UserTypeListName += UserType.getUserTypeName() + ",";
				}
			}
			row.put("UserTypeListName", UserTypeListName);
			row.put("Proportion1", ConDiscount1.getProportion1() / 100);
			row.put("Money1", ConDiscount1.getMoney1() / 100);
			row.put("Proportion2", ConDiscount1.getProportion2() / 100);
			row.put("Money2", ConDiscount1.getMoney2() / 100);
			row.put("Proportion3", ConDiscount1.getProportion3() / 100);
			row.put("Money3", ConDiscount1.getMoney3() / 100);
			row.put("Remark", ConDiscount1.getRemark());
			row.put("StandbyA", ConDiscount1.getStandbyA());
			row.put("StandbyB", ConDiscount1.getStandbyB());
			row.put("StandbyC", ConDiscount1.getStandbyC());
			row.put("StandbyD", ConDiscount1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectConDiscountTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertConDiscount(ConDiscount ConDiscount, HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		String[] UserTypeList = ConDiscount.getUserTypeList().split(",");
		Boolean flag = true;
		for (String string : UserTypeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "ConDiscountInnerId");
			map.put("start", 0);
			map.put("UserTypeInnerId", Integer.valueOf(string));
			List<ConDiscount> ConDiscountList = xiaofeiguanliMapper.selectConDiscount(map);
			if (!ConDiscountList.isEmpty()) {
				flag = false;
				break;
			}
		}
		if (flag) {
			ConDiscount.setStandbyD("1");
			Integer result = xiaofeiguanliMapper.insertConDiscount(ConDiscount);

			if (result > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 501);
			jo.put("msg", "人员类型有重复，不能添加！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String deleteConDiscount(ConDiscount ConDiscount, HttpServletRequest request) {
		ConDiscount.setStandbyD("1");
		Integer result = xiaofeiguanliMapper.deleteConDiscount(ConDiscount);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "删除成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "删除失败！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectTimeRule(DataGridModel dgm, HttpServletRequest request) {
		String TimeRuleId = request.getParameter("TimeRuleId");
		String TimeRuleName = request.getParameter("TimeRuleName");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("TimeRuleInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("TimeRuleId", TimeRuleId);
		map.put("TimeRuleName", TimeRuleName);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<TimeRule> TimeRuleList = xiaofeiguanliMapper.selectTimeRule(map);
		for (TimeRule TimeRule1 : TimeRuleList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("TimeRuleInnerId", TimeRule1.getTimeRuleInnerId());
			row.put("TimeRuleId", TimeRule1.getTimeRuleId());
			row.put("TimeRuleName", TimeRule1.getTimeRuleName());
			row.put("MerchantInnerId", TimeRule1.getMerchantInnerId());
			Merchant Merchant = xiaofeiguanliMapper.selectMerchantByInnerId(TimeRule1.getMerchantInnerId());
			if (Merchant != null) {
				row.put("MerchantName", Merchant.getMerchantName());
			}

			row.put("Minutes", TimeRule1.getMinutes());
			row.put("Money", TimeRule1.getMoney() / 100.0);
			row.put("Intpart", TimeRule1.getIntpart());
			if (TimeRule1.getIntpart() == 1) {
				row.put("IntpartName", "四舍五入");
			} else if (TimeRule1.getIntpart() == 2) {
				row.put("IntpartName", "舍去余数");
			} else if (TimeRule1.getIntpart() == 3) {
				row.put("IntpartName", "余数加一");
			}
			row.put("Timeout", TimeRule1.getTimeout());
			row.put("MinMoney", TimeRule1.getMinMoney());
			row.put("Freetime", TimeRule1.getFreetime());

			row.put("Remark", TimeRule1.getRemark());
			row.put("StandbyA", TimeRule1.getStandbyA());
			row.put("StandbyB", TimeRule1.getStandbyB());
			row.put("StandbyC", TimeRule1.getStandbyC());
			row.put("StandbyD", TimeRule1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectTimeRuleTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertTimeRule(TimeRule TimeRule, HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		List<TimeRule> TimeRuleList = xiaofeiguanliMapper
				.selectTimeRuleByMerchantInnerId(TimeRule.getMerchantInnerId());
		if (TimeRuleList.isEmpty()) {
			Integer result = xiaofeiguanliMapper.insertTimeRule(TimeRule);
			if (result > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 500);
			jo.put("msg", "此商户已经有计时消费规则，不能重复添加！");
		}

		return jo.toString();
	}

	@Override
	@Transactional
	public String updateTimeRule(TimeRule TimeRule, HttpServletRequest request) {
		Integer result = xiaofeiguanliMapper.updateTimeRule(TimeRule);
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
	public String selectConDeviceNums(TimeRule timeRule, HttpServletRequest request) {
		Integer result = xiaofeiguanliMapper.selectConDeviceNums();
		Integer nums = xiaofeiguanliMapper.selectMaxConDeviceNums();
		JSONObject jo = new JSONObject();
		if (result >= nums) {
			jo.put("code", 500);
			jo.put("msg", "超过最大限制，不允许继续添加！");
		} else {
			jo.put("code", 200);
		}
		return jo.toString();
	}

	@Override
	public String selectMenuTypeBox(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "FMenuTypeInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		map.put("merchantList", request.getParameter("FMerchantInnerId"));
		List<MenuType> MenuTypes = xiaofeiguanliMapper.selectMenuType(map);
		for (MenuType Merchant : MenuTypes) {
			jo = new JSONObject();
			jo.put("id", Merchant.getFMenuTypeInnerId());
			jo.put("text", Merchant.getFMenuTypeName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	public String selectMenuList(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("FMenuInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("FMenuId", request.getParameter("FMenuId"));
		map.put("FMenuName", request.getParameter("FMenuName"));
		map.put("FMenuTypeInnerId", request.getParameter("FMenuTypeInnerId"));
		map.put("FRemark", request.getParameter("FRemark"));
		map.put("FStandbyA", request.getParameter("FStandbyA"));
		map.put("FStandbyB", request.getParameter("FStandbyB"));
		map.put("FEnableFlag", request.getParameter("FEnableFlag"));
		List<MenuList> menuList = xiaofeiguanliMapper.selectMenuList(map);
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (MenuList menu : menuList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("FMenuInnerId", menu.getFMenuInnerId());
			row.put("FMenuId", menu.getFMenuId());
			row.put("FMenuName", menu.getFMenuName());
			row.put("FMenuTypeInnerId", menu.getFMenuTypeInnerId());
			row.put("FEnableFlag", menu.getFEnableFlag());
			if (menu.getFEnableFlag() == 0) {
				row.put("FEnableFlagName", "启用");
			} else {
				row.put("FEnableFlagName", "禁用");
			}
			MenuType menuType = xiaofeiguanliMapper.selectFMenuTypeNameByMenuTypeInnerId(menu.getFMenuTypeInnerId());
			row.put("FMenuTypeName", menuType.getFMenuTypeName());
			row.put("FMerchantInnerId", menuType.getFMerchantInnerId());
			Merchant merchant = xiaofeiguanliMapper.selectMerchantByInnerId(menuType.getFMerchantInnerId());
			if( merchant!=null){
				row.put("MerchantName",merchant.getMerchantName());
			}else{
				row.put("MerchantName","");
			}
		
			row.put("FMenuPrice", menu.getFMenuPrice() / 100.0);
			row.put("FRemark", menu.getFRemark());
			row.put("FStandbyA", menu.getFStandbyA());
			row.put("FStandbyB", menu.getFStandbyB());
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows", rows);
		jo.put("total", xiaofeiguanliMapper.selectMenuListTotal(map));
		return jo.toString();
	}

	@Override
	public String insertMenuList(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FMenuId", request.getParameter("FMenuId"));
		map.put("FMenuName", request.getParameter("FMenuName"));
		map.put("FMenuTypeInnerId", request.getParameter("FMenuTypeInnerId"));
		System.out.println(request.getParameter("FMenuPrice"));
		map.put("FMenuPrice", request.getParameter("FMenuPrice"));
		map.put("FRemark", request.getParameter("FRemark"));
		map.put("FStandbyA", request.getParameter("FStandbyA"));
		map.put("FStandbyB", request.getParameter("FStandbyB"));
		map.put("FEnableFlag", request.getParameter("FEnableFlag"));
		JSONObject jo = new JSONObject();
		Integer result = xiaofeiguanliMapper.insertMenuList(map);
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "添加成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "添加失败！");
		}
		return jo.toString();
	}

	@Override
	public String updateMenuList(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FMenuInnerId", request.getParameter("FMenuInnerId"));
		map.put("FMenuId", request.getParameter("FMenuId"));
		map.put("FMenuName", request.getParameter("FMenuName"));
		map.put("FMenuTypeInnerId", request.getParameter("FMenuTypeInnerId"));
		map.put("FMenuPrice", request.getParameter("FMenuPrice"));
		map.put("FRemark", request.getParameter("FRemark"));
		map.put("FStandbyA", request.getParameter("FStandbyA"));
		map.put("FStandbyB", request.getParameter("FStandbyB"));
		map.put("FEnableFlag", request.getParameter("FEnableFlag"));
		JSONObject jo = new JSONObject();
		Integer result = xiaofeiguanliMapper.updateMenuList(map);
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
	public String selectMealPrice(DataGridModel dgm, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("MealPriceInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<MealPrice> MealPriceList = xiaofeiguanliMapper.selectMealPrice(map);
		for (MealPrice MealPrice1 : MealPriceList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("MealPriceInnerId", MealPrice1.getMealPriceInnerId());
			row.put("UserTypeList", MealPrice1.getUserTypeList());
			String[] UserTypeList = MealPrice1.getUserTypeList().split(",");
			String UserTypeListName = "";
			for (String string : UserTypeList) {
				UserType UserType = peizhiguanliMapper.selectUserTypeByInnerId(Integer.valueOf(string));
				if (UserType != null) {
					UserTypeListName += UserType.getUserTypeName() + ",";
				}
			}
			row.put("UserTypeListName", UserTypeListName);

			row.put("MerchantList", MealPrice1.getMerchantList());
			String[] MerchantList = MealPrice1.getMerchantList().split(",");
			String MerchantListName = "";
			for (String string : MerchantList) {
				Merchant Merchant = xiaofeiguanliMapper.selectMerchantByInnerId(Integer.valueOf(string));
				if (Merchant != null) {
					MerchantListName += Merchant.getMerchantName() + ",";
				}
			}
			row.put("MerchantListName", MerchantListName);

			row.put("BreakfastFirst", MealPrice1.getBreakfastFirst() / 100);
			row.put("BreakfastNext", MealPrice1.getBreakfastNext() / 100);
			row.put("BreakfastNums", MealPrice1.getBreakfastNums());
			row.put("LunchFirst", MealPrice1.getLunchFirst() / 100);
			row.put("LunchNext", MealPrice1.getLunchNext() / 100);
			row.put("LunchNums", MealPrice1.getLunchNums());
			row.put("DinnerFirst", MealPrice1.getDinnerFirst() / 100);
			row.put("DinnerNext", MealPrice1.getDinnerNext() / 100);
			row.put("DinnerNums", MealPrice1.getDinnerNums());
			row.put("NightFirst", MealPrice1.getNightFirst() / 100);
			row.put("NightNext", MealPrice1.getNightNext() / 100);
			row.put("NightNums", MealPrice1.getNightNums());
			row.put("Remark", MealPrice1.getRemark());
			row.put("StandbyA", MealPrice1.getStandbyA());
			row.put("StandbyB", MealPrice1.getStandbyB());
			row.put("StandbyC", MealPrice1.getStandbyC());
			row.put("StandbyD", MealPrice1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectMealPriceTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertMealPrice(MealPrice MealPrice, HttpServletRequest request) {
		// 验证是否重复
		String[] UserTypeList = MealPrice.getUserTypeList().split(",");
		String[] MerchantList = MealPrice.getMerchantList().split(",");
		Map<String, Object> map;
		Boolean flag = true;
		for (String UserType : UserTypeList) {
			for (String Merchant : MerchantList) {
				map = new HashMap<String, Object>();
				map.put("MealPriceInnerId", 0);
				map.put("UserTypeInnerId", Integer.valueOf(UserType));
				map.put("MerchantInnerId", Integer.valueOf(Merchant));
				Integer count = xiaofeiguanliMapper.verifyMealPrice(map);
				if (count > 0) {
					flag = false;
					break;
				}
			}
		}
		JSONObject jo = new JSONObject();
		if (flag) {
			Integer result = xiaofeiguanliMapper.insertMealPrice(MealPrice);
			if (result > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
		} else {
			jo.put("code", 501);
			jo.put("msg", "商户和人员类型不能重复设置！");
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateMealPrice(MealPrice MealPrice, HttpServletRequest request) {
		// 验证是否重复
		String[] UserTypeList = MealPrice.getUserTypeList().split(",");
		String[] MerchantList = MealPrice.getMerchantList().split(",");
		Map<String, Object> map;
		Boolean flag = true;
		for (String UserType : UserTypeList) {
			for (String Merchant : MerchantList) {
				map = new HashMap<String, Object>();
				map.put("MealPriceInnerId", MealPrice.getMealPriceInnerId());
				map.put("UserTypeInnerId", Integer.valueOf(UserType));
				map.put("MerchantInnerId", Integer.valueOf(Merchant));
				Integer count = xiaofeiguanliMapper.verifyMealPrice(map);
				if (count > 0) {
					flag = false;
					break;
				}
			}
		}
		JSONObject jo = new JSONObject();
		if (flag) {
			Integer result = xiaofeiguanliMapper.updateMealPrice(MealPrice);
			if (result > 0) {
				jo.put("code", 200);
				jo.put("msg", "修改成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "修改失败！");
			}
		} else {
			jo.put("code", 501);
			jo.put("msg", "商户和人员类型不能重复设置！");
		}

		return jo.toString();
	}

	@Override
	@Transactional
	public String deleteMealPrice(MealPrice MealPrice, HttpServletRequest request) {
		Integer result = xiaofeiguanliMapper.deleteMealPrice(MealPrice);
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			jo.put("msg", "删除成功！");
		} else {
			jo.put("code", 500);
			jo.put("msg", "删除失败！");
		}
		return jo.toString();
	}

	@Override
	public String selectFMenuType(DataGridModel dgm, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String FMenuTypeId = request.getParameter("FMenuTypeId");
		String FMenuTypeName = request.getParameter("FMenuTypeName");
		String FRemark = request.getParameter("FRemark");
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("FMenuTypeInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("FMenuTypeId", FMenuTypeId);
		map.put("FMenuTypeName", FMenuTypeName);
		map.put("FRemark", FRemark);
		JSONObject jo = new JSONObject();
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		if (!operator.getOperatorId().equals("admin")) {
			Integer operatorInnerId = operator.getOperatorInnerId();
			String merchantList = xiaofeiguanliMapper.selectMerchantByOperatorInnerId(operatorInnerId);
			map.put("merchantList", merchantList);
		} else {
			map.put("merchantList", "");
		}
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<MenuType> menuTypeList = xiaofeiguanliMapper.selectMenuType(map);
		for (MenuType menuType : menuTypeList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("FMenuTypeId", menuType.getFMenuTypeId());
			row.put("FMenuTypeInnerId", menuType.getFMenuTypeInnerId());
			row.put("FMenuTypeName", menuType.getFMenuTypeName());
			row.put("FRemark", menuType.getFRemark());
			row.put("FMerchantInnerId", menuType.getFMerchantInnerId());
			Merchant merchant = xiaofeiguanliMapper.selectMerchantByInnerId(menuType.getFMerchantInnerId());
			row.put("MerchantName", merchant.getMerchantName());
			if (menuType.getFEnableFlag() == 0) {
				row.put("FEnableFlagName", "启用");
			} else {
				row.put("FEnableFlagName", "禁用");
			}
			row.put("FEnableFlag", menuType.getFEnableFlag());
			rows.add(row);
		}
		jo.put("total", xiaofeiguanliMapper.selectMenuTypeTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertMenuType(DataGridModel dgm, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer fEnableFlag = Integer.valueOf(request.getParameter("FEnableFlag"));
		map.put("FEnableFlag", request.getParameter("FEnableFlag"));
		map.put("FMenuTypeName", request.getParameter("FMenuTypeName"));
		map.put("FMenuTypeId", request.getParameter("FMenuTypeId"));
		map.put("FRemark", request.getParameter("FRemark"));
		map.put("FMerchantInnerId", request.getParameter("FMerchantInnerId"));
		Integer nums = xiaofeiguanliMapper.selectMenuTypeNumsByFMerchantInnerId(Integer.valueOf(request.getParameter("FMerchantInnerId")));
		JSONObject jo = new JSONObject();
		if(nums>=8 && fEnableFlag == 0){
			jo.put("code", 500);
			jo.put("msg", "添加失败，同一商户菜单启用数量最大为8个");
			return jo.toString();
		}else{
			Integer result = xiaofeiguanliMapper.insertMenuType(map);
			if (result > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
			return jo.toString();
		}
	}

	@Override
	@Transactional
	public String updateMenuType(DataGridModel dgm, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer fEnableFlag = Integer.valueOf(request.getParameter("FEnableFlag"));
		map.put("FEnableFlag", request.getParameter("FEnableFlag"));
		map.put("FMenuTypeName", request.getParameter("FMenuTypeName"));
		map.put("FMenuTypeId", request.getParameter("FMenuTypeId"));
		map.put("FRemark", request.getParameter("FRemark"));
		map.put("FMerchantInnerId", request.getParameter("FMerchantInnerId"));
		map.put("FMenuTypeInnerId", request.getParameter("FMenuTypeInnerId"));
		MenuType menuType = xiaofeiguanliMapper.selectFMenuTypeNameByMenuTypeInnerId(Integer.valueOf(request.getParameter("FMenuTypeInnerId")));
		Integer nums = xiaofeiguanliMapper.selectMenuTypeNumsByFMerchantInnerId(Integer.valueOf(request.getParameter("FMerchantInnerId")));
		JSONObject jo = new JSONObject();
		if(nums>=8 && fEnableFlag == 0 && menuType.getFEnableFlag() == 1){
			jo.put("code", 500);
			jo.put("msg", "修改失败，同一商户菜单启用数量最大为8个");
			return jo.toString();
		}else{
			Integer result = xiaofeiguanliMapper.updateMenuType(map);
			if (result > 0) {
				jo.put("code", 200);
				jo.put("msg", "添加成功！");
			} else {
				jo.put("code", 500);
				jo.put("msg", "添加失败！");
			}
			return jo.toString();
		}
	}

	@Override
	public String menuTypeBox(HttpServletRequest request) {
		System.out.println(request);
		return null;
	}

	@Override
	public String selectTerminalByContype(DataGridModel dgm, HttpServletRequest request) {
		Integer conTypeInnerId = Integer.valueOf(request.getParameter("InnerId"));
		Integer nums = xiaofeiguanliMapper.selectTerminalByConTypeInnerId(conTypeInnerId);
		JSONObject jo = new JSONObject();
		if(nums>0){
			jo.put("code", 500);
			jo.put("msg", "消费类型被占用，不能修改");
		}else{
			jo.put("code", 200);
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String showMenusExcel(HttpServletRequest request)throws IOException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		JSONObject json = new JSONObject();
		String result = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		InputStream fs = multipartFile.getInputStream();
		List<String[]> menusList = readXls(fs);
		String regExp = "^([1-9][\\d]{0,5}|0)(\\.[\\d]{1,2})?$";
		Pattern p = Pattern.compile(regExp);
		int i = 0;
		for (String[] menu : menusList) {
			if(i==0 || i==1){
				i++;
				continue;
			}
			Map<String, Object> row = new HashMap<String, Object>();
			// Validate 0:正常 1：非法 2：警告
			row.put("Validate", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "UserInnerId");
			map.put("start", 0);
			map.put("MerchantName", menu[0].toString());
			map.put("FMenuTypeId", menu[1].toString());
			map.put("FMenuTypeName", menu[2].toString());
			map.put("FMenuId", menu[3].toString());
			map.put("FMenuName", menu[4].toString());
			map.put("FEnableFlag", menu[6].toString());
			Matcher m = p.matcher(String.format("%.1f",Double.parseDouble( menu[5])));
			if (!m.matches()) {
				row.put("MerchantName", menu[0]);
				row.put("MenuTypeId", menu[1]);
				row.put("MenuTypeName", menu[2]);
				row.put("MenuId", menu[3]);
				row.put("MenuName", menu[4]);
				row.put("MenuPrice", String.format("%.1f",Double.parseDouble( menu[5]))+"(非法金额)");
				row.put("EnableFlag", menu[6]);
				row.put("Validate", 1);
			}else{
				map.put("FMenuPrice", (int)(Double.parseDouble(menu[5])*100));
				List<Menus> userList = xiaofeiguanliMapper.selectMenus(map);
				if (userList.size() > 0) {
					row.put("MerchantName", menu[0] + "(此商户菜类下已存在该的菜品)");
					row.put("MenuTypeId", menu[1]);
					row.put("MenuTypeName", menu[2]);
					row.put("MenuId", menu[3]);
					row.put("MenuName", menu[4]);
					row.put("MenuPrice",String.format("%.1f",Double.parseDouble( menu[5])));
					row.put("EnableFlag", menu[6]);
					row.put("Validate", 1);
				} else {
					row.put("MerchantName", menu[0]);
					row.put("MenuTypeId", menu[1]);
					row.put("MenuTypeName", menu[2]);
					row.put("MenuId", menu[3]);
					row.put("MenuName", menu[4]);
					row.put("MenuPrice",String.format("%.1f",Double.parseDouble( menu[5])));
					row.put("EnableFlag", menu[6]);
					//查询商户是否存在
					Merchant metchant = xiaofeiguanliMapper.selectMerchantByMerchantName(map);
					//查询菜类是否存在
					if(metchant != null){
						map.put("FMerchantInnerId", metchant.getMerchantInnerId());
						MenuType menuType = xiaofeiguanliMapper.selectMenuTypeByMenuTypeName(map);
						if(menuType != null){
							if(!(menu[6].equals("0") || menu[6].equals("1"))){
								row.put("EnableFlag", menu[6] + "(内容不合法)");
								row.put("Validate", 1);
							}
						}else{
							if(menu[6].equals("0")||menu[6].equals("1")){
									Map<String, Object> map1 = new HashMap<String, Object>();
									map1.put("FEnableFlag", menu[6]);
									map1.put("FMenuTypeName", menu[2]);
									map1.put("FMenuTypeId", menu[1]);
									map1.put("FRemark", "");
									map1.put("FMerchantInnerId", metchant.getMerchantInnerId());
									Integer nums = xiaofeiguanliMapper.selectMenuTypeNumsByFMerchantInnerId(metchant.getMerchantInnerId());
									if(nums>=8 && menu[6].equals("0")){
										map1.put("EnableFlag", 1);
									}else{
										xiaofeiguanliMapper.insertMenuType(map1);
									}
							}else{
								row.put("EnableFlag", menu[6] + "(内容不合法)");
								row.put("Validate", 1);
							}
						}
					}else{
						row.put("MerchantName", menu[0] + "(此商户不存在)");
						row.put("Validate", 1);
					}
				}
			}
			rows.add(row);
		}
		json.put("rows", rows);
		result = json.toString();
		return result;
	}

	public List<String[]> readXls(InputStream file) throws IOException {
		// List<String[]> 的元素 行数组String[]为excel中的每一行
		List<String[]> list = new ArrayList<String[]>();
		// 将is流实例到 一个excel流里
		XSSFWorkbook hwk = new XSSFWorkbook(file);
		file.close();
		// 得到book第一个工作薄sheet
		XSSFSheet sh = hwk.getSheetAt(0);
		// 总行数
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		// 从头标题下一行开始
		UpLoadExcel uploadExcel = new UpLoadExcel();
		for (int i = 1; i < rows; i++) {
			XSSFRow row = sh.getRow(i);
			int cols = row.getLastCellNum() + 1 - row.getFirstCellNum(); // 该行的总列数
			String[] str = new String[cols]; // 用来存放该行每一列的值
			for (int j = 0; j < cols; j++) {
				Cell col = row.getCell((short) j);
				String colum = uploadExcel.getValue(col);// 对列值进行处理
				// String colum = UpLoadExcel.getStringCellValue(col);
				Cell colNext = row.getCell((short) (j + 1));
				uploadExcel.getValue(colNext);
				// UpLoadExcel.getStringCellValue(colNext);
				if (col != null) { // 该列不为空，直接读到 行数组里
					str[j] = colum.toString();
				} else { // 该列为空
					// 该列的后面一列不为空，用空字符串占位
					if (colNext != null) {
						Object colValue = "";
						str[j] = colValue.toString();
					}
				}
			}
			list.add(str);
		}
		return list;
	}

	@Override
	@Transactional
	public String importMenusExcel(HttpServletRequest request) throws Exception {
		Boolean flag = true;
		JSONObject jsonObjects = new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		String fileName = multipartFile.getOriginalFilename();
		// 截取文件名的后三位字符
		String fileType = fileName.substring(fileName.length() - 3, fileName.length());
		System.out.println("导入的文件名 ：" + fileName + "\t 文件后缀名：" + fileType);
		InputStream fs = multipartFile.getInputStream();
		// 导入操作
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(fs);
		// 循环工作表Sheet
		XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		// 循环行Row
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			if(rowNum == 1 || rowNum == 2){
				continue;
			}
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rows", 100000000);
				map.put("order", "asc");
				map.put("sort", "UserInnerId");
				map.put("start", 0);
				map.put("MerchantName", hssfRow.getCell(0).toString());
				map.put("FMenuTypeId", hssfRow.getCell(1).toString());
				map.put("FMenuId", hssfRow.getCell(3).toString());
				map.put("FMenuName", hssfRow.getCell(4).toString());
				map.put("FEnableFlag",(int) (Double.parseDouble(hssfRow.getCell(6).toString())));
				map.put("FMenuPrice",(int) (Double.parseDouble(hssfRow.getCell(5).toString()) * 100));
				map.put("FMenuTypeName", hssfRow.getCell(2).toString());
				List<Menus> menuList = xiaofeiguanliMapper.selectMenus(map);
				if(menuList.size() > 0){
					jsonObjects.put("code", 500);
					jsonObjects.put("msg", "导入数据存在重复！请勿重复导入");
					return jsonObjects.toString();
				}
				Merchant metchant = xiaofeiguanliMapper.selectMerchantByMerchantName(map);
				map.put("FMerchantInnerId", metchant.getMerchantInnerId());
				MenuType menuType = xiaofeiguanliMapper.selectMenuTypeByMenuTypeName(map);
				map.put("FRemark", "");
				if(menuType != null){
					map.put("FMenuTypeInnerId", menuType.getFMenuTypeInnerId());
				}else{
					Integer nums = xiaofeiguanliMapper.selectMenuTypeNumsByFMerchantInnerId(metchant.getMerchantInnerId());
					if(nums>=8 && hssfRow.getCell(6).equals("0")){
						map.put("EnableFlag", 1);
					}
					Integer result = xiaofeiguanliMapper.insertMenuType(map);
					if(result >0){
						MenuType menuType1 = xiaofeiguanliMapper.selectMenuTypeByMenuTypeName(map);
						map.put("FMenuTypeInnerId", menuType1.getFMenuTypeInnerId());
					}
				}
				Integer result = xiaofeiguanliMapper.insertMenuList(map);
				if(result <= 0){
					flag = false;
				}
			}
		}
		if (flag) {
			jsonObjects.put("code", 200);
			jsonObjects.put("msg", "导入成功！");
		} else {
			jsonObjects.put("code", 500);
			jsonObjects.put("msg", "导入失败！");
		}
		return jsonObjects.toString();
	}

	@Override
	public String selectInvoiceLog(HttpServletRequest request, DataGridModel dgm) {
		Map<String, Object> map = new HashMap<String, Object>();
		String UserName = request.getParameter("UserName");
		String UserId = request.getParameter("UserId");
		String StandbyB = request.getParameter("StandbyB");
		/*String departmentInnerId = request.getParameter("DepartmentInnerId");*/
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		/*StringBuilder DepartmentInnerId = new StringBuilder();*/
		/*if(operator.getOperatorId().equals("admin")){
			map.put("DepartmentInnerId", departmentInnerId);
		}else{
			if (departmentInnerId != null) {
				String departmentList = operator.getDepartmentList();
				String[] ccdepartments = departmentList.split(","); // 存储的部门权限
				String[] crdepartments = departmentInnerId.split(","); // 前台传入的要查询的部门
				for (String department : crdepartments) {
					for (String department1 : ccdepartments) {
						if (department.equals(department1)) {
							DepartmentInnerId.append(department + ",");
							break;
						}
					}
				}
				map.put("DepartmentInnerId", DepartmentInnerId.toString());
			}else{
				map.put("DepartmentInnerId", operator.getDepartmentList());
			}
		}*/
		String ConTerminalInnerId = request.getParameter("ConTerminalInnerId");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String ConTypeList = request.getParameter("ConTypeList");
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("ConLogInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		String[] sortS = dgm.getSort().split(",");
		String[] orderS = dgm.getOrder().split(",");
		String sortString = "";
		for (int i = 0; i < sortS.length; i++) {
			if (i == sortS.length - 1) {
				sortString += sortS[i] + " " + orderS[i];
			} else {
				sortString += sortS[i] + " " + orderS[i] + ",";
			}
		}
		Con_OperatorPer operatorPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operator.getOperatorInnerId());
		if (operatorPer != null) {
			if(operatorPer.getOperatorId().equals("admin")){
				map.put("MerchantList", "");
			} else {
				if(operatorPer.getMerchantList() != null){
					map.put("MerchantList", operatorPer.getMerchantList());
				}else{
					map.put("MerchantList", "100000,");
				}
			}
		} else {
			map.put("MerchantList", "100000,");
		}
		map.put("rows", dgm.getRows());
		// map.put("order", dgm.getOrder());
		map.put("sort", sortString);
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("ConTerminalInnerId", ConTerminalInnerId);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", "ConDatetime");
		map.put("ConTypeList", ConTypeList);
		map.put("StandbyB", StandbyB);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<ConLog_User> ConLogList = xiaofeiguanliMapper.selectConLog(map);
		for (ConLog_User conLog : ConLogList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("ConLogInnerId", conLog.getConLogInnerId());
			row.put("UserId", conLog.getUserId());
			row.put("Remark", conLog.getRemark());
			row.put("CompanyName", conLog.getCompanyName());
			row.put("DepartmentName", conLog.getDepartmentName());
			row.put("UserName", conLog.getUserName());
			row.put("Money", conLog.getMoney() / 100.0);
			row.put("DiscountMoney", conLog.getDiscountMoney() / 100.0);
			row.put("Proportion", conLog.getProportion() / 100);
			row.put("ConDatetime", conLog.getConDatetime().substring(0, 19));
			row.put("ConTerminalName", conLog.getConTerminalName());
			row.put("MerchantName", conLog.getMerchantName());
			if(conLog.getStandbyB()==0){
				row.put("StandbyB", "未开票");
			}else if(conLog.getStandbyB()==1){
				row.put("StandbyB", "已开票");
			}
			rows.add(row);
		}
		Long total = xiaofeiguanliMapper.selectConLogTotal(map);
		if (total > 0 && dgm.getRows() * dgm.getPage() >= total) {
			map.put("rows", 100000000);
			map.put("start", 0);
			List<ConLog_User> ConLogList1 = xiaofeiguanliMapper.selectConLog(map);
			Long MoneySum = (long) 0;
			Long DiscountMoneySum = (long) 0;
			Long Count = (long) 0;
			for (ConLog_User conLog : ConLogList1) {
				Count++;
				MoneySum += conLog.getMoney();
				DiscountMoneySum += conLog.getDiscountMoney();
			}
			// 最后一行合计
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserId", "合计");
			row.put("Money", MoneySum / 100.0);
			row.put("DiscountMoney", DiscountMoneySum / 100.0);
			row.put("UserName", Count + "人");
			rows.add(row);
		}
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String updateInvoiceLog(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String ConLogInnerId = request.getParameter("ConLogInnerId");
		System.out.println(ConLogInnerId);
		String money = request.getParameter("Money");
		InvoiceLog invoiceLog = new InvoiceLog();
		invoiceLog.setMoney(Integer.valueOf(money));
		invoiceLog.setOperDT(sdf.format(new Date()));
		invoiceLog.setOperInnerId(operator.getOperatorInnerId());
		invoiceLog.setConLog(ConLogInnerId);
		Integer result = xiaofeiguanliMapper.insertInvoiceLog(invoiceLog);
		JSONObject jo = new JSONObject();
		if(result > 0){
			String[] split = ConLogInnerId.split(",");
			Map<String,Object> map = new HashMap<>();
			map.put("StandbyC", invoiceLog.getInvoiceLogInnerId());
			for (String string : split) {
				map.put("ConLogInnerId", string);
				result = xiaofeiguanliMapper.updateConLogforInvoiceLog(map);
				if(result <= 0){
					jo.put("code", 500);
					jo.put("msg", "开票失败");
				}
			}
			jo.put("code", 200);
			jo.put("msg", "开票完成");
		}else{
			jo.put("code", 500);
			jo.put("msg", "开票失败");
		}
		return jo.toString();
	}

	@Override
	public String selectConMenu(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		String StartTime = request.getParameter("StartTime");
		String ConTerminalInnerId = request.getParameter("ConTerminalInnerId");
		String EndTime = request.getParameter("EndTime");
		String TimeType = request.getParameter("TimeType"); 
		
		map.put("StartTime", StartTime+" 00:00:00");
		map.put("EndTime", EndTime+" 23:59:59");
		map.put("TimeType", TimeType);
		
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		Integer operatorInnerId = operator.getOperatorInnerId();
		Con_OperatorPer operPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operatorInnerId);
		if(operPer == null){
			map.put("ConTerminalInnerId", 11111111);
		}else{
			if(ConTerminalInnerId == null || ConTerminalInnerId.equals("")){
				String merchantList = operPer.getMerchantList();
				if(merchantList != null){
					List<Integer> terminalList = xiaofeiguanliMapper.selectTerminalByMerchantList(merchantList);
					map.put("ConTerminalInnerId", terminalList.toString().replace("[", "").replace("]", ""));
				}else{
					map.put("ConTerminalInnerId", 11111111);
				}
			}else{
				String[] split = ConTerminalInnerId.split(",");
				String merchantList = operPer.getMerchantList();
				if(merchantList != null){
					List<Integer> terminalList = xiaofeiguanliMapper.selectTerminalByMerchantList(merchantList);
					StringBuilder sb = new StringBuilder();
					for (String string : split) {
						for (Integer integer : terminalList) {
							if(string.equals(integer+"")){
								sb.append(string+",");
							}
						}
					}
					map.put("ConTerminalInnerId", sb.toString());
				}else{
					map.put("ConTerminalInnerId", 11111111);
				}
			}
		}
		List<MenuStatistics> conMenuList = xiaofeiguanliMapper.selectConMenuStatistics(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		for (MenuStatistics menuStatistics : conMenuList) {
			Map<String,Object> row = new HashMap<>();
			row.put("MenuName", menuStatistics.getMenuName());
			row.put("Number", menuStatistics.getNumber());
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectLSConStatistics(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String RowName = request.getParameter("RowName");
		String Type = request.getParameter("Type");
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String TimeType = request.getParameter("TimeType");
		String ConTypeList = request.getParameter("ConTypeList");
		String ConWay = request.getParameter("ConWay");
		String ConPattern = request.getParameter("ConPattern");
		if (RowName.equals("Money")) {
			// 按现金补贴只能统计金额
			Type = "sum";
		}
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		Con_OperatorPer operatorPer = xiaofeiguanliMapper.selectConOperatorPerByInnerId(operator.getOperatorInnerId());
		if (operatorPer != null) {
			if(operatorPer.getOperatorId().equals("admin")){
				map.put("MerchantList", "");
			} else {
				if(operatorPer.getMerchantList() != null){
					map.put("MerchantList", operatorPer.getMerchantList());
				}else{
					map.put("MerchantList", "100000,");
				}
			}
		} else {
			map.put("MerchantList", "100000,");
		}
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("TimeType", TimeType);
		map.put("ConTypeList", ConTypeList);
		map.put("ConWay", ConWay);
		map.put("ConPattern", ConPattern);
		map.put("Type", Type);
		if (RowName.equals("Money")) {
			// 按现金补贴统计
			List<ConStatistics> ConStatisticsList = xiaofeiguanliMapper.selectLSConStatistics(map);
			if (ConStatisticsList.size() > 0) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("RowName", "现金消费");
				Long count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getPersonMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "补贴消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getAllowanceMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "商品原价");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "实际消费");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getDiscountMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);

				row = new HashMap<String, Object>();
				row.put("RowName", "总计");
				count = (long) 0;
				for (int i = 0; i < ConStatisticsList.size(); i++) {
					ConStatistics conStatisticsNow = ConStatisticsList.get(i);
					Long Money = conStatisticsNow.getDiscountMoney();
					count += Money;
					row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
					if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
						row.put("app" + conStatisticsNow.getAppInnerId(),
								Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
					} else {
						row.put("app" + conStatisticsNow.getAppInnerId(), Money);
					}
					if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
						row.put("area" + conStatisticsNow.getAreaInnerId(),
								Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
					} else {
						row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
					}
				}
				row.put("count", count);
				rows.add(row);
			}
		} else {
			map.put("RowName", RowName);
			List<ConStatistics> ConStatisticsList = xiaofeiguanliMapper.selectLSConStatistics(map);
			if (ConStatisticsList.size() > 0) {
				// 将第一个对象设为游标
				ConStatistics cursor = ConStatisticsList.get(0);
				Boolean flag = true;
				int port = 0;
				// 合计行
				Map<String, Object> row1 = new HashMap<String, Object>();
				row1.put("RowName", "合计");
				while (flag) {
					Map<String, Object> row = new HashMap<String, Object>();
					if (RowName.equals("ConTypeInnerId")) {
						// 消费类型
						ConType conType = xiaofeiguanliMapper
								.selectConTypeByInnerId(Integer.valueOf(cursor.getRowName()));
						row.put("RowName", conType.getConTypeName());
					} else if (RowName.equals("ConWay")) {
						// 消费途径
						if (cursor.getRowName().equals("0")) {
							row.put("RowName", "POS机消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("RowName", "智能餐盘消费");
						} else if (cursor.getRowName().equals("2")) {
							row.put("RowName", "智能餐台消费");
						} else if (cursor.getRowName().equals("3")) {
							row.put("RowName", "安卓APP消费");
						} else if (cursor.getRowName().equals("4")) {
							row.put("RowName", "IOSAPP消费");
						} else if (cursor.getRowName().equals("5")) {
							row.put("RowName", "微信APP消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("ConPattern")) {
						// 消费模式
						 if (cursor.getRowName().equals("1")) {
							row.put("RowName", "定额消费");
						} else if (cursor.getRowName().equals("2")) {
							row.put("RowName", "计额消费");
						} else if (cursor.getRowName().equals("4")) {
							row.put("RowName", "菜单消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("Year") || RowName.equals("Month") || RowName.equals("Day")) {
						// 按时间分组
						row.put("RowName", cursor.getRowName());
					} else if (RowName.equals("CompanyInnerId")) {
						// 公司
						Department company = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(cursor.getRowName()));
						row.put("RowName", company.getDepartmentName());
					} else if (RowName.equals("ErrorConLog")) {
						// 正常、误消费统计
						if (cursor.getRowName().equals("1")) {
							row.put("RowName", "误消费");
						} else if (cursor.getRowName().equals("0")) {
							row.put("RowName", "正常消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("LimitTimes")) {
						// 限次、不限次统计
						if (cursor.getRowName().equals("0")) {
							row.put("RowName", "不限次消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("RowName", "限次消费");
						} else {
							row.put("RowName", "其它消费");
						}
					} else if (RowName.equals("OffLine")) {
						// 脱机、联机统计
						if (cursor.getRowName().equals("0")) {
							row.put("RowName", "脱机消费");
						} else if (cursor.getRowName().equals("1")) {
							row.put("RowName", "联机消费");
						} else {
							row.put("RowName", "其它消费");
						}
					}
					Long count = (long) 0;
					for (int i = port; i < ConStatisticsList.size(); i++) {
						ConStatistics conStatisticsNow = ConStatisticsList.get(i);
						if (conStatisticsNow.getRowName().equals(cursor.getRowName())) {
							if (i == ConStatisticsList.size() - 1) {
								flag = false;
							}
							Long Money = conStatisticsNow.getDiscountMoney();
							// 此对象和游标对象是同一行
							count += Money;
							row.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
							if (row.get("app" + conStatisticsNow.getAppInnerId()) != null) {
								row.put("app" + conStatisticsNow.getAppInnerId(),
										Money + (Long) row.get("app" + conStatisticsNow.getAppInnerId()));
							} else {
								row.put("app" + conStatisticsNow.getAppInnerId(), Money);
							}
							if (row.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
								row.put("area" + conStatisticsNow.getAreaInnerId(),
										Money + (Long) row.get("area" + conStatisticsNow.getAreaInnerId()));
							} else {
								row.put("area" + conStatisticsNow.getAreaInnerId(), Money);
							}
							// 合计行数据
							if (row1.get("merchant" + conStatisticsNow.getMerchantInnerId()) != null) {
								row1.put("merchant" + conStatisticsNow.getMerchantInnerId(),
										Money + (Long) row1.get("merchant" + conStatisticsNow.getMerchantInnerId()));
							} else {
								row1.put("merchant" + conStatisticsNow.getMerchantInnerId(), Money);
							}
							if (row1.get("app" + conStatisticsNow.getAppInnerId()) != null) {
								row1.put("app" + conStatisticsNow.getAppInnerId(),
										Money + (Long) row1.get("app" + conStatisticsNow.getAppInnerId()));
							} else {
								row1.put("app" + conStatisticsNow.getAppInnerId(), Money);
							}
							if (row1.get("area" + conStatisticsNow.getAreaInnerId()) != null) {
								row1.put("area" + conStatisticsNow.getAreaInnerId(),
										Money + (Long) row1.get("area" + conStatisticsNow.getAreaInnerId()));
							} else {
								row1.put("area" + conStatisticsNow.getAreaInnerId(), Money);
							}
						} else {
							// 此对象和游标对象不是同一行
							// 游标下移
							cursor = conStatisticsNow;
							port = i;
							break;
						}
					}
					// 合计行数据

					if (row1.get("count") != null) {
						row1.put("count", count + (Long) row1.get("count"));
					} else {
						row1.put("count", count);
					}
					row.put("count", count);
					// 最后一行合计

					rows.add(row);
				}
				rows.add(row1);
			}
		}
		if (Type.equals("sum")) {
			// 按金额统计除100
			for (Map<String, Object> row : rows) {
				for (Map.Entry<String, Object> entry : row.entrySet()) {
					if (!entry.getKey().equals("RowName")) {
						entry.setValue(((Long) entry.getValue()) / 100.0);
					}
				}
			}
		}
		jo.put("rows", rows);
		return jo.toString();
	}
}
