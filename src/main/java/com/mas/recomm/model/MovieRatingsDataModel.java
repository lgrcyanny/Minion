package com.mas.recomm.model;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;

public class MovieRatingsDataModel extends MySQLJDBCDataModel{
	
	public static final String TBL_NAME = "tbl_ratings";
	public static final String TBL_COLUMN_USERID = "userid";
	public static final String TBL_COLUMN_ITEMID = "movieid";
	public static final String TBL_COLUMN_PREFERENCE = "rating";
	public static final String TBL_COLUMN_TIMESTAMP = "timestamp";

	public MovieRatingsDataModel() throws TasteException {
		super(DataSourceFactory.getMySQLDataSource(), 
				TBL_NAME, TBL_COLUMN_USERID, TBL_COLUMN_ITEMID, TBL_COLUMN_PREFERENCE, TBL_COLUMN_TIMESTAMP);
	}

}
