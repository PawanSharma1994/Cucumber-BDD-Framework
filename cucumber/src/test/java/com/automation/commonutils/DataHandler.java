package com.automation.commonutils;

import java.util.List;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import cucumber.api.DataTable;

public class DataHandler {

	private static String tempData;
	private static int tempDataCounter;
	private static String[] tempDataSet = new String[100];
	private static Fillo filo = new Fillo();
	private static Connection con;
	private static Recordset recordsetFetchData;
	private static Recordset recordsetFetchColumn;
	private final static String filePath = System.getProperty("user.dir") + "\\TestData\\DataSet.xlsx";

	public static final String getDataByIndex(DataTable table, int row, int column) {
		List<List<String>> data = table.raw();
		tempData = data.get(row).get(column).toString();
		System.out.println("Data from table is:" + tempData);
		return tempData;
	}

	public static final String getXLData(String sheetName, String colName) {
		int lowCounter = 0;
		try {
			con = filo.getConnection(filePath);
			String fetchData = "Select * from " + sheetName + " where FetchData='Y'";
			recordsetFetchData = con.executeQuery(fetchData);
			while (recordsetFetchData.next()) {
				String scenario = recordsetFetchData.getField("Scenario");
				// System.out.println(scenario);
				String fetchCol = "Select " + colName + " from " + sheetName + " where Scenario='" + scenario + "'";
				recordsetFetchColumn = con.executeQuery(fetchCol);
				while (recordsetFetchColumn.next()) {
					tempDataSet[lowCounter] = recordsetFetchColumn.getField(colName);
					// System.out.println(tempDataSet[lowCounter]);
				}
				++lowCounter;
			}
		} catch (FilloException e) {
			e.printStackTrace();
		} finally {
			closeXLConnection();
		}
		return tempDataSet[tempDataCounter++];
	}

	private static void closeXLConnection() {
		recordsetFetchColumn.close();
		recordsetFetchData.close();
		con.close();
	}

}
