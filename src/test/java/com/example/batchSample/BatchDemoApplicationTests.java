package com.example.batchSample;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BatchDemoApplication.class)
@AutoConfigureTestDatabase
public class BatchDemoApplicationTests {


	JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	@Qualifier("job")
	Job batchConfiguration;

	@Before
	public void setupTest() {

	}

	@After
	public void afterTests() {

	}


	@Test
	public void contextLoads() throws Exception {
		jobLauncherTestUtils.setJob(batchConfiguration);
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

}
