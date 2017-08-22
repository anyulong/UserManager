package com.yuhf.domain;

public class Role {
	private long rowId;
	private long id;
	private String name;
	private String remark;
	private boolean checked = false;
	
	public Role() {
		super();
	}
	
	public Role(long id, String name, String remark) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
	}
	public Role(long rowId, long id, String name, String remark) {
		super();
		this.rowId = rowId;
		this.id = id;
		this.name = name;
		this.remark = remark;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Role){
			Role role = (Role) o;
			if(id==role.getId()&&name.equals(role.getName())&&remark.equals(role.getRemark())){
				return true;	
			}
		}
		return false;
	}
	

	public long getRowId() {
		return rowId;
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
