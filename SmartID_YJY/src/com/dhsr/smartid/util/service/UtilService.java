package com.dhsr.smartid.util.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * 公用方法Service层，一些公用方法
 * 
 * @author qidong
 *
 */
public interface UtilService {

	// 校验数据唯一，统一调用此方法
	public boolean remoteColumn(Integer InnerId, String Column, String ColumnName) throws IOException;

	// 删除数据（软删除）统一调用此方法
	public String delete(Integer InnerId, String TableName, HttpServletRequest request) throws IOException;
}
