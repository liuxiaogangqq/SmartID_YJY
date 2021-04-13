package com.dhsr.smartid.peizhiguanli.domain;

public class PageRight {
	private Integer PageRightId;
	private String PageRightFlag;
	private String PageRightName;
	private Integer UpId;
	private Integer PageRightLevel;

	public PageRight() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PageRight(Integer pageRightId, String pageRightFlag, String pageRightName, Integer upId, Integer pageRightLevel) {
		super();
		PageRightId = pageRightId;
		PageRightFlag = pageRightFlag;
		PageRightName = pageRightName;
		UpId = upId;
		PageRightLevel = pageRightLevel;
	}

	public Integer getPageRightId() {
		return PageRightId;
	}

	public void setPageRightId(Integer pageRightId) {
		PageRightId = pageRightId;
	}

	public String getPageRightFlag() {
		return PageRightFlag;
	}

	public void setPageRightFlag(String pageRightFlag) {
		PageRightFlag = pageRightFlag;
	}

	public String getPageRightName() {
		return PageRightName;
	}

	public void setPageRightName(String pageRightName) {
		PageRightName = pageRightName;
	}

	public Integer getUpId() {
		return UpId;
	}

	public void setUpId(Integer upId) {
		UpId = upId;
	}

	public Integer getPageRightLevel() {
		return PageRightLevel;
	}

	public void setPageRightLevel(Integer pageRightLevel) {
		PageRightLevel = pageRightLevel;
	}

	@Override
	public String toString() {
		return "PageRight [PageRightId=" + PageRightId + ", PageRightFlag=" + PageRightFlag + ", PageRightName=" + PageRightName + ", UpId=" + UpId + ", PageRightLevel=" + PageRightLevel + "]";
	}

}
