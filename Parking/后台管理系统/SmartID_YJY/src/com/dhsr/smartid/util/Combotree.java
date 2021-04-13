package com.dhsr.smartid.util;

import java.util.List;

public class Combotree {

	private String id;  
    private String text;  
    private List<Combotree> children;
    private boolean checkbox;
    private String state = "open";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Combotree> getChildren() {
		return children;
	}
	public void setChildren(List<Combotree> children) {
		this.children = children;
	}
	public boolean isCheckbox() {
		return checkbox;
	}
	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
    
    
}
