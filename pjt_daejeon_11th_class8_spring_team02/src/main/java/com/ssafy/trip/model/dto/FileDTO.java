package com.ssafy.trip.model.dto;

public class FileDTO {
	private int no;
	private int nno;
	private String originalPath;
	private String savedPath;

	public FileDTO() {
	}

	public FileDTO(int no, int nno, String originalPath, String savedPath) {
		this.no = no;
		this.nno = nno;
		this.originalPath = originalPath;
		this.savedPath = savedPath;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getNno() {
		return nno;
	}

	public void setNno(int nno) {
		this.nno = nno;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public String getSavedPath() {
		return savedPath;
	}

	public void setSavedPath(String savedPath) {
		this.savedPath = savedPath;
	}
}
