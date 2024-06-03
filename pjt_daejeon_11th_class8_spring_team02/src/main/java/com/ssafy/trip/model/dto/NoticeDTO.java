package com.ssafy.trip.model.dto;

public class NoticeDTO {
	private int no;
	private String mid;
	private String title;
	private String content;
	private String write_date;
	private int read_count;

	public NoticeDTO() {
	}

	public NoticeDTO(int no, String mid, String title, String content, String write_date, int read_count) {
		this.no = no;
		this.mid = mid;
		this.title = title;
		this.content = content;
		this.write_date = write_date;
		this.read_count = read_count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public int getRead_count() {
		return read_count;
	}

	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}

}
