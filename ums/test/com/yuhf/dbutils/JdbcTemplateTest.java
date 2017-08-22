package com.yuhf.dbutils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sun.security.acl.PermissionImpl;

import com.yuhf.dao.AreaJdbcDaoImpl;
import com.yuhf.dao.PermissionJdbcDaoImpl;
import com.yuhf.dao.RoleJdbcDaoImpl;
import com.yuhf.dao.UserJdbcDaoImpl;
import com.yuhf.dao.iface.AreaDao;
import com.yuhf.dao.iface.PermissionDao;
import com.yuhf.dao.iface.RoleDao;
import com.yuhf.dao.iface.UserDao;
import com.yuhf.domain.Area;
import com.yuhf.domain.AreaList;
import com.yuhf.domain.Permission;
import com.yuhf.domain.Role;
import com.yuhf.domain.User;

public class JdbcTemplateTest {

	private AreaDao area = new AreaJdbcDaoImpl();
	private UserDao user = new UserJdbcDaoImpl();
	private RoleDao role = new RoleJdbcDaoImpl();
	private PermissionDao per = new PermissionJdbcDaoImpl();

	public void queryForList0(){
		String sql="select * from area where length(code)=4 and substr(code,0,2)=?";
		List<Area> list=JdbcTemplate.queryForList(sql, (rs)->{
			List<Area> list0=new ArrayList<Area>();
			try {
				while(rs.next()){
					Area area=new Area(rs.getLong(1),rs.getString(2),rs.getString(3));
					list0.add(area);
				}
			} catch (SQLException e) {
				System.out.println("convert error! ---"+e.getMessage());
			}
			return list0;
		}, new Object[]{"37"});
		for(Area area:list){
			System.out.println(area.getName());
		}
	}
	public void queryForList1(){
		String sql="select * from area where length(code)=2";
		List<Area> list=JdbcTemplate.queryForList(sql,new AreaList(),new Object[]{});
		for(Area area:list){
			System.out.println(area.getName());
		}
	}
	
	public void checkName(){
		boolean flag =  user.checkName("admin2");
		System.out.println(flag);
	}
	
	public void getAllUser(){
		List<User> list = user.getAllUser();
		for(User user:list){
			System.out.println(user.getId()+","+user.getName()+","+user.getPassword()+","+user.getTelephone());
		}
	}

	public void delete(){
		boolean flag =  user.daleteTable("3");
		if(flag){
			System.out.println("ok");
		}else{
			System.out.println("no");
		}
	}
	public void add(){
		User users = new User(0,"x","x","x");
		boolean flag = user.addTable(users);
		if(flag){
			System.out.println("ok");
		}else{
			System.out.println("no");
		}
	}
/*	public void order(){
		List<User> list = user.orderAllUser("asc");
		for(User i:list){
			System.out.println(i.getName());
		}
	}*/

	public void update(){
		User users = new User(45,"x2","x","x");
		boolean flag = user.upDate(users);
		if(flag){
			System.out.println("ok");
		}else{
			System.out.println("no");
		}
	}

/*	public void page(){
		List<User> list = user.pageUser(2);
		for(User u:list){
			System.out.println(u.getRownum()+","+u.getName());
		}
	}*/

	public void like(){
		List<User> list = user.LikeUser("’≈");
		for(User i:list){
			System.out.println(i.getName()+","+i.getPassword()+","+i.getTelephone());
		}
	}

	public void login(){
		long id = user.login("admin", "admin");
		System.out.println(id);
	}
	//Role≤‚ ‘

	public void role(){
		List<Role> list = role.getAllRole();
		for(Role r:list){
			System.out.println(r.getId()+","+r.getName()+","+r.getRemark());
		}
	}

	public void deleteAllRole(){
		boolean flag = role.deleteAllUserRole(2);
		System.out.println(flag);
	}

	public void addRole(){
		boolean flag = role.addRole("2222","2");
		System.out.println(flag);
	}

	public void updateRole(){
		System.out.println(role.updateRole(12, "333", "333"));
		
	}

	public void likeQuery(){
		List<Role> list = role.likeAll("");
		for(Role l:list){
			System.out.println(l.getName());
		}
	}

	public void role_permission(){
		List<Role> list = role.getAllRole();
		for(Role r:list){
			System.out.println(r.getId()+","+r.getName()+","+r.getRemark());
		}
	}
	@Test
	public void roles(){
		List<Permission>list = per.getPermisssionsByUserId(2);
		System.out.println(list.size());
	}
}
