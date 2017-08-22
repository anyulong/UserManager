package com.yuhf.dao.iface;

import java.util.List;

import com.yuhf.domain.Role;

public interface RoleDao {
	public List<Role> getAllRole();
	public List<Role> getUserRoleById(long id);
	public boolean deleteAllUserRole(long id);
	public boolean addUserRole(long id,long ids);
	//角色管理
	public boolean deleteRoleById(long rowId);
	public boolean addRole(String name,String remark);
	public boolean updateRole(long id, String name, String remark);
	public List<Role> likeQuery(long page,String likeText);
	public List<Role> likeAll(String likeText);
	public List<Role> orderRole(String orderParam,long pageCount,String param);
	//查找权限所对应的角色信息
	public List<Role> queryRoleById(long permissionId);
}
