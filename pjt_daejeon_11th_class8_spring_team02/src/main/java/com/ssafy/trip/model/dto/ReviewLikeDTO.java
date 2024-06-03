package com.ssafy.trip.model.dto;

public class ReviewLikeDTO {
	private String mid;
	private int rno;

	public ReviewLikeDTO() {
	}

	public ReviewLikeDTO(String mid, int rno) {
		this.mid = mid;
		this.rno = rno;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}
}
