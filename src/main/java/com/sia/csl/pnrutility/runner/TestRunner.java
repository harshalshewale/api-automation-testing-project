package com.sia.csl.pnrutility.runner;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.sia.csl.cryptic.api.CrypticHelper;
import com.sia.csl.pnrutility.util.DatabaseUtil;
import com.sia.csl.pnrutility.util.GneratePnrUtil;

public class TestRunner {

	static Logger logger = Logger.getLogger(TestRunner.class);

	public static String executionid = generateExecutionId();
	public static String Category = "";
	public static String testCaseName = "";
	public static String firstLeg = "";
	public static String firstLeg_flightNumber = "";
	public static String firstLeg_dateOfjourney = "";
	public static String secondLeg = "";
	public static String secondLeg_flightNumber = "";
	public static String secondLeg_dateOfjourney = "";
	public static String thirdLeg = "";
	public static String thirdLeg_flightNumber = "";
	public static String thirdLeg_dateOfjourney = "";
	public static String fourthLeg = "";
	public static String fourthLeg_flightNumber = "";
	public static String fourthLeg_dateOfjourney = "";
	public static String seatClass = "";
	public static String noOfAdults = "";
	public static String noOfChilds = "";
	public static String noOfAdultsWithInfant = "";
	public static String lastName = "";
	public static String squadName = "";
	public static String TestScenariosExcelPath = "NA";
	public static int noOfAdultsInInteger = 0;
	public static int noOfChildsInInteger = 0;
	public static int noOfAdultsWithInfantInInteger = 0;
	public static String sessionID = "";
	public static String key="";

	public static void main(String[] args) {
		logger.info("Jar Executing..");
		
		String excelName=args[0];
		logger.info("excelName : "+excelName);
		
		TestScenariosExcelPath="upload/"+excelName;
		logger.info("TestScenariosExcelPath : "+TestScenariosExcelPath);
		
		loginToARD();
		executeScenarios();
		logoutFromARD();
			
	}
	
	
	public static void loginToARD() {
		loginToAltea();
	}
	
	
	public static void logoutFromARD() {
		logoutFromAltea();
	}
	
	public static void executeScenarios() {

		try {

			String excelPath = TestScenariosExcelPath;
			FileInputStream input = new FileInputStream(excelPath);
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			//Authenticate User
			XSSFSheet AuthenticationSheet = workbook.getSheet("Authentication");
			XSSFRow AuthenticationSheetRow = AuthenticationSheet.getRow(1);
			HSSFDataFormatter Stringformatter = new HSSFDataFormatter();
			key=Stringformatter.formatCellValue(AuthenticationSheetRow.getCell(2)).toString().trim();
			logger.info("Key - "+key);
			
			authenticateKey(key);
			
			// Find PNR Category to Run
			XSSFSheet EnvironmentSheet = workbook.getSheet("Category");
			int numberOfRowsinEnvironmentSheet = EnvironmentSheet.getLastRowNum();
			for (int i = 0; i <= numberOfRowsinEnvironmentSheet; i++) {
				XSSFRow EnvironmentSheetRow = EnvironmentSheet.getRow(i);
				if (EnvironmentSheetRow != null) {
					if (EnvironmentSheetRow.getCell(2).getStringCellValue().equalsIgnoreCase("yes")) {
						Category = EnvironmentSheetRow.getCell(1).getStringCellValue().toString().trim();
						logger.info("TestType :" + Category);
					}
				}
			}

			// Go to Category Sheet

			XSSFSheet Sheet = workbook.getSheet(Category);
			int numberOfRows = Sheet.getLastRowNum();

			for (int i = 0; i <= numberOfRows; i++) {
				XSSFRow row = Sheet.getRow(i);
				if (row != null) {
					if (row.getCell(19).getStringCellValue().equalsIgnoreCase("yes")) {

						HSSFDataFormatter formatter = new HSSFDataFormatter();

						testCaseName = formatter.formatCellValue(row.getCell(0)).toString().trim();

						firstLeg = formatter.formatCellValue(row.getCell(1)).toString().trim();
						firstLeg_flightNumber = formatter.formatCellValue(row.getCell(2)).toString().trim();
						firstLeg_dateOfjourney = formatter.formatCellValue(row.getCell(3)).toString().trim();

						secondLeg = formatter.formatCellValue(row.getCell(4)).toString().trim();
						secondLeg_flightNumber = formatter.formatCellValue(row.getCell(5)).toString().trim();
						secondLeg_dateOfjourney = formatter.formatCellValue(row.getCell(6)).toString().trim();

						thirdLeg = formatter.formatCellValue(row.getCell(7)).toString().trim();
						thirdLeg_flightNumber = formatter.formatCellValue(row.getCell(8)).toString().trim();
						thirdLeg_dateOfjourney = formatter.formatCellValue(row.getCell(9)).toString().trim();

						fourthLeg = formatter.formatCellValue(row.getCell(10)).toString().trim();
						fourthLeg_flightNumber = formatter.formatCellValue(row.getCell(11)).toString().trim();
						fourthLeg_dateOfjourney = formatter.formatCellValue(row.getCell(12)).toString().trim();

						seatClass = formatter.formatCellValue(row.getCell(13)).toString().trim();
						noOfAdults = formatter.formatCellValue(row.getCell(14)).toString().trim();
						noOfChilds = formatter.formatCellValue(row.getCell(15)).toString().trim();
						noOfAdultsWithInfant = formatter.formatCellValue(row.getCell(16)).toString().trim();
						lastName = formatter.formatCellValue(row.getCell(17)).toString().trim();
						squadName = formatter.formatCellValue(row.getCell(18)).toString().trim();

						noOfAdultsInInteger = Integer.parseInt(noOfAdults);
						noOfChildsInInteger = Integer.parseInt(noOfChilds);
						noOfAdultsWithInfantInInteger = Integer.parseInt(noOfAdultsWithInfant);

						GneratePnrUtil.generateCustomisedPnr(squadName, testCaseName, lastName, firstLeg, firstLeg_flightNumber,
								firstLeg_dateOfjourney, secondLeg, secondLeg_flightNumber, secondLeg_dateOfjourney, thirdLeg,
								thirdLeg_flightNumber, thirdLeg_dateOfjourney, fourthLeg, fourthLeg_flightNumber, fourthLeg_dateOfjourney,
								seatClass, noOfAdultsInInteger, noOfChildsInInteger, noOfAdultsWithInfantInInteger);
					}

				} else {
					logger.info("File is empty");
				}
			}

			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

	}

	private static void authenticateKey(String Key) throws ClassNotFoundException, SQLException {
		
		boolean b=DatabaseUtil.authenticateUserKey(Key);
		if(b==true) {
			logger.info("Authentication Successfull");
		}else {
			logger.info("Authentication Failed, Key not valid");
			System.exit(0);
		}
		
	}


	public static String generateExecutionId() {
		return UUID.randomUUID().toString();
	}

	public static void loginToAltea() {
		logger.info("*********************** Logg-in to Altea ****************************\n");
		sessionID = CrypticHelper.alteaSign();
		logger.info("Session ID - " + sessionID + "\n");
	}

	public static void logoutFromAltea() {
		logger.info("*********************** Logg Out From Altea ****************************\n");
		CrypticHelper.alteaSignOut(sessionID);
		sessionID = null;
	}

}
