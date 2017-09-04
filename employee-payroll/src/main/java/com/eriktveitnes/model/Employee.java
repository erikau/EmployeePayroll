package com.eriktveitnes.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import java.util.Date;
import static com.eriktveitnes.util.SalaryCalculations.calculateGrossIncomeForMonth;
import static com.eriktveitnes.util.SalaryCalculations.calculateIncomeTaxForMonth;
import static com.eriktveitnes.util.SalaryCalculations.calculateNetIncomeForMonth;
import static com.eriktveitnes.util.SalaryCalculations.calculateSuperForMonth;
import static com.eriktveitnes.util.SalaryCalculations.calculatePayPeriod;

public class Employee {

	@CsvBindByPosition(position = 0, required = true)
	private String firstName;

	@CsvBindByPosition(position = 1, required = true)
	private String lastName;

	@CsvBindByPosition(position = 2, required = true)
	private Double annualSalary;

	@CsvBindByPosition(position = 3, required = true)
	private Double superRate;

	@CsvBindByPosition(position = 4, required = true)
	@CsvDate("dd/MM/yyyy")
	private Date paymentStartDate;

	public Employee() {
	}

	public Employee(String firstName, String lastName, Double annualSalary, Double superRate, Date paymentStartDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.annualSalary = annualSalary;
		this.superRate = superRate;
		this.paymentStartDate = paymentStartDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(Double annualSalary) {
		this.annualSalary = annualSalary;
	}

	public Double getSuperRate() {
		return superRate;
	}

	public void setSuperRate(Double superRate) {
		this.superRate = superRate;
	}

	public Date getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(Date paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	/**
	 * generates the Employee's full name by joining the first name and last name together.
	 * This is limited to regions where the first name is displayed before the last name,
	 * @return the full name of the employee
	 */
	public String getEmployeeFullName() {
		return String.format("%s %s", this.firstName, this.lastName); 
	}

	/**
	 * Generates a String that represents the Employees remuneration slip.
	 * @return the formatted string of the remuneration slip
	 */
	public String getEmployeeRemunerationSlip() {
		
		Long grossIncome = Math.round(calculateGrossIncomeForMonth(this.annualSalary));
		Long incomeTax = Math.round(calculateIncomeTaxForMonth(this.annualSalary));
		Long netIncome = Math.round(calculateNetIncomeForMonth(this.annualSalary));
		Long superAmount = Math.round(calculateSuperForMonth(this.annualSalary, this.superRate));
		
		return String.format("%s,%s,%d,%d,%d,%s", getEmployeeFullName(),
										calculatePayPeriod(this.paymentStartDate),
										grossIncome,
										incomeTax,
										netIncome,
										superAmount);
	}
}
