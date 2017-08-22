package com.yuhf.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yuhf.domain.User;

import com.yuhf.dbutils.ResultSetHandler;

public class UserList implements ResultSetHandler<List<User>> {

	@Override
	public List<User> handler(ResultSet rs) {
		List<User> list = new ArrayList<User>();
		try {
			if(rs.getMetaData().getColumnCount()==4){//ÅÐ¶ÏÁÐÊý
				while (rs.next()) {
					User user = new User(rs.getLong(1), rs.getString(2),rs.getString(3), rs.getString(4));
					list.add(user);
				}
			}else{
				while (rs.next()) {
					User user = new User(rs.getLong(1),rs.getLong(2), rs.getString(3),rs.getString(4), rs.getString(5));
					list.add(user);
				}
			}
			

		} catch (SQLException e) {
			System.out.println("convert error! ---" + e.getMessage());
		}
		return list;
	}
}
