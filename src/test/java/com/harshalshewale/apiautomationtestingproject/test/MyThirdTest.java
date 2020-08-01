package com.harshalshewale.apiautomationtestingproject.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.harshalshewale.apiautomationtestingframework.test.BaseTest;
import com.harshalshewale.apiautomationtestingframework.util.DataUtil;
import com.harshalshewale.apiautomationtestingframework.util.FileUtil;
import com.harshalshewale.apiautomationtestingframework.util.JsonUtil;
import com.harshalshewale.apiautomationtestingframework.util.RestUtil;
import com.jayway.jsonpath.JsonPath;

public class MyThirdTest extends BaseTest{

	Map<String,String>headers=new HashMap<String,String>();

	static String tcName="TC001";
	
	public MyThirdTest() {
		
		super(tcName, "TC_MyTestCase_Name", "MyTestCase_description");
	}

	public void runTest() throws Exception {
		
		String url=FileUtil.getURL("dev","URL.employee.create");
		String jsonInString=FileUtil.readJsonFile("createemployee.json");
	
		//update JSON
		Object readDataJson=DataUtil.readDataFile("MyThirdTest.json");
		Integer dataLength=JsonPath.read(readDataJson,"$.data.length()");
		
		for (int i = 0; i < dataLength; i++) {
			
			String dataPart=JsonPath.read(readDataJson,"$.data["+i+"]").toString();
			
			Map<String, String> dataToUpdate = new Gson().fromJson(
					dataPart, new TypeToken<HashMap<String, String>>() {}.getType()
				);
		
			String updated_Json=JsonUtil.generateJsonWithDynamicData(jsonInString, dataToUpdate, true);
			
			System.out.println("Updated JSON -"+updated_Json);
			
			tcName="harshal";
			
			
			
		}
	}
	
	private Map<String,String> getHeaders(){
		headers.put("api_key", "abcd1234");
		return headers;
	}

}
