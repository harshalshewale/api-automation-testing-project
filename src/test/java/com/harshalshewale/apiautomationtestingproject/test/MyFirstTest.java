package com.harshalshewale.apiautomationtestingproject.test;

import java.util.HashMap;
import java.util.Map;
import com.harshalshewale.apiautomationtestingframework.test.BaseTest;
import com.harshalshewale.apiautomationtestingframework.util.FileUtil;
import com.harshalshewale.apiautomationtestingframework.util.JsonUtil;
import com.harshalshewale.apiautomationtestingframework.util.RestUtil;

public class MyFirstTest extends BaseTest{
	

	Map<String,String>headers=new HashMap<String,String>();

	public MyFirstTest() {
		super("TC001", "TC_MyTestCase_Name", "MyTestCase_description");
	}

	public void runTest() throws Exception {
		
		String url=FileUtil.getURL("dev","URL.employee.create");
		System.out.println("url "+url);
		
		String jsonInString=FileUtil.readJsonFile("createemployee.json");
		
		System.out.println("Original JSON - "+jsonInString);
		
		//update JSON
		
		Map<String,String> dataToUpdate=new HashMap<String,String>();
		dataToUpdate.put("$.name", "Senty");
		
		String updated_Json=JsonUtil.generateJsonWithDynamicData(jsonInString, dataToUpdate, true);
		
		System.out.println("Updated JSON -"+updated_Json);
		
		
		
		//String response=RestUtil.makePostCall(url, headers, updated_Json);
		//System.out.println("Response - "+response);
		
		
		
		//String response=RestUtil.makePostCall(url, headers, body);

	}
	
	private Map<String,String> getHeaders(){
		headers.put("api_key", "abcd1234");
		return headers;
	}

}
