package com.yuhf.dao;

import java.util.List;

import com.yuhf.dao.iface.RoleDao;
import com.yuhf.dbutils.JdbcTemplate;
import com.yuhf.domain.Role;
import com.yuhf.domain.RoleList;
import com.yuhf.domain.User;
import com.yuhf.domain.UserList;

public class RoleJdbcDaoImpl implements RoleDao {

	@Override
	public List<Role> getAllRole() {
		String sql = "select * from role";
		List<Role> list = JdbcTemplate.queryForList(sql,new RoleList(), new Object[]{});
		return list;
	}

	@Override
	public List<Role> getUserRoleById(long id) {
		String sql = "select * from role where id in (select roleid from user_role where userid=?)";
		List<Role> list = JdbcTemplate.queryForList(sql, new RoleList(), new Object[]{id});
		return list;
	}

	@Override
	public boolean deleteAllUserRole(long id) {
		String sql = "delete user_role where userid=?";
		int count = JdbcTemplate.delete(sql, new Object[]{id});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean addUserRole(long id,long ids) {
		String sql = "insert into user_role values(user_role_id.nextval,?,?)";
		int count = JdbcTemplate.add(sql, new Object[]{id,ids});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	//角色管理
	@Override
	public boolean deleteRoleById(long rowId) {
		String sql = "delete role where id=?";
		int count = JdbcTemplate.delete(sql, new Object[]{rowId});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean addRole(String name, String remark) {
		String sql = "insert into role values(role_id.nextval,?,?)";
		int count = JdbcTemplate.add(sql, new Object[]{name,remark});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateRole(long id, String name, String remark) {
		String sql = "update role set name=? , remark=? where id=?";
		int count = JdbcTemplate.add(sql, new Object[]{name,remark,id});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	//模糊查询
	@Override
	public List<Role> likeQuery(long page, String likeText) {
		long pageMin = (page-1)*5;
		long pageMax = (page*5);
		String sql = "select * from (select rownum r,id,name,remark from role where name like ? or remark like ?) where r>"+pageMin+" and  r<="+pageMax;
		List<Role> list = JdbcTemplate.queryForList(sql, new RoleList(), new Object[]{'%'+likeText+'%','%'+likeText+'%'});
		return list;
	}

	@Override
	public List<Role> likeAll(String likeText) {
		String sql = "select * from role where name like ? or remark like ?";
		List<Role> list = JdbcTemplate.queryForList(sql, new RoleList(), new Object[]{'%'+likeText+'%','%'+likeText+'%'});
		return list;
	}

	@Override
	public List<Role> orderRole(String orderParam, long pageCount, String likeText) {
		long pageMin = (pageCount-1)*5;
		long pageMax = (pageCount*5);
		String sql = "select * from (select rownum r,id,name,remark from role where name like ? or remark like ?) where r>"+pageMin+" and  r<="+pageMax+"order by id "+orderParam;
		List<Role> list = JdbcTemplate.queryForList(sql, new RoleList(), new Object[]{'%'+likeText+'%','%'+likeText+'%'});
		return list;
	}

	@Override
	public List<Role> queryRoleById(long permissionId) {
		String sql = "select * from role where id in(select roleId from role_permission where permissionId = ?)";
		List<Role> list = JdbcTemplate.queryForList(sql, new RoleList(), new Object[]{permissionId});
		return list;
	}
}
