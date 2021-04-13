package com.dhsr.smartid.zhanghuyewu.service;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dhsr.smartid.peizhiguanli.dao.PeizhiguanliMapper;
import com.dhsr.smartid.peizhiguanli.domain.App;
import com.dhsr.smartid.peizhiguanli.domain.Operator;
import com.dhsr.smartid.peizhiguanli.domain.SysTerminal;
import com.dhsr.smartid.peizhiguanli.domain.UserType;
import com.dhsr.smartid.renshiyewu.dao.RenshiyewuMapper;
import com.dhsr.smartid.renshiyewu.domain.Company;
import com.dhsr.smartid.renshiyewu.domain.Department;
import com.dhsr.smartid.renshiyewu.domain.Mark_User;
import com.dhsr.smartid.renshiyewu.domain.User;
import com.dhsr.smartid.util.DataGridModel;
import com.dhsr.smartid.util.ExportExcelUtils;
import com.dhsr.smartid.util.UpLoadExcel;
import com.dhsr.smartid.zhanghuyewu.dao.ZhanghuyewuMapper;
import com.dhsr.smartid.zhanghuyewu.domain.Account;
import com.dhsr.smartid.zhanghuyewu.domain.AccountDiscount;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog;
import com.dhsr.smartid.zhanghuyewu.domain.AccountLog_User;
import com.dhsr.smartid.zhanghuyewu.domain.AccountStatistics;
import com.dhsr.smartid.zhanghuyewu.domain.Payment;
import com.dhsr.smartid.zhanghuyewu.domain.Settlement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 账户业务的Service层
 * 
 * @author qidong
 *
 */
public class ZhanghuyewuServiceImpl implements ZhanghuyewuService {

	@Resource
	private RenshiyewuMapper renshiyewuMapper;
	@Resource
	private ZhanghuyewuMapper zhanghuyewuMapper;
	@Resource
	private PeizhiguanliMapper peizhiguanliMapper;

