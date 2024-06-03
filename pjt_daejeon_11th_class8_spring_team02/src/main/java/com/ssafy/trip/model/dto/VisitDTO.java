package com.ssafy.trip.model.dto;

public class VisitDTO {
	private int no;
	private String mid;
	private int ino;
	private int ano;
	private String url;
	private String iName;
	private String iImg;

	public VisitDTO() {
	}

	public VisitDTO(int no, String mid, int ino, int ano, String url, String iName, String iImg) {
		this.no = no;
		this.mid = mid;
		this.ino = ino;
		this.ano = ano;
		this.url = url;
		this.iName = iName;
		this.iImg = iImg;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIName() {
		return iName;
	}

	public void setIName(String iName) {
		this.iName = iName;
	}

	public String getIImg() {
		return iImg;
	}

	public void setIImg(String iImg) {
		this.iImg = iImg;
	}
	
	
}
