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

import com.alibaba.fastjson.JSON;
import com.yuhf.dao.RoleJdbcDaoImpl;
import com.yuhf.dao.iface.RoleDao;
import com.yuhf.domain.Role;

import jdk.nashorn.internal.ir.RuntimeNode.Request;


/**
 * Servlet implementation class RoleServlet
 */
@WebServlet("/role.servlet")
public class RoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(RoleServlet.class);
	private RoleDao roleDao = new RoleJdbcDaoImpl();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String param = request.getParameter("param")==null?"":request.getParameter("param");
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String []ids = request.getParameterValues("ids")==null?new String[]{}:request.getParameterValues("ids");
		String []rowId = request.getParameterValues("row_id")==null?new String[]{}:request.getParameterValues("row_id");
		String []params = request.getParameterValues("params")==null?new String[]{}:request.getParameterValues("params");
		String likeText = request.getParameter("likeText")==null?"":request.getParameter("likeText");
		String page = request.getParameter("page")==null?"":request.getParameter("page");
		String orderParam = request.getParameter("orderParam")==null?"":request.getParameter("orderParam");

		
		PrintWriter out = response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();
		log.debug(param+","+id);
		//排序
		if("order".equals(param)){
			List<Role> list = roleDao.orderRole(orderParam, Long.parseLong(page), likeText);
			List<Role> allList = roleDao.likeAll(likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", list);
				map.put("pageCount", allList.size());
			}else{
				map.put("result", "error");
				map.put("table", "没有数据！");
			}
		}
		//模糊查询
		if("like".equals(param)){
			List<Role> listpage = roleDao.likeQuery(Long.parseLong(page), likeText);
			List<Role> list = roleDao.likeAll(likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", listpage);
				map.put("pageCount", list.size());
			}else{
				map.put("result", "error");
				map.put("table", "没有数据！");
			}
		}
		//修改
		if("upRole".equals(param)){
			if(roleDao.updateRole(Long.parseLong(id), params[0],params[1])){
				map.put("result", "ok");
				map.put("message", "更新成功！");
			}else{
				map.put("result", "error");
				map.put("message", "更新失败！");
			}
		}
		//增加
		if("addRoleInform".equals(param)){
			if(roleDao.addRole(params[0], params[1])){
				map.put("result", "ok");
				map.put("message", "添加成功！");
			}else{
				map.put("result", "error");
				map.put("message", "添加失败！");
			}
		}
		//删除
		if("deleteRole".equals(param)){
			int count = 0;
			for(String s:rowId){
				if(roleDao.deleteRoleById(Long.parseLong(s))){
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
		//更新角色信息界面
		if("updateRoleInform".equals(param)){
			List<Role> list=roleDao.getAllRole();
			List<Role> listpage = roleDao.likeQuery(1, likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", listpage);
				map.put("pageCount", list.size());
			}else{
				map.put("result", "error");
				map.put("table", "没有数据！");
			}
		}
		if("getRole".equals(param)){
			List<Role> allList=roleDao.getAllRole();
			List<Role> list = roleDao.getUserRoleById(Long.parseLong(id));
			for(Role role:allList){
				if(list.contains(role)){
					role.setChecked(true);
				}
			}
			map.put("result", "ok");
			map.put("userId", id);
			map.put("roles", allList);
		}
		/*if("updateRole".equals(param)){
			List<Role> list = roleDao.getUserRoleById(Long.parseLong(id));
			boolean flag1 = false;
			boolean flag2 = false;
			int count = 0;
			if(list.size()==0){
				for(String s:ids){
					flag2 = roleDao.addUserRole(Long.parseLong(id), Long.parseLong(s));
					if(flag2){
						count++;
					}
				}
			}else{
				flag1 = roleDao.deleteAllUserRole(Long.parseLong(id));
				if(flag1){
					for(String s:ids){
						flag2 = roleDao.addUserRole(Long.parseLong(id), Long.parseLong(s));
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
		*/
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
