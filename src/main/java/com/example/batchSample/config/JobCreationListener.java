//package com.example.batchSample.config;
//
//import com.example.batchSample.utils.CommonJobFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.configuration.DuplicateJobException;
//import org.springframework.batch.core.configuration.JobRegistry;
//import org.springframework.batch.core.launch.NoSuchJobException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import java.util.Map;
//
///**
// * Created by alokkulkarni on 25/03/17.
// */
//public class JobCreationListener implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final Logger log = LoggerFactory.getLogger(JobCreationListener.class);
//
//    private final ApplicationContext applicationContext;
//    private final JobRegistry jobRegistry;
//
//    public JobCreationListener(ApplicationContext applicationContext, JobRegistry jobRegistry) {
//        this.applicationContext = applicationContext;
//        this.jobRegistry = jobRegistry;
//    }
//
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        log.info("context refreshed event ********************************************************************");
//        final Map<String, Job> jobs = applicationContext.getBeansOfType(Job.class);
//        log.info("Jobs :- " + jobs.values());
//        if (jobs != null) {
//            for (final Map.Entry<String, Job> jobEntry : jobs.entrySet()) {
//                final Job job = jobEntry.getValue();
//                final String jobName = job.getName();
//                final CommonJobFactory commonJobFactory = new CommonJobFactory(job, jobName);
//                try {
//                    jobRegistry.register(commonJobFactory);
//                    try {
//                        log.info(String.valueOf(jobRegistry.getJob(jobName)));
//                    } catch (NoSuchJobException e) {
//                        e.printStackTrace();
//                    }
//                } catch (final DuplicateJobException e) {
//                    log.error("Job with name: " + jobName + " is already registered!");
//                }
//            }
//        }
//    }
//}
