package com.sia.csl.pnrutility.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FlightUtils {
	
	static String flightOperator=" ";

	static String data = "AN12OCTSINBOM/ASQ\n"
			+ "** SINGAPORE AIRLINES - AN ** BOM MUMBAI.IN                    1 FR 12OCT 0000\n"
			+ " 1   SQ 422  Z9 C9 J9 U9 D8 I9 S9 /SIN 3 BOM 2  0740    1030  E0/359       5:20\n"
			+ "             T9 P9 R9 YL B9 E9 M9 H9 W9 Q9 N9 V9 K9 L5 X9\n"
			+ " 2MI:SQ426  Z9 C9 J9 U9 D8 I9 Y9 /SIN 3 BOM 2  1755    2100  E0/333       5:35\n"
			+ "             B9 E9 M9 H9 W9 Q9 N9 V9 K9 L5 X9\n"
			+ " 3   SQ 424  F9 A4 O2 Z9 C9 J9 U9 /SIN 3 BOM 2  1900    2150  E0/388       5:20\n"
			+ "             D8 I9 S9 T9 P9 R9 Y9 B9 E9 M9 H9 W9 Q9 N9 V9 K9 L5 X9\n"
			+ " 4   SQ 402  Z9 C9 J9 U9 DL I9 Y9 /SIN 3 DEL 3  0235    0540  E0/772\n"
			+ "             B9 E9 M9 H9 W9 Q9 N9 V9 L5 X9\n"
			+ "  UK:SQ4741  Z3 C3 JL UL DL S9 T9 /DEL 3 BOM 2  0730    0940  E0/320  TR   9:35\n"
			+ "             P3 Y9 B9 E9 M9 H9 W9 N9 Q9 K9 V9\n"
			+ " 5   SQ 402  Z9 C9 J9 U9 DL I9 Y9 /SIN 3 DEL 3  0235    0540  E0/772\n"
			+ "             B9 E9 M9 H9 W9 Q9 N9 V9 L5 X9\n"
			+ "  UK:SQ4767  Z3 C3 JL UL DL S9 T9 /DEL 3 BOM 2  0845    1110  E0/320  TR  11:05\n"
			+ "             P6 Y9 B9 E9 M9 H9 W9 N9 Q9 K9 V9\n"
			+ " 6   SQ 402  Z9 C9 J9 U9 DL I9 Y9 /SIN 3 DEL 3  0235    0540  E0/772\n"
			+ "             B9 E9 M9 H9 W9 Q9 N9 V9 L5 X9\n"
			+ "  UK:SQ4759  Z5 C5 J2 U2 D2 S9 T6 /DEL 3 BOM 2  1020    1230  E0/320  TR  12:25\n"
			+ "             P6 Y9 B9 E9 M9 H9 W9 N9 Q9 K9 V9";
/*
	public static void main(String[] args) {
		//System.out.println(findAvaiableFlight(data));
		writeAPiStatusInDatabase("getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax", "getpax");
	}
	*/

	public static int findAvaiableFlight(String flightData) {
		System.out.println("*********** Checking Availability ***********");
		StringTokenizer tokenizer = new StringTokenizer(flightData, "\n"); 
		// ignore first two lines
		tokenizer.nextElement();
		tokenizer.nextElement();

		List<String> data = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			data.add(tokenizer.nextElement().toString());
		}

		for (int index = 0; index < data.size(); index = index + 2) {
			System.out.println(data.get(index));
			System.out.println(data.get(index + 1));
			if (data.get(index).contains("SQ") || data.get(index).contains("MI")) {
				
				if(data.get(index).contains("SQ"))
				{
					flightOperator="SQ";
					
				}else if(data.get(index).contains("MI"))
				{
					flightOperator="MI";
				}
				
				if (data.get(index).contains("YL") || data.get(index + 1).contains("YL")) {
					continue;
				} else if (data.get(index).contains("Y9") || data.get(index + 1).contains("Y9")) {
					int result = (index / 2) + 1;
					System.out.println("Available in Flight: " + result);
					return result;
				}
			}
			System.out.println("*********************************");
		}
		System.out.println("******** No Avaiable Flights **************");
		return 0;
	}
	

}
