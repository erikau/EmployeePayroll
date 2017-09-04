package com.eriktveitnes.employeepayroll;

import com.eriktveitnes.model.Employee;
import com.eriktveitnes.util.CsvParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class EmployeePayroll {

	private static  final Logger LOGGER = Logger.getLogger(EmployeePayroll.class);
	protected static final String DEFAULT_FILE_NAME = "input.csv";

	public static void main(String[] args) {

		List<Employee> employees = CsvParser.parseFile(getFilePathOrDefault(args));
		LOGGER.info("---------------------------------------------------------------");
		for (Employee employee : employees) {
			//Print the pay slip
			LOGGER.info(employee.getEmployeeRemunerationSlip());
		}
		LOGGER.info("---------------------------------------------------------------");
	}

	protected static String getFilePathOrDefault(String[] args) {
		String fileName = args != null && args.length > 0 && StringUtils.isNotEmpty(args[0]) ? args[0] : DEFAULT_FILE_NAME;
		try {
			URL resource = EmployeePayroll.class.getResource("/" + fileName);
			return Paths.get(resource.toURI()).toFile().getAbsolutePath();
		} catch (Exception e) {
			LOGGER.error("File:" + fileName + " could not be loaded. Please check the filename is correct and that the file exists in the resources folder of the project.");
			throw new RuntimeException("File:" + fileName + " could not be loaded.");
		}
	}
}
