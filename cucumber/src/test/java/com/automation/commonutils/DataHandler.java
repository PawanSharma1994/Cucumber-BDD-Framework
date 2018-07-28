package com.automation.commonutils;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import cucumber.api.DataTable;
import cucumber.api.Scenario;

public class DataHandler implements Log4Interface{

	private static String tempData;
	private static Fillo filo = new Fillo();
	private static Connection con;
	private static Recordset recordsetFetchData;
	private final static String filePath = System.getProperty("user.dir") + "\\TestData\\DataSet.xlsx";
	private static String tempScenarioName = "";
	private Logger logger = LogManager.getLogger(DataHandler.class);

	public static final String getDataByIndex(DataTable table, int row, int column) {
		List<List<String>> data = table.raw();
		tempData = data.get(row).get(column).toString();
		System.out.println("Data from table is:" + tempData);
		return tempData;
	}

	public static void getScenarioName(Scenario scenario) {
		tempScenarioName = scenario.getName();
	}

	public static String getData(String sheetName, String column) throws FilloException {
		String temp = "";
		try {
			con = filo.getConnection(filePath);
			String queryData = "Select " + column + " from " + sheetName + " Where Scenario='" + tempScenarioName + "'";
			recordsetFetchData = con.executeQuery(queryData);
			while (recordsetFetchData.next()) {
				temp = recordsetFetchData.getField(column);
				log.info("Data get from Sheet : " + temp);
			}
		} catch (FilloException e) {
			log.error("FilloException found!!");
		} finally {
			closeXLConnection();
		}
		return temp;
	}

	private static void closeXLConnection() {
		recordsetFetchData.close();
		con.close();
	}

	@Override
	public Logger getLogs() {
		return logger;
	}

}
