package com.mas.recomm.model;

public class RecommendedMovieItem {
	private int id;
	private String title;
	private String genres;
	private float score;
	public RecommendedMovieItem() {
		this(-1, null, null, 0);
	}
	public RecommendedMovieItem(int id, String title, String genre, float score) {
		super();
		this.id = id;
		this.title = title;
		this.genres = genre;
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
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}	
}
