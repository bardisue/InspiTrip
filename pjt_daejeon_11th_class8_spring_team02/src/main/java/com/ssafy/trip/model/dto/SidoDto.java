package com.ssafy.trip.model.dto;

public class SidoDto {
	private int sidoCode;
	private String sidoName;
	public SidoDto(int sidoCode, String sidoName) {
		super();
		this.sidoCode = sidoCode;
		this.sidoName = sidoName;
	}
	public SidoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	@Override
	public String toString() {
		return "SidoDto [sidoCode=" + sidoCode + ", sidoName=" + sidoName + "]";
	}
	
	
}
