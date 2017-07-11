package com.n26.metrics;

import com.n26.metrics.controller.MetricController;
import com.n26.metrics.data.MetricRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by musta on 2.07.2017.
 */
@EnableAutoConfiguration
@ComponentScan
@EnableAsync
@EnableScheduling
public class AppConfig {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

	@Scheduled(fixedDelay = 1000)
	public void shrinkTransactions() {
		MetricController.shrinkTransactions();
	}

}
