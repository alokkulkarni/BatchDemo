package com.example.batchSample.config;

import org.springframework.batch.admin.service.SimpleJobServiceFactoryBean;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.scope.JobScope;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by alokkulkarni on 09/03/17.
 */
@Configuration
public class JobConfiguration {


    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(final JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
//        jobRegistryBeanPostProcessor.setGroupName("PBE");
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public SimpleJobServiceFactoryBean simpleJobServiceFactoryBean(DataSource dataSource,
                                                                   JobRepositoryFactoryBean repositoryFactoryBean) throws Exception {
        SimpleJobServiceFactoryBean factoryBean = new SimpleJobServiceFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJobRepository(repositoryFactoryBean.getObject());
        factoryBean.setJobLocator(new MapJobRegistry());
        factoryBean.setJobLauncher(new SimpleJobLauncher());
        return factoryBean;
    }

    @Bean
    public JobExplorerFactoryBean jobExplorerFactoryBean(DataSource dataSource) {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource);
        return jobExplorerFactoryBean;
    }

    @Bean
    @Primary
    public JobRepositoryFactoryBean jobRepositoryFactoryBean(DataSource dataSource, PlatformTransactionManager transactionManager) {
        JobRepositoryFactoryBean repositoryFactoryBean = new JobRepositoryFactoryBean();
        repositoryFactoryBean.setDataSource(dataSource);
        repositoryFactoryBean.setTransactionManager(transactionManager);
        return repositoryFactoryBean;
    }

    @Bean
    public StepScope stepScope() {
        StepScope stepScope = new StepScope();
        stepScope.setAutoProxy(true);
        stepScope.setProxyTargetClass(true);
        return stepScope;
    }

    @Bean
    public JobScope jobScope() {
        JobScope jobScope = new JobScope();
        jobScope.setAutoProxy(true);
        jobScope.setProxyTargetClass(true);
        return jobScope;
    }
}
