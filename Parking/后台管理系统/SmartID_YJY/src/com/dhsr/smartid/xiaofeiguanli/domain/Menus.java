package com.dhsr.smartid.xiaofeiguanli.domain;

public class Menus {
    private Integer FMenuInnerId;
    /**
     * 菜品编号
     */
	private String FMenuId;
	/**
	 * 菜品名称
	 */
	private String FMenuName;
	/**
	 * 菜类主键
	 */
	private Integer FMenuTypeInnerId;
	/**
	 * 餐次主键
	 */
	private Integer FConTypeInnerId;
	/**
	 * 菜品价格
	 */
	private Integer FMenuPrice;
	/**
	 * 菜类名称
	 */
	private String FMenuTypeName;
	/**
	 * 商户主键
	 */
	private Integer FMerchantInnerId;
	/**
	 * 菜类的启用标识
	 */
	private Integer FEnableFlag;
	/**
	 * 菜类的删除标识
	 */
	private Integer DelFlag;
	/**
	 * 菜品的启用标识
	 */
	private Integer EnableFlag;
	/**
	 * 菜品的删除标识
	 */
	private Integer Del;
	/**
	 * 商户名称
	 */
	private String MerchantName;
	
	public Menus() {
		super();
	}

	public Menus(Integer fMenuInnerId, String fMenuId, String fMenuName, Integer fMenuTypeInnerId,
			Integer fConTypeInnerId, Integer fMenuPrice, String fMenuTypeName, Integer fMerchantInnerId,
			Integer fEnableFlag, Integer delFlag, Integer enableFlag, Integer del, String merchantName) {
		super();
		FMenuInnerId = fMenuInnerId;
		FMenuId = fMenuId;
		FMenuName = fMenuName;
		FMenuTypeInnerId = fMenuTypeInnerId;
		FConTypeInnerId = fConTypeInnerId;
		FMenuPrice = fMenuPrice;
		FMenuTypeName = fMenuTypeName;
		FMerchantInnerId = fMerchantInnerId;
		FEnableFlag = fEnableFlag;
		DelFlag = delFlag;
		EnableFlag = enableFlag;
		Del = del;
		MerchantName = merchantName;
	}

	public Integer getFMenuInnerId() {
		return FMenuInnerId;
	}

	public void setFMenuInnerId(Integer fMenuInnerId) {
		FMenuInnerId = fMenuInnerId;
	}

	public String getFMenuId() {
		return FMenuId;
	}

	public void setFMenuId(String fMenuId) {
		FMenuId = fMenuId;
	}

	public String getFMenuName() {
		return FMenuName;
	}

	public void setFMenuName(String fMenuName) {
		FMenuName = fMenuName;
	}

	public Integer getFMenuTypeInnerId() {
		return FMenuTypeInnerId;
	}

	public void setFMenuTypeInnerId(Integer fMenuTypeInnerId) {
		FMenuTypeInnerId = fMenuTypeInnerId;
	}

	public Integer getFConTypeInnerId() {
		return FConTypeInnerId;
	}

	public void setFConTypeInnerId(Integer fConTypeInnerId) {
		FConTypeInnerId = fConTypeInnerId;
	}

	public Integer getFMenuPrice() {
		return FMenuPrice;
	}

	public void setFMenuPrice(Integer fMenuPrice) {
		FMenuPrice = fMenuPrice;
	}

	public String getFMenuTypeName() {
		return FMenuTypeName;
	}

	public void setFMenuTypeName(String fMenuTypeName) {
		FMenuTypeName = fMenuTypeName;
	}

	public Integer getFMerchantInnerId() {
		return FMerchantInnerId;
	}

	public void setFMerchantInnerId(Integer fMerchantInnerId) {
		FMerchantInnerId = fMerchantInnerId;
	}

	public Integer getFEnableFlag() {
		return FEnableFlag;
	}

	public void setFEnableFlag(Integer fEnableFlag) {
		FEnableFlag = fEnableFlag;
	}

	public Integer getDelFlag() {
		return DelFlag;
	}

	public void setDelFlag(Integer delFlag) {
		DelFlag = delFlag;
	}

	public Integer getEnableFlag() {
		return EnableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		EnableFlag = enableFlag;
	}

	public Integer getDel() {
		return Del;
	}

	public void setDel(Integer del) {
		Del = del;
	}

	public String getMerchantName() {
		return MerchantName;
	}

	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
	}

	@Override
	public String toString() {
		return "Menus [FMenuInnerId=" + FMenuInnerId + ", FMenuId=" + FMenuId + ", FMenuName=" + FMenuName
				+ ", FMenuTypeInnerId=" + FMenuTypeInnerId + ", FConTypeInnerId=" + FConTypeInnerId + ", FMenuPrice="
				+ FMenuPrice + ", FMenuTypeName=" + FMenuTypeName + ", FMerchantInnerId=" + FMerchantInnerId
				+ ", FEnableFlag=" + FEnableFlag + ", DelFlag=" + DelFlag + ", EnableFlag=" + EnableFlag + ", Del="
				+ Del + ", MerchantName=" + MerchantName + "]";
	}
	
}
