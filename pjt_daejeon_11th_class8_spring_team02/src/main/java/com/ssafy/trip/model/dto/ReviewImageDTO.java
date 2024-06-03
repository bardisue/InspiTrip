package com.ssafy.trip.model.dto;

public class ReviewImageDTO {
	private int no;
	private int rno;
	private String url;

	public ReviewImageDTO() {
	}

	public ReviewImageDTO(int no, int rno, String url) {
		this.no = no;
		this.rno = rno;
		this.url = url;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
