package com.yuhf.dao.iface;

import java.util.List;

import com.yuhf.domain.Permission;

public interface PermissionDao {
	public List<Permission> getPermisssionsByUserId(long userId);
	public List<Permission> likeQueryAllPermission(String likeText);
	public boolean deletePermission(long id);
	public boolean updatePermission(String[]params);
	public boolean addPermission(String []params);
	public boolean addRolePermission(long id,long ids);
	public boolean deleteRolePermission(long id);
}
