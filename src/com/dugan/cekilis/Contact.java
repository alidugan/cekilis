package com.dugan.cekilis;

public class Contact {
	private String name;
	private String email;
	private int picture;

	public Contact() {
		this.name="";
		this.email="";
		this.picture=0;
	}
	
	public Contact(int flag, String name, String email) {
		this.name = name;
		this.email = email;
		this.picture = flag;
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

	public int getPicture() {
		return picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}

}
