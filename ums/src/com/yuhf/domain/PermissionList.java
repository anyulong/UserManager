package com.yuhf.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yuhf.domain.Permission;

import com.yuhf.dbutils.ResultSetHandler;

public class PermissionList implements ResultSetHandler<List<Permission>> {

	@Override
	public List<Permission> handler(ResultSet rs) {
		List<Permission> list=new ArrayList<Permission>();
		try {
			if(rs.getMetaData().getColumnCount()==6){
				while(rs.next()){
					Permission p=new Permission(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getLong(6));
					list.add(p);
				}
			}else{
				while(rs.next()){
					Permission p=new Permission(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getLong(5));
					list.add(p);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
