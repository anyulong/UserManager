package com.yuhf.dao;

import java.util.List;

import oracle.security.o3logon.O3LoginClientHelper;

import com.yuhf.dbutils.JdbcTemplate;
import com.yuhf.dao.iface.PermissionDao;
import com.yuhf.domain.Permission;
import com.yuhf.domain.PermissionList;

public class PermissionJdbcDaoImpl implements PermissionDao {

	@Override
	public List<Permission> getPermisssionsByUserId(long userId) {
		//String sql="select distinct p.id,u.name,p.name as permissionName,p.url,p.remark,p.parentId from users u inner join user_role ur on u.id=ur.userid inner join role_permission rp on ur.roleid=rp.roleid inner join permission p on rp.permissionid=p.id where u.id=? order by permissionname";
		String sql = "select * from (select distinct p.id,u.name,p.name as permissionName,p.url,p.remark,p.parentId  from users u inner join user_role ur on u.id=ur.userid inner join role_permission rp on ur.roleid=rp.roleid inner join permission p on rp.permissionid=p.id where u.id=?) sss order by DECODE(sss.permissionname,'用户权限管理','1')";
		List<Permission> list=JdbcTemplate.queryForList(sql, new PermissionList(), new Object[]{userId});
		return list;
	}

	@Override
	public List<Permission> likeQueryAllPermission(String likeText) {
		String sql = "select * from permission where name like ? or url like ? or remark like ?";
		List<Permission> list = JdbcTemplate.queryForList(sql, new PermissionList(), new Object[]{'%'+likeText+'%','%'+likeText+'%','%'+likeText+'%'});
		return list;
	}

	@Override
	public boolean deletePermission(long id) {
		String sql = "delete permission where id = ?";
		int count = JdbcTemplate.delete(sql, id);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updatePermission(String[]params) {
		String sql = "update permission set name = ? , url = ? , remark = ? , parentId = ? where id = ?";
		int count = JdbcTemplate.queryForCount(sql, new Object[]{params[1],params[2],params[3],Long.parseLong(params[4]),Long.parseLong(params[0])});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean addPermission(String []params) {
		String sql = "insert into permission values(permission_id.nextval,?,?,?,?)";
		int count = JdbcTemplate.add(sql, new Object[]{params[0],params[1],params[2],Long.parseLong(params[3])});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean addRolePermission(long id, long ids) {
		String sql = "insert into role_permission values(role_permission_id.nextval,?,?)";
		int count = JdbcTemplate.add(sql, new Object[]{ids,id});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteRolePermission(long id) {
		String sql = "delete role_permission where permissionid = ?";
		int count = JdbcTemplate.delete(sql, new Object[]{id});
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
}
