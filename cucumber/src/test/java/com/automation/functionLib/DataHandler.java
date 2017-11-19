package com.automation.functionLib;

import java.util.List;
import java.util.UUID;

import cucumber.api.DataTable;
import net.bytebuddy.utility.RandomString;

public class DataHandler {

	private static String tempData;
	
	public static String getDataByIndex(DataTable table,int row,int column){
		List<List<String>> data = table.raw();
		tempData = data.get(row).get(column).toString();
		System.out.println("Data from table is:"+tempData);
		return tempData;
	}
	
}