	@Override
	@Transactional
	public String readCard(String MarkId,HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		System.out.println(MarkId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "MarkState");
		map.put("start", 0);
		map.put("MarkId", "2499134785");
		JSONObject jo = new JSONObject();
		List<Mark_User> markList = renshiyewuMapper.selectMark(map);
		System.out.println(markList.size());
		if (markList.size() < 1) {
			// 没有查到任何卡
			jo.put("code", 501);
			jo.put("msg", "非本系统卡或未关联人员！");
		} else {
			// 查到卡，按状态排序，取第一个
			Mark_User mark = markList.get(0);
			if (mark.getMarkState() == 1) {
				// 挂失
				jo.put("code", 502);
				jo.put("msg", "此卡已挂失！");
			} else if (mark.getMarkState() == 2) {
				// 退卡
				jo.put("code", 503);
				jo.put("msg", "此卡已退卡！");
			} else {
				// 查询现金账户
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 1);
				map1.put("UserInnerId", mark.getUserInnerId());
				Account account = zhanghuyewuMapper.selectAccount(map1);
				if (account == null) {
					// 没有现金账户
					jo.put("code", 504);
					jo.put("msg", "没有现金账户！");
				} else {
					// 成功
					jo.put("code", 200);
					jo.put("msg", "读卡成功");
					// 补贴账户
					map1.put("AccountTypeInnerId", 2);
					map1.put("UserInnerId", mark.getUserInnerId());
					Account account1 = zhanghuyewuMapper.selectAccount(map1);
					if (account1 != null) {
						jo.put("Subsidy", account1.getMoney() / 100.0);
					} else {
						jo.put("Subsidy", 0);
					}
					// 次账户
					map1.put("AccountTypeInnerId", 3);
					map1.put("UserInnerId", mark.getUserInnerId());
					Account account2 = zhanghuyewuMapper.selectAccount(map1);
					if (account2 != null) {
						jo.put("FavorableMoneyBefore", account2.getMoney() / 100.0);
						jo.put("FavorableAccountInnerId", account2.getAccountInnerId());
					} else {
						jo.put("FavorableMoneyBefore", 0);
					}
					// 现金账户
					jo.put("BeforeMoney", account.getMoney() / 100.0);
					jo.put("AccountInnerId", account.getAccountInnerId());
					//卡费账户
					map1.put("AccountTypeInnerId", 4);
					map1.put("UserInnerId", mark.getUserInnerId());
					Account account3 = zhanghuyewuMapper.selectAccount(map1);
					if (account3 != null) {
						jo.put("MarkMoney", account3.getMoney() / 100.0);
						jo.put("MarkMoneyAccountInnerId", account3.getAccountInnerId());
					} 
					// 人员信息
					User User = renshiyewuMapper.selectUserByInnerId(mark.getUserInnerId());
					Integer Proportion = zhanghuyewuMapper.selectUserTypeDiscount(User.getUserTypeInnerId());
					if (Proportion != null) {
						if(Proportion == 0){
							jo.put("Proportion", 10000);
						}else{
							jo.put("Proportion", Proportion);
						}
					} else {
						jo.put("Proportion", 10000);
					}
					
					jo.put("UserId", mark.getUserId());
					jo.put("UserName", mark.getUserName());
					jo.put("UserInnerId", mark.getUserInnerId());
					jo.put("CompanyName", mark.getCompanyName());
					jo.put("DepartmentName", mark.getDepartmentName());
					jo.put("DepartmentInnerId", mark.getDepartmentInnerId());
					
				}
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertAccountLog(AccountLog accountLog, HttpServletRequest request) {
		// 查询账户
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AccountInnerId", accountLog.getAccountInnerId());
		Account account = zhanghuyewuMapper.selectAccount(map);
		accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
		accountLog.setUserInnerId(account.getUserInnerId());
		accountLog.setBeforeMoney(account.getMoney());
		accountLog.setAfterMoney(accountLog.getBeforeMoney() + accountLog.getMoney());
		// 虚拟操作员，以后改写
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		accountLog.setOperator("Operator" + operator.getOperatorInnerId());
		if (accountLog.getMoneySource() == 0) {
			accountLog.setPayee("Operator" + operator.getOperatorInnerId());
		} else if (accountLog.getMoneySource() == 1) {
			accountLog.setPayee("WeiXin");
		} else if (accountLog.getMoneySource() == 2) {
			accountLog.setPayee("ZhiFuBao");
		} else if (accountLog.getMoneySource() == 3) {
			accountLog.setPayee("YinHangKa");
		} else if (accountLog.getMoneySource() == 4) {
			accountLog.setPayee("ZhiPiao");
		} else if (accountLog.getMoneySource() == 5) {
			accountLog.setPayee("KongChong");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		accountLog.setAccountDate(sdf.format(new Date()));
		User user = renshiyewuMapper.selectUserByInnerId(account.getUserInnerId());
		accountLog.setCompanyInnerId(user.getCompanyInnerId());
		accountLog.setDepartmentInnerId(user.getDepartmentInnerId());
		Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
		
		JSONObject jo = new JSONObject();
		if (result > 0) {
			jo.put("code", 200);
			if (accountLog.getInOrOut() == 0) {
				jo.put("msg", "充值成功！");
			} else {
				jo.put("msg", "减值成功！");
			}
		} else {
			jo.put("code", 500);
			if (accountLog.getInOrOut() == 0) {
				jo.put("msg", "充值失败！");
			} else {
				jo.put("msg", "减值失败！");
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertAccountLog1(AccountLog accountLog, HttpServletRequest request, Integer AccountInnerId1) {
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		// 查询账户
		JSONObject jo = new JSONObject();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AccountInnerId", accountLog.getAccountInnerId());
		Account account = zhanghuyewuMapper.selectAccount(map);
		accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
		accountLog.setUserInnerId(account.getUserInnerId());
		accountLog.setBeforeMoney(account.getMoney());
		accountLog.setAfterMoney(accountLog.getBeforeMoney() + accountLog.getMoney());
		// 虚拟操作员，以后改写
		accountLog.setOperator("Operator" + operator.getOperatorInnerId());
		if (accountLog.getMoneySource() == 0) {
			accountLog.setPayee("Operator" + operator.getOperatorInnerId());
		} else if (accountLog.getMoneySource() == 1) {
			accountLog.setPayee("WeiXin");
		} else if (accountLog.getMoneySource() == 2) {
			accountLog.setPayee("ZhiFuBao");
		} else if (accountLog.getMoneySource() == 3) {
			accountLog.setPayee("YinHangKa");
		} else if (accountLog.getMoneySource() == 4) {
			accountLog.setPayee("ZhiPiao");
		} else if (accountLog.getMoneySource() == 5) {
			accountLog.setPayee("KongChong");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		accountLog.setAccountDate(sdf.format(new Date()));
		User user = renshiyewuMapper.selectUserByInnerId(account.getUserInnerId());
		accountLog.setCompanyInnerId(user.getCompanyInnerId());
		accountLog.setDepartmentInnerId(user.getDepartmentInnerId());
		if(operator.getOperatorInnerId()  == 9){
			if(accountLog.getDepartmentInnerId() != 42){
				jo.put("code", 504);
				jo.put("msg", "此卡 非临时卡");
				return jo.toString();
			}
		}
		Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
		System.out.println(accountLog.toString());
		System.out.println(result);
		Integer rt = zhanghuyewuMapper.selectAccountLogByAccountLog(accountLog); 
		if (rt > 0) {
			jo.put("code", 200);
			if (accountLog.getInOrOut() == 0) {
				jo.put("msg", "充值成功！");
			} else {
				jo.put("msg", "减值成功！");
			} 
		}else {
			jo.put("code", 500);
			if (accountLog.getInOrOut() == 0) {
				jo.put("msg", "充值失败！");
			} else {
				jo.put("msg", "减值失败！");
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertDimission(HttpServletRequest request) {
		JSONObject jo = new JSONObject();
		Integer AccountInnerId = Integer.valueOf(request.getParameter("AccountInnerId"));
		Integer AllowanceMoney = Integer.valueOf(request.getParameter("AllowanceMoney"));
		Integer PersonMoney = Integer.valueOf(request.getParameter("PersonMoney"));
		Integer InOrOut = Integer.valueOf(request.getParameter("InOrOut"));
		Integer RechargeType = Integer.valueOf(request.getParameter("RechargeType"));
		Integer MoneySource = Integer.valueOf(request.getParameter("MoneySource"));
		String Remark = request.getParameter("Remark");
		{
			AccountLog accountLog = new AccountLog();
			// 现金账户
			// 查询账户
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("AccountInnerId", AccountInnerId);
			Account account = zhanghuyewuMapper.selectAccount(map);
			accountLog.setInOrOut(InOrOut);
			accountLog.setRechargeType(RechargeType);
			accountLog.setMoneySource(MoneySource);
			accountLog.setRemark(Remark);
			accountLog.setAccountInnerId(AccountInnerId);

			accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
			accountLog.setUserInnerId(account.getUserInnerId());
			accountLog.setBeforeMoney(account.getMoney());
			accountLog.setMoney(PersonMoney);
			accountLog.setAfterMoney(0);
			// 虚拟操作员，以后改写
			Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
			accountLog.setOperator("Operator" + operator.getOperatorInnerId());
			if (MoneySource == 0) {
				accountLog.setPayee("Operator" + operator.getOperatorInnerId());
			} else if (MoneySource == 1) {
				accountLog.setPayee("WeiXin");
			} else if (MoneySource == 2) {
				accountLog.setPayee("ZhiFuBao");
			} else if (MoneySource == 3) {
				accountLog.setPayee("YinHangKa");
			} else if (MoneySource == 4) {
				accountLog.setPayee("ZhiPiao");
			} else if (MoneySource == 5) {
				accountLog.setPayee("KongChong");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			accountLog.setAccountDate(sdf.format(new Date()));
			User user = renshiyewuMapper.selectUserByInnerId(account.getUserInnerId());
			accountLog.setCompanyInnerId(user.getCompanyInnerId());
			accountLog.setDepartmentInnerId(user.getDepartmentInnerId());
			Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
			if (result > 0) {

				// 补贴账户
				// 查询账户
				accountLog = new AccountLog();
				accountLog.setInOrOut(InOrOut);
				accountLog.setRechargeType(RechargeType);
				accountLog.setMoneySource(MoneySource);
				accountLog.setRemark(Remark);
				accountLog.setAccountInnerId(AccountInnerId + 1);

				map = new HashMap<String, Object>();
				map.put("AccountInnerId", AccountInnerId + 1);
				account = zhanghuyewuMapper.selectAccount(map);
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				accountLog.setUserInnerId(account.getUserInnerId());
				accountLog.setBeforeMoney(account.getMoney());
				accountLog.setMoney(AllowanceMoney);
				accountLog.setAfterMoney(0);
				// 虚拟操作员，以后改写
				operator = (Operator) request.getSession().getAttribute("operatorSession");
				accountLog.setOperator("Operator" + operator.getOperatorInnerId());
				if (MoneySource == 0) {
					accountLog.setPayee("Operator" + operator.getOperatorInnerId());
				} else if (MoneySource == 1) {
					accountLog.setPayee("WeiXin");
				} else if (MoneySource == 2) {
					accountLog.setPayee("ZhiFuBao");
				} else if (MoneySource == 3) {
					accountLog.setPayee("YinHangKa");
				} else if (MoneySource == 4) {
					accountLog.setPayee("ZhiPiao");
				} else if (MoneySource == 5) {
					accountLog.setPayee("KongChong");
				}

				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				accountLog.setAccountDate(sdf.format(new Date()));
				user = renshiyewuMapper.selectUserByInnerId(account.getUserInnerId());
				accountLog.setCompanyInnerId(user.getCompanyInnerId());
				accountLog.setDepartmentInnerId(user.getDepartmentInnerId());
				result = zhanghuyewuMapper.insertAccountLog(accountLog);
				if(result>0){
					// 次 账户
					// 查询账户
					accountLog = new AccountLog();
					accountLog.setInOrOut(InOrOut);
					accountLog.setRechargeType(RechargeType);
					accountLog.setMoneySource(MoneySource);
					accountLog.setRemark(Remark);
					accountLog.setAccountInnerId(AccountInnerId + 2);

					map = new HashMap<String, Object>();
					map.put("AccountInnerId", AccountInnerId + 2);
					account = zhanghuyewuMapper.selectAccount(map);
					accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
					accountLog.setUserInnerId(account.getUserInnerId());
					accountLog.setBeforeMoney(account.getMoney());
					accountLog.setMoney(-account.getMoney());
					accountLog.setAfterMoney(0);
					// 虚拟操作员，以后改写
					operator = (Operator) request.getSession().getAttribute("operatorSession");
					accountLog.setOperator("Operator" + operator.getOperatorInnerId());
					if (MoneySource == 0) {
						accountLog.setPayee("Operator" + operator.getOperatorInnerId());
					} else if (MoneySource == 1) {
						accountLog.setPayee("WeiXin");
					} else if (MoneySource == 2) {
						accountLog.setPayee("ZhiFuBao");
					} else if (MoneySource == 3) {
						accountLog.setPayee("YinHangKa");
					} else if (MoneySource == 4) {
						accountLog.setPayee("ZhiPiao");
					} else if (MoneySource == 5) {
						accountLog.setPayee("KongChong");
					}

					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					accountLog.setAccountDate(sdf.format(new Date()));
					user = renshiyewuMapper.selectUserByInnerId(account.getUserInnerId());
					accountLog.setCompanyInnerId(user.getCompanyInnerId());
					accountLog.setDepartmentInnerId(user.getDepartmentInnerId());
					result = zhanghuyewuMapper.insertAccountLog(accountLog);
					if (result > 0) {
						jo.put("code", 200);
						jo.put("msg", "减值成功！");
					} else {
						jo.put("code", 500);
						jo.put("msg", "减值失败！");
					}
				}
				
			}
		}
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectAccountLog(DataGridModel dgm, HttpServletRequest request) {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String DepartmentList = request.getParameter("DepartmentList");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String AccountTypeInnerId = request.getParameter("AccountTypeInnerId");
		String InOrOut = request.getParameter("InOrOut");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("UserInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
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
		map.put("rows", dgm.getRows());
		// map.put("order", dgm.getOrder());
		map.put("sort", sortString);
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		map.put("DepartmentList", DepartmentList);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("AccountTypeInnerId", AccountTypeInnerId);
		map.put("InOrOut", InOrOut);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectAccountLog(map);
		for (AccountLog_User accountLog : accountLogList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("AccountLogInnerId", accountLog.getAccountLogInnerId());
			row.put("Money", accountLog.getMoney() / 100.0);
			row.put("BeforeMoney", accountLog.getBeforeMoney() / 100.0);
			row.put("AfterMoney", accountLog.getAfterMoney() / 100.0);
			row.put("NowMoney", accountLog.getNowMoney() / 100.0);
			row.put("AccountDate", accountLog.getAccountDate().substring(0, 19));
			row.put("UserId", accountLog.getUserId());
			row.put("UserName", accountLog.getUserName());
			row.put("CompanyName", accountLog.getCompanyName());
			row.put("DepartmentName", accountLog.getDepartmentName());
			row.put("AccountTypeName", accountLog.getAccountTypeName());
			if (accountLog.getInOrOut() == 0) {
				row.put("InOrOut", "充值");
			} else {
				row.put("InOrOut", "减值");
			}
			if (accountLog.getRechargeType() == 0) {
				row.put("RechargeType", "平台有卡");
			} else if (accountLog.getRechargeType() == 1) {
				row.put("RechargeType", "平台无卡");
			} else if (accountLog.getRechargeType() == 2) {
				row.put("RechargeType", "App充值");
			} else if (accountLog.getRechargeType() == 3) {
				row.put("RechargeType", "App充值");
			} else if (accountLog.getRechargeType() == 4) {
				row.put("RechargeType", "微信app");
			} else if (accountLog.getRechargeType() == 5) {
				row.put("RechargeType", "现金圈存机");
			} else if (accountLog.getRechargeType() == 6) {
				row.put("RechargeType", "银行圈存机");
			} else if (accountLog.getRechargeType() == 7) {
				row.put("RechargeType", "补卡");
			}
			if (accountLog.getMoneySource() == 0) {
				row.put("MoneySource", "现金");
			} else if (accountLog.getMoneySource() == 1) {
				row.put("MoneySource", "微信");
			} else if (accountLog.getMoneySource() == 2) {
				row.put("MoneySource", "支付宝");
			} else if (accountLog.getMoneySource() == 3) {
				row.put("MoneySource", "银行卡");
			} else if (accountLog.getMoneySource() == 4) {
				row.put("MoneySource", "支票");
			} else if (accountLog.getMoneySource() == 5) {
				row.put("MoneySource", "空充");
			}

			if (accountLog.getOperator().equals("Android")) {
				row.put("Operator", "安卓app");
			} else if (accountLog.getOperator().equals("IOS")) {
				row.put("Operator", "IOSapp");
			} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
				if (operator != null) {
					row.put("Operator", operator.getOperatorName());
				}
			} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
				if (sysTerminal != null) {
					row.put("Operator", sysTerminal.getSysTerminalName());
				}
			}

			if (accountLog.getPayee().equals("ZhiFuBao")) {
				row.put("Payee", "支付宝");
			} else if (accountLog.getPayee().equals("WeiXin")) {
				row.put("Payee", "微信");
			} else if (accountLog.getPayee().equals("YinHangKa")) {
				row.put("Payee", "银行卡");
			} else if (accountLog.getPayee().equals("ZhiPiao")) {
				row.put("Payee", "支票");
			} else if (accountLog.getPayee().equals("KongChong")) {
				row.put("Payee", "空充");
			} else if (accountLog.getPayee().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getPayee().substring(8)));
				if (operator != null) {
					row.put("Payee", operator.getOperatorName());
				}
			} else if (accountLog.getPayee().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getPayee().substring(11)));
				if (sysTerminal != null) {
					row.put("Payee", sysTerminal.getSysTerminalName());
				}
			}

			row.put("Remark", accountLog.getRemark());
			row.put("StandbyA", accountLog.getStandbyA());
			row.put("StandbyB", accountLog.getStandbyB());
			row.put("StandbyC", accountLog.getStandbyC());
			row.put("StandbyD", accountLog.getStandbyD());
			rows.add(row);
		}
		Integer total = zhanghuyewuMapper.selectAccountLogTotal(map);
		if (total > 0 && dgm.getRows() * dgm.getPage() >= total) {
			map.put("rows", 100000000);
			map.put("start", 0);
			List<AccountLog_User> accountLogList1 = zhanghuyewuMapper.selectAccountLog(map);
			Long Money = (long) 0;
			Long Count = (long) 0;
			for (AccountLog_User accountLog : accountLogList1) {
				Count++;
				Money += accountLog.getMoney();
			}
			// 最后一行合计
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserId", "合计");
			row.put("Money", Money / 100.0);
			row.put("UserName", Count + "人");
			rows.add(row);
		}
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String selectAccountLogExcel(HttpServletRequest request) throws Exception {

		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String DepartmentList = request.getParameter("DepartmentList");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String AccountTypeInnerId = request.getParameter("AccountTypeInnerId");
		String InOrOut = request.getParameter("InOrOut");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Map<String, Object> map = new HashMap<String, Object>();
		if (sort == null || sort.equals("")) {
			sort = "UserInnerId";
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
		map.put("rows", 100000000);
		// map.put("order", order);
		map.put("sort", sortString);
		map.put("start", 0);
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		map.put("DepartmentList", DepartmentList);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("AccountTypeInnerId", AccountTypeInnerId);
		map.put("InOrOut", InOrOut);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);

		List<List<Object>> data = new ArrayList<List<Object>>();
		int count = 0;
		Long sum = (long) 0;

		List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectAccountLog(map);
		for (AccountLog_User accountLog : accountLogList) {
			count++;
			sum += accountLog.getMoney();
			List<Object> rowData = new ArrayList<Object>();
			rowData.add(accountLog.getUserId());
			rowData.add(accountLog.getUserName());
			rowData.add(accountLog.getDepartmentName());
			rowData.add(accountLog.getAccountTypeName());
			rowData.add(accountLog.getMoney() / 100.0);
			if (accountLog.getInOrOut() == 0) {
				rowData.add("充值");
			} else {
				rowData.add("减值");
			}
			rowData.add(accountLog.getAccountDate().substring(0, 19));
			if (accountLog.getRechargeType() == 0) {
				rowData.add("平台有卡");
			} else if (accountLog.getRechargeType() == 1) {
				rowData.add("平台无卡");
			} else if (accountLog.getRechargeType() == 2) {
				rowData.add("App充值");
			} else if (accountLog.getRechargeType() == 3) {
				rowData.add("App充值");
			}else if (accountLog.getRechargeType() == 4) {
				rowData.add("微信app");
			} else if (accountLog.getRechargeType() == 5) {
				rowData.add("现金圈存机");
			} else if (accountLog.getRechargeType() == 6) {
				rowData.add("银行圈存机");
			} else if (accountLog.getRechargeType() == 7) {
				rowData.add("补卡");
			}  else {
				rowData.add(" ");
			}

			if (accountLog.getMoneySource() == 0) {
				rowData.add("现金");
			} else if (accountLog.getMoneySource() == 1) {
				rowData.add("微信");
			} else if (accountLog.getMoneySource() == 2) {
				rowData.add("支付宝");
			} else if (accountLog.getMoneySource() == 3) {
				rowData.add("银行卡");
			} else if (accountLog.getMoneySource() == 4) {
				rowData.add("支票");
			} else if (accountLog.getMoneySource() == 5) {
				rowData.add("空充");
			} else {
				rowData.add(" ");
			}

			if (accountLog.getOperator().equals("Android")) {
				rowData.add("安卓app");
			} else if (accountLog.getOperator().equals("IOS")) {
				rowData.add("IOSapp");
			} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
				if (operator != null) {
					rowData.add(operator.getOperatorName());
				} else {
					rowData.add(" ");
				}
			} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
				if (sysTerminal != null) {
					rowData.add(sysTerminal.getSysTerminalName());
				} else {
					rowData.add(" ");
				}
			} else {
				rowData.add(" ");
			}

			/*if (accountLog.getPayee().equals("ZhiFuBao")) {
				rowData.add("支付宝");
			} else if (accountLog.getPayee().equals("WeiXin")) {
				rowData.add("微信");
			} else if (accountLog.getPayee().equals("YinHangKa")) {
				rowData.add("银行卡");
			} else if (accountLog.getPayee().equals("ZhiPiao")) {
				rowData.add("支票");
			} else if (accountLog.getPayee().equals("KongChong")) {
				rowData.add("空充");
			} else if (accountLog.getPayee().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getPayee().substring(8)));
				if (operator != null) {
					rowData.add(operator.getOperatorName());
				} else {
					rowData.add(" ");
				}
			} else if (accountLog.getPayee().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getPayee().substring(11)));
				if (sysTerminal != null) {
					rowData.add(sysTerminal.getSysTerminalName());
				} else {
					rowData.add(" ");
				}
			} else {
				rowData.add(" ");
			}*/

			if (accountLog.getRemark() != null) {
				rowData.add(accountLog.getRemark());
			} else {
				rowData.add(" ");
			}

			data.add(rowData);
		}
		List<Object> rowData = new ArrayList<Object>();
		rowData.add("总计");
		rowData.add(count + "人");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(sum / 100.0);
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		/*rowData.add(" ");*/
		rowData.add(" ");
		data.add(rowData);
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/AccountLog" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		String header0 = "充减值记录表";
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
		List<String> headerList = new ArrayList<String>();
		headerList.add(header0);
		headerList.add(header1);
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("人员编号");
		headers0.add("人员姓名");
		headers0.add("公司");
		headers0.add("账户");
		headers0.add("金额");
		headers0.add("充/减值");
		headers0.add("充值时间");
		headers0.add("充值方式");
		headers0.add("资金来源");
		headers0.add("操作人");
		/*headers0.add("收款账户");*/
		headers0.add("备注");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(15);
		length0.add(15);
		length0.add(20);
		length0.add(20);
		length0.add(15);
		length0.add(7);
		length0.add(7);
		length0.add(20);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		/*length0.add(15);*/
		length0.add(15);
		eeu.selectUseruserExcel(workbook, 0, "充减值记录表", headerList, headers0, length0, data, out);
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
	public String selectAccountStatistics(HttpServletRequest request) {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String DepartmentList = request.getParameter("DepartmentList");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		String RowName = request.getParameter("RowName");
		String Type = request.getParameter("Type");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UserName", UserName);
		map.put("UserId", UserId);
		map.put("DepartmentList", DepartmentList);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);
		map.put("RowName", RowName);
		map.put("Type", Type);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		List<AccountStatistics> AccountStatisticsList = zhanghuyewuMapper.selectAccountStatistics(map);
		if (AccountStatisticsList.size() > 0) {
			// 将第一个对象设为游标
			AccountStatistics cursor = AccountStatisticsList.get(0);
			Boolean flag = true;
			int port = 0;
			// 合计行
			Map<String, Object> row1 = new HashMap<String, Object>();
			row1.put("RowName", "合计");
			while (flag) {
				Map<String, Object> row = new HashMap<String, Object>();
				if (RowName.equals("RechargeType")) {
					// 充值方式
					if (cursor.getRowName().equals("0")) {
						row.put("RowName", "平台有卡");
					} else if (cursor.getRowName().equals("1")) {
						row.put("RowName", "平台无卡");
					} else if (cursor.getRowName().equals("2")) {
						row.put("RowName", "安卓app");
					} else if (cursor.getRowName().equals("3")) {
						row.put("RowName", "IOSapp");
					} else if (cursor.getRowName().equals("4")) {
						row.put("RowName", "微信app");
					} else if (cursor.getRowName().equals("5")) {
						row.put("RowName", "现金圈存机");
					} else if (cursor.getRowName().equals("6")) {
						row.put("RowName", "银行圈存机");
					} else if (cursor.getRowName().equals("7")) {
						row.put("RowName", "补卡");
					}
				} else if (RowName.equals("MoneySource")) {
					// 资金来源
					if (cursor.getRowName().equals("0")) {
						row.put("RowName", "现金");
					} else if (cursor.getRowName().equals("1")) {
						row.put("RowName", "微信");
					} else if (cursor.getRowName().equals("2")) {
						row.put("RowName", "支付宝");
					} else if (cursor.getRowName().equals("3")) {
						row.put("RowName", "银行卡");
					} else if (cursor.getRowName().equals("4")) {
						row.put("RowName", "支票");
					} else if (cursor.getRowName().equals("5")) {
						row.put("RowName", "空充");
					}
				} else if (RowName.equals("Year") || RowName.equals("Month") || RowName.equals("Day")) {
					// 按时间分组
					row.put("RowName", cursor.getRowName());
				} else if (RowName.equals("CompanyInnerId")) {
					// 公司
					Department company = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(cursor.getRowName()));
					row.put("RowName", company.getDepartmentName());
				} else if (RowName.equals("Operator")) {
					// 操作人
					if (cursor.getRowName().equals("Android")) {
						row.put("RowName", "安卓app");
					} else if (cursor.getRowName().equals("IOS")) {
						row.put("RowName", "IOSapp");
					} else if (cursor.getRowName().indexOf("Operator") >= 0) {
						Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(cursor.getRowName().substring(8)));
						if (operator != null) {
							row.put("RowName", operator.getOperatorName());
						}
					} else if (cursor.getRowName().indexOf("SysTerminal") >= 0) {
						SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(cursor.getRowName().substring(11)));
						if (sysTerminal != null) {
							row.put("RowName", sysTerminal.getSysTerminalName());
						}
					}
				} else if (RowName.equals("Payee")) {
					// 收款账户
					if (cursor.getRowName().equals("ZhiFuBao")) {
						row.put("RowName", "支付宝");
					} else if (cursor.getRowName().equals("WeiXin")) {
						row.put("RowName", "微信");
					} else if (cursor.getRowName().equals("YinHangKa")) {
						row.put("RowName", "银行卡");
					} else if (cursor.getRowName().equals("ZhiPiao")) {
						row.put("RowName", "支票");
					} else if (cursor.getRowName().equals("KongChong")) {
						row.put("RowName", "空充");
					} else if (cursor.getRowName().indexOf("Operator") >= 0) {
						Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(cursor.getRowName().substring(8)));
						if (operator != null) {
							row.put("RowName", operator.getOperatorName());
						}
					} else if (cursor.getRowName().indexOf("SysTerminal") >= 0) {
						SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(cursor.getRowName().substring(11)));
						if (sysTerminal != null) {
							row.put("RowName", sysTerminal.getSysTerminalName());
						}
					}
				}
				Long Count = (long) 0;
				for (int i = port; i < AccountStatisticsList.size(); i++) {
					AccountStatistics accountStatisticsNow = AccountStatisticsList.get(i);
					if (accountStatisticsNow.getRowName().equals(cursor.getRowName())) {
						if (i == AccountStatisticsList.size() - 1) {
							flag = false;
						}
						Long Money = accountStatisticsNow.getMoney();
						// 此对象和游标对象是同一行
						Count += Money;
						if (accountStatisticsNow.getAccountTypeInnerId() == 1) {
							// 现金合计
							if (row.get("PersonCount") != null) {
								row.put("PersonCount", Money + (Long) row.get("PersonCount"));
							} else {
								row.put("PersonCount", Money);
							}
							if (row1.get("PersonCount") != null) {
								row1.put("PersonCount", Money + (Long) row1.get("PersonCount"));
							} else {
								row1.put("PersonCount", Money);
							}
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 现金充值
								row.put("PersonIn", Money);
								if (row1.get("PersonIn") != null) {
									row1.put("PersonIn", Money + (Long) row1.get("PersonIn"));
								} else {
									row1.put("PersonIn", Money);
								}
							} else {
								// 现金减值
								row.put("PersonOut", Money);
								if (row1.get("PersonOut") != null) {
									row1.put("PersonOut", Money + (Long) row1.get("PersonOut"));
								} else {
									row1.put("PersonOut", Money);
								}
							}
						} else if (accountStatisticsNow.getAccountTypeInnerId() == 2) {
							// 补贴合计
							if (row.get("AllowanceCount") != null) {
								row.put("AllowanceCount", Money + (Long) row.get("AllowanceCount"));
							} else {
								row.put("AllowanceCount", Money);
							}
							if (row1.get("AllowanceCount") != null) {
								row1.put("AllowanceCount", Money + (Long) row1.get("AllowanceCount"));
							} else {
								row1.put("AllowanceCount", Money);
							}
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 补贴充值
								row.put("AllowanceIn", Money);
								if (row1.get("AllowanceIn") != null) {
									row1.put("AllowanceIn", Money + (Long) row1.get("AllowanceIn"));
								} else {
									row1.put("AllowanceIn", Money);
								}
							} else {
								// 补贴减值
								row.put("AllowanceOut", Money);
								if (row1.get("AllowanceOut") != null) {
									row1.put("AllowanceOut", Money + (Long) row1.get("AllowanceOut"));
								} else {
									row1.put("AllowanceOut", Money);
								}
							}
						} /*else if (accountStatisticsNow.getAccountTypeInnerId() == 3) {
							// 返现合计
							if (row.get("FavorableCount") != null) {
								row.put("FavorableCount", Money + (Long) row.get("FavorableCount"));
							} else {
								row.put("FavorableCount", Money);
							}
							if (row1.get("FavorableCount") != null) {
								row1.put("FavorableCount", Money + (Long) row1.get("FavorableCount"));
							} else {
								row1.put("FavorableCount", Money);
							}
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 返现充值
								row.put("FavorableIn", Money);
								if (row1.get("FavorableIn") != null) {
									row1.put("FavorableIn", Money + (Long) row1.get("FavorableIn"));
								} else {
									row1.put("FavorableIn", Money);
								}
							} else {
								// 返现减值
								row.put("FavorableOut", Money);
								if (row1.get("FavorableOut") != null) {
									row1.put("FavorableOut", Money + (Long) row1.get("FavorableOut"));
								} else {
									row1.put("FavorableOut", Money);
								}
							}
						}else if (accountStatisticsNow.getAccountTypeInnerId() == 4) {
							// 补贴合计
							if (row.get("KaCount") != null) {
								row.put("KaCount", Money + (Long) row.get("KaCount"));
							} else {
								row.put("KaCount", Money);
							}
							if (row1.get("KaCount") != null) {
								row1.put("KaCount", Money + (Long) row1.get("KaCount"));
							} else {
								row1.put("KaCount", Money);
							}
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 补贴充值
								row.put("KaIn", Money);
								if (row1.get("KaIn") != null) {
									row1.put("KaIn", Money + (Long) row1.get("KaIn"));
								} else {
									row1.put("KaIn", Money);
								}
							} else {
								// 补贴减值
								row.put("KaOut", Money);
								if (row1.get("KaOut") != null) {
									row1.put("KaOut", Money + (Long) row1.get("KaOut"));
								} else {
									row1.put("KaOut", Money);
								}
							}
						} */

					} else {
						// 此对象和游标对象不是同一行
						// 游标下移
						cursor = accountStatisticsNow;
						port = i;
						break;
					}
				}
				// 合计行数据

				if (row1.get("Count") != null) {
					row1.put("Count", Count + (Long) row1.get("Count"));
				} else {
					row1.put("Count", Count);
				}
				row.put("Count", Count);
				// 最后一行合计

				rows.add(row);
			}
			rows.add(row1);
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
	public String selectAccountStatisticsExcel(HttpServletRequest request) throws Exception {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String DepartmentList = request.getParameter("DepartmentList");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		String RowName = request.getParameter("RowName");
		String Type = request.getParameter("Type");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("UserName", UserName);
		map.put("UserId", UserId);
		map.put("DepartmentList", DepartmentList);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);
		map.put("RowName", RowName);
		map.put("Type", Type);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		List<AccountStatistics> AccountStatisticsList = zhanghuyewuMapper.selectAccountStatistics(map);
		List<List<Object>> data = new ArrayList<List<Object>>();
		Long CountPersonCount = (long) 0;
		Long CountPersonIn = (long) 0;
		Long CountPersonOut = (long) 0;
		Long CountAllowanceCount = (long) 0;
		Long CountAllowanceIn = (long) 0;
		Long CountAllowanceOut = (long) 0;
		Long CountFavorableCount = (long) 0;
		Long CountFavorableIn = (long) 0;
		Long CountFavorableOut = (long) 0;
		Long CountKaCount = (long) 0;
		Long CountKaIn = (long) 0;
		Long CountKaOut = (long) 0;
		Long CountCount = (long) 0;

		if (AccountStatisticsList.size() > 0) {
			// 将第一个对象设为游标
			AccountStatistics cursor = AccountStatisticsList.get(0);
			Boolean flag = true;
			int port = 0;
			// 合计行
			List<Object> rowData1 = new ArrayList<Object>();
			rowData1.add("合计");
			while (flag) {
				List<Object> rowData = new ArrayList<Object>();
				if (RowName.equals("RechargeType")) {
					// 充值方式
					if (cursor.getRowName().equals("0")) {
						rowData.add("平台有卡");
					} else if (cursor.getRowName().equals("1")) {
						rowData.add("平台无卡");
					} else if (cursor.getRowName().equals("2")) {
						rowData.add("安卓app");
					} else if (cursor.getRowName().equals("3")) {
						rowData.add("IOSapp");
					} else if (cursor.getRowName().equals("4")) {
						rowData.add("微信app");
					} else if (cursor.getRowName().equals("5")) {
						rowData.add("现金圈存机");
					} else if (cursor.getRowName().equals("6")) {
						rowData.add("银行圈存机");
					} else if (cursor.getRowName().equals("7")) {
						rowData.add("补卡");
					}
				} else if (RowName.equals("MoneySource")) {
					// 资金来源
					if (cursor.getRowName().equals("0")) {
						rowData.add("现金");
					} else if (cursor.getRowName().equals("1")) {
						rowData.add("微信");
					} else if (cursor.getRowName().equals("2")) {
						rowData.add("支付宝");
					} else if (cursor.getRowName().equals("3")) {
						rowData.add("银行卡");
					} else if (cursor.getRowName().equals("4")) {
						rowData.add("支票");
					} else if (cursor.getRowName().equals("5")) {
						rowData.add("空充");
					}
				} else if (RowName.equals("Year") || RowName.equals("Month") || RowName.equals("Day")) {
					// 按时间分组
					rowData.add(cursor.getRowName());
				} else if (RowName.equals("CompanyInnerId")) {
					// 公司
					Department company = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(cursor.getRowName()));
					rowData.add(company.getDepartmentName());
				} else if (RowName.equals("Operator")) {
					// 操作人
					if (cursor.getRowName().equals("Android")) {
						rowData.add("安卓app");
					} else if (cursor.getRowName().equals("IOS")) {
						rowData.add("IOSapp");
					} else if (cursor.getRowName().indexOf("Operator") >= 0) {
						Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(cursor.getRowName().substring(8)));
						if (operator != null) {
							rowData.add(operator.getOperatorName());
						}
					} else if (cursor.getRowName().indexOf("SysTerminal") >= 0) {
						SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(cursor.getRowName().substring(11)));
						if (sysTerminal != null) {
							rowData.add(sysTerminal.getSysTerminalName());
						}
					}
				} else if (RowName.equals("Payee")) {
					// 收款账户
					if (cursor.getRowName().equals("ZhiFuBao")) {
						rowData.add("支付宝");
					} else if (cursor.getRowName().equals("WeiXin")) {
						rowData.add("微信");
					} else if (cursor.getRowName().equals("YinHangKa")) {
						rowData.add("银行卡");
					} else if (cursor.getRowName().equals("ZhiPiao")) {
						rowData.add("支票");
					} else if (cursor.getRowName().equals("KongChong")) {
						rowData.add("空充");
					} else if (cursor.getRowName().indexOf("Operator") >= 0) {
						Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(cursor.getRowName().substring(8)));
						if (operator != null) {
							rowData.add(operator.getOperatorName());
						}
					} else if (cursor.getRowName().indexOf("SysTerminal") >= 0) {
						SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(cursor.getRowName().substring(11)));
						if (sysTerminal != null) {
							rowData.add(sysTerminal.getSysTerminalName());
						}
					}
				}
				Long PersonCount = (long) 0;
				Long PersonIn = (long) 0;
				Long PersonOut = (long) 0;
				Long AllowanceCount = (long) 0;
				Long AllowanceIn = (long) 0;
				Long AllowanceOut = (long) 0;
				Long FavorableCount = (long) 0;
				Long FavorableIn = (long) 0;
				Long FavorableOut = (long) 0;
				Long KaCount = (long) 0;
				Long KaIn = (long) 0;
				Long KaOut = (long) 0;
				Long Count = (long) 0;
				for (int i = port; i < AccountStatisticsList.size(); i++) {
					AccountStatistics accountStatisticsNow = AccountStatisticsList.get(i);
					if (accountStatisticsNow.getRowName().equals(cursor.getRowName())) {
						if (i == AccountStatisticsList.size() - 1) {
							flag = false;
						}
						Long Money = accountStatisticsNow.getMoney();
						// 此对象和游标对象是同一行
						Count += Money;
						CountCount += Money;
						if (accountStatisticsNow.getAccountTypeInnerId() == 1) {
							// 现金合计
							PersonCount += Money;
							CountPersonCount += Money;
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 现金充值
								PersonIn += Money;
								CountPersonIn += Money;
							} else {
								// 现金减值
								PersonOut += Money;
								CountPersonOut += Money;
							}
						} else if (accountStatisticsNow.getAccountTypeInnerId() == 2) {
							// 补贴合计
							AllowanceCount += Money;
							CountAllowanceCount += Money;
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 补贴充值
								AllowanceIn += Money;
								CountAllowanceIn += Money;
							} else {
								// 补贴减值
								AllowanceOut += Money;
								CountAllowanceOut += Money;
							}
						} /*else if (accountStatisticsNow.getAccountTypeInnerId() == 3) {
							// 返现合计
							FavorableCount += Money;
							CountFavorableCount += Money;
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 返现充值
								FavorableIn += Money;
								CountFavorableIn += Money;
							} else {
								// 返现减值
								FavorableOut += Money;
								CountFavorableOut += Money;
							}
						}else if (accountStatisticsNow.getAccountTypeInnerId() == 4) {
							// 返现合计
							KaCount += Money;
							CountKaCount += Money;
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 返现充值
								KaIn += Money;
								CountKaIn += Money;
							} else {
								// 返现减值
								KaOut += Money;
								CountKaOut += Money;
							}
						}*/
					} else {
						// 此对象和游标对象不是同一行
						// 游标下移
						cursor = accountStatisticsNow;
						port = i;
						break;
					}
				}
				if (Type.equals("sum")) {
					rowData.add(PersonIn / 100.0);
					rowData.add(PersonOut / 100.0);
					rowData.add(PersonCount / 100.0);
					rowData.add(AllowanceIn / 100.0);
					rowData.add(AllowanceOut / 100.0);
					rowData.add(AllowanceCount / 100.0);
					/*rowData.add(FavorableIn / 100.0);
					rowData.add(FavorableOut / 100.0);
					rowData.add(FavorableCount / 100.0);
					rowData.add(KaIn / 100.0);
					rowData.add(KaOut / 100.0);
					rowData.add(KaCount / 100.0);*/
					rowData.add(Count / 100.0);
				} else {
					rowData.add(PersonIn);
					rowData.add(PersonOut);
					rowData.add(PersonCount);
					rowData.add(AllowanceIn);
					rowData.add(AllowanceOut);
					rowData.add(AllowanceCount);
					/*rowData.add(FavorableIn);
					rowData.add(FavorableOut);
					rowData.add(FavorableCount);
					rowData.add(KaIn);
					rowData.add(KaOut);
					rowData.add(KaCount);*/
					rowData.add(Count);
				}
				data.add(rowData);
			}
			if (Type.equals("sum")) {
				rowData1.add(CountPersonIn / 100.0);
				rowData1.add(CountPersonOut / 100.0);
				rowData1.add(CountPersonCount / 100.0);
				rowData1.add(CountAllowanceIn / 100.0);
				rowData1.add(CountAllowanceOut / 100.0);
				rowData1.add(CountAllowanceCount / 100.0);
				/*rowData1.add(CountFavorableIn / 100.0);
				rowData1.add(CountFavorableOut / 100.0);
				rowData1.add(CountFavorableCount / 100.0);
				rowData1.add(CountKaIn / 100.0);
				rowData1.add(CountKaOut / 100.0);
				rowData1.add(CountKaCount / 100.0);*/
				rowData1.add(CountCount / 100.0);
			} else {
				rowData1.add(CountPersonIn);
				rowData1.add(CountPersonOut);
				rowData1.add(CountPersonCount);
				rowData1.add(CountAllowanceIn);
				rowData1.add(CountAllowanceOut);
				rowData1.add(CountAllowanceCount);
				/*rowData1.add(CountFavorableIn);
				rowData1.add(CountFavorableOut);
				rowData1.add(CountFavorableCount);
				rowData1.add(CountKaIn);
				rowData1.add(CountKaOut);
				rowData1.add(CountKaCount);*/
				rowData1.add(CountCount);
			}
			data.add(rowData1);
		}
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/AccountStatistics" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		String header0 = "充减值统计表";
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
		List<String> headerList = new ArrayList<String>();
		headerList.add(header0);
		headerList.add(header1);
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("统计方式");
		headers0.add("现金账户");
		headers0.add("现金账户");
		headers0.add("现金账户");
		headers0.add("补贴账户");
		headers0.add("补贴账户");
		headers0.add("补贴账户");
		/*headers0.add("次账户");
		headers0.add("次账户");
		headers0.add("次账户");
		headers0.add("卡费");
		headers0.add("卡费");
		headers0.add("卡费");*/
		headers0.add("合计");
		List<String> headers1 = new ArrayList<String>();
		headers1.add("统计方式");
		headers1.add("充值");
		headers1.add("减值");
		headers1.add("合计");
		headers1.add("充值");
		headers1.add("减值");
		headers1.add("合计");
		/*headers1.add("充值");
		headers1.add("减值");
		headers1.add("合计");
		headers1.add("充值");
		headers1.add("减值");
		headers1.add("合计");*/
		headers1.add("合计");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(20);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		/*length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		length0.add(15);*/
		length0.add(15);
		eeu.selectAccountStatisticsExcel(workbook, 0, "充减值统计表", headerList, headers0, headers1, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
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
	public String showAllowanceExcel(HttpServletRequest request) throws IOException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		JSONObject json = new JSONObject();
		String result = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		InputStream fs = multipartFile.getInputStream();
		List<String[]> AllowanceList = readXls(fs);
		String regExp = "^([1-9][\\d]{0,5}|0)(\\.[\\d]{1,2})?$";
		Pattern p = Pattern.compile(regExp);
		for (String[] allowance : AllowanceList) {
			Map<String, Object> row = new HashMap<String, Object>();
			// Validate 0:正常 1：非法 2：警告
			row.put("Validate", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "UserInnerId");
			map.put("start", 0);
			map.put("UserIdEqu", allowance[0]);
			List<User> userList = renshiyewuMapper.selectUser(map);
			if (userList.size() == 0) {
				row.put("UserId", allowance[0] + "(此人员不存在)");
				row.put("Allowance", "(此人员不存在)");
				row.put("Validate", 1);
			} else {
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 2);
				map1.put("UserInnerId", userList.get(0).getUserInnerId());
				Account account1 = zhanghuyewuMapper.selectAccount(map1);
				if (account1 != null) {
					Matcher m = p.matcher(allowance[1]);
					if (!m.matches()) {
						row.put("UserId", allowance[0]);
						row.put("Allowance", allowance[1] + "(金额非法)");
						row.put("Validate", 1);
					} else {
						int flag = 0;
						for (String[] allowance1 : AllowanceList) {
							if (allowance1[0].equals(allowance[0])) {
								flag++;
							}
						}
						if (flag > 1) {
							row.put("UserId", allowance[0] + "(此人员重复补贴)");
							row.put("Allowance", allowance[1]);
							row.put("BeforeAllowance", account1.getMoney() / 100.0);
							row.put("AfterAllowance", account1.getMoney() / 100.0 + Double.parseDouble(allowance[1]));
							row.put("Validate", 2);
						} else {
							row.put("UserId", allowance[0]);
							row.put("Allowance", allowance[1]);
							row.put("BeforeAllowance", account1.getMoney() / 100.0);
							row.put("AfterAllowance", account1.getMoney() / 100.0 + Double.parseDouble(allowance[1]));
						}
					}
				} else {
					row.put("UserId", allowance[0] + "(人员补贴账户不存在)");
					row.put("Allowance", "(人员补贴账户不存在)");
					row.put("Validate", 1);
				}
			}
			rows.add(row);
		}
		json.put("rows", rows);
		result = json.toString();
		return result;
	}

	@Override
	@Transactional
	public String importAllowanceExcel(HttpServletRequest request) throws Exception {
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
		String StartTime = sdf.format(new Date());
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			} else {
				AccountLog accountLog = new AccountLog();
				UpLoadExcel uploadExcel = new UpLoadExcel();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rows", 100000000);
				map.put("order", "asc");
				map.put("sort", "UserInnerId");
				map.put("start", 0);
				map.put("UserIdEqu", uploadExcel.getValue(hssfRow.getCell(0)));
				List<User> userList = renshiyewuMapper.selectUser(map);

				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 2);
				map1.put("UserInnerId", userList.get(0).getUserInnerId());
				Account account = zhanghuyewuMapper.selectAccount(map1);

				accountLog.setAccountInnerId(account.getAccountInnerId());
				accountLog.setUserInnerId(account.getUserInnerId());
				accountLog.setDepartmentInnerId(userList.get(0).getDepartmentInnerId());
				accountLog.setCompanyInnerId(userList.get(0).getCompanyInnerId());
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				Integer Money = (int) (Double.parseDouble(hssfRow.getCell(1).toString()) * 100);
				accountLog.setMoney(Money);
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				accountLog.setBeforeMoney(account.getMoney());
				accountLog.setAfterMoney(accountLog.getBeforeMoney() + accountLog.getMoney());
				accountLog.setInOrOut(0);
				accountLog.setRechargeType(1);
				accountLog.setMoneySource(5);
				// 虚拟操作员，以后改写
				accountLog.setOperator("Operator" + operator.getOperatorInnerId());
				if (accountLog.getMoneySource() == 0) {
					accountLog.setPayee("Operator" + operator.getOperatorInnerId());
				} else if (accountLog.getMoneySource() == 1) {
					accountLog.setPayee("WeiXin");
				} else if (accountLog.getMoneySource() == 2) {
					accountLog.setPayee("ZhiFuBao");
				} else if (accountLog.getMoneySource() == 3) {
					accountLog.setPayee("YinHangKa");
				} else if (accountLog.getMoneySource() == 4) {
					accountLog.setPayee("ZhiPiao");
				} else if (accountLog.getMoneySource() == 5) {
					accountLog.setPayee("KongChong");
				}
				accountLog.setAccountDate(sdf.format(new Date()));
				Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
				if (result <= 0) {
					flag = false;
				}
			}
		}
		String EndTime = sdf.format(new Date());
		String Operator = "Operator" + operator.getOperatorInnerId();
		if (flag) {
			Long count = (long) 0;
			Long sum = (long) 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("sort", "AccountDate");
			map.put("start", 0);
			map.put("StartTime", StartTime);
			map.put("EndTime", EndTime);
			map.put("Operator", Operator);
			List<List<Object>> data = new ArrayList<List<Object>>();
			List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectAccountLog(map);
			for (AccountLog_User accountLog : accountLogList) {
				count++;
				sum += accountLog.getMoney();
				List<Object> rowData = new ArrayList<Object>();
				rowData.add(accountLog.getUserName());
				rowData.add(accountLog.getUserId());
				rowData.add(accountLog.getMoney() / 100.0);

				if (accountLog.getOperator().equals("Android")) {
					rowData.add("安卓app");
				} else if (accountLog.getOperator().equals("IOS")) {
					rowData.add("IOSapp");
				} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
					Operator operator1 = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
					if (operator1 != null) {
						rowData.add(operator1.getOperatorName());
					} else {
						rowData.add(" ");
					}
				} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
					SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
					if (sysTerminal != null) {
						rowData.add(sysTerminal.getSysTerminalName());
					} else {
						rowData.add(" ");
					}
				} else {
					rowData.add(" ");
				}

				rowData.add(accountLog.getAccountDate().substring(0, 19));
				data.add(rowData);
			}

			List<Object> rowData = new ArrayList<Object>();
			rowData.add("总计");
			rowData.add(count + "人");
			rowData.add(sum / 100.0);
			rowData.add(" ");
			rowData.add(" ");
			data.add(rowData);
			String relpath = request.getSession().getServletContext().getRealPath("excel");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
			String name = "/AllowanceLog" + sdf2.format(new Date()) + ".xlsx";
			OutputStream out = new FileOutputStream(relpath + name);
			ExportExcelUtils eeu = new ExportExcelUtils();
			XSSFWorkbook workbook = new XSSFWorkbook();
			String header0 = "补贴表";
			String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
			List<String> headerList = new ArrayList<String>();
			headerList.add(header0);
			headerList.add(header1);
			List<String> headers0 = new ArrayList<String>();
			headers0.add("人员姓名");
			headers0.add("人员编号");
			headers0.add("金额");
			headers0.add("操作人");
			headers0.add("充值时间");
			List<Integer> length0 = new ArrayList<Integer>();
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(20);
			eeu.selectUseruserExcel(workbook, 0, "补贴表", headerList, headers0, length0, data, out);
			workbook.write(out);
			out.close();
			jsonObjects.put("data", "excel/" + name);
			jsonObjects.put("code", 200);
			jsonObjects.put("msg", "充值成功！");
		} else {
			jsonObjects.put("code", 500);
			jsonObjects.put("msg", "充值失败！");
		}
		return jsonObjects.toString();
	}

	@Override
	@Transactional
	public String importAllowanceExcelAubtract(HttpServletRequest request) throws Exception {
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
		String StartTime = sdf.format(new Date());
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			} else {
				AccountLog accountLog = new AccountLog();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rows", 100000000);
				map.put("order", "asc");
				map.put("sort", "UserInnerId");
				map.put("start", 0);
				UpLoadExcel uploadExcel = new UpLoadExcel();
				map.put("UserIdEqu", uploadExcel.getValue(hssfRow.getCell(0)));
				List<User> userList = renshiyewuMapper.selectUser(map);

				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 2);
				map1.put("UserInnerId", userList.get(0).getUserInnerId());
				Account account = zhanghuyewuMapper.selectAccount(map1);

				accountLog.setAccountInnerId(account.getAccountInnerId());
				accountLog.setUserInnerId(account.getUserInnerId());
				accountLog.setDepartmentInnerId(userList.get(0).getDepartmentInnerId());
				accountLog.setCompanyInnerId(userList.get(0).getCompanyInnerId());
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				Integer Money = (int) (Double.parseDouble(hssfRow.getCell(1).toString()) * 100);
				accountLog.setMoney(-Money);
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				accountLog.setBeforeMoney(account.getMoney());
				accountLog.setAfterMoney(accountLog.getBeforeMoney() + accountLog.getMoney());
				accountLog.setInOrOut(1);
				accountLog.setRechargeType(1);
				accountLog.setMoneySource(5);
				// 虚拟操作员，以后改写
				accountLog.setOperator("Operator" + operator.getOperatorInnerId());
				if (accountLog.getMoneySource() == 0) {
					accountLog.setPayee("Operator" + operator.getOperatorInnerId());
				} else if (accountLog.getMoneySource() == 1) {
					accountLog.setPayee("WeiXin");
				} else if (accountLog.getMoneySource() == 2) {
					accountLog.setPayee("ZhiFuBao");
				} else if (accountLog.getMoneySource() == 3) {
					accountLog.setPayee("YinHangKa");
				} else if (accountLog.getMoneySource() == 4) {
					accountLog.setPayee("ZhiPiao");
				} else if (accountLog.getMoneySource() == 5) {
					accountLog.setPayee("KongChong");
				}
				accountLog.setAccountDate(sdf.format(new Date()));
				Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
				if (result <= 0) {
					flag = false;
				}
			}
		}
		String EndTime = sdf.format(new Date());
		String Operator = "Operator" + operator.getOperatorInnerId();
		if (flag) {
			Long count = (long) 0;
			Long sum = (long) 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("sort", "AccountDate");
			map.put("start", 0);
			map.put("StartTime", StartTime);
			map.put("EndTime", EndTime);
			map.put("Operator", Operator);
			List<List<Object>> data = new ArrayList<List<Object>>();
			List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectAccountLog(map);
			for (AccountLog_User accountLog : accountLogList) {
				count++;
				sum += accountLog.getMoney();
				List<Object> rowData = new ArrayList<Object>();
				rowData.add(accountLog.getUserName());
				rowData.add(accountLog.getUserId());
				rowData.add(accountLog.getMoney() / 100.0);

				if (accountLog.getOperator().equals("Android")) {
					rowData.add("安卓app");
				} else if (accountLog.getOperator().equals("IOS")) {
					rowData.add("IOSapp");
				} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
					Operator operator1 = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
					if (operator1 != null) {
						rowData.add(operator1.getOperatorName());
					} else {
						rowData.add(" ");
					}
				} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
					SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
					if (sysTerminal != null) {
						rowData.add(sysTerminal.getSysTerminalName());
					} else {
						rowData.add(" ");
					}
				} else {
					rowData.add(" ");
				}

				rowData.add(accountLog.getAccountDate().substring(0, 19));
				data.add(rowData);
			}

			List<Object> rowData = new ArrayList<Object>();
			rowData.add("总计");
			rowData.add(count + "人");
			rowData.add(sum / 100.0);
			rowData.add(" ");
			rowData.add(" ");
			data.add(rowData);
			String relpath = request.getSession().getServletContext().getRealPath("excel");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
			String name = "/AllowanceLog" + sdf2.format(new Date()) + ".xlsx";
			OutputStream out = new FileOutputStream(relpath + name);
			ExportExcelUtils eeu = new ExportExcelUtils();
			XSSFWorkbook workbook = new XSSFWorkbook();
			String header0 = "补贴表";
			String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
			List<String> headerList = new ArrayList<String>();
			headerList.add(header0);
			headerList.add(header1);
			List<String> headers0 = new ArrayList<String>();
			headers0.add("人员姓名");
			headers0.add("人员编号");
			headers0.add("次数");
			headers0.add("操作人");
			headers0.add("充值时间");
			List<Integer> length0 = new ArrayList<Integer>();
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(20);
			eeu.selectUseruserExcel(workbook, 0, "补贴表", headerList, headers0, length0, data, out);
			workbook.write(out);
			out.close();
			jsonObjects.put("data", "excel/" + name);
			jsonObjects.put("code", 200);
			jsonObjects.put("msg", "充值成功！");
		} else {
			jsonObjects.put("code", 500);
			jsonObjects.put("msg", "充值失败！");
		}
		return jsonObjects.toString();
	}

	@Override
	@Transactional
	public String selectOperatorAll() {
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
			jo.put("id", "Operator" + operator.getOperatorInnerId());
			jo.put("text", operator.getOperatorName());
			ja.add(jo);
		}

		map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "SysTerminalInnerId");
		map.put("start", 0);
		map.put("TerminalTypeInnerId", 5);
		List<SysTerminal> SysTerminalList = peizhiguanliMapper.selectSysTerminal(map);
		for (SysTerminal SysTerminal : SysTerminalList) {
			jo = new JSONObject();
			jo.put("id", "SysTerminal" + SysTerminal.getSysTerminalInnerId());
			jo.put("text", SysTerminal.getSysTerminalName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectPayeeAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "OperatorInnerId");
		map.put("start", 0);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();

		jo = new JSONObject();
		jo.put("id", "ZhiFuBao");
		jo.put("text", "支付宝");
		ja.add(jo);

		jo = new JSONObject();
		jo.put("id", "WeiXin");
		jo.put("text", "微信");
		ja.add(jo);

		jo = new JSONObject();
		jo.put("id", "YinHangKa");
		jo.put("text", "银行卡");
		ja.add(jo);

		jo = new JSONObject();
		jo.put("id", "ZhiPiao");
		jo.put("text", "支票");
		ja.add(jo);

		jo = new JSONObject();
		jo.put("id", "KongChong");
		jo.put("text", "空充");
		ja.add(jo);

		List<Operator> OperatorList = peizhiguanliMapper.selectOperator(map);
		for (Operator operator : OperatorList) {
			jo = new JSONObject();
			jo.put("id", "Operator" + operator.getOperatorInnerId());
			jo.put("text", operator.getOperatorName());
			ja.add(jo);
		}

		map = new HashMap<String, Object>();
		map.put("rows", 100000000);
		map.put("order", "asc");
		map.put("sort", "SysTerminalInnerId");
		map.put("start", 0);
		map.put("TerminalTypeInnerId", 5);
		List<SysTerminal> SysTerminalList = peizhiguanliMapper.selectSysTerminal(map);
		for (SysTerminal SysTerminal : SysTerminalList) {
			jo = new JSONObject();
			jo.put("id", "SysTerminal" + SysTerminal.getSysTerminalInnerId());
			jo.put("text", SysTerminal.getSysTerminalName());
			ja.add(jo);
		}
		return ja.toString();
	}

	@Override
	@Transactional
	public String selectAccountDiscount(DataGridModel dgm, HttpServletRequest request) {
		String AccountDiscountId = request.getParameter("AccountDiscountId");
		String AccountDiscountName = request.getParameter("AccountDiscountName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("AccountDiscountInnerId");
			dgm.setOrder("asc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(100000000);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("AccountDiscountId", AccountDiscountId);
		map.put("AccountDiscountName", AccountDiscountName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<AccountDiscount> AccountDiscountList = zhanghuyewuMapper.selectAccountDiscount(map);
		for (AccountDiscount AccountDiscount1 : AccountDiscountList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("BeginTime", AccountDiscount1.getBeginTime().substring(0, 19));
			row.put("EndTime", AccountDiscount1.getEndTime().substring(0, 19));

			row.put("AccountDiscountInnerId", AccountDiscount1.getAccountDiscountInnerId());
			row.put("AccountDiscountId", AccountDiscount1.getAccountDiscountId());
			row.put("AccountDiscountName", AccountDiscount1.getAccountDiscountName());
			row.put("UserTypeList", AccountDiscount1.getUserTypeList());
			String[] UserTypeList = AccountDiscount1.getUserTypeList().split(",");
			String UserTypeListName = "";
			for (String string : UserTypeList) {
				UserType UserType = peizhiguanliMapper.selectUserTypeByInnerId(Integer.valueOf(string));
				if (UserType != null) {
					UserTypeListName += UserType.getUserTypeName() + ",";
				}
			}
			row.put("UserTypeListName", UserTypeListName);
			row.put("Proportion", AccountDiscount1.getProportion() / 100);
			row.put("Remark", AccountDiscount1.getRemark());
			row.put("StandbyA", AccountDiscount1.getStandbyA());
			row.put("StandbyB", AccountDiscount1.getStandbyB());
			row.put("StandbyC", AccountDiscount1.getStandbyC());
			row.put("StandbyD", AccountDiscount1.getStandbyD());
			rows.add(row);
		}
		jo.put("total", zhanghuyewuMapper.selectAccountDiscountTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String insertAccountDiscount(AccountDiscount AccountDiscount, HttpServletRequest request) {
		AccountDiscount.setStandbyD("1");
		Integer result = zhanghuyewuMapper.insertAccountDiscount(AccountDiscount);
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
	public String updateAccountDiscount(AccountDiscount AccountDiscount, HttpServletRequest request) {
		Integer result = zhanghuyewuMapper.updateAccountDiscount(AccountDiscount);
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
	public String deleteAccountDiscount(AccountDiscount AccountDiscount, HttpServletRequest request) {
		AccountDiscount.setStandbyD("1");
		Integer result = zhanghuyewuMapper.deleteAccountDiscount(AccountDiscount);
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
	public String showNumberExcel(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		JSONObject json = new JSONObject();
		String result = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fileExcel");
		InputStream fs = multipartFile.getInputStream();
		List<String[]> AllowanceList = readXls(fs);
		String regExp = "^([1-9][\\d]{0,5}|0)$";
		Pattern p = Pattern.compile(regExp);
		for (String[] allowance : AllowanceList) {
			Map<String, Object> row = new HashMap<String, Object>();
			// Validate 0:正常 1：非法 2：警告
			row.put("Validate", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("order", "asc");
			map.put("sort", "UserInnerId");
			map.put("start", 0);
			map.put("UserIdEqu",allowance[0]);
			List<User> userList = renshiyewuMapper.selectUser(map);
			if (userList.size() == 0) {
				row.put("UserId", allowance[0] + "(此人员不存在)");
				row.put("Allowance", "(此人员不存在)");
				row.put("Validate", 1);
			} else {
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 3);
				map1.put("UserInnerId", userList.get(0).getUserInnerId());
				Account account1 = zhanghuyewuMapper.selectAccount(map1);
				if (account1 != null) {
					Matcher m = p.matcher(allowance[1]);
					if (!m.matches()) {
						row.put("UserId", allowance[0]);
						row.put("Allowance", allowance[1] + "(金额非法)");
						row.put("Validate", 1);
					} else {
						int flag = 0;
						for (String[] allowance1 : AllowanceList) {
							if (allowance1[0].equals(allowance[0])) {
								flag++;
							}
						}
						if (flag > 1) {
							row.put("UserId", allowance[0] + "(此人员重复补贴)");
							row.put("Allowance", allowance[1]);
							row.put("BeforeAllowance", account1.getMoney() / 100.0);
							row.put("AfterAllowance", account1.getMoney() / 100.0 + Double.parseDouble(allowance[1]));
							row.put("Validate", 2);
						} else {
							row.put("UserId", allowance[0]);
							row.put("Allowance", allowance[1]);
							row.put("BeforeAllowance", account1.getMoney() / 100.0);
							row.put("AfterAllowance", account1.getMoney() / 100.0 + Double.parseDouble(allowance[1]));
						}
					}
				} else {
					row.put("UserId", allowance[0] + "(人员补贴账户不存在)");
					row.put("Allowance", "(人员补贴账户不存在)");
					row.put("Validate", 1);
				}
			}
			rows.add(row);
		}
		json.put("rows", rows);
		result = json.toString();
		return result;
	}

	@Override
	@Transactional
	public String importNumberExcel(HttpServletRequest request) throws Exception {
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
		String StartTime = sdf.format(new Date());
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			} else {
				AccountLog accountLog = new AccountLog();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rows", 100000000);
				map.put("order", "asc");
				map.put("sort", "UserInnerId");
				map.put("start", 0);
				UpLoadExcel uploadExcel = new UpLoadExcel();
				map.put("UserIdEqu", uploadExcel.getValue(hssfRow.getCell(0)));
				List<User> userList = renshiyewuMapper.selectUser(map);

				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 3);
				map1.put("UserInnerId", userList.get(0).getUserInnerId());
				Account account = zhanghuyewuMapper.selectAccount(map1);

				accountLog.setAccountInnerId(account.getAccountInnerId());
				accountLog.setUserInnerId(account.getUserInnerId());
				accountLog.setDepartmentInnerId(userList.get(0).getDepartmentInnerId());
				accountLog.setCompanyInnerId(userList.get(0).getCompanyInnerId());
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				Integer Money = (int) (Double.parseDouble(hssfRow.getCell(1).toString()) * 100);
				accountLog.setMoney(Money);
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				accountLog.setBeforeMoney(account.getMoney());
				accountLog.setAfterMoney(accountLog.getBeforeMoney() + accountLog.getMoney());
				accountLog.setInOrOut(0);
				accountLog.setRechargeType(1);
				accountLog.setMoneySource(5);
				// 虚拟操作员，以后改写
				accountLog.setOperator("Operator" + operator.getOperatorInnerId());
				if (accountLog.getMoneySource() == 0) {
					accountLog.setPayee("Operator" + operator.getOperatorInnerId());
				} else if (accountLog.getMoneySource() == 1) {
					accountLog.setPayee("WeiXin");
				} else if (accountLog.getMoneySource() == 2) {
					accountLog.setPayee("ZhiFuBao");
				} else if (accountLog.getMoneySource() == 3) {
					accountLog.setPayee("YinHangKa");
				} else if (accountLog.getMoneySource() == 4) {
					accountLog.setPayee("ZhiPiao");
				} else if (accountLog.getMoneySource() == 5) {
					accountLog.setPayee("KongChong");
				}
				accountLog.setAccountDate(sdf.format(new Date()));
				Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
				if (result <= 0) {
					flag = false;
				}
			}
		}
		String EndTime = sdf.format(new Date());
		String Operator = "Operator" + operator.getOperatorInnerId();
		if (flag) {
			Long count = (long) 0;
			Long sum = (long) 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("sort", "AccountDate");
			map.put("start", 0);
			map.put("StartTime", StartTime);
			map.put("EndTime", EndTime);
			map.put("Operator", Operator);
			List<List<Object>> data = new ArrayList<List<Object>>();
			List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectAccountLog(map);
			for (AccountLog_User accountLog : accountLogList) {
				count++;
				sum += accountLog.getMoney();
				List<Object> rowData = new ArrayList<Object>();
				rowData.add(accountLog.getUserName());
				rowData.add(accountLog.getUserId());
				rowData.add(accountLog.getMoney() / 100.0);

				if (accountLog.getOperator().equals("Android")) {
					rowData.add("安卓app");
				} else if (accountLog.getOperator().equals("IOS")) {
					rowData.add("IOSapp");
				} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
					Operator operator1 = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
					if (operator1 != null) {
						rowData.add(operator1.getOperatorName());
					} else {
						rowData.add(" ");
					}
				} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
					SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
					if (sysTerminal != null) {
						rowData.add(sysTerminal.getSysTerminalName());
					} else {
						rowData.add(" ");
					}
				} else {
					rowData.add(" ");
				}

				rowData.add(accountLog.getAccountDate().substring(0, 19));
				data.add(rowData);
			}

			List<Object> rowData = new ArrayList<Object>();
			rowData.add("总计");
			rowData.add(count + "人");
			rowData.add(sum / 100.0);
			rowData.add(" ");
			rowData.add(" ");
			data.add(rowData);
			String relpath = request.getSession().getServletContext().getRealPath("excel");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
			String name = "/NumberLog" + sdf2.format(new Date()) + ".xlsx";
			OutputStream out = new FileOutputStream(relpath + name);
			ExportExcelUtils eeu = new ExportExcelUtils();
			XSSFWorkbook workbook = new XSSFWorkbook();
			String header0 = "次数充值表";
			String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
			List<String> headerList = new ArrayList<String>();
			headerList.add(header0);
			headerList.add(header1);
			List<String> headers0 = new ArrayList<String>();
			headers0.add("人员姓名");
			headers0.add("人员编号");
			headers0.add("金额");
			headers0.add("操作人");
			headers0.add("充值时间");
			List<Integer> length0 = new ArrayList<Integer>();
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(20);
			eeu.selectUseruserExcel(workbook, 0, "次数充值表", headerList, headers0, length0, data, out);
			workbook.write(out);
			out.close();
			jsonObjects.put("data", "excel/" + name);
			jsonObjects.put("code", 200);
			jsonObjects.put("msg", "充值成功！");
		} else {
			jsonObjects.put("code", 500);
			jsonObjects.put("msg", "充值失败！");
		}
		return jsonObjects.toString();
	}

	@Override
	public String importNumberExcelAubtract(HttpServletRequest request) throws Exception {
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
		String StartTime = sdf.format(new Date());
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			} else {
				AccountLog accountLog = new AccountLog();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rows", 100000000);
				map.put("order", "asc");
				map.put("sort", "UserInnerId");
				map.put("start", 0);
				UpLoadExcel uploadExcel = new UpLoadExcel();
				map.put("UserIdEqu", uploadExcel.getValue(hssfRow.getCell(0)));
				List<User> userList = renshiyewuMapper.selectUser(map);

				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("AccountTypeInnerId", 3);
				map1.put("UserInnerId", userList.get(0).getUserInnerId());
				Account account = zhanghuyewuMapper.selectAccount(map1);

				accountLog.setAccountInnerId(account.getAccountInnerId());
				accountLog.setUserInnerId(account.getUserInnerId());
				accountLog.setDepartmentInnerId(userList.get(0).getDepartmentInnerId());
				accountLog.setCompanyInnerId(userList.get(0).getCompanyInnerId());
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				Integer Money = (int) (Double.parseDouble(hssfRow.getCell(1).toString()) * 100);
				accountLog.setMoney(-Money);
				accountLog.setAccountTypeInnerId(account.getAccountTypeInnerId());
				accountLog.setBeforeMoney(account.getMoney());
				accountLog.setAfterMoney(accountLog.getBeforeMoney() + accountLog.getMoney());
				accountLog.setInOrOut(1);
				accountLog.setRechargeType(1);
				accountLog.setMoneySource(5);
				// 虚拟操作员，以后改写
				accountLog.setOperator("Operator" + operator.getOperatorInnerId());
				if (accountLog.getMoneySource() == 0) {
					accountLog.setPayee("Operator" + operator.getOperatorInnerId());
				} else if (accountLog.getMoneySource() == 1) {
					accountLog.setPayee("WeiXin");
				} else if (accountLog.getMoneySource() == 2) {
					accountLog.setPayee("ZhiFuBao");
				} else if (accountLog.getMoneySource() == 3) {
					accountLog.setPayee("YinHangKa");
				} else if (accountLog.getMoneySource() == 4) {
					accountLog.setPayee("ZhiPiao");
				} else if (accountLog.getMoneySource() == 5) {
					accountLog.setPayee("KongChong");
				}
				accountLog.setAccountDate(sdf.format(new Date()));
				Integer result = zhanghuyewuMapper.insertAccountLog(accountLog);
				if (result <= 0) {
					flag = false;
				}
			}
		}
		String EndTime = sdf.format(new Date());
		String Operator = "Operator" + operator.getOperatorInnerId();
		if (flag) {
			Long count = (long) 0;
			Long sum = (long) 0;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", 100000000);
			map.put("sort", "AccountDate");
			map.put("start", 0);
			map.put("StartTime", StartTime);
			map.put("EndTime", EndTime);
			map.put("Operator", Operator);
			List<List<Object>> data = new ArrayList<List<Object>>();
			List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectAccountLog(map);
			for (AccountLog_User accountLog : accountLogList) {
				count++;
				sum += accountLog.getMoney();
				List<Object> rowData = new ArrayList<Object>();
				rowData.add(accountLog.getUserName());
				rowData.add(accountLog.getUserId());
				rowData.add(accountLog.getMoney() / 100.0);

				if (accountLog.getOperator().equals("Android")) {
					rowData.add("安卓app");
				} else if (accountLog.getOperator().equals("IOS")) {
					rowData.add("IOSapp");
				} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
					Operator operator1 = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
					if (operator1 != null) {
						rowData.add(operator1.getOperatorName());
					} else {
						rowData.add(" ");
					}
				} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
					SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
					if (sysTerminal != null) {
						rowData.add(sysTerminal.getSysTerminalName());
					} else {
						rowData.add(" ");
					}
				} else {
					rowData.add(" ");
				}

				rowData.add(accountLog.getAccountDate().substring(0, 19));
				data.add(rowData);
			}

			List<Object> rowData = new ArrayList<Object>();
			rowData.add("总计");
			rowData.add(count + "人");
			rowData.add(sum / 100.0);
			rowData.add(" ");
			rowData.add(" ");
			data.add(rowData);
			String relpath = request.getSession().getServletContext().getRealPath("excel");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
			String name = "/AccountLog" + sdf2.format(new Date()) + ".xlsx";
			OutputStream out = new FileOutputStream(relpath + name);
			ExportExcelUtils eeu = new ExportExcelUtils();
			XSSFWorkbook workbook = new XSSFWorkbook();
			String header0 = "次数减值表";
			String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
			List<String> headerList = new ArrayList<String>();
			headerList.add(header0);
			headerList.add(header1);
			List<String> headers0 = new ArrayList<String>();
			headers0.add("人员姓名");
			headers0.add("人员编号");
			headers0.add("金额");
			headers0.add("操作人");
			headers0.add("充值时间");
			List<Integer> length0 = new ArrayList<Integer>();
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(15);
			length0.add(20);
			eeu.selectUseruserExcel(workbook, 0, "次数减值表", headerList, headers0, length0, data, out);
			workbook.write(out);
			out.close();
			jsonObjects.put("data", "excel/" + name);
			jsonObjects.put("code", 200);
			jsonObjects.put("msg", "充值成功！");
		} else {
			jsonObjects.put("code", 500);
			jsonObjects.put("msg", "充值失败！");
		}
		return jsonObjects.toString();
	}

	@Override
	public String selectSettlement(AccountDiscount accountDiscount, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("StartTime", request.getParameter("StartTime"));
		map.put("EndTime", request.getParameter("EndTime"));
		List<Settlement> settlementList = zhanghuyewuMapper.selectSettlement(map);
		List<Map<String,Object>> rows = new ArrayList<>();
		for (Settlement settlement : settlementList) {
			Map<String,Object> row = new HashMap<String,Object>();
			row.put("CardMoney", Double.parseDouble(settlement.getCardMoney())/100.0);
			row.put("CashMoney", Double.parseDouble(settlement.getCashMoney())/100.0);
			row.put("FDateTime", settlement.getFDateTime().substring(0, 10));
			row.put("NumberMoney", Double.parseDouble(settlement.getNumberMoney())/100.0);
			row.put("PeopleTotal", settlement.getPeopleTotal());
			row.put("SubsidyMoney", Double.parseDouble(settlement.getSubsidyMoney())/100.0);
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows", rows);
		jo.put("total", settlementList.size());
		return jo.toString();
	}

	@Override
	public String selectPayment(HttpServletRequest request) {
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String PaymentFlag = request.getParameter("PaymentFlag");
		Map<String,Object> map = new HashMap<>();
		map.put("StartTime", StartTime +" 00:00:00");
		map.put("EndTime", EndTime +" 23:59:59");
		map.put("PaymentFlag", PaymentFlag);
		List<Payment> paymentList = zhanghuyewuMapper.selectPayment(map); 
		List<App> appList = peizhiguanliMapper.selectAppAll();
		List<Map<String,Object>> rows = new ArrayList<>();
		for (Payment payment : paymentList) {
			Map<String,Object> row = new HashMap<>();
			row.put("DateTime", payment.getDateTime().substring(0, 10));
			row.put("PaymentInnerId", payment.getPaymentInnerId());
			if(payment.getPaymentFlag() == 0){
				row.put("PaymentFlag", "未付款");
			}else if(payment.getPaymentFlag() == 1){
				row.put("PaymentFlag", "已付款");
			}
			if(payment.getAppAName() != null && !payment.getAppAName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppAName().equals(appList.get(i).getAppName())){
						row.put("AppAName", payment.getAppAName());
						row.put("AppAPM", payment.getAppAPM()/100.0);
						row.put("AppAAM", payment.getAppAAM()/100.0);
					}
				}
			}
			if(payment.getAppBName() != null && !payment.getAppBName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppBName().equals(appList.get(i).getAppName())){
						row.put("AppBName", payment.getAppBName());
						row.put("AppBPM", payment.getAppBPM()/100.0);
						row.put("AppBAM", payment.getAppBAM()/100.0);
					}
				}
			}
			if(payment.getAppCName() != null && !payment.getAppCName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppCName().equals(appList.get(i).getAppName())){
						row.put("AppCName", payment.getAppCName());
						row.put("AppCPM", payment.getAppCPM()/100.0);
						row.put("AppCAM", payment.getAppCAM()/100.0);
					}
				}
			}
			if(payment.getAppDName() != null && !payment.getAppDName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppDName().equals(appList.get(i).getAppName())){
						row.put("AppDName", payment.getAppDName());
						row.put("AppDPM", payment.getAppDPM()/100.0);
						row.put("AppDAM", payment.getAppDAM()/100.0);
					}
				}
			}
			if(payment.getAppEName() != null && !payment.getAppEName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppEName().equals(appList.get(i).getAppName())){
						row.put("AppEName", payment.getAppEName());
						row.put("AppEPM", payment.getAppEPM()/100.0);
						row.put("AppEAM", payment.getAppEAM()/100.0);
					}
				}
			}
			if(payment.getAppFName() != null && !payment.getAppFName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppFName().equals(appList.get(i).getAppName())){
						row.put("AppFName", payment.getAppFName());
						row.put("AppFPM", payment.getAppFPM()/100.0);
						row.put("AppFAM", payment.getAppFAM()/100.0);
					}
				}
			}
			if(payment.getAppGName() != null && !payment.getAppGName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppGName().equals(appList.get(i).getAppName())){
						row.put("AppGName", payment.getAppGName());
						row.put("AppGPM", payment.getAppGPM()/100.0);
						row.put("AppGAM", payment.getAppGAM()/100.0);
					}
				}
			}
			if(payment.getAppHName() != null && !payment.getAppHName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppHName().equals(appList.get(i).getAppName())){
						row.put("AppHName", payment.getAppHName());
						row.put("AppHPM", payment.getAppHPM()/100.0);
						row.put("AppHAM", payment.getAppHAM()/100.0);
					}
				}
			}
			rows.add(row);
		}
		JSONObject jo = new JSONObject();
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectPaymentTree(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		map.put("PaymentFlag", 0);
		List<Payment> paymentList = zhanghuyewuMapper.selectPayment(map); 
		List<App> appList = peizhiguanliMapper.selectAppAll();
		List<Map<String,Object>> rows = new ArrayList<>();
		int total = 0;
		for (Payment payment : paymentList) {
			if(payment.getAppAName() != null && !payment.getAppAName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppAName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppAName", payment.getAppAName());
						row.put("flag", 1);
						if(total < 1){
							total = 1;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppBName() != null && !payment.getAppBName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppBName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppBName", payment.getAppBName());
						row.put("flag", 2);
						if(total < 2){
							total = 2;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppCName() != null && !payment.getAppCName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppCName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppCName", payment.getAppCName());
						row.put("flag", 3);
						if(total < 3){
							total = 3;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppDName() != null && !payment.getAppDName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppDName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppDName", payment.getAppDName());
						row.put("flag", 4);
						if(total < 4){
							total = 4;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppEName() != null && !payment.getAppEName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppEName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppEName", payment.getAppEName());
						row.put("flag", 5);
						if(total < 5){
							total = 5;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppFName() != null && !payment.getAppFName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppFName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppFName", payment.getAppFName());
						row.put("flag", 6);
						if(total < 6){
							total = 6;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppGName() != null && !payment.getAppGName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppGName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppGName", payment.getAppGName());
						row.put("flag", 7);
						if(total < 7){
							total = 7;
							rows.add(row);
						}
					}
				}
			}
			if(payment.getAppHName() != null && !payment.getAppHName().equals("")){
				for(int i = 0;i<appList.size();i++){
					if(payment.getAppHName().equals(appList.get(i).getAppName())){
						Map<String,Object> row = new HashMap<>();
						row.put("AppHName", payment.getAppHName());
						row.put("flag", 8);
						if(total < 8){
							total = 8;
							rows.add(row);
						}
					}
				}
			}
		}
		JSONObject jo = new JSONObject();
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	@Transactional
	public String updatePayment(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String PaymentInnerId = request.getParameter("PaymentInnerId");
		JSONObject jo = new JSONObject();
		String[] split = PaymentInnerId.split(",");
		for (String string : split) {
			Map<String,Object> map = new HashMap<>();
			map.put("PaymentInnerId", string);
			map.put("OperUser", operator.getOperatorInnerId());
			map.put("OperDT", sdf.format(new Date()));
			int result = zhanghuyewuMapper.updatePayment(map);
			if(result <= 0){
				jo.put("code", 500);
				jo.put("msg", "付款操作失败");
				return jo.toString();
			}
		}
		jo.put("code", 200);
		jo.put("msg", "付款操作完成");
		return jo.toString();
	}

	@Override
	public String selectLSAccountStatistics(HttpServletRequest request) {
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		String RowName = request.getParameter("RowName");
		String Type = request.getParameter("Type");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);
		map.put("RowName", RowName);
		map.put("Type", Type);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		List<AccountStatistics> AccountStatisticsList = zhanghuyewuMapper.selectLSAccountStatistics(map);
		if (AccountStatisticsList.size() > 0) {
			// 将第一个对象设为游标
			AccountStatistics cursor = AccountStatisticsList.get(0);
			Boolean flag = true;
			int port = 0;
			// 合计行
			Map<String, Object> row1 = new HashMap<String, Object>();
			row1.put("RowName", "合计");
			while (flag) {
				Map<String, Object> row = new HashMap<String, Object>();
				if (RowName.equals("RechargeType")) {
					// 充值方式
					if (cursor.getRowName().equals("0")) {
						row.put("RowName", "平台有卡");
					} else if (cursor.getRowName().equals("1")) {
						row.put("RowName", "平台无卡");
					} else if (cursor.getRowName().equals("2")) {
						row.put("RowName", "安卓app");
					} else if (cursor.getRowName().equals("3")) {
						row.put("RowName", "IOSapp");
					} else if (cursor.getRowName().equals("4")) {
						row.put("RowName", "微信app");
					} else if (cursor.getRowName().equals("5")) {
						row.put("RowName", "现金圈存机");
					} else if (cursor.getRowName().equals("6")) {
						row.put("RowName", "银行圈存机");
					} else if (cursor.getRowName().equals("7")) {
						row.put("RowName", "补卡");
					}
				} else if (RowName.equals("MoneySource")) {
					// 资金来源
					if (cursor.getRowName().equals("0")) {
						row.put("RowName", "现金");
					} else if (cursor.getRowName().equals("1")) {
						row.put("RowName", "微信");
					} else if (cursor.getRowName().equals("2")) {
						row.put("RowName", "支付宝");
					} else if (cursor.getRowName().equals("3")) {
						row.put("RowName", "银行卡");
					} else if (cursor.getRowName().equals("4")) {
						row.put("RowName", "支票");
					} else if (cursor.getRowName().equals("5")) {
						row.put("RowName", "空充");
					}
				} else if (RowName.equals("Year") || RowName.equals("Month") || RowName.equals("Day")) {
					// 按时间分组
					row.put("RowName", cursor.getRowName());
				} else if (RowName.equals("CompanyInnerId")) {
					// 公司
					Department company = renshiyewuMapper.selectDepartmentByInnerId(Integer.valueOf(cursor.getRowName()));
					row.put("RowName", company.getDepartmentName());
				} else if (RowName.equals("Operator")) {
					// 操作人
					if (cursor.getRowName().equals("Android")) {
						row.put("RowName", "安卓app");
					} else if (cursor.getRowName().equals("IOS")) {
						row.put("RowName", "IOSapp");
					} else if (cursor.getRowName().indexOf("Operator") >= 0) {
						Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(cursor.getRowName().substring(8)));
						if (operator != null) {
							row.put("RowName", operator.getOperatorName());
						}
					} else if (cursor.getRowName().indexOf("SysTerminal") >= 0) {
						SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(cursor.getRowName().substring(11)));
						if (sysTerminal != null) {
							row.put("RowName", sysTerminal.getSysTerminalName());
						}
					}
				} else if (RowName.equals("Payee")) {
					// 收款账户
					if (cursor.getRowName().equals("ZhiFuBao")) {
						row.put("RowName", "支付宝");
					} else if (cursor.getRowName().equals("WeiXin")) {
						row.put("RowName", "微信");
					} else if (cursor.getRowName().equals("YinHangKa")) {
						row.put("RowName", "银行卡");
					} else if (cursor.getRowName().equals("ZhiPiao")) {
						row.put("RowName", "支票");
					} else if (cursor.getRowName().equals("KongChong")) {
						row.put("RowName", "空充");
					} else if (cursor.getRowName().indexOf("Operator") >= 0) {
						Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(cursor.getRowName().substring(8)));
						if (operator != null) {
							row.put("RowName", operator.getOperatorName());
						}
					} else if (cursor.getRowName().indexOf("SysTerminal") >= 0) {
						SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(cursor.getRowName().substring(11)));
						if (sysTerminal != null) {
							row.put("RowName", sysTerminal.getSysTerminalName());
						}
					}
				}
				Long Count = (long) 0;
				for (int i = port; i < AccountStatisticsList.size(); i++) {
					AccountStatistics accountStatisticsNow = AccountStatisticsList.get(i);
					if (accountStatisticsNow.getRowName().equals(cursor.getRowName())) {
						if (i == AccountStatisticsList.size() - 1) {
							flag = false;
						}
						Long Money = accountStatisticsNow.getMoney();
						// 此对象和游标对象是同一行
						Count += Money;
						if (accountStatisticsNow.getAccountTypeInnerId() == 1) {
							// 现金合计
							if (row.get("PersonCount") != null) {
								row.put("PersonCount", Money + (Long) row.get("PersonCount"));
							} else {
								row.put("PersonCount", Money);
							}
							if (row1.get("PersonCount") != null) {
								row1.put("PersonCount", Money + (Long) row1.get("PersonCount"));
							} else {
								row1.put("PersonCount", Money);
							}
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 现金充值
								row.put("PersonIn", Money);
								if (row1.get("PersonIn") != null) {
									row1.put("PersonIn", Money + (Long) row1.get("PersonIn"));
								} else {
									row1.put("PersonIn", Money);
								}
							} else {
								// 现金减值
								row.put("PersonOut", Money);
								if (row1.get("PersonOut") != null) {
									row1.put("PersonOut", Money + (Long) row1.get("PersonOut"));
								} else {
									row1.put("PersonOut", Money);
								}
							}
						} else if (accountStatisticsNow.getAccountTypeInnerId() == 2) {
							// 补贴合计
							if (row.get("AllowanceCount") != null) {
								row.put("AllowanceCount", Money + (Long) row.get("AllowanceCount"));
							} else {
								row.put("AllowanceCount", Money);
							}
							if (row1.get("AllowanceCount") != null) {
								row1.put("AllowanceCount", Money + (Long) row1.get("AllowanceCount"));
							} else {
								row1.put("AllowanceCount", Money);
							}
							if (accountStatisticsNow.getInOrOut() == 0) {
								// 补贴充值
								row.put("AllowanceIn", Money);
								if (row1.get("AllowanceIn") != null) {
									row1.put("AllowanceIn", Money + (Long) row1.get("AllowanceIn"));
								} else {
									row1.put("AllowanceIn", Money);
								}
							} else {
								// 补贴减值
								row.put("AllowanceOut", Money);
								if (row1.get("AllowanceOut") != null) {
									row1.put("AllowanceOut", Money + (Long) row1.get("AllowanceOut"));
								} else {
									row1.put("AllowanceOut", Money);
								}
							}
						} 

					} else {
						// 此对象和游标对象不是同一行
						// 游标下移
						cursor = accountStatisticsNow;
						port = i;
						break;
					}
				}
				// 合计行数据

				if (row1.get("Count") != null) {
					row1.put("Count", Count + (Long) row1.get("Count"));
				} else {
					row1.put("Count", Count);
				}
				row.put("Count", Count);
				// 最后一行合计

				rows.add(row);
			}
			rows.add(row1);
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
	public String selectlsAccountLog(DataGridModel dgm, HttpServletRequest request) {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String AccountTypeInnerId = request.getParameter("AccountTypeInnerId");
		String InOrOut = request.getParameter("InOrOut");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("AccountDate");
			dgm.setOrder("desc");
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
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
		map.put("rows", dgm.getRows());
		// map.put("order", dgm.getOrder());
		map.put("sort", sortString);
		map.put("start", dgm.getStart());
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		map.put("DepartmentList", 42);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("AccountTypeInnerId", AccountTypeInnerId);
		map.put("InOrOut", InOrOut);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectlsAccountLog(map);
		for (AccountLog_User accountLog : accountLogList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("AccountLogInnerId", accountLog.getAccountLogInnerId());
			row.put("Money", accountLog.getMoney() / 100.0);
			row.put("BeforeMoney", accountLog.getBeforeMoney() / 100.0);
			row.put("AfterMoney", accountLog.getAfterMoney() / 100.0);
			row.put("NowMoney", accountLog.getNowMoney() / 100.0);
			row.put("AccountDate", accountLog.getAccountDate().substring(0, 19));
			row.put("UserId", accountLog.getUserId());
			row.put("UserName", accountLog.getUserName());
			row.put("CompanyName", accountLog.getCompanyName());
			row.put("DepartmentName", accountLog.getDepartmentName());
			row.put("AccountTypeName", accountLog.getAccountTypeName());
			if (accountLog.getInOrOut() == 0) {
				row.put("InOrOut", "充值");
			} else {
				row.put("InOrOut", "减值");
			}
			if (accountLog.getRechargeType() == 0) {
				row.put("RechargeType", "平台有卡");
			} else if (accountLog.getRechargeType() == 1) {
				row.put("RechargeType", "平台无卡");
			} else if (accountLog.getRechargeType() == 2) {
				row.put("RechargeType", "安卓app");
			} else if (accountLog.getRechargeType() == 3) {
				row.put("RechargeType", "IOSapp");
			} else if (accountLog.getRechargeType() == 4) {
				row.put("RechargeType", "微信app");
			} else if (accountLog.getRechargeType() == 5) {
				row.put("RechargeType", "现金圈存机");
			} else if (accountLog.getRechargeType() == 6) {
				row.put("RechargeType", "银行圈存机");
			} else if (accountLog.getRechargeType() == 7) {
				row.put("RechargeType", "补卡");
			}
			if (accountLog.getMoneySource() == 0) {
				row.put("MoneySource", "现金");
			} else if (accountLog.getMoneySource() == 1) {
				row.put("MoneySource", "微信");
			} else if (accountLog.getMoneySource() == 2) {
				row.put("MoneySource", "支付宝");
			} else if (accountLog.getMoneySource() == 3) {
				row.put("MoneySource", "银行卡");
			} else if (accountLog.getMoneySource() == 4) {
				row.put("MoneySource", "支票");
			} else if (accountLog.getMoneySource() == 5) {
				row.put("MoneySource", "空充");
			}

			if (accountLog.getOperator().equals("Android")) {
				row.put("Operator", "安卓app");
			} else if (accountLog.getOperator().equals("IOS")) {
				row.put("Operator", "IOSapp");
			} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
				if (operator != null) {
					row.put("Operator", operator.getOperatorName());
				}
			} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
				if (sysTerminal != null) {
					row.put("Operator", sysTerminal.getSysTerminalName());
				}
			}

			if (accountLog.getPayee().equals("ZhiFuBao")) {
				row.put("Payee", "支付宝");
			} else if (accountLog.getPayee().equals("WeiXin")) {
				row.put("Payee", "微信");
			} else if (accountLog.getPayee().equals("YinHangKa")) {
				row.put("Payee", "银行卡");
			} else if (accountLog.getPayee().equals("ZhiPiao")) {
				row.put("Payee", "支票");
			} else if (accountLog.getPayee().equals("KongChong")) {
				row.put("Payee", "空充");
			} else if (accountLog.getPayee().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getPayee().substring(8)));
				if (operator != null) {
					row.put("Payee", operator.getOperatorName());
				}
			} else if (accountLog.getPayee().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getPayee().substring(11)));
				if (sysTerminal != null) {
					row.put("Payee", sysTerminal.getSysTerminalName());
				}
			}

			row.put("Remark", accountLog.getRemark());
			row.put("StandbyA", accountLog.getStandbyA());
			row.put("StandbyB", accountLog.getStandbyB());
			row.put("StandbyC", accountLog.getStandbyC());
			row.put("StandbyD", accountLog.getStandbyD());
			rows.add(row);
		}
		Integer total = zhanghuyewuMapper.selectlsAccountLogTotal(map);
		if (total > 0 && dgm.getRows() * dgm.getPage() >= total) {
			map.put("rows", 100000000);
			map.put("start", 0);
			List<AccountLog_User> accountLogList1 = zhanghuyewuMapper.selectlsAccountLog(map);
			Long Money = (long) 0;
			Long Count = (long) 0;
			for (AccountLog_User accountLog : accountLogList1) {
				Count++;
				Money += accountLog.getMoney();
			}
			// 最后一行合计
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserId", "合计");
			row.put("Money", Money / 100.0);
			row.put("UserName", Count + "人");
			rows.add(row);
		}
		jo.put("total", total);
		jo.put("rows", rows);
		return jo.toString();
	}

	@Override
	public String selectlsAccountLogExcel(HttpServletRequest request) throws Exception {
		String UserId = request.getParameter("UserId");
		String UserName = request.getParameter("UserName");
		String Remark = request.getParameter("Remark");
		String StandbyA = request.getParameter("StandbyA");
		String StandbyB = request.getParameter("StandbyB");
		String StandbyC = request.getParameter("StandbyC");
		String StandbyD = request.getParameter("StandbyD");
		String StartTime = request.getParameter("StartTime");
		String EndTime = request.getParameter("EndTime");
		String AccountTypeInnerId = request.getParameter("AccountTypeInnerId");
		String InOrOut = request.getParameter("InOrOut");
		String Operator = request.getParameter("Operator");
		String Payee = request.getParameter("Payee");
		String RechargeType = request.getParameter("RechargeType");
		String MoneySource = request.getParameter("MoneySource");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Map<String, Object> map = new HashMap<String, Object>();
		if (sort == null || sort.equals("")) {
			sort = "UserInnerId";
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
		map.put("rows", 100000000);
		// map.put("order", order);
		map.put("sort", sortString);
		map.put("start", 0);
		map.put("UserId", UserId);
		map.put("UserName", UserName);
		map.put("Remark", Remark);
		map.put("StandbyA", StandbyA);
		map.put("StandbyB", StandbyB);
		map.put("StandbyC", StandbyC);
		map.put("StandbyD", StandbyD);
		map.put("DepartmentList", 42);
		map.put("StartTime", StartTime);
		map.put("EndTime", EndTime);
		map.put("AccountTypeInnerId", AccountTypeInnerId);
		map.put("InOrOut", InOrOut);
		map.put("Operator", Operator);
		map.put("Payee", Payee);
		map.put("RechargeType", RechargeType);
		map.put("MoneySource", MoneySource);

		List<List<Object>> data = new ArrayList<List<Object>>();
		int count = 0;
		Long sum = (long) 0;

		List<AccountLog_User> accountLogList = zhanghuyewuMapper.selectlsAccountLog(map);
		for (AccountLog_User accountLog : accountLogList) {
			count++;
			sum += accountLog.getMoney();
			List<Object> rowData = new ArrayList<Object>();
			rowData.add(accountLog.getUserId());
			rowData.add(accountLog.getUserName());
			rowData.add(accountLog.getDepartmentName());
			rowData.add(accountLog.getAccountTypeName());
			rowData.add(accountLog.getMoney() / 100.0);
			if (accountLog.getInOrOut() == 0) {
				rowData.add("充值");
			} else {
				rowData.add("减值");
			}
			rowData.add(accountLog.getAccountDate().substring(0, 19));
			if (accountLog.getRechargeType() == 0) {
				rowData.add("平台有卡");
			} else if (accountLog.getRechargeType() == 1) {
				rowData.add("平台无卡");
			} else if (accountLog.getRechargeType() == 2) {
				rowData.add("安卓app");
			} else if (accountLog.getRechargeType() == 3) {
				rowData.add("IOSapp");
			} else if (accountLog.getRechargeType() == 4) {
				rowData.add("微信app");
			} else if (accountLog.getRechargeType() == 5) {
				rowData.add("现金圈存机");
			} else if (accountLog.getRechargeType() == 6) {
				rowData.add("银行圈存机");
			} else if (accountLog.getRechargeType() == 7) {
				rowData.add("补卡");
			}  else {
				rowData.add(" ");
			}

			if (accountLog.getMoneySource() == 0) {
				rowData.add("现金");
			} else if (accountLog.getMoneySource() == 1) {
				rowData.add("微信");
			} else if (accountLog.getMoneySource() == 2) {
				rowData.add("支付宝");
			} else if (accountLog.getMoneySource() == 3) {
				rowData.add("银行卡");
			} else if (accountLog.getMoneySource() == 4) {
				rowData.add("支票");
			} else if (accountLog.getMoneySource() == 5) {
				rowData.add("空充");
			} else {
				rowData.add(" ");
			}

			if (accountLog.getOperator().equals("Android")) {
				rowData.add("安卓app");
			} else if (accountLog.getOperator().equals("IOS")) {
				rowData.add("IOSapp");
			} else if (accountLog.getOperator().indexOf("Operator") >= 0) {
				Operator operator = peizhiguanliMapper.selectOperatorByInnerId(Integer.valueOf(accountLog.getOperator().substring(8)));
				if (operator != null) {
					rowData.add(operator.getOperatorName());
				} else {
					rowData.add(" ");
				}
			} else if (accountLog.getOperator().indexOf("SysTerminal") >= 0) {
				SysTerminal sysTerminal = peizhiguanliMapper.selectSysTerminalByInnerId(Integer.valueOf(accountLog.getOperator().substring(11)));
				if (sysTerminal != null) {
					rowData.add(sysTerminal.getSysTerminalName());
				} else {
					rowData.add(" ");
				}
			} else {
				rowData.add(" ");
			}


			if (accountLog.getRemark() != null) {
				rowData.add(accountLog.getRemark());
			} else {
				rowData.add(" ");
			}

			data.add(rowData);
		}
		List<Object> rowData = new ArrayList<Object>();
		rowData.add("总计");
		rowData.add(count + "人");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(sum / 100.0);
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		rowData.add(" ");
		/*rowData.add(" ");*/
		rowData.add(" ");
		data.add(rowData);
		String relpath = request.getSession().getServletContext().getRealPath("excel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
		String name = "/AccountLog" + sdf2.format(new Date()) + ".xlsx";
		OutputStream out = new FileOutputStream(relpath + name);
		ExportExcelUtils eeu = new ExportExcelUtils();
		XSSFWorkbook workbook = new XSSFWorkbook();
		String header0 = "充减值记录表";
		Operator operator = (Operator) request.getSession().getAttribute("operatorSession");
		String header1 = "制表人：" + operator.getOperatorId() + "    制表时间：" + sdf.format(new Date());
		List<String> headerList = new ArrayList<String>();
		headerList.add(header0);
		headerList.add(header1);
		headerList.add("查询开始时间：" + StartTime + "    查询结束时间：" + EndTime);
		List<String> headers0 = new ArrayList<String>();
		headers0.add("人员编号");
		headers0.add("人员姓名");
		headers0.add("公司");
		headers0.add("账户");
		headers0.add("金额");
		headers0.add("充/减值");
		headers0.add("充值时间");
		headers0.add("充值方式");
		headers0.add("资金来源");
		headers0.add("操作人");
		/*headers0.add("收款账户");*/
		headers0.add("备注");
		List<Integer> length0 = new ArrayList<Integer>();
		length0.add(15);
		length0.add(15);
		length0.add(20);
		length0.add(20);
		length0.add(15);
		length0.add(7);
		length0.add(7);
		length0.add(20);
		length0.add(15);
		length0.add(15);
		length0.add(15);
		/*length0.add(15);*/
		length0.add(15);
		eeu.selectUseruserExcel(workbook, 0, "充减值记录表", headerList, headers0, length0, data, out);
		workbook.write(out);
		out.close();
		JSONObject jo = new JSONObject();
		jo.put("code", 200);
		jo.put("msg", "导出成功！");
		jo.put("data", "excel/" + name);
		return jo.toString();
	}

	@Override
	public String selectDepUser(HttpServletRequest request, DataGridModel dgm) {
		String DepartmentList = request.getParameter("DepartmentList");
		Map<String, Object> map = new HashMap<String, Object>();
		if (dgm.getSort() == null || dgm.getSort().equals("")) {
			dgm.setSort("UserInnerId");
			dgm.setOrder("asc");
		} else {
			if (dgm.getSort().equals("DepartmentName")) {
				dgm.setSort("DepartmentInnerId");
			} 
		}
		if (dgm.getRows() == 0) {
			dgm.setRows(15);
		}
		map.put("rows", dgm.getRows());
		map.put("order", dgm.getOrder());
		map.put("sort", dgm.getSort());
		map.put("start", dgm.getStart());
		map.put("DepartmentList", DepartmentList);
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<User> UserList = zhanghuyewuMapper.selectUser(map);
		for (User user1 : UserList) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("UserInnerId", user1.getUserInnerId());
			row.put("UserId", user1.getUserId());
			row.put("UserName", user1.getUserName());
			row.put("CompanyInnerId", user1.getCompanyInnerId());
			Company company = renshiyewuMapper.selectCompanyByInnerId(user1.getCompanyInnerId());
			if (company != null) {
				row.put("CompanyName", company.getCompanyName());
			}
			row.put("DepartmentInnerId", user1.getDepartmentInnerId());
			Department department = renshiyewuMapper.selectDepartmentByInnerId(user1.getDepartmentInnerId());
			if (department != null) {
				row.put("DepartmentName", department.getDepartmentName());
			}
		
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("AccountTypeInnerId", 1);
			map1.put("UserInnerId", user1.getUserInnerId());
			Account account = zhanghuyewuMapper.selectAccount(map1);
			if (account != null) {
				row.put("AccountInnerId", account.getAccountInnerId());
				row.put("BeforeMoney", account.getMoney() / 100.0);
			} else {
				row.put("BeforeMoney", "无现金账户");
			}
			map1.put("AccountTypeInnerId", 2);
			account = zhanghuyewuMapper.selectAccount(map1);
			if (account != null) {
				row.put("Subsidy", account.getMoney() / 100.0);
			} else {
				row.put("Subsidy", "无补贴账户");
			}
			map1.put("AccountTypeInnerId", 3);
			account = zhanghuyewuMapper.selectAccount(map1);
			if (account != null) {
				row.put("AccountInnerId1", account.getAccountInnerId());
				row.put("FavorableMoneyBefore", account.getMoney() / 100.0);
			} else {
				row.put("FavorableMoneyBefore", "无次账户");
			}
			Integer Proportion = zhanghuyewuMapper.selectUserTypeDiscount(user1.getUserTypeInnerId());
			if (Proportion != null) {
				if(Proportion == 0){
					row.put("Proportion", 10000);
				}else{
					row.put("Proportion", Proportion);
				}
			} else {
				row.put("Proportion", 10000);
			}
			rows.add(row);
		}
		jo.put("total", zhanghuyewuMapper.selectUserTotal(map));
		jo.put("rows", rows);
		return jo.toString();
	}

}
