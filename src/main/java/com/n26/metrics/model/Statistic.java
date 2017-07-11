package com.n26.metrics.model;

import java.util.DoubleSummaryStatistics;

/**
 * Created by musta on 5.07.2017.
 */
public class Statistic {
	private Long count;
	private Double sum;
	private Double avg;
	private Double max;
	private Double min;

	public Statistic(Long count, Double sum, Double avg, Double max, Double min) {
		this.count = count;
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
	}

	public Statistic(DoubleSummaryStatistics dstats) {
		this.count = dstats.getCount();
		this.sum = dstats.getSum();
		this.avg = dstats.getAverage();
		this.max = dstats.getMax();
		this.min = dstats.getMin();
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}
}
