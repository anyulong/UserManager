package com.yuhf.dao.iface;

import java.util.List;

import com.yuhf.domain.User;

public interface UserDao {
	public boolean checkName(String name);
	public List<User> getAllUser();
	public List<User> LikeUser(String param);
	public boolean daleteTable(String id);
	public boolean addTable(User user);
	public List<User> orderAllUser(String orderParam,long pageCount,String param);
	public boolean upDate(User user);
	public List<User> pageUser(long pageCount,String param);
	//登录并获取用户id
	public long login(String name,String pwd);
}
