package com.eriktveitnes.util;

import com.eriktveitnes.model.Employee;
import org.joda.time.DateTime;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvParserTests {

    @Test(expected = RuntimeException.class)
    public void parseFileNullFileInputTest(){
        CsvParser.parseFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseFileEmptyFileInputTest(){
        CsvParser.parseFile("");
    }

    @Test(expected = RuntimeException.class)
    public void parseFileWrongFileInputTest(){
        CsvParser.parseFile("/fakeFilePath/fakeFile.csv");
    }

    @Test()
    public void parseFileValidFileInputTest() throws URISyntaxException {

        URL resource = CsvParserTests.class.getResource("/input-test.csv");
        String testFilePath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
        List<Employee> employees = CsvParser.parseFile(testFilePath);
        assertThat(employees.size(), is(2));
        assertThat(employees , contains(allOf(hasProperty("firstName", is("David")),
                                              hasProperty("lastName", is("Rudd")),
                                              hasProperty("annualSalary", is(60050d)),
                                              hasProperty("superRate", is(0.09d)),
                                              hasProperty("paymentStartDate", is(new DateTime(2015, 3, 1, 0, 0).toDate()))), //Note date format is in US format.
                                        allOf(hasProperty("firstName", is("Ryan")),
                                              hasProperty("lastName", is("Chen")),
                                              hasProperty("annualSalary", is(120000d)),
                                              hasProperty("superRate", is(0.1d)),
                                              hasProperty("paymentStartDate", is(new DateTime(2015, 2, 1, 0, 0).toDate()))))); //Note date format is in US format.
    }

    //
    // Allow Line Tests
    //

    @Test
    public void allowLineInputAllFieldsTest(){
        assertThat(CsvParser.allowLine(new String[]{"John", "Smith", "60000", "0.09", "01/01/2017"}), is(true));
    }

    @Test
    public void allowLineNullLineTest(){
        assertThat( CsvParser.allowLine(null), is(false));
    }

    @Test
    public void allowLineEmptyLineTest(){
        assertThat(CsvParser.allowLine(new String[]{""}), is(false));
    }

    @Test
    public void allowLineInputMissingDateTest(){
        assertThat(CsvParser.allowLine(new String[]{"John", "Smith", "60000", "0.09"}), is(false));
    }

    @Test
    public void allowLineInputAllFieldsWithOneExtraTest(){
        assertThat(CsvParser.allowLine(new String[]{"John", "Smith", "60000", "0.09", "01/01/2017", "ExtraField"}), is(false));
    }

    //
    //  Validate First Name Tests
    //

    @Test
    public void validateFirstNameWithValidNameTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateFirstName("john", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateFirstNameWithValidNameWithNumbersTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateFirstName("1jo2hn3", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateFirstNameWithInValidNameEmptyStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateFirstName("", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("First name is not provided"));
    }

    @Test
    public void validateFirstNameWithInValidNameNullStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateFirstName(null, errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("First name is not provided"));
    }

    //
    //  Validate Last Name Tests
    //

    @Test
    public void validateLastNameWithValidNameTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateLastName("smith", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateLastNameWithValidNameWithNumbersTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateLastName("1sm2ith3", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateLastNameWithInValidNameEmptyStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateLastName("", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Last name is not provided"));
    }

    @Test
    public void validateLastNameWithInValidNameNullStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateLastName(null, errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Last name is not provided"));
    }


    //
    //  Validate Annual Salary Tests
    //

    @Test
    public void validateAnnualSalaryWithValidNumberTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary("100000", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateAnnualSalaryWithValidNumberAndDecimalPointTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary("100000.00", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateAnnualSalaryWithValidNumberAsZeroPointTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary("0", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateAnnualSalaryWithInValidValueEmptyStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary("", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Annual Salary is not provided"));
    }

    @Test
    public void validateAnnualSalaryWithInValidValueNullStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary(null, errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Annual Salary is not provided"));
    }

    @Test
    public void validateAnnualSalaryWithInValidNegativeNumberTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary("-100000", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Annual Salary must be a positive number"));
    }

    @Test
    public void validateAnnualSalaryWithInValidNumberAsStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateAnnualSalary("a100000", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Annual Salary is not a number"));
    }

    //
    //  Validate Super Rate Tests
    //

    @Test
    public void validateSuperRateWithValidNumberTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate(".10", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateSuperRateWithValidNumberAndDecimalPointTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("0.09", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateSuperRateWithValidNumberAsZeroPointTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("0", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateSuperRateWithValidNumberAsOnePointTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("1.0", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateSuperRateWithInValidValueEmptyStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Super Rate is not provided"));
    }

    @Test
    public void validateSuperRateWithInValidValueNullStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate(null, errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Super Rate is not provided"));
    }

    @Test
    public void validateSuperRateWithInValidNegativeNumberTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("-0.10", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Super Rate must be a positive number"));
    }

    @Test
    public void validateSuperRateWithInValidNumberGreaterThan1Test(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("1.1", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Super Rate must be a positive number between 0 and 1"));
    }

    @Test
    public void validateSuperRateWithInValidNumberAsStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateSuperRate("a100000", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Super Rate is not a number"));
    }


    //
    //  Validate Start Date Tests
    //

    @Test
    public void validateStartDateWithValidDateTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateStartDate("01/01/2015", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateStartDateWithValidDateInLeapYearPointTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateStartDate("29/02/2016", errors);
        assertThat(errors, is(empty()));
    }

    @Test
    public void validateStartDateWithInValidValueEmptyStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateStartDate("", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Start Date is not provided"));
    }

    @Test
    public void validateStartDateWithInValidValueNullStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateStartDate(null, errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Start Date is not provided"));
    }

    @Test
    public void validateStartDateWithInValidDateFormatLongMonthNullStringTest(){
        List<String> errors = new ArrayList<>();
        CsvParser.validateStartDate("01/MAR/2017", errors);
        assertThat(errors, is(not(empty())));
        assertThat(errors, containsInAnyOrder("Start Date is not recognised a date format"));
    }

}
