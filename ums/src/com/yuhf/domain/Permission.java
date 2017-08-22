package com.yuhf.domain;

public class Permission {

	private long id;
	private String userName;
	private String permissionName;
	private String url;
	private String remark;
	private long parentId;
	
	public Permission(){}

	public Permission(long id, String userName, String permissionName, String url, String remark,long parentId) {
		super();
		this.id = id;
		this.userName = userName;
		this.permissionName = permissionName;
		this.url = url;
		this.remark = remark;
		this.parentId=parentId;
	}
	public Permission(long id, String permissionName, String url, String remark,long parentId) {
		super();
		this.id = id;
		this.permissionName = permissionName;
		this.url = url;
		this.remark = remark;
		this.parentId=parentId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
