package com.eriktveitnes.util;

import com.eriktveitnes.model.TaxBracket;
import com.eriktveitnes.model.TaxBracketCache;
import org.joda.time.LocalDateTime;
import java.util.Date;

public class SalaryCalculations {

    /**
     * Calculates the Pay period for the given start date.
     * Using the Start Date, show the boundaries of the month. E.g. 01 March - 31 March
     * @param paymentStartDate The payment start date.
     * @return The boundaries of the given month
     */
    public static String calculatePayPeriod(Date paymentStartDate) {

        LocalDateTime startDateAsLocalDate = new LocalDateTime(paymentStartDate);

        LocalDateTime lastDayOfMonth = startDateAsLocalDate.dayOfMonth().withMaximumValue();
        String paymentMonth = startDateAsLocalDate.monthOfYear().getAsText();

        return String.format("01 %s - %d %s", paymentMonth, lastDayOfMonth.getDayOfMonth(), paymentMonth);
    }

    /**
     * Calculate the gross income for the month.
     *  Annual salary / 12 months
     * @param annualSalary the annual salary of the employee
     * @return the gross income for the month
     */
    public static Double calculateGrossIncomeForMonth(Double annualSalary) {
        return  annualSalary / 12;
    }

    /**
     * Calculate the income tax for the month.
     * Based on the tax table from the TaxBracketCache.
     * This will then compute:
     *  (lump sum + ((annual salary - bracket low range) * tax percentage in bracket) / 12
     * @param annualSalary the annual salary for the employee
     * @return the income tax for the month
     */
    public static Double calculateIncomeTaxForMonth(Double annualSalary) {
        TaxBracket bracket = TaxBracketCache.getTaxBracket(annualSalary);
        return (bracket.getLumpSum() + ((annualSalary - bracket.getLowRange()) * bracket.getTaxPercentage())) / 12;
    }

    /**
     * Calculate the net income for the month.
     * This is calculated by the gross income for the Month - the Income Tax for the month
     * @param annualSalary the annual salary for the employee
     * @return the net income for the month
     */
    public static Double calculateNetIncomeForMonth(Double annualSalary) {
        return calculateGrossIncomeForMonth(annualSalary) - calculateIncomeTaxForMonth(annualSalary);
    }

    /**
     * Calculate the super payment for the month.
     * This is calculated by the gross income for the month * the super rate.
     * @param annualSalary the annual salary for the employee
     * @param superRate the super rate to be applied
     * @return the super payment for the month
     */
    public static Double calculateSuperForMonth(Double annualSalary, Double superRate) {
        return calculateGrossIncomeForMonth(annualSalary) * superRate;
    }
}
