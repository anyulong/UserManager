package com.yuhf.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yuhf.web.servlet.PermissionServlet;
import com.alibaba.fastjson.JSON;
import com.yuhf.dao.AreaJdbcDaoImpl;
import com.yuhf.dao.PermissionJdbcDaoImpl;
import com.yuhf.dao.RoleJdbcDaoImpl;
import com.yuhf.dao.UserJdbcDaoImpl;
import com.yuhf.dao.iface.AreaDao;
import com.yuhf.dao.iface.PermissionDao;
import com.yuhf.dao.iface.RoleDao;
import com.yuhf.dao.iface.UserDao;
import com.yuhf.domain.Permission;
import com.yuhf.domain.Role;

/**
 * Servlet implementation class PermissionServlet
 */
@WebServlet("/permission.servlet")
public class PermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger log=Logger.getLogger(PermissionServlet.class);
	
	private PermissionDao permissionDao = new PermissionJdbcDaoImpl();
    private RoleDao roleDao = new RoleJdbcDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();
		String param = request.getParameter("param")==null?"":request.getParameter("param");
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		String likeText = request.getParameter("likeText")==null?"":request.getParameter("likeText");
		String []rowId = request.getParameterValues("row_id")==null?new String[]{}:request.getParameterValues("row_id");
		String []params = request.getParameterValues("params")==null?new String[]{}:request.getParameterValues("params");
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String []ids = request.getParameterValues("ids")==null?new String[]{}:request.getParameterValues("ids");
		log.debug(param+","+userId);
		if("getRole".equals(param)){
			List<Role> list = roleDao.queryRoleById(Long.parseLong(id));
			List<Role> allList=roleDao.getAllRole();
			for(Role role:allList){
				if(list.contains(role)){
					role.setChecked(true);
				}
			}
			map.put("result", "ok");
			map.put("userId", id);
			map.put("roles", allList);
		}
		//添加
		if("addTable".equals(param)){
			if(permissionDao.addPermission(params)){
				map.put("result", "ok");
				map.put("message", "添加成功！");
			}else{
				map.put("result", "ok");
				map.put("message", "添加失败！");
			}
		}
		//更新
		if("upDate".equals(param)){
			if(permissionDao.updatePermission(params)){
				map.put("result", "ok");
				map.put("message", "更新成功！");
			}else{
				map.put("result", "ok");
				map.put("message", "更新失败！");
			}
		}
		//删除
		if("deletePermission".equals(param)){
			int count = 0;
			for(String s:rowId){
				if(permissionDao.deletePermission(Long.parseLong(s))){
					count++;
				}
			}
			if(count>0){
				map.put("result", "ok");
				map.put("message", "删除成功！");
			}else{
				map.put("result", "error");
				map.put("message", "删除失败！");
			}
		}
		if("getMenu".equals(param)){
			List<Permission> list = permissionDao.getPermisssionsByUserId(Long.parseLong(userId));
			if(list.size()!=0){
				map.put("result", "ok");
				map.put("list", list);
			}else{
				map.put("result", "error");
				map.put("message", "没有权限！");
			}
		}
		if("updatepermission".equals(param)){
			List<Permission> list = permissionDao.likeQueryAllPermission(likeText);
			if(list.size()!=0){
				map.put("result", "ok");
				map.put("table", list);
				map.put("pageCount", list.size());
			}else{
				map.put("result", "error");
				map.put("message", "没有权限！");
			}
		}
		if("updateRole".equals(param)){
			List<Permission> list = permissionDao.getPermisssionsByUserId(Long.parseLong(id));
			boolean flag1 = false;
			boolean flag2 = false;
			int count = 0;
			if(list.size()==0){
				for(String s:ids){
					flag2 = permissionDao.addRolePermission(Long.parseLong(id), Long.parseLong(s));
					if(flag2){
						count++;
					}
				}
			}else{
				flag1 = permissionDao.deleteRolePermission(Long.parseLong(id));
				if(flag1){
					for(String s:ids){
						flag2 = permissionDao.addRolePermission(Long.parseLong(id), Long.parseLong(s));
						if(flag2){
							count++;
						}
					}
				}
			}
			if(count>0){
				map.put("result", "ok");
			}else{
				map.put("result", "error");
			}
		}
		String json=JSON.toJSONString(map);
		log.debug(json);
		out.println(json);
		out.flush();
		out.close();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
