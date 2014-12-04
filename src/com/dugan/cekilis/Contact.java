package com.dugan.cekilis;

public class Contact {
	private String name;
	private String email;
	private int picture;

	public Contact(int flag, String name, String email) {
		this.name = name;
		this.email = email;
		this.picture = flag;
	}

	public String getRank() {
		return name;
	}

	public void setRank(String rank) {
		this.name = rank;
	}

	public String getCountry() {
		return email;
	}

	public void setCountry(String country) {
		this.email = country;
	}

	public int getFlag() {
		return picture;
	}

	public void setFlag(int flag) {
		this.picture = flag;
	}

}
