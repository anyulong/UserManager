package com.yuhf.domain;

public class User {
	private long rownum;
	private long id;
	private String name;
	private String password;
	private String telephone;
	
	public User() {
		super();
	}
	
	public User(long rownum, long id, String name, String password, String telephone) {
		super();
		this.rownum = rownum;
		this.id = id;
		this.name = name;
		this.password = password;
		this.telephone = telephone;
	}
	
	public User(long id, String name, String password, String telephone) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.telephone = telephone;
	}
	
	public long getRownum() {
		return rownum;
	}

	public void setRownum(long rownum) {
		this.rownum = rownum;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	
	
}
