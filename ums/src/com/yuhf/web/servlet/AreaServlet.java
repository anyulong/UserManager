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
import com.yuhf.dao.iface.AreaDao;
import com.yuhf.domain.Area;

/**
 * Servlet implementation class AreaServlet
 */
@WebServlet("/area.servlet")
public class AreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log=Logger.getLogger(UserServlet.class);
	private AreaDao areaDao=new AreaJdbcDaoImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		String param=request.getParameter("param")==null?"":request.getParameter("param");
		String provinceCode=request.getParameter("province")==null?"":request.getParameter("province");
		PrintWriter out=response.getWriter();
		Map<String,Object> map=new HashMap<String,Object>();
		
		if("cityInit".equals(param)){
			List<Area> list=areaDao.getCity(provinceCode);
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("province", list);
			}else{
				map.put("result", "error");
				map.put("message", "没有数据");
			}			
		}
		if("provinceInit".equals(param)){
			List<Area> list=areaDao.getProvinces();
			if(list!=null||list.size()!=0){
				map.put("result", "ok");
				map.put("province", list);
			}else{
				map.put("result", "error");
				map.put("message", "没有数据！");
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
