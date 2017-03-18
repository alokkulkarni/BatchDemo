package com.example.batchSample.config;

import org.springframework.batch.admin.service.SimpleJobServiceFactoryBean;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
    public SimpleJobServiceFactoryBean simpleJobServiceFactoryBean(DataSource dataSource,
                                                                   JobRepositoryFactoryBean repositoryFactoryBean) throws Exception {
        SimpleJobServiceFactoryBean factoryBean = new SimpleJobServiceFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJobRepository(repositoryFactoryBean.getObject());
        factoryBean.setJobLocator(new MapJobRegistry());
        factoryBean.setJobLauncher(new SimpleJobLauncher());
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }

    @Bean
    public JobExplorerFactoryBean jobExplorerFactoryBean(DataSource dataSource) {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource);
        return jobExplorerFactoryBean;
    }

    @Configuration
    @ConditionalOnExpression("#{!'${spring.datasource.url:}'.startsWith('jdbc:h2:tcp://localhost:') || " +
            "('${spring.datasource.url:}'.startsWith('jdbc:h2:tcp://localhost:') &&" +
            "'${spring.dataflow.embedded.database.enabled}'.equals('false'))}")
    public static class NoH2ServerConfiguration {

        @Bean
        @Primary
        public JobRepositoryFactoryBean jobRepositoryFactoryBean(DataSource dataSource, PlatformTransactionManager transactionManager) {
            JobRepositoryFactoryBean repositoryFactoryBean = new JobRepositoryFactoryBean();
            repositoryFactoryBean.setDataSource(dataSource);
            repositoryFactoryBean.setTransactionManager(transactionManager);
            return repositoryFactoryBean;
        }
    }
}
