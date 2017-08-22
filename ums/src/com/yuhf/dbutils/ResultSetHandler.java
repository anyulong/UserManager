package com.yuhf.dbutils;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {

	public T handler(ResultSet rs);
}
