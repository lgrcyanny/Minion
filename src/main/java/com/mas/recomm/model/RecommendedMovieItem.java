package com.mas.recomm.model;

public class RecommendedMovieItem {
	private int id;
	private String title;
	private String genre;
	private String score;
	public RecommendedMovieItem() {
		this(-1, null, null, null);
	}
	public RecommendedMovieItem(int id, String title, String genre, String score) {
		super();
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.score = score;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}	
}
