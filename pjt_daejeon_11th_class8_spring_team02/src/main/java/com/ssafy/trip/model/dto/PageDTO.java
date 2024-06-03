package com.ssafy.trip.model.dto;

import java.util.List;

public class PageDTO {
	private List<NoticeDTO> noticeList;
	private int curPage;
	private int startPage;
	private int endPage;
	private int totalPage;
	public PageDTO(List<NoticeDTO> noticeList, int curPage, int startPage, int endPage, int totalPage) {
		super();
		this.noticeList = noticeList;
		this.curPage = curPage;
		this.startPage = startPage;
		this.endPage = endPage;
		this.totalPage = totalPage;
	}
	public PageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<NoticeDTO> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(List<NoticeDTO> noticeList) {
		this.noticeList = noticeList;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
