package com.eriktveitnes.util;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.eriktveitnes.util.SalaryCalculations.*;

public class SalaryCalculationTests {

    //Note: All data is assumed to be correct if it passes validation.. These tests only cover real possible situations (happy path):

    //
    //  Pay Period Tests
    //

    @Test
    public void calculatePayPeriodWithEasyDateTest(){
        assertThat(calculatePayPeriod(new DateTime(2015, 01, 01,0, 0).toDate()), is("01 January - 31 January"));
    }

    @Test
    public void calculatePayPeriodWithLeapYearTest(){
        assertThat(calculatePayPeriod(new DateTime(2016, 2, 15, 0, 0).toDate()), is("01 February - 29 February"));
    }

    //
    //  Gross Income Tests
    //

    @Test
    public void calculateGrossIncomeForMonthWithDivisiableNumberTest(){
        assertThat(calculateGrossIncomeForMonth(120d), closeTo(10d, 10d));
    }

    @Test
    public void calculateGrossIncomeForMonthWithZeroValueTest(){
        assertThat(calculateGrossIncomeForMonth(0d), closeTo(0d, 0d));
    }

    @Test
    public void calculateGrossIncomeForMonthWithDecimalValueTest(){
        assertThat(calculateGrossIncomeForMonth(6554.34d), closeTo(546.195d, 546.195d));
    }

    //
    // Income Tax Tests
    //

    @Test
    public void calculateIncomeTaxForMonthBracket0To18Test(){
        assertThat(calculateIncomeTaxForMonth(10000d), closeTo(0d, 0d));
    }

    @Test
    public void calculateIncomeTaxForMonthBracket18To37Test(){
        assertThat(calculateIncomeTaxForMonth(30000d), closeTo(186.83333333333334d, 186.83333333333334d));
    }

    @Test
    public void calculateIncomeTaxForMonthBracket37To80Test(){
        assertThat(calculateIncomeTaxForMonth(60050d), closeTo(921.9375d, 921.9375d));
    }

    @Test
    public void calculateIncomeTaxForMonthBracket80To180Test(){
        assertThat(calculateIncomeTaxForMonth(150000d), closeTo(3620.583333333333d, 3620.583333333333d));
    }

    @Test
    public void calculateIncomeTaxForMonthBracket180PlusTest(){
        assertThat(calculateIncomeTaxForMonth(250000d), closeTo(7170.583333333333d, 7170.583333333333d));
    }

    //
    //  Net  Income Tests
    //

    @Test
    public void calculateNetIncomeForMonthBracket0To18Test(){
        assertThat(calculateNetIncomeForMonth(10000d), closeTo(833.3333333333333d, 833.3333333333333d));
    }

    @Test
    public void calculateNetIncomeForMonthBracket18To37Test(){
        assertThat(calculateNetIncomeForMonth(30000d), closeTo(2313.166666666667d, 2313.166666666667d));
    }

    @Test
    public void calculateNetIncomeForMonthBracket37To80Test(){
        assertThat(calculateNetIncomeForMonth(60050d), closeTo(4082.229166666667d, 4082.229166666667d));
    }

    @Test
    public void calculateNetIncomeForMonthBracket80To180Test(){
        assertThat(calculateNetIncomeForMonth(150000d), closeTo(7449.50000000000d, 7449.50000000000d));
    }

    @Test
    public void calculateNetIncomeForMonthBracket180PlusTest(){
        assertThat(calculateNetIncomeForMonth(250000d), closeTo(12232.833333333336d, 12232.833333333336d));
    }

    //
    // Super Tests
    //

    @Test
    public void calculateSuperTest(){
        assertThat(calculateSuperForMonth(100000d, 0.1d), closeTo(833.3333333333333d, 833.3333333333333d));
    }
}
