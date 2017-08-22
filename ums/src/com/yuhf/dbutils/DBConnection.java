package com.yuhf.dbutils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBConnection {

	private static final String CLASS_NAME;
	private static final String URL;
	private static final String USER_NAME;
	private static final String PASSWORD;
	
	private static Logger log=Logger.getLogger(DBConnection.class);
	
	static{
		Properties prop=new Properties();
		try {
			prop.load(DBConnection.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			log.error("load property error! ----"+e.getMessage());
		}
		CLASS_NAME=prop.getProperty("jdbc.className");
		URL=prop.getProperty("jdbc.url");
		USER_NAME=prop.getProperty("jdbc.userName");
		PASSWORD=prop.getProperty("jdbc.password");
		log.debug(CLASS_NAME+","+URL+","+USER_NAME+","+PASSWORD);
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			log.error("driver class ont found! ---"+e.getMessage());
		}
	}
	
	public static Connection getConnection(){
		Connection connection=null;
		try {
			connection=DriverManager.getConnection(URL,USER_NAME,PASSWORD);
			log.debug("ok");
		} catch (SQLException e) {
			log.error("connection open error ---"+e.getMessage());
		}
		return connection;
	}
	public static void closeConnection(Connection connection){
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				log.error("connection close error! ---"+e.getMessage());
			}
		}
	}	
}





















