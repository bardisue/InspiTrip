package com.ssafy.trip.model.dto;

public class MemberReviewDTO {
	private int no;
	private int ano;
	private String mid;
	private String content;
	private String writeDate;
	private int grade;

	public MemberReviewDTO() {
	}

	public MemberReviewDTO(int no, int ano, String mid, String content, String writeDate, int grade) {
		this.no = no;
		this.ano = ano;
		this.mid = mid;
		this.content = content;
		this.writeDate = writeDate;
		this.grade = grade;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "MemberReviewDTO [no=" + no + ", ano=" + ano + ", mid=" + mid + ", content=" + content + ", writeDate="
				+ writeDate + ", grade=" + grade + "]";
	}
}
