package com.mas.recomm.model;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;

public class DataSourceFactory {
	public static final String DB_DRIVER_CLASS_NAME="com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/movielens";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root";
	
	public static ConnectionPoolDataSource getMySQLDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DataSourceFactory.DB_DRIVER_CLASS_NAME);
        dataSource.setUrl(DataSourceFactory.DB_URL);
        dataSource.setUsername(DataSourceFactory.DB_USER);
        dataSource.setPassword(DataSourceFactory.DB_PASSWORD);
        ConnectionPoolDataSource poolDataSource = new ConnectionPoolDataSource(dataSource);
        return poolDataSource;
	}

}
