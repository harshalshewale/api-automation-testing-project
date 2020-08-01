package com.harshalshewale.apiautomationtestingproject.test;

import java.util.HashMap;
import java.util.Map;

import com.harshalshewale.apiautomationtestingframework.test.BaseTest;
import com.harshalshewale.apiautomationtestingframework.util.FileUtil;
import com.harshalshewale.apiautomationtestingframework.util.JsonUtil;
import com.harshalshewale.apiautomationtestingframework.util.RestUtil;

public class MySecondTest extends BaseTest{

	Map<String,String>headers=new HashMap<String,String>();

	public MySecondTest() {
		super("TC002", "SecondTestCase_Name", "SecondTestCase_description");
	}

	public void runTest() throws Exception {
		
		String url=FileUtil.getURL("dev","URL.employee.create");
		System.out.println("url "+url);
		String jsonInString=FileUtil.readJsonFile("createemployee.json");
		System.out.println("Original JSON - "+jsonInString);
		
		//update JSON
		
		Map<String,String> dataToUpdate=new HashMap<String,String>();
		dataToUpdate.put("$.name", "Bunty");
		String updated_Json=JsonUtil.generateJsonWithDynamicData(jsonInString, dataToUpdate, true);
		System.out.println("Updated JSON -"+updated_Json);
		


	}
	
	private Map<String,String> getHeaders(){
		headers.put("api_key", "abcd1234");
		return headers;
	}

}
