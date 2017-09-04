package com.eriktveitnes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The tax bracket cache is an in memory cache to store the Tax Bracket information for 2012-2013 financial year.
 * Future developments should look to move this information to a DB or config file if further years are to be added.
 */
public class TaxBracketCache {

	private static final List<TaxBracket> taxBrackets = new ArrayList<>();
	
	static {
		taxBrackets.add(new TaxBracket(0d, 0d, 0d, 18200d));
		taxBrackets.add(new TaxBracket(0d, 0.19d, 18200d, 37000d));
		taxBrackets.add(new TaxBracket(3572d, 0.325d, 37000d, 80000d));
		taxBrackets.add(new TaxBracket(17547d, 0.37d, 80000d, 180000d));
		taxBrackets.add(new TaxBracket(54547d, 0.45d, 180000d, Double.MAX_VALUE));
	}

	/**
	 * Get the tax bracket information for the annual salary provided.
	 * @param annualSalary the employees annual salary
	 * @return The tax bracket information or NoSuchElementException If the bracket can not be found
	 */
	public static TaxBracket getTaxBracket(double annualSalary) {
		
		for (TaxBracket taxBracket : taxBrackets) {
			if(annualSalary >= taxBracket.getLowRange() && annualSalary <= taxBracket.getHighRange()) {
				return taxBracket;
			}
		}
		
		throw new NoSuchElementException("Tax bracket not found for annualSalary provided.");
	}
	
}
