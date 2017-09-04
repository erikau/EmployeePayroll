package com.eriktveitnes.employeepayroll;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class EmployeePayrollTests {

    @Test
    public void getFilePathOrDefaultWithNullArgsUsesDefaultValueTest() {
        assertThat(EmployeePayroll.getFilePathOrDefault(null),  containsString(EmployeePayroll.DEFAULT_FILE_NAME));
    }

    @Test
    public void getFilePathOrDefaultWithEmptyArgsUsesDefaultValueTest() {
        assertThat(EmployeePayroll.getFilePathOrDefault(new String[]{}),  containsString(EmployeePayroll.DEFAULT_FILE_NAME));
    }

    @Test
    public void getFilePathOrDefaultWithArgs0AsNullUsesDefaultValueTest() {
        assertThat(EmployeePayroll.getFilePathOrDefault(new String[]{null}),  containsString(EmployeePayroll.DEFAULT_FILE_NAME));
    }

    @Test
    public void getFilePathOrDefaultWithArgs0AsEmptyUsesDefaultValueTest() {
        assertThat(EmployeePayroll.getFilePathOrDefault(new String[]{""}),  containsString(EmployeePayroll.DEFAULT_FILE_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void getFilePathOrDefaultWithMissingFileNameTest() {
        EmployeePayroll.getFilePathOrDefault(new String[]{"fakeFileName.fake"});
    }

    @Test
    public void getFilePathOrDefaultWithRealFileTest() {
        assertThat(EmployeePayroll.getFilePathOrDefault(new String[]{"input.csv"}),  containsString("input.csv"));
    }


    @Test(expected = RuntimeException.class)
    public void mainWithMissingFileTest() {
        EmployeePayroll.main(new String[]{"MissingFile"});
    }

    @Test()
    public void mainWithInputFileTest() {
        //We expect no exceptions to be thrown.
        EmployeePayroll.main(new String[]{"input.csv"});
    }
}
