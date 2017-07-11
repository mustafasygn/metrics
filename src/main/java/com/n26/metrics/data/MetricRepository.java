package com.n26.metrics.data;

import com.n26.metrics.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by musta on 5.07.2017.
 */
@Component
public class MetricRepository {
	private static final Map<Long, ArrayList<Double>> ALL_TRANSACTIONS = new ConcurrentHashMap<>();
	private static final Long MILLIS_TO_SECOND = 1000L;

	public static DoubleSummaryStatistics getStatistics() {
		ArrayList<Double> allTransactionList = new ArrayList<>();
		ALL_TRANSACTIONS.values().forEach(list -> allTransactionList.addAll(list));
		DoubleSummaryStatistics dstats = allTransactionList.parallelStream().
				collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);

		return dstats;
	}

	public void validateBySecond(Long second, Long secondLimit) {
		if (second >= secondLimit)
			throw new IllegalArgumentException();
	}

	public void clearTransactions() {
		ALL_TRANSACTIONS.clear();
	}

	public static void shrinkTransactions( Long secondLimit) {
		System.out.print("size " + ALL_TRANSACTIONS.size());
		long secondValue60SecondsAgo = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - secondLimit + 1;
		ALL_TRANSACTIONS.remove(secondValue60SecondsAgo);
		System.out.println(" -> " + ALL_TRANSACTIONS.size());
	}

	public void addAmountTransaction(Transaction transaction, Long secondLimit) {
		Long transactionSecond = TimeUnit.MILLISECONDS.toSeconds(transaction.getTimestamp());
		Long second = (System.currentTimeMillis() - transaction.getTimestamp()) / MILLIS_TO_SECOND;
		validateBySecond(second, secondLimit);
		//		updateStatisticsByNewAmount(amount);
		ArrayList<Double> amountList = ALL_TRANSACTIONS.getOrDefault(second, new ArrayList<>());
		amountList.add(transaction.getAmount());
		ALL_TRANSACTIONS.put(transactionSecond, amountList);
	}

	//	public synchronized static void updateStatisticsByNewAmount(Double amount) {
	//		statistic.setCount(statistic.getCount() + 1L);
	//		statistic.setSum(statistic.getSum() + amount);
	//		statistic.setAvg(statistic.getSum() / statistic.getCount());
	//		if (statistic.getMin() > amount) {
	//			statistic.setMin(amount);
	//		}
	//		if (statistic.getMax() < amount) {
	//			statistic.setMax(amount);
	//		}
	//	}

	//	public synchronized static void updateStatisticsAfterOneSecond() {
	//		ArrayList<Double> lastSecondAmounts = ALL_TRANSACTIONS.get(SECOND_LIMIT - 1);
	//		statistic.setCount(statistic.getCount() - lastSecondAmounts.size());
	//		Double sumLastSecondAmounts = lastSecondAmounts.stream().mapToDouble(Double::doubleValue).sum();
	//		statistic.setSum(statistic.getSum() - sumLastSecondAmounts);
	//		statistic.setAvg(statistic.getSum() / statistic.getCount());
	//		statistic.setMin(getMinOfAllAmounts());
	//		statistic.setMax(getMaxOfAllAmounts());
	//	}

	//	public static Double getMinOfAllAmounts() {
	//		Double min = 0D;
	//		//TODO: figure out clever way of obtaining min
	//		return min;
	//	}
	//
	//	public static Double getMaxOfAllAmounts() {
	//		Double max = 0D;
	//		//TODO: figure out clever way of obtaining max
	//		return max;
	//	}

}
