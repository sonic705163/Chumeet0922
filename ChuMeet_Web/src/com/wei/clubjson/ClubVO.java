package com.wei.clubjson;



import java.sql.Timestamp;

import oracle.sql.TIMESTAMP;

public class ClubVO {
	private Integer clubId;
	private Integer clubMemId;
	private String clubName;
	private Integer clubTypeId;
	private String clubContent;
	private byte[] clubPhoto;
	private Timestamp clubStartDate;
	private Integer clubStatus;
	private Double clubLong;
	private Double clubLat;
	
	public Integer getClubId() {
		return clubId;
	}
	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}
	public Integer getClubMemId() {
		return clubMemId;
	}
	public void setClubMemId(Integer clubMemId) {
		this.clubMemId = clubMemId;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public Integer getClubTypeId() {
		return clubTypeId;
	}
	public void setClubTypeId(Integer clubTypeId) {
		this.clubTypeId = clubTypeId;
	}
	public String getClubContent() {
		return clubContent;
	}
	public void setClubContent(String clubContent) {
		this.clubContent = clubContent;
	}
	public byte[] getClubPhoto() {
		return clubPhoto;
	}
	public void setClubPhoto(byte[] clubPhoto) {
		this.clubPhoto = clubPhoto;
	}
	public Timestamp getClubStartDate() {
		return clubStartDate;
	}
	public void setClubStartDate(Timestamp clubStartDate) {
		this.clubStartDate = clubStartDate;
	}
	public Integer getClubStatus() {
		return clubStatus;
	}
	public void setClubStatus(Integer clubStatus) {
		this.clubStatus = clubStatus;
	}
	public Double getClubLong() {
		return clubLong;
	}
	public void setClubLong(Double clubLong) {
		this.clubLong = clubLong;
	}
	public Double getClubLat() {
		return clubLat;
	}
	public void setClubLat(Double clubLat) {
		this.clubLat = clubLat;
	}
	


	

	

}
