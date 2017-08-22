package com.yuhf.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yuhf.dbutils.ResultSetHandler;

public class RoleList implements ResultSetHandler<List<Role>> {

	@Override
	public List<Role> handler(ResultSet rs) {
		List<Role> list = new ArrayList<Role>();
		try {
			if(rs.getMetaData().getColumnCount()==3){
				while(rs.next()){
					Role role = new Role(rs.getLong(1),rs.getString(2),rs.getString(3));
					list.add(role);
				}
			}else{
				while(rs.next()){
					Role role = new Role(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4));
					list.add(role);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
