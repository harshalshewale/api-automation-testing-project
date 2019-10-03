package com.sia.csl.pnrutility.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.log4j.Logger;
import com.sia.csl.cryptic.api.CrypticHelper;
import com.sia.csl.pnrutility.runner.TestRunner;

public class GneratePnrUtilBackup {

	static Logger logger = Logger.getLogger(GneratePnrUtilBackup.class);
	public static String InfantBirthDate = "";
	public static String commandOutput = "";
	public static String PNR = "";
	public static int NoOfLegs = 0;
	public static String fareCommand = "NO_COMMAND";
	public static String TestCaseResult = "No_RESULT";
	public static String SquadName = "SEAT-MAP";
	public static String TestCaseName = "";

	public static String generateCustomisedPnr(String squadName, String testCaseName, String lastName, String firstLeg,
			String firstLeg_flightNumber, String firstLeg_dateOfjourney, String secondLeg, String secondLeg_flightNumber,
			String secondLeg_dateOfjourney, String thirdLeg, String thirdLeg_flightNumber, String thirdLeg_dateOfjourney, String fourthLeg,
			String fourthLeg_flightNumber, String fourthLeg_dateOfjourney, String seatClass, int noOfAdults, int noOfChilds,
			int noOfAdultsWithInfants) {

		logger.info("******************************************"+testCaseName+"**************************************************\n");
		logger.info("PNRDATA - " + squadName + " " + testCaseName + " " + lastName + " " + firstLeg + " " + firstLeg_flightNumber + " "
				+ firstLeg_dateOfjourney + " " + secondLeg + " " + secondLeg_flightNumber + " " + secondLeg_dateOfjourney + " " + thirdLeg
				+ " " + thirdLeg_flightNumber + " " + thirdLeg_dateOfjourney + " " + fourthLeg + " " + fourthLeg_flightNumber + " "
				+ fourthLeg_dateOfjourney + " " + seatClass + " " + noOfAdults + " " + noOfChilds + " " + noOfAdultsWithInfants);

		try {

			SquadName = squadName;
			TestCaseName = testCaseName;
			int totalNumberOfPax = noOfAdults + noOfChilds + noOfAdultsWithInfants;

			// Only First Leg
			if (firstLeg != null && !firstLeg.isEmpty() && (secondLeg == null || secondLeg.isEmpty())
					&& (thirdLeg == null || thirdLeg.isEmpty()) && (fourthLeg == null || fourthLeg.isEmpty())) {

				NoOfLegs = 1;

				// If There is no Flight Number
				if (firstLeg_flightNumber.isEmpty() || firstLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided so any available flight will be chosen");

					String searchFlightsCommand = "AN" + firstLeg_dateOfjourney + firstLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR", "TEST", firstLeg, secondLeg, thirdLeg,
								fourthLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String FirstFlightBookingCommand = "SS" + firstLeg_flightNumber + seatClass + firstLeg_dateOfjourney + firstLeg
							+ totalNumberOfPax;
					logger.info("FirstFlightBookingCommand = " + FirstFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(FirstFlightBookingCommand, TestRunner.sessionID);
					logger.info(FirstFlightBookingCommand + "\n" + commandOutput);
				}

			}
			// Only First Two Leg
			if (firstLeg != null && !firstLeg.isEmpty() && (secondLeg != null && !secondLeg.isEmpty())
					&& (thirdLeg == null || thirdLeg.isEmpty()) && (fourthLeg == null || fourthLeg.isEmpty())) {
				NoOfLegs = 2;

				// If There is no Flight Number
				if (firstLeg_flightNumber.isEmpty() || firstLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for First Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + firstLeg_dateOfjourney + firstLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR", "TEST", firstLeg, secondLeg, thirdLeg,
								fourthLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String FirstFlightBookingCommand = "SS" + firstLeg_flightNumber + seatClass + firstLeg_dateOfjourney + firstLeg
							+ totalNumberOfPax;
					logger.info("FirstFlightBookingCommand = " + FirstFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(FirstFlightBookingCommand, TestRunner.sessionID);
					logger.info(FirstFlightBookingCommand + "\n" + commandOutput);
				}

				if (secondLeg_flightNumber.isEmpty() || secondLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for Second Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + secondLeg_dateOfjourney + secondLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						// DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR",
						// "TEST", firstLeg, secondLeg, thirdLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String SecondFlightBookingCommand = "SS" + secondLeg_flightNumber + seatClass + secondLeg_dateOfjourney + secondLeg
							+ totalNumberOfPax;
					logger.info("SecondFlightBookingCommand = " + SecondFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(SecondFlightBookingCommand, TestRunner.sessionID);
					logger.info(SecondFlightBookingCommand + "\n" + commandOutput);
				}

			}
			// Only First Three Leg
			if (firstLeg != null && !firstLeg.isEmpty() && (secondLeg != null && !secondLeg.isEmpty())
					&& (thirdLeg != null && !thirdLeg.isEmpty()) && (fourthLeg == null || fourthLeg.isEmpty())) {
				NoOfLegs = 3;

				// If There is no Flight Number
				if (firstLeg_flightNumber.isEmpty() || firstLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for First Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + firstLeg_dateOfjourney + firstLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR", "TEST", firstLeg, secondLeg, thirdLeg,
								fourthLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String FirstFlightBookingCommand = "SS" + firstLeg_flightNumber + seatClass + firstLeg_dateOfjourney + firstLeg
							+ totalNumberOfPax;
					logger.info("FirstFlightBookingCommand = " + FirstFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(FirstFlightBookingCommand, TestRunner.sessionID);
					logger.info(FirstFlightBookingCommand + "\n" + commandOutput);
				}

				if (secondLeg_flightNumber.isEmpty() || secondLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for Second Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + secondLeg_dateOfjourney + secondLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						// DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR",
						// "TEST", firstLeg, secondLeg, thirdLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String SecondFlightBookingCommand = "SS" + secondLeg_flightNumber + seatClass + secondLeg_dateOfjourney + secondLeg
							+ totalNumberOfPax;
					logger.info("SecondFlightBookingCommand = " + SecondFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(SecondFlightBookingCommand, TestRunner.sessionID);
					logger.info(SecondFlightBookingCommand + "\n" + commandOutput);
				}

				if (thirdLeg_flightNumber.isEmpty() || thirdLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for Third Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + thirdLeg_dateOfjourney + thirdLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						// DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR",
						// "TEST", firstLeg, secondLeg, thirdLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String ThirdFlightBookingCommand = "SS" + thirdLeg_flightNumber + seatClass + thirdLeg_dateOfjourney + thirdLeg
							+ totalNumberOfPax;
					logger.info("ThirdFlightBookingCommand = " + ThirdFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(ThirdFlightBookingCommand, TestRunner.sessionID);
					logger.info(ThirdFlightBookingCommand + "\n" + commandOutput);
				}

			}
			// All Legs
			if (firstLeg != null && !firstLeg.isEmpty() && (secondLeg != null && !secondLeg.isEmpty())
					&& (thirdLeg != null && !thirdLeg.isEmpty()) && (fourthLeg != null && !fourthLeg.isEmpty())) {
				NoOfLegs = 4;

				// If There is no Flight Number
				if (firstLeg_flightNumber.isEmpty() || firstLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for First Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + firstLeg_dateOfjourney + firstLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR", "TEST", firstLeg, secondLeg, thirdLeg,
								fourthLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String FirstFlightBookingCommand = "SS" + firstLeg_flightNumber + seatClass + firstLeg_dateOfjourney + firstLeg
							+ totalNumberOfPax;
					logger.info("FirstFlightBookingCommand = " + FirstFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(FirstFlightBookingCommand, TestRunner.sessionID);
					logger.info(FirstFlightBookingCommand + "\n" + commandOutput);
				}

				if (secondLeg_flightNumber.isEmpty() || secondLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for Second Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + secondLeg_dateOfjourney + secondLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						// DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR",
						// "TEST", firstLeg, secondLeg, thirdLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String SecondFlightBookingCommand = "SS" + secondLeg_flightNumber + seatClass + secondLeg_dateOfjourney + secondLeg
							+ totalNumberOfPax;
					logger.info("SecondFlightBookingCommand = " + SecondFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(SecondFlightBookingCommand, TestRunner.sessionID);
					logger.info(SecondFlightBookingCommand + "\n" + commandOutput);
				}

				if (thirdLeg_flightNumber.isEmpty() || thirdLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for Third Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + thirdLeg_dateOfjourney + thirdLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						// DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR",
						// "TEST", firstLeg, secondLeg, thirdLeg, errorMessage);
						return errorMessage;

					}

				} else {

					String ThirdFlightBookingCommand = "SS" + thirdLeg_flightNumber + seatClass + thirdLeg_dateOfjourney + thirdLeg
							+ totalNumberOfPax;
					logger.info("ThirdFlightBookingCommand = " + ThirdFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(ThirdFlightBookingCommand, TestRunner.sessionID);
					logger.info(ThirdFlightBookingCommand + "\n" + commandOutput);
				}

				if (fourthLeg_flightNumber.isEmpty() || fourthLeg_flightNumber == null) {

					logger.info("There is no Flight Number provided for Fourth Leg so any available flight will be chosen");

					String searchFlightsCommand = "AN" + fourthLeg_dateOfjourney + fourthLeg;
					commandOutput = CrypticHelper.executeCrypticCommand(searchFlightsCommand, TestRunner.sessionID);
					logger.info(searchFlightsCommand + "\n" + commandOutput);
					int availableFlightIndex = FlightUtils.findAvaiableFlight(commandOutput);
					logger.info(availableFlightIndex + "\n" + availableFlightIndex);
					String selectFlightCommand = "ss" + totalNumberOfPax + seatClass + availableFlightIndex;
					commandOutput = CrypticHelper.executeCrypticCommand(selectFlightCommand, TestRunner.sessionID);
					logger.info(selectFlightCommand + "\n" + commandOutput);

					if (commandOutput.contains("CHECK CLASS OF SERVICE")) {
						String errorMessage = "The selling class you selected is not present";
						logger.info("Error - " + errorMessage);
						// DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, "NO-PNR",
						// "TEST", firstLeg, secondLeg, thirdLeg, errorMessage);
						return errorMessage;

					}

				} else {
					String FourthLegFlightBookingCommand = "SS" + fourthLeg_flightNumber + seatClass + fourthLeg_dateOfjourney + fourthLeg
							+ totalNumberOfPax;
					logger.info("FourthLegFlightBookingCommand = " + FourthLegFlightBookingCommand + "\n");

					commandOutput = CrypticHelper.executeCrypticCommand(FourthLegFlightBookingCommand, TestRunner.sessionID);
					logger.info(FourthLegFlightBookingCommand + "\n" + commandOutput);
				}

			}

			// ADD PAX

			if (noOfAdults != 0) {
				for (int i = 0; i < noOfAdults; i++) {
					String addAdultCommand = "NM1" + " " + lastName + "/" + getRandomFirstName();
					commandOutput = CrypticHelper.executeCrypticCommand(addAdultCommand, TestRunner.sessionID);
					logger.info(addAdultCommand + "\n" + commandOutput);
				}
			}

			if (noOfChilds != 0) {
				for (int i = 0; i < noOfChilds; i++) {
					String noOfChildsCommand = "NM1" + lastName + "/CHILD (CHD/14APR15)";
					commandOutput = CrypticHelper.executeCrypticCommand(noOfChildsCommand, TestRunner.sessionID);
					logger.info(noOfChildsCommand + "\n" + commandOutput);
				}
			}

			if (noOfAdultsWithInfants != 0) {
				for (int i = 0; i < noOfAdultsWithInfants; i++) {
					String noOfAdultsWithInfantsCommand = "NM1" + lastName + "/" + getRandomFirstName() + "(INF " + lastName + "/" + "INFY"
							+ "/" + getInfantBirthDate() + ")";
					commandOutput = CrypticHelper.executeCrypticCommand(noOfAdultsWithInfantsCommand, TestRunner.sessionID);
					logger.info(noOfAdultsWithInfantsCommand + "\n" + commandOutput);
				}
			}

			// ADD MOBILE
			String addPhoneNumberCommand = "APM 9861 3847";
			commandOutput = CrypticHelper.executeCrypticCommand(addPhoneNumberCommand, TestRunner.sessionID);
			logger.info(addPhoneNumberCommand + "\n" + commandOutput);

			// PAY FARE AND TICKETING COMMANDS

			String tkCommand = "TKOK";
			commandOutput = CrypticHelper.executeCrypticCommand(tkCommand, TestRunner.sessionID);
			logger.info(tkCommand + "\n" + commandOutput);

			// case 1 : ADT+CHD+INF

			if (noOfAdults > 0 && noOfChilds > 0 && noOfAdultsWithInfants > 0) {

				// shell 1

				String command1 = "TTC";
				String command2 = "TTI/T1/BHS";
				String command3 = "TTI/T1/A30K";

				if (NoOfLegs == 1) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command5 = "TTK/T1/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command1, TestRunner.sessionID);
				logger.info(command1 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command2, TestRunner.sessionID);
				logger.info(command2 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command3, TestRunner.sessionID);
				logger.info(command3 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command5, TestRunner.sessionID);
				logger.info(command5 + "\n" + commandOutput);

				// shell 2

				String command6 = "TTI/T2/BHS";
				String command7 = "TTI/T2/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}
				String command9 = "TTK/T2/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command6, TestRunner.sessionID);
				logger.info(command6 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command7, TestRunner.sessionID);
				logger.info(command7 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command9, TestRunner.sessionID);
				logger.info(command9 + "\n" + commandOutput);

				// shell 3

				String command10 = "TTI/T3/BHS";
				String command11 = "TTI/T3/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}
				String command13 = "TTK/T3/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command10, TestRunner.sessionID);
				logger.info(command10 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command11, TestRunner.sessionID);
				logger.info(command11 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command13, TestRunner.sessionID);
				logger.info(command13 + "\n" + commandOutput);

			}
			// Case 2 : ADT+CHID
			if (noOfAdults > 0 && noOfChilds > 0 && noOfAdultsWithInfants == 0) {

				// shell 1

				String command1 = "TTC";
				String command2 = "TTI/T1/BHS";
				String command3 = "TTI/T1/A30K";

				if (NoOfLegs == 1) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command5 = "TTK/T1/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command1, TestRunner.sessionID);
				logger.info(command1 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command2, TestRunner.sessionID);
				logger.info(command2 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command3, TestRunner.sessionID);
				logger.info(command3 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command5, TestRunner.sessionID);
				logger.info(command5 + "\n" + commandOutput);

				// shell 2

				String command6 = "TTI/T2/BHS";
				String command7 = "TTI/T2/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command9 = "TTK/T2/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command6, TestRunner.sessionID);
				logger.info(command6 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command7, TestRunner.sessionID);
				logger.info(command7 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command9, TestRunner.sessionID);
				logger.info(command9 + "\n" + commandOutput);

			}
			// Case 3 :ADT+ADTWITHINF
			if (noOfAdults > 0 && noOfAdultsWithInfants > 0 && noOfChilds == 0) {

				// shell 1

				String command1 = "TTC";
				String command2 = "TTI/T1/BHS";
				String command3 = "TTI/T1/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command5 = "TTK/T1/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command1, TestRunner.sessionID);
				logger.info(command1 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command2, TestRunner.sessionID);
				logger.info(command2 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command3, TestRunner.sessionID);
				logger.info(command3 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command5, TestRunner.sessionID);
				logger.info(command5 + "\n" + commandOutput);

				// shell 2

				String command6 = "TTI/T2/BHS";
				String command7 = "TTI/T2/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command9 = "TTK/T2/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command6, TestRunner.sessionID);
				logger.info(command6 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command7, TestRunner.sessionID);
				logger.info(command7 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command9, TestRunner.sessionID);
				logger.info(command9 + "\n" + commandOutput);

			}
			// Case 4 : CHD+ADTWITHINF
			if (noOfChilds > 0 && noOfAdultsWithInfants > 0 && noOfAdults == 0) {

				// shell 1

				String command1 = "TTC";
				String command2 = "TTI/T1/BHS";
				String command3 = "TTI/T1/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command5 = "TTK/T1/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command1, TestRunner.sessionID);
				logger.info(command1 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command2, TestRunner.sessionID);
				logger.info(command2 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command3, TestRunner.sessionID);
				logger.info(command3 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command5, TestRunner.sessionID);
				logger.info(command5 + "\n" + commandOutput);

				// shell 2

				String command6 = "TTI/T2/BHS";
				String command7 = "TTI/T2/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command9 = "TTK/T2/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command6, TestRunner.sessionID);
				logger.info(command6 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command7, TestRunner.sessionID);
				logger.info(command7 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command9, TestRunner.sessionID);
				logger.info(command9 + "\n" + commandOutput);

				// shell 3

				String command10 = "TTI/T3/BHS";
				String command11 = "TTI/T3/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T3/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command13 = "TTK/T3/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command10, TestRunner.sessionID);
				logger.info(command10 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command11, TestRunner.sessionID);
				logger.info(command11 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command13, TestRunner.sessionID);
				logger.info(command13 + "\n" + commandOutput);

			}
			// Case 5 : ONLY ADT
			if (noOfAdults > 0 && noOfChilds == 0 && noOfAdultsWithInfants == 0) {

				String command2 = "TTI/BHS";
				String command3 = "TTI/A30K";

				if (NoOfLegs == 1) {

					fareCommand = "TTI/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command5 = "TTK/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command2, TestRunner.sessionID);
				logger.info(command2 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command3, TestRunner.sessionID);
				logger.info(command3 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command5, TestRunner.sessionID);
				logger.info(command5 + "\n" + commandOutput);

			}
			// Case 6 : ONLY ADTWITHINF
			if (noOfAdults == 0 && noOfChilds == 0 && noOfAdultsWithInfants > 0) {

				// shell 1

				String command1 = "TTC";
				String command2 = "TTI/T1/BHS";
				String command3 = "TTI/T1/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T1/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}
				String command5 = "TTK/T1/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command1, TestRunner.sessionID);
				logger.info(command1 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command2, TestRunner.sessionID);
				logger.info(command2 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command3, TestRunner.sessionID);
				logger.info(command3 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command5, TestRunner.sessionID);
				logger.info(command5 + "\n" + commandOutput);

				// shell 2

				String command6 = "TTI/T2/BHS";
				String command7 = "TTI/T2/A30K";
				if (NoOfLegs == 1) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 2) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 3) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + "100.00NUC100.00END ROE1.0000";

				} else if (NoOfLegs == 4) {

					fareCommand = "TTI/T2/C" + getOriginCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(firstLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(firstLeg) + " " + getFlightOperator(secondLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(secondLeg) + " " + getFlightOperator(thirdLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(thirdLeg) + " " + getFlightOperator(fourthLeg_flightNumber) + " "
							+ getDestinationCountryFromFirsLeg(fourthLeg) + "100.00NUC100.00END ROE1.0000";

				}

				String command9 = "TTK/T2/FSGD100.00";

				commandOutput = CrypticHelper.executeCrypticCommand(command6, TestRunner.sessionID);
				logger.info(command6 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command7, TestRunner.sessionID);
				logger.info(command7 + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(fareCommand, TestRunner.sessionID);
				logger.info(fareCommand + "\n" + commandOutput);
				commandOutput = CrypticHelper.executeCrypticCommand(command9, TestRunner.sessionID);
				logger.info(command9 + "\n" + commandOutput);

			}

			// FARE COMMANDS

			String command_RF = "RF HS";
			String payByCashCommand = "FPCA";
			String createTicketCommand = "TTP/RT";
			String retrivePnrCommand = "RT";
			String ignorePNRCommand = "IG";

			commandOutput = CrypticHelper.executeCrypticCommand(command_RF, TestRunner.sessionID);
			logger.info(command_RF + "\n" + commandOutput);
			commandOutput = CrypticHelper.executeCrypticCommand(payByCashCommand, TestRunner.sessionID);
			logger.info(payByCashCommand + "\n" + commandOutput);
			commandOutput = CrypticHelper.executeCrypticCommand(createTicketCommand, TestRunner.sessionID);
			logger.info(createTicketCommand + "\n" + commandOutput);
			commandOutput = CrypticHelper.executeCrypticCommand(retrivePnrCommand, TestRunner.sessionID);
			logger.info(retrivePnrCommand + "\n" + commandOutput);

			PNR = CrypticHelper.executeCrypticCommand(ignorePNRCommand, TestRunner.sessionID);
			logger.info(ignorePNRCommand + "\n" + commandOutput);

			PNR = PNR.trim();
			PNR = PNR.substring(PNR.indexOf("-") + 1, PNR.length()).trim();
			logger.info(PNR);

			// Write PNR in Database

			DatabaseUtil.writePnrToDatabase(TestRunner.executionid, SquadName, PNR, lastName, firstLeg, secondLeg, thirdLeg, fourthLeg, "NA");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		return PNR;

	}

	private static String getFlightOperator(String flightNumber) {
		if (flightNumber.isEmpty() || flightNumber == null) {
			return FlightUtils.flightOperator;
		}
		String flightOperator = flightNumber.substring(0, 2);
		return flightOperator;
	}

	private static String getDestinationCountryFromFirsLeg(String firstLeg) {
		String destinationCountry = firstLeg.substring(3, 6);
		return destinationCountry;
	}

	private static String getOriginCountryFromFirsLeg(String firstLeg) {
		String originCountry = firstLeg.substring(0, 3);
		return originCountry;
	}

	private static String getInfantBirthDate() {
		Calendar cal2 = new GregorianCalendar();
		cal2.setLenient(false);
		cal2.roll(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMMyy");
		InfantBirthDate = dateFormat2.format(cal2.getTime());
		return InfantBirthDate;
	}

	private static String getRandomFirstName() {
		Random randomNum = new Random();
		String[] firstNames = { "Harshal", "Vaibhav", "Rasith", "Thyagesh", "Bala", "Jiva", "Pawan", "Vincent", "Philip", "Tzkeen",
				"Szeyen", "Balaji", "Ekwin", "Murali", "Donald", "Sunny", "Maxwell", "Dhoni", "Aishwarya", "Modi", "Soniya", "Trump",
				"Mandela", "Rahul", "Salman", "Alia", "Deepika", "Imran", "Jude", "Robin", "jackma", "Ganguly", "Harsh", "Aliya",
				"PutinKumar" };
		int firstNameArrayLength = firstNames.length;
		int IndexOfFirstName = 0 + randomNum.nextInt(firstNameArrayLength - 1);
		return firstNames[IndexOfFirstName];
	}

}
