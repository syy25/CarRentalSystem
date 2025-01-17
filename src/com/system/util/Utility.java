package com.system.util;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.system.model.CarRentalModel;
import com.system.pojo.CarRegistration;
import com.system.pojo.Customer;

public class Utility {
	public static String getFormat(double input) {
		DecimalFormat df = new DecimalFormat("###.00");
		BigDecimal bd = new BigDecimal(input);
		return df.format(input);	
	}
	public boolean checkLogin(Scanner scanner) {
		boolean result = false;
		System.out.println("Kindly enter your email..");	//prompt user to login first
		String email = scanner.nextLine();
		Map<String,Boolean> users = CarRentalModel.loginUsers;		
		if(users != null && users.get(email) == null) {		//map is not null, map not user id
			System.out.println("You are not login. Kindly select option 1.");
			result = true;
		}
		return result;
	}
	public static String validUser(String emailID) {
		String email=null;
		String result = "";
		Scanner scanner = new Scanner(System.in);
		Map<String,Boolean> users = CarRentalModel.loginUsers; //map is not null, map not user id
		if(users != null && users.get(emailID) == null) {	
			System.out.println("\nKindly enter your email..");	//prompt user to login first
			email = scanner.nextLine();	
			System.out.println("You are not login. Kindly select option 1.");
		}else {
			result = emailID;
		}
		return result;
	}
	public static Map<String, List<CarRegistration>> loadData()throws Exception {		//file handling
		File file = new File ("inputData.txt");
		Scanner scanner = new Scanner(file);							//reading from file
		Map<String, List<CarRegistration>> map = CarRentalModel.reg;		//import list
		String seq = "V00";
		int counter = 1;
		while(scanner.hasNextLine()) {
			CarRegistration carReg = new CarRegistration (); //car object
			String data = scanner.nextLine();
			String[] dataArray = data.split(",");
			carReg.setRegID(dataArray[0]);
			carReg.setCarBrand(dataArray[1]);
			carReg.setCarModel(dataArray[2]);
			carReg.setCarNumber(dataArray[3]);
			carReg.setRegNumber(dataArray[4]);
			carReg.setCarDescription(dataArray[5]);
			carReg.setBasePrice(Double.parseDouble(dataArray[6]));	//Double.parseDouble	
			carReg.setRegID(seq + counter++);
			if(dataArray.length > 7) {
				carReg.setUserID(dataArray[7]);
			}
			if(dataArray.length > 7 && !"null".equals(dataArray[8])) {
				LocalDateTime datetime = LocalDateTime.parse(dataArray[8]);
				carReg.setStartDate(datetime);
			}
			if(dataArray.length > 8 && !"null".equals(dataArray[9])) {
				LocalDateTime datetime = LocalDateTime.parse(dataArray[9]);
				carReg.setEndDate(datetime);
			}
			String rental = (dataArray.length > 9 && dataArray[10] != null && !"null".equals(dataArray[10]))? dataArray[10]: "0.0";
			carReg.setCarRental(Double.parseDouble(rental));
			String deposit = (dataArray.length > 10 && dataArray[11] != null && !"null".equals(dataArray[11]))? dataArray[11]: "0.0";
			carReg.setCarDeposit(Double.parseDouble(deposit));
			List list = new ArrayList();				//ctrl space
			list.add(carReg);
			map.put(carReg.getRegID(), list);
			
			/*if(map.get(carReg.getRegID())!=null) { 					//registration details
				List list = map.get(carReg.getRegID());
				list.add(carReg);
				map.put(carReg.getRegID(), list);
			}else {
				List list = new ArrayList();				//ctrl space
				list.add(carReg);
				//map.put("regList",list);
				map.put(carReg.getRegID(), list);
			}*/
		}
		return map;
	}
	//Toyota,Vios,AW123,1001,Used 2 years,100000
	public static void storeData(Map <String,List <CarRegistration>> map)throws Exception {
		String fileName = System.getProperty("user.dir")+"/inputData.txt";
		String regID;
		String name;
		String model;
		String plate;
		String regNum;
		String desc;
		double basePrice;
		String startDate;
		String endDate;
		String rental;
		double deposit;
		String data = "";
		String userID = "";
		CarRegistration car;
		
		int numUpdatedCars = map.size();
		boolean deleteFile = false;
		if(numUpdatedCars == 0) {
			deleteFile = true;
			System.out.println("Empty file");
		}
		if(deleteFile) {
			PrintWriter pw = new PrintWriter(fileName);		//Class to write content to file
			pw.print("");
			pw.close();
		}
		/*Map <String,List<CarRegistration>> fileMap = loadData();
			if(map.size()>0) {
				map.putAll(fileMap);
			}*/
		for(Map.Entry <String,List<CarRegistration>> set: map.entrySet()) {
			//System.out.println("Testing");
			regID = set.getKey();
			car = set.getValue().get(0);
			name = car.getCarBrand();
			model = car.getCarModel();
			plate = car.getCarNumber();
			regNum = car.getRegNumber();
			desc = car.getCarDescription();
			basePrice = car.getBasePrice();
			startDate = ""+car.getStartDate();	//convert to string with ""
			endDate = ""+car.getEndDate();
			rental = ""+car.getCarRental();
			userID = car.getUserID();
			deposit = car.getCarDeposit();
			data = data + regID + "," + name + "," + model + "," + plate + "," + regNum + "," + desc + "," + basePrice+ "," + userID;
			if(!startDate.equals("") && !endDate.equals("")) {
				data = data + "," + startDate + "," + endDate + "," + rental  + "," + deposit;
			}
			data = data + "\n";
			Path path = Paths.get(fileName);		//Get old obj from file 
			Files.write(path, data.getBytes(), StandardOpenOption.APPEND);		//getBytes() = convert data to byte
		}
	}
	public static void storeCustData(Map <String,List <Customer>> map)throws Exception {
		String fileName = System.getProperty("user.dir")+"/custData.txt";
		String custID;
		String name;
		String birthDate;
		String phoneNum;
		String email;
		String address;
		String data = "";
		String password = "";
		Customer cust;
		
		int numUpdatedCustomer = map.size();
		boolean deleteFile = false;
		if(numUpdatedCustomer == 0) {
			deleteFile = true;
			System.out.println("Empty file");
		}
		if(deleteFile) {
			PrintWriter pw = new PrintWriter(fileName);		//Class to write content to file
			pw.print("");
			pw.close();
		}
		for(Map.Entry <String,List<Customer>> set: map.entrySet()) {
			//System.out.println("Testing");
			custID = set.getKey();
			cust = set.getValue().get(0);
			name = cust.getCustomerName();
			birthDate = cust.getCustomerBirthDate();
			phoneNum = cust.getCustomerMobileNumber();
			email = cust.getCustomerEmail();
			address = cust.getCustomerAddress();
			password = cust.getPassword();
			data = data + custID + ";" + name + ";" + birthDate + ";" + phoneNum + ";" + email + ";" + address + ";" + password +"\n";
			Path path = Paths.get(fileName);		//Get old obj from file 
			Files.write(path, data.getBytes());		//getBytes() = convert data to byte
		}
	}
	public static Customer getCustomer(String emailID) throws Exception{
		loadCustData(System.getProperty("user.dir")+"/custData.txt");
		Map<String, List<Customer>> map = CarRentalModel.user;
		String custID;
		Customer cust = null;
		boolean isExist = false; 
		for(Map.Entry <String,List<Customer>> set: map.entrySet()) {
			//System.out.println("Testing");
			custID = set.getKey();
			cust = set.getValue().get(0);
			if(emailID.equals(cust.getCustomerEmail())) {
				isExist = true;
				break;
			}
		}
		if(isExist) {
			
		}else {
			cust = new Customer();
			cust.setCustomerID("");
		}
		return cust;
		
	}
	//method return map of key: String, return Customer list
	public static Map<String, List<Customer>> loadCustData(String fileName)throws Exception {		//file handling
		File file = new File (fileName);
		Scanner scanner = new Scanner(file);							//reading from file
		Map<String, List<Customer>> map = CarRentalModel.user;		//import list
		
		while(scanner.hasNextLine()) {
			Customer cust = new Customer (); //car object
			String data = scanner.nextLine();
			String[] dataArray = data.split(";");
			cust.setCustomerID(dataArray[0]);
			cust.setCustomerName(dataArray[1]);
			cust.setCustomerBirthDate(dataArray[2]);
			cust.setCustomerMobileNumber(dataArray[3]);
			cust.setCustomerEmail(dataArray[4]);
			cust.setCustomerAddress(dataArray[5]);
			SequenceGenerator.getCustomerNext();
			cust.setPassword(dataArray[6]);
			
			if(map.get(dataArray[0])!=null) { 					//registration details
				List list = map.get(dataArray[0]);
				list.add(cust);
				map.put(dataArray[0],list);
			}else {
				List list = new ArrayList();				//ctrl space
				list.add(cust);
				//map.put("regList",list);
				map.put(cust.getCustomerID(), list);
			}
		}
		return map;
	}
}
