package com.n26.metrics.controller;

import com.n26.metrics.data.MetricRepository;
import com.n26.metrics.model.Statistic;
import com.n26.metrics.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.TimeUnit;

/**
 * Created by musta on 3.07.2017.
 */
@RestController
public class MetricController {
	private static final Long SECOND_LIMIT = 60L;

	@Autowired
	private MetricRepository metricRepository;

	@RequestMapping("/statistics")
	public Statistic getStatistics() {
		DoubleSummaryStatistics dstats = metricRepository.getStatistics();
		Statistic statistic = new Statistic(dstats);

		return statistic;
	}

	@PostMapping("/transaction")
	public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
		try {
			metricRepository.addAmountTransaction(transaction, SECOND_LIMIT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	public static void shrinkTransactions() {
		MetricRepository.shrinkTransactions(SECOND_LIMIT);
	}
}
