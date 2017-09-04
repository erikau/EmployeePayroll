package com.eriktveitnes.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class EmployeeTests {

    @Test
    public void getEmployeeFullNameTest(){
        Employee employee = new Employee("John", "Smith", 100000d, 0.10d, new DateTime(2017, 01, 01, 00, 00).toDate());
        assertThat(employee.getEmployeeFullName(), is(equalTo("John Smith")));
    }

    @Test
    public void getEmployeeRemunerationSlipTest(){
        Employee employee = new Employee("David", "Rudd", 60050d, 0.09d, new DateTime(2017, 03, 01, 00, 00).toDate());
        assertThat(employee.getEmployeeRemunerationSlip(), is(equalTo("David Rudd,01 March - 31 March,5004,922,4082,450")));
    }
}
