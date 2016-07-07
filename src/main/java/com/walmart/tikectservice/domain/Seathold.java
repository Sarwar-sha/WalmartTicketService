package com.walmart.tikectservice.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Seathold implements Comparable<Seathold> {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "db_id")
	private long dbId;

	@Column(name = "level_id")
	private Integer levelId;

	@Column(name = "level_name")
	private String levelName;

	@Column(name = "price")
	private String price;

	@Column(name = "row_num")
	private Integer rowNum;

	@Column(name = "seats_in_row")
	private Integer seatsInRow;

	@Column(name = "status")
	private String status;

	@Column(name = "modified")
	private Date modified;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "priority")
	private Integer priority;

	public Seathold(Integer constructorLevelId, String constructorlevelname, Integer constructorRows,
			Integer constructorSeatsInRows, Integer constructorPriority, String constructorPhoneNumber,
			String constructorEmailAddress, String status) {

		this.levelId = constructorLevelId;
		this.levelName = constructorlevelname;
		this.phoneNumber = constructorPhoneNumber;
		this.emailAddress = constructorEmailAddress;
		this.rowNum = constructorRows;
		this.seatsInRow = constructorSeatsInRows;
		this.priority = constructorPriority;
		// this.modified = new Date(constructorModified.getTime());
		this.status = status;
	}

	public Seathold() {

	}

	public long getDbId() {
		return this.dbId;
	}

	public Integer getLevelId() {
		return this.levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getlevelName() {
		return this.levelName;
	}

	public void setlevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getprice() {
		return this.price;
	}

	public void setprice(String price) {
		this.price = price;
	}

	public Integer getRowNum() {
		return this.rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModified() {
		return (Date)this.modified.clone();
	}

	public void setModified(Date modified) {
		this.modified = (Date) modified.clone();
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(this.levelId).append(", ").append(this.levelName).append(", ")
				.append(this.price).append(", ").append(this.seatsInRow);
		return builder.toString();
	}

	@Override
	public int compareTo(Seathold arg0) {

		return 0;
	}

}
