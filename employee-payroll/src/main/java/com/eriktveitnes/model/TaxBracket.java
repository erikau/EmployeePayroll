package com.eriktveitnes.model;

public class TaxBracket {

	private Double lumpSum;
	private Double taxPercentage;
	private Double lowRange;
	private Double highRange;
	
	public TaxBracket(Double lumpSum, Double taxPercentage, Double lowRange, Double highRange) {
		this.lumpSum = lumpSum;
		this.taxPercentage = taxPercentage;
		this.lowRange = lowRange;
		this.highRange = highRange;
	}

	public Double getLumpSum() {
		return lumpSum;
	}

	public void setLumpSum(Double lumpSum) {
		this.lumpSum = lumpSum;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Double getLowRange() {
		return lowRange;
	}
	public void setLowRange(Double lowRange) {
		this.lowRange = lowRange;
	}
	public Double getHighRange() {
		return highRange;
	}
	public void setHighRange(Double highRange) {
		this.highRange = highRange;
	}
	
	
	
}
