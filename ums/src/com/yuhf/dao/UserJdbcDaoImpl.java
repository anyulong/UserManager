package com.yuhf.dao;

import java.util.List;

import javax.swing.JButton;

import com.yuhf.dbutils.DBConnection;
import com.yuhf.dbutils.JdbcTemplate;

import com.yuhf.dao.iface.UserDao;
import com.yuhf.domain.User;
import com.yuhf.domain.UserList;

public class UserJdbcDaoImpl implements UserDao {

	@Override
	public boolean checkName(String name) {
		boolean flag = false;
		String sql = "select count(*) from users where name=?";
		int count = JdbcTemplate.queryForCount(sql, new Object[]{name});
		if(count==1){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}

	@Override
	public List<User> getAllUser() {
		String sql = "select * from users";
		List<User> list = JdbcTemplate.queryForList(sql, new UserList(), new Object[]{});
		return list;
	}

	@Override
	public boolean daleteTable(String id) {
		boolean flag = false;
		String sql = "delete users where id=?";
		int count = JdbcTemplate.delete(sql,new Object[]{id});
		if(count==0){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean addTable(User user) {
		boolean flag = false;
		String sql = "insert into users values(users_id.nextval,?,?,?)";
		int count = JdbcTemplate.add(sql, new Object[]{user.getName(),user.getPassword(),user.getTelephone()});
		if(count==1){
			flag = true;
		}
		return flag;
	}
	//模糊查询分页排序
	@Override
	public List<User> orderAllUser(String orderParam,long pageCount,String param) {
		long pageMin = (pageCount-1)*5;
		long pageMax = (pageCount*5);
		String sql = "select * from (select rownum r,id,name,password,telephone from users where name like ? or password like ? or telephone like ?) where r>"+pageMin+" and  r<="+pageMax+"order by name "+orderParam;
		List<User> list = JdbcTemplate.queryForList(sql, new UserList(), new Object[]{'%'+param+'%','%'+param+'%','%'+param+'%'});
		return list;
	}

	@Override
	public boolean upDate(User user) {
		String sql = "update users set name=?,password=?,telephone=?where id=?";
		boolean flag = false;
		int count = JdbcTemplate.add(sql, new Object[]{user.getName(),user.getPassword(),user.getTelephone(),user.getId()});	
		if(count==1){
			flag = true;
		}
		return flag;
	}
	//模糊分页查询
	@Override
	public List<User> pageUser(long pageCount,String param) {
		long pageMin = (pageCount-1)*5;
		long pageMax = (pageCount*5);
		String sql = "select * from (select rownum r,id,name,password,telephone from users where name like ? or password like ? or telephone like ?) where r>"+pageMin+" and  r<="+pageMax;
		List<User> list = JdbcTemplate.queryForList(sql, new UserList(), new Object[]{'%'+param+'%','%'+param+'%','%'+param+'%'});
		return list;
	}
	//模糊查询
	@Override
	public List<User> LikeUser(String param) {
		String sql = "select * from users where name like ? or password like ? or telephone like ?";
		List<User> list = JdbcTemplate.queryForList(sql, new UserList(), new Object[]{'%'+param+'%','%'+param+'%','%'+param+'%'});
		return list;
	}
	//登录
	@Override
	public long login(String name, String pwd) {
		String sql = "select id from users where name=? and password = ?";
		long id = JdbcTemplate.queryForCount(sql, new Object[]{name,pwd});
		return id;
	}

}
