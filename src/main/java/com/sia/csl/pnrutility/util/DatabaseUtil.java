package com.sia.csl.pnrutility.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.sia.csl.pnrutility.runner.TestRunner;

public class DatabaseUtil {

	static Logger logger = Logger.getLogger(DatabaseUtil.class);

	public static final String DB_URL = TestUtil.readPropertyValues("database.properties", "DB_URL");
	public static final String DB_USER_NAME = TestUtil.readPropertyValues("database.properties", "DB_USER_NAME");
	public static final String DB_PASSWORD = TestUtil.readPropertyValues("database.properties", "DB_PASSWORD");
	public static final String myDriver = TestUtil.readPropertyValues("database.properties", "myDriver");

	public static void writePnrToDatabase(String executionid, String squad, String pnr, String lastname, String leg1, String leg2,
			String leg3, String leg4, String comment) {

		String datetime = TestUtil.getCurrentTime();
		String userkey = TestRunner.key;

		try {

			if (leg2.isEmpty() || leg2 == null) {
				leg2 = "NA";
			}

			if (leg3.isEmpty() || leg3 == null) {
				leg3 = "NA";
			}

			if (leg4.isEmpty() || leg4 == null) {
				leg4 = "NA";
			}

			logger.info("Writting PNR's to Database");
			logger.info("PNR Details - " + executionid + " " + squad + " " + pnr + " " + lastname + " " + leg1 + " " + leg2 + " " + leg3
					+ " " + leg4 + " " + comment + " " + datetime + " " + userkey);

			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);

			String query = "insert into CSL_Automation_pnrs (executionid,squad,pnr,lastname,leg1,leg2,leg3,leg4,comment,datetime,userkey)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			logger.info("QUERY -" + query);

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			logger.info("preparedStmt -" + preparedStmt);

			preparedStmt.setString(1, executionid);
			preparedStmt.setString(2, squad);
			preparedStmt.setString(3, pnr);
			preparedStmt.setString(4, lastname);
			preparedStmt.setString(5, leg1);
			preparedStmt.setString(6, leg2);
			preparedStmt.setString(7, leg3);
			preparedStmt.setString(8, leg4);
			preparedStmt.setString(9, comment);
			preparedStmt.setString(10, datetime);
			preparedStmt.setString(11, userkey);
			preparedStmt.execute();
			conn.close();

		}

		catch (Exception e) {
			logger.error("Got an exception while writing pnrs in to the database");
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	public static void storeApiRequestResponse(String testExecutionId, String testCaseName, String apiName, String request, String response,
			String timestamp) {
		try {

			logger.info("Writting API Requests Responses to Database");
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);

			String query = "insert into CSL_Automation_API (executionid,testcase,apiname,request,response,timestamp)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, testExecutionId);
			preparedStmt.setString(2, testCaseName);
			preparedStmt.setString(3, apiName);
			preparedStmt.setString(4, request);
			preparedStmt.setString(5, response);
			preparedStmt.setString(6, timestamp);
			preparedStmt.execute();
			conn.close();

		}

		catch (Exception e) {
			logger.error("Got an exception!");
			logger.error(e.getMessage());
		}

	}

	public static void writeTestCaseResult(String squad, String executionID, String testCaseName, String description, String testCaseResult,
			String comment, String timeStamp) {
		try {
			logger.info("Writting Test Case Result to Database");

			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);

			String query = "insert into CSL_Automation_Results (squad,executionid,testcase,description,result,comment,timestamp)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, squad);
			preparedStmt.setString(2, executionID);
			preparedStmt.setString(3, testCaseName);
			preparedStmt.setString(4, description);
			preparedStmt.setString(5, testCaseResult);
			preparedStmt.setString(6, comment);
			preparedStmt.setString(7, timeStamp);
			preparedStmt.execute();

			conn.close();

		}

		catch (Exception e) {
			logger.error("Got an exception!");
			logger.error(e.getMessage());
		}

	}

	public static boolean authenticateUserKey(String userkey) throws ClassNotFoundException, SQLException {

			logger.info("Verifying User Key...");
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
			String query = "select * from csl_automation_keys where userkey='" + userkey + "'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				System.out.println(userkey + " : This key is authenticated");
				return true;
			} else {

				System.out.println(userkey + " : This key is not valid");
				return false;
			}

	}

}
