package com.dhsr.smartid.util.dao;

import java.util.Map;

/**
 * 公用方法Dao层，一些公用方法
 * 
 * @author qidong
 *
 */
public interface UtilMapper {

	// 校验数据唯一，统一调用此方法
	public Integer remoteColumn(Map<String, Object> map);

	// 删除数据（软删除）统一调用此方法
	public Integer delete(Map<String, Object> map);

	public Integer selectJiesuanPingzheng(Map<String, Object> map);

	public void updateJiesuanPingzheng(Map<String, Object> map);

	public void insertJiesuanPingzheng(Map<String, Object> map);


}
