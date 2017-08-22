package com.yuhf.dbutils;

import java.util.List;

import org.junit.Test;

import com.yuhf.dao.PermissionJdbcDaoImpl;
import com.yuhf.dao.iface.PermissionDao;
import com.yuhf.domain.Permission;
import com.yuhf.domain.PermissionList;

public class PermissionTest {
	private PermissionDao permission = new PermissionJdbcDaoImpl();

	public void likeAllPermission(){
		List<Permission> list = permission.likeQueryAllPermission("");
		for(Permission p:list){
			System.out.println(p.getPermissionName());
		}
	}
	@Test
	public void test(){
		String sql = "select * from (select distinct u.id,u.name,p.name as permissionName from users u inner join user_role ur on u.id=ur.userid inner join role_permission rp on ur.roleid=rp.roleid inner join permission p on rp.permissionid=p.id where u.id=2) sss order by DECODE(sss.permissionname,'用户权限管理','1')";
		//String sql = "select distinct u.id,u.name,p.name as permissionName from users u inner join user_role ur on u.id=ur.userid inner join role_permission rp on ur.roleid=rp.roleid inner join permission p on rp.permissionid=p.id where u.id=2";
		List<Permission> list=JdbcTemplate.queryForList(sql, new PermissionList(), new Object[]{});
		System.out.println(list.size());
	}
}
