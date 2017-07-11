/**
 * Created by musta on 11.07.2017.
 */

import com.n26.metrics.data.MetricRepository;
import com.n26.metrics.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class MetricTest {
	@Autowired
	private MetricRepository metricRepository;

	private Long testTime;
	private Long testTimeOneSecondLater;
	private Transaction transaction1;
	private Transaction transaction2;
	private Long secondLimit;

	@Before
	public void setUp() throws Exception {
		metricRepository = new MetricRepository();
		metricRepository.clearTransactions();
		testTime = System.currentTimeMillis();
		Thread.sleep(1000);
		testTimeOneSecondLater = System.currentTimeMillis();
		transaction1 = new Transaction(10D, testTime);
		transaction2 = new Transaction(20D, testTimeOneSecondLater);
		secondLimit = 5L;
	}

	@Test
	public void statisticCountWhenAmountAdded() throws Exception {
		metricRepository.addAmountTransaction(transaction1, secondLimit);
		metricRepository.addAmountTransaction(transaction2, secondLimit);

		assertEquals(2, MetricRepository.getStatistics().getCount());
	}

	@Test
	public void shrinkingWhenAmountAdded() throws Exception {
		metricRepository.addAmountTransaction(transaction1, secondLimit);
		metricRepository.addAmountTransaction(transaction2, secondLimit);
		for (int i = 1; i < secondLimit; i++) {
			Thread.sleep(1000L);
			MetricRepository.shrinkTransactions(secondLimit);
		}

		assertEquals(0, MetricRepository.getStatistics().getCount());
	}
}
