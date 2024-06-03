package com.ssafy.trip.model.dto;

public class GunguDto {
	private int gunguCode;
	private String gunguName;
	private int sidoCode;

	public int getGunguCode() {
		return gunguCode;
	}

	public void setGunguCode(int gunguCode) {
		this.gunguCode = gunguCode;
	}

	public String getGunguName() {
		return gunguName;
	}

	public void setGunguName(String gunguName) {
		this.gunguName = gunguName;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public GunguDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GunguDto(int gunguCode, String gunguName, int sidoCode) {
		super();
		this.gunguCode = gunguCode;
		this.gunguName = gunguName;
		this.sidoCode = sidoCode;
	}

	@Override
	public String toString() {
		return "GunguDto [gunguCode=" + gunguCode + ", gunguName=" + gunguName + ", sidoCode=" + sidoCode + "]";
	}

}
