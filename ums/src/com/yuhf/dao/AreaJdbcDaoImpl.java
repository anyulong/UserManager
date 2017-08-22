package com.yuhf.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yuhf.dbutils.JdbcTemplate;

import com.yuhf.dao.iface.AreaDao;
import com.yuhf.domain.Area;

public class AreaJdbcDaoImpl implements AreaDao {

	@Override
	public List<Area> getProvinces() {
		String sql="select * from area where length(code)=2";
		List<Area> list=JdbcTemplate.queryForList(sql, (rs)->{//????
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
		}, new Object[]{});
		return list;
	}

	@Override
	public List<Area> getCity(String provinceCode) {
		String sql="select * from area where length(code)=4 and substr(code,0,2)=?";
		List<Area> list=JdbcTemplate.queryForList(sql, (rs)->{
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
		}, new Object[]{provinceCode});
		return list;
	}

}
