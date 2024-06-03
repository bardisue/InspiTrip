package com.ssafy.trip.model.dto;

public class FollowerDTO {
	private String mid;
	private int ino;
	private String followDate;

	public FollowerDTO() {
	}

	public FollowerDTO(String mid, int ino, String followDate) {
		this.mid = mid;
		this.ino = ino;
		this.followDate = followDate;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getIno() {
		return ino;
	}

	public void setIno(int ino) {
		this.ino = ino;
	}

	public String getFollowDate() {
		return followDate;
	}

	public void setFollowDate(String followDate) {
		this.followDate = followDate;
	}

}
