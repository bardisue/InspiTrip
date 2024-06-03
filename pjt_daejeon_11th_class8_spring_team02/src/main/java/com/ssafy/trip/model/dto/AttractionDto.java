package com.ssafy.trip.model.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "AttractionDto (관광지정보)", description = "관광지의 정보를 가지는 Domain Class")
public class AttractionDto {
	
	@Schema(description = "관광지 아이디")
	private int contentId;
	private int contentTypeId;
	private String title;
	private String address;
	private String image;
	private int sidoCode;
	private int gunguCode;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String overview;
	
	public AttractionDto(int contentId, int contentTypeId, String title, String address, String image, int sidoCode,
			int gunguCode, BigDecimal latitude, BigDecimal longitude, String overview) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.address = address;
		this.image = image;
		this.sidoCode = sidoCode;
		this.gunguCode = gunguCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.overview = overview;
	}
	public AttractionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}
	public int getGunguCode() {
		return gunguCode;
	}
	public void setGunguCode(int gunguCode) {
		this.gunguCode = gunguCode;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	@Override
	public String toString() {
		return "AttractionDto [contentId=" + contentId + ", contentTypeId=" + contentTypeId + ", title=" + title
				+ ", address=" + address + ", image=" + image + ", sidoCode=" + sidoCode + ", gunguCode=" + gunguCode
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", overview=" + overview + "]";
	}
}
