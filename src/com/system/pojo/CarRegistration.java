package com.system.pojo; //plain old java object

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;		//Formatting date
import java.time.LocalDateTime;
public class CarRegistration {

	/*-carBrand:String
	-carModel:String
	-carNumber:String
	-carDescription:String
	-registrationDetails:String        //name,startDate,endDate,address
	-carRental:double
	-basePrice:double
*/
	String carBrand;
	String carModel;
	String carNumber;
	String carDescription;
	String regNumber;
	double basePrice;
	double carRental;		//status
	String regID;
	LocalDateTime startDate;
	LocalDateTime endDate;
	String userID;
	
	
	//source>getter,setter
	public String getRegID() {
		return regID;
	}
	public void setRegID(String regID) {
		this.regID = regID;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {		
		this.carModel = carModel;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarDescription() {
		return carDescription;
	}
	public void setCarDescription(String carDescription) {
		this.carDescription = carDescription;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getCarRental() {
		return carRental;
	}
	public void setCarRental(double carRental) {
		this.carRental = carRental;
	}
	public static String getFormat(double input) {
		DecimalFormat df = new DecimalFormat("###.00");
		BigDecimal bd = new BigDecimal(input);
		return df.format(input);	
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime dt) {
		this.startDate = dt;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime enDt) {
		this.endDate = enDt;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	@Override
	public String toString() {
		return "regID = " +regID+ ", regNumber = " +regNumber+ ", carBrand = " + carBrand + 
				"\n carModel = " + carModel + ", carNumber = " + carNumber+ " carDescription = " + carDescription + 
				"\n  price(Per Hour) = "+getFormat(basePrice)+ ", carRental = "+getFormat(carRental)+ 
				"\n -------------------------------------------------------------------------------------------------------";	  
	}
	

}

