package com.ssafy.trip.model.dto;

import java.math.BigDecimal;

public class SearchInfoDto {
	private int contentId;
	private int contentTypeId;
	private int sidoCode;
	private int gunguCode;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private int level;

	public SearchInfoDto(int contentId, int contentTypeId, int sidoCode, int gunguCode, BigDecimal latitude,
			BigDecimal longitude, int level) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.sidoCode = sidoCode;
		this.gunguCode = gunguCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.level = level;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
