package com.yuhf.dao.iface;

import java.util.List;

import com.yuhf.domain.Role;

public interface RoleDao {
	public List<Role> getAllRole();
	public List<Role> getUserRoleById(long id);
	public boolean deleteAllUserRole(long id);
	public boolean addUserRole(long id,long ids);
	//��ɫ����
	public boolean deleteRoleById(long rowId);
	public boolean addRole(String name,String remark);
	public boolean updateRole(long id, String name, String remark);
	public List<Role> likeQuery(long page,String likeText);
	public List<Role> likeAll(String likeText);
	public List<Role> orderRole(String orderParam,long pageCount,String param);
	//����Ȩ������Ӧ�Ľ�ɫ��Ϣ
	public List<Role> queryRoleById(long permissionId);
}
