package com.yuhf.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class JdbcTemplate {

	private static Logger log=Logger.getLogger(JdbcTemplate.class);
	
	public static <T> T queryForList(String sql,ResultSetHandler<T> rsh,Object...param){
		Connection connection=DBConnection.getConnection();
		T t=null;
		try {
			PreparedStatement psmt=connection.prepareStatement(sql);
			if(param!=null||param.length!=0){
				for(int i=0,len=param.length;i<len;i++){
					psmt.setObject(i+1, param[i]);
				}
			}
			ResultSet rs=psmt.executeQuery();
			t=rsh.handler(rs);
		} catch (SQLException e) {
			log.error("queryForList error! ---"+e.getMessage());
		}
		return t;
	}
	
	public static int queryForCount(String sql,Object...param){
		int rowNumber=0;
		Connection connection=DBConnection.getConnection();
		try {
			PreparedStatement psmt=connection.prepareStatement(sql);
			if(param!=null||param.length!=0){
				for(int i=0,len=param.length;i<len;i++){
					psmt.setObject(i+1, param[i]);
				}
			}
			ResultSet rs=psmt.executeQuery();
			if(rs.next()){
				rowNumber=rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("sql command error! ---"+e.getMessage());
		}
		return rowNumber;
	}

	public static int delete(String sql,Object...param){
		int count = 0;
		Connection connection=DBConnection.getConnection();
		try {
			PreparedStatement psmt=connection.prepareStatement(sql);
			if(param!=null||param.length!=0){
				for(int i=0,len=param.length;i<len;i++){
					psmt.setObject(i+1, param[i]);
				}
			}
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			log.error("deleteTable error! ---"+e.getMessage());
		}
		return count;
	}
	public static int add(String sql,Object...param){
		int count = 0;
		Connection connection=DBConnection.getConnection();
		try {
			PreparedStatement psmt=connection.prepareStatement(sql);
			if(param!=null||param.length!=0){
				for(int i=0,len=param.length;i<len;i++){
					psmt.setObject(i+1, param[i]);
				}
			}
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			log.error("addTable error! ---"+e.getMessage());
		}		
		return count;
	}
}
