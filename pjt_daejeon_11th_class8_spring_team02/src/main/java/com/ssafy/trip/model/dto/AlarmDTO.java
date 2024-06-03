package com.ssafy.trip.model.dto;

public class AlarmDTO {
	private int no;
	private int vno;
	private String mid;
	private boolean checked;
	private String createdAt;
	private int ino;
	private int ano;
	private String iName;
	private String img;
	private String aName;


	public AlarmDTO(int no, int vno, String mid, boolean checked, String createdAt, int ino, int ano, String iName,
			String img, String aName) {
		super();
		this.no = no;
		this.vno = vno;
		this.mid = mid;
		this.checked = checked;
		this.createdAt = createdAt;
		this.ino = ino;
		this.ano = ano;
		this.iName = iName;
		this.img = img;
		this.aName = aName;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getVno() {
		return vno;
	}

	public void setVno(int vno) {
		this.vno = vno;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
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


	public String getIName() {
		return iName;
	}

	public void setIName(String iName) {
		this.iName = iName;
	}

	public String getAName() {
		return aName;
	}

	public void setAName(String aName) {
		this.aName = aName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
