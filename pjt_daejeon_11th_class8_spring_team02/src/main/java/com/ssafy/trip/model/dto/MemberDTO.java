package com.ssafy.trip.model.dto;

public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String gender;
	private String tel;
	private String img;

	public MemberDTO() {
	}

	public MemberDTO(String id, String pw, String name, String email, String gender, String tel, String img) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.tel = tel;
		this.img = img;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", gender=" + gender
				+ ", tel=" + tel + ", img=" + img + "]";
	}
}
