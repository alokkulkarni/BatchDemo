package com.example.batchSample.jobs;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alokkulkarni on 30/09/16.
 */
@Configuration
public class BatchConfiguration {

    @Autowired
    public BatchConfiguration(JobRepository jobrepository, PlatformTransactionManager platformTransactionManager) {
        this.jobrepository = jobrepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    private JobRepository jobrepository;

    private PlatformTransactionManager platformTransactionManager;

    @Bean
    JobBuilderFactory jobBuilderFactory() {
        return new JobBuilderFactory(jobrepository);
    }

    @Bean
    StepBuilderFactory stepBuilderFactory() {
        return new StepBuilderFactory(jobrepository,platformTransactionManager);
    }



    @Bean
    public Step commandStep() {
        return stepBuilderFactory().get("commandStep")
                .allowStartIfComplete(true)
                .transactionManager(platformTransactionManager)
                .tasklet((contribution, chunkContext) -> {
                    SystemCommandTasklet systemCommandTasklet = new SystemCommandTasklet();
                    systemCommandTasklet.setCommand("cp ~/Documents/myImage.jpg ~/Downloads/");
                    systemCommandTasklet.setTimeout(10000);
                    systemCommandTasklet.execute(contribution,chunkContext);
                    throw new IllegalStateException();
                })
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory().get("startStep")
                .allowStartIfComplete(true)
                .transactionManager(platformTransactionManager)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the start tasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory().get("evenStep")
                .allowStartIfComplete(true)
                .transactionManager(platformTransactionManager)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the even tasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory().get("oddStep")
                .allowStartIfComplete(true)
                .transactionManager(platformTransactionManager)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the odd tasklet");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory().get("job")
                .repository(jobrepository)
                .incrementer(new RunIdIncrementer())
                .listener(listenerSupport())
                .start(commandStep())
                .next(startStep())
                .next(decider())
                .from(decider()).on("ODD").to(oddStep())
                .from(decider()).on("EVEN").to(evenStep())
                .from(oddStep()).on("*").to(decider())
                .end()
                .build();
    }

    @Bean
    JobListner listenerSupport() {
        return new JobListner();
    }

//    @Bean
//    public Flow flow() {
//        FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("flow");
//        flowBuilder.start(job())
//
//    }
//
//
//    public FlowJob flowJob() {
//        flowJobBuilder.start()
//    }


    public static class OddDecider implements JobExecutionDecider {

        private int count = 0;

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            count++;

            if(count % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            }
            else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }


    public static class JobListner implements JobExecutionListener {

        @Autowired
        @Qualifier("jobChannel")
        MessageChannel channel;


        @Override
        public void beforeJob(JobExecution jobExecution) {
            System.out.println("before job execution");
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            System.out.println("After job execution");

            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("serviceName", "myService");
            headerMap.put("jobName", "tasklet");

            MessageHeaders headers = new MessageHeaders(headerMap);



            MessagingTemplate messagingTemplate = new MessagingTemplate();
            messagingTemplate.send(channel, new GenericMessage<>("my Name is Alok", headers));
        }
    }
}
