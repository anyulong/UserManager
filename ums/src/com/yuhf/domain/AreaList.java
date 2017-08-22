package com.yuhf.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yuhf.domain.Area;

import com.yuhf.dbutils.ResultSetHandler;

public class AreaList implements ResultSetHandler<List<Area>>{

	@Override
	public List<Area> handler(ResultSet rs) {
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
	}
}
