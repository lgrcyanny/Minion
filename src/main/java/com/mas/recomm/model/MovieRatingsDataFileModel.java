package com.mas.recomm.model;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

public class MovieRatingsDataFileModel extends FileDataModel{
	public static final String FILE_PATH = "src/main/resources/ratings.dat";
	
	private static MovieRatingsDataFileModel dataModel = null;
	
	private MovieRatingsDataFileModel() throws IOException {
		super(new File(FILE_PATH));
	}
	
	public static MovieRatingsDataFileModel instance () {
		if (dataModel == null) {
			try {
				dataModel = new MovieRatingsDataFileModel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataModel;
	}

}
