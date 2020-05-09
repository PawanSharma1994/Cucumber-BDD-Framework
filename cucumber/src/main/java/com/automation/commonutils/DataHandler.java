package com.automation.commonutils;


import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import cucumber.api.Scenario;
import io.cucumber.datatable.DataTable;

public class DataHandler implements Log4Interface{

	private static Fillo filo = new Fillo();
	private static Connection con;
	private static Recordset recordsetFetchData;
	private static final String FILEPATH = System.getProperty("user.dir") + "\\TestData\\DataSet.xlsx";
	private static String tempScenarioName = "";
	private Logger logger = LogManager.getLogger(DataHandler.class);

	
	/**
	 * To get the value of column from cucumber data table <K,V>
	 * @param table
	 * @param colName
	 */
	
	public static final String getDataFromCucumberTable(DataTable table,String colName) {
		Map<String,String> data = table.asMap(String.class,String.class);
		return data.get(colName);
	}

	public static void getScenarioName(Scenario scenario) {
		tempScenarioName = scenario.getName();
	}

	/**
	 * @author pawan 
	 * @param sheetName
	 * @param column
	 * @return Data Stored in Cell of excel sheet as per given column name
	 * @throws FilloException
	 */
	
	public static String getDataFromXL(String sheetName, String column) throws FilloException {
		String temp = "";
		try {
			con = filo.getConnection(FILEPATH);
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
