package com.ssafy.trip.model.dto;

public class InfluencerDTO {
	private int no;
	private String name;
	private String youtubeId;
	private String img;
	private String content;
	private String url;

	public InfluencerDTO() {
	}

	public InfluencerDTO(int no, String name, String youtubeId, String img, String content, String url) {
		this.no = no;
		this.name = name;
		this.youtubeId = youtubeId;
		this.img = img;
		this.content = content;
		this.url = url;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
