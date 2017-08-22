package com.yuhf.dao.iface;

import java.util.List;

import com.yuhf.domain.Area;

public interface AreaDao {
	public List<Area> getProvinces();
	public List<Area> getCity(String provinceCode);
}
