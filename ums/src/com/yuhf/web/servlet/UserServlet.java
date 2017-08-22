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

import com.yuhf.web.servlet.UserServlet;

import com.alibaba.fastjson.JSON;

import com.yuhf.dao.AreaJdbcDaoImpl;
import com.yuhf.dao.UserJdbcDaoImpl;
import com.yuhf.dao.iface.AreaDao;
import com.yuhf.dao.iface.UserDao;
import com.yuhf.domain.Area;
import com.yuhf.domain.User;


@WebServlet("/user.servlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger log=Logger.getLogger(UserServlet.class);
	private UserDao userDao = new UserJdbcDaoImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chaset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		String param=request.getParameter("param")==null?"":request.getParameter("param");
		String checkName = request.getParameter("checkName")==null?"":request.getParameter("checkName");
		String []row_id = request.getParameterValues("row_id")==null?new String[]{}:request.getParameterValues("row_id");
		//人员管理个人信息新增
		String []params = request.getParameterValues("params")==null?new String[]{}:request.getParameterValues("params");
		String orderParam = request.getParameter("orderParam")==null?"":request.getParameter("orderParam");
		String page = request.getParameter("page")==null?"":request.getParameter("page");
		String likeText = request.getParameter("likeText")==null?"":request.getParameter("likeText");
		//用户登录
		String userName = request.getParameter("userName")==null?"":request.getParameter("userName");
		String passWord = request.getParameter("passWord")==null?"":request.getParameter("passWord");

		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();
		log.debug(param+","+orderParam+","+page+","+likeText);
		//模糊查询
		if("like".equals(param)){
			List<User> list = userDao.LikeUser(likeText);
			List<User> listpage = userDao.pageUser(Long.parseLong(page),likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", listpage);
				map.put("pageCount", list.size());
			}else{
				map.put("result", "error");
				map.put("table", "没有数据！");
			}
		}
		//分页
		if("pageData".equals(param)){
			List<User> list = userDao.pageUser(Long.parseLong(page),likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", list);
			}else{
				map.put("result", "error");
				map.put("result", "没有数据");
			}
		}
		//更新
		if("upDate".equals(param)){
			User user = new User(Integer.parseInt(params[0]),params[1],params[2],params[3]);
			boolean flag = userDao.upDate(user);
			if(flag){
				map.put("result", "ok");
				map.put("message", "更新成功！");
			}else{
				map.put("result", "error");
				map.put("message", "更新失败！");
			}
		}
		//排序
		if("order".equals(param)){
			List<User> list = userDao.orderAllUser(orderParam,Long.parseLong(page),likeText);
			List<User> likeList = userDao.LikeUser(likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", list);
				map.put("pageCount", likeList.size());
			}else{
				map.put("result", "error");
				map.put("table", "没有数据！");
			}
		}
		//添加
		if("addTable".equals(param)){
			User user = new User(0,params[0],params[1],params[2]);
			boolean flag = userDao.addTable(user);
			if(flag){
				map.put("result", "ok");
				map.put("message", "添加成功！");
			}else{
				map.put("result", "error");
				map.put("message", "添加失败！");
			}
		}
		//删除
		if("deleteTable".equals(param)){
			int count = 0;
			for(String i : row_id){
				boolean flag = userDao.daleteTable(i);
				if(flag){
					count++;
				}
			}
			if(count!=0){
				map.put("result", "ok");
				map.put("message", "删除成功！");
			}else{
				map.put("result", "error");
				map.put("message", "删除失败");
			}
		}
		//注册
		if("userCheck".equals(param)){
			boolean flag = userDao.checkName(checkName);
			if(flag==false){
				map.put("result", "repeat");
				map.put("message", "用户名重复！");
			}else{
				map.put("result", "norepeat");
				map.put("message", "用户名可以注册！");
			}
		}
		//登录
		if("login".equals(param)){
			long id = userDao.login(userName, passWord);
			if(id!=0){
				map.put("result", "ok");
				map.put("userId", id);
			}else{
				map.put("result", "error");
				map.put("message", "数据不存在！");
			}
		}
		if("updateTable".equals(param)){
			List<User> list = userDao.LikeUser(likeText);
			List<User> listpage = userDao.pageUser(1,likeText);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("table", listpage);
				map.put("pageCount", list.size());
			}else{
				map.put("result", "error");
				map.put("table", "没有数据！");
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
