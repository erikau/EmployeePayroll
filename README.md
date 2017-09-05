# EmployeePayroll

A simple Employee Payroll system that parses a given CSV file, loads the records as Employee objects and then calculates there payment summary which are displayed in the output.
Any errors in the CSV file, will cause that line to be ignored and any errors associated will also be displayed.


### The output of the file is in the following order:
    Employee Name,Pay Period, Gross Income, Income Tax, Net Income, Super Payment

### Sample Output:
    C:\Users\Erik Tveitnes\IdeaProjects\EmployeePayroll>gradlew.bat run
    :employee-payroll:compileJava UP-TO-DATE
    :employee-payroll:processResources
    :employee-payroll:classes
    :employee-payroll:run
    2017-09-05 09:10:04 INFO  EmployeePayroll:21 - ---------------------------------------------------------------
    2017-09-05 09:10:04 INFO  EmployeePayroll:24 - David Rudd,01 March - 31 March,5004,922,4082,450
    2017-09-05 09:10:04 INFO  EmployeePayroll:24 - Ryan Chen,01 February - 28 February,10000,2696,7304,1000
    2017-09-05 09:10:04 INFO  EmployeePayroll:26 - ---------------------------------------------------------------


### CSV input file details:
    The CSV input file must be a comma delimited file where each line is made up of 5 records in the following format:
        firstName,lastName,annualSalary,superRate,startDate

    Where
        firstName    = The first name of the employee    - Text input
        lastName     = The last name of the employee     - Text input
        annualSalary = The annual salary of the employee - Number input, must be a positive number
        superRate    = The super rate of the employee    - Number input, must be a positive number between 0 and 1 inclusive
        startDate    = The start date of the payment     - Text input, must be in the format dd/MM/yyyy


### Calculation Formula:
    Employee Name = First name + Last name
    Pay Period    = Using the Start Date, show the boundaries of the month. E.g. 01 March - 31 March
    Gross Income  = Annual salary / 12 months
    Income Tax    = Based on the tax table provide below
    Net Income    = Gross income - Income tax
    Super Payment = Gross income x Super rate

#### Tax Table 2012-2013:
    0 - $18,200 Nil
    $18,201 - $37,000 19c for each $1 over $18,200
    $37,001 - $80,000 $3,572 plus 32.5c for each $1 over $37,000
    $80,001 - $180,000 $17,547 plus 37c for each $1 over $80,000
    $180,001 and over $54,547 plus 45c for each $1 over $180,000


### Gradle Tasks:
    This project has been built using Gradle. For more information on gradle see: https://gradle.org/

    To build the application:
        gradle build

    To run the application with the default input file input.csv in /resources:
        gradle run

    To Run the application with your own file:
        gradle run -PappArgs="['YOUR_FILE_NAME']"
    where YOUR_FILE_NAME is the csv file name including extension that you want to run.  E.g. input.csv


### Assumptions:
    1.  Any malformed data in the csv file will be ignored.  The line in question will be displayed to the output along with the error(s) that caused it to be ignored.
    2.  Only comma delimited csv is supported.
    3.  Rounding occurs only at the time of display.  All calculations are performed raw, then rounding is performed when displaying the results.
    4.  Tax Bracket information is from the 2012-2013 financial year and is used for calculating any start date.  No year specific information is available.


Created by:
    Erik Tveitnes - 05/09/2017
