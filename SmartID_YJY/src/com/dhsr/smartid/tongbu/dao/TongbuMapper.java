package com.dhsr.smartid.tongbu.dao;

import java.util.List;

import com.dhsr.smartid.tongbu.domain.LFCompany;
import com.dhsr.smartid.tongbu.domain.LFDep;
import com.dhsr.smartid.tongbu.domain.LFUser;


public interface TongbuMapper {
	/**
	 * 查询公司信息
	 * @return
	 */
	public List<LFCompany> selectCompany();

	/**
	 * 查询部门信息
	 * @return
	 */
	public List<LFDep> selectDepartment();

	/**
	 * 查询人员信息
	 * @return
	 */
	public List<LFUser> selectUserInfo();
}
