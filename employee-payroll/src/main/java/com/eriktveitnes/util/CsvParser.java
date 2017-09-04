package com.eriktveitnes.util;

import com.eriktveitnes.model.Employee;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CsvParser {

	private static final Logger LOGGER = Logger.getLogger(CsvParser.class);
	protected static final String ACCEPTED_DATE_FORMAT = "dd/MM/yyyy";

	public static List<Employee> parseFile(String filePath) {
		try {
			return new CsvToBeanBuilder(new FileReader(filePath))
							.withType(Employee.class)
							.withSeparator(',')
							.withFilter(line -> allowLine(line))
							.build().parse();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected static boolean allowLine(String[] line) {

		List<String> errors = new ArrayList<>();

		if(line == null || line.length != 5){
			errors.add("Invalid line length");
		} else {
			validateFirstName(line[0], errors);
			validateLastName(line[1], errors);
			validateAnnualSalary(line[2], errors);
			validateSuperRate(line[3], errors);
			validateStartDate(line[4], errors);
		}

		if(!errors.isEmpty()){
				LOGGER.warn("Rejected line :" + Arrays.toString(line) + "\n  Errors: \n" + Arrays.toString(errors.toArray()));
			return false;
		}

		return true;
	}

	protected static void validateFirstName(String s, List<String> errors) {
		if(StringUtils.isEmpty(s)) {
			errors.add("First name is not provided");
		}
	}

	protected static void validateLastName(String s, List<String> errors) {
		if(StringUtils.isEmpty(s)) {
			errors.add("Last name is not provided");
		}
	}

	protected static void validateAnnualSalary(String s, List<String> errors) {
		if(StringUtils.isEmpty(s)) {
			errors.add("Annual Salary is not provided");
			return;
		}
		try {
			if (Double.parseDouble(s) < 0) {
				errors.add("Annual Salary must be a positive number");
			}
		} catch (NumberFormatException e){
			errors.add("Annual Salary is not a number");
		}
	}

	protected static void validateSuperRate(String s, List<String> errors) {
		if(StringUtils.isEmpty(s)) {
			errors.add("Super Rate is not provided");
			return;
		}

		try {
			double superRate = Double.parseDouble(s);
			if (superRate < 0) {
				errors.add("Super Rate must be a positive number");
			}
			if(superRate > 1) {
				errors.add("Super Rate must be a positive number between 0 and 1");
			}
		} catch (NumberFormatException e){
			errors.add("Super Rate is not a number");
		}
	}

	protected static void validateStartDate(String s, List<String> errors) {
		if(StringUtils.isEmpty(s)) {
			errors.add("Start Date is not provided");
			return;
		}

		try {
			DateTimeFormatter fmt = DateTimeFormat.forPattern(ACCEPTED_DATE_FORMAT);
			fmt.parseDateTime(s);
		} catch (IllegalArgumentException e){
			errors.add("Start Date is not recognised a date format");
		}
	}
}
