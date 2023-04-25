//package com.sr.datagen.jobs;
//
//import com.sr.datagen.processors.StateProcessor;
//import com.sr.datagen.processors.UserProcessor;
//import com.sr.datagen.readers.ListTransactionReader;
//import com.sr.datagen.readers.TransactionReader;
//import com.sr.datagen.utilities.StatePopulator;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.JobRepositoryTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBatchTest
//@EnableAutoConfiguration
//@ContextConfiguration(classes = {UserGenerationJob.class, TransactionReader.class, UserProcessor.class,
//        ListTransactionReader.class})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@TestPropertySource(properties = "spring.batch.job.name=usersJob")
//public class UserGenerationJobTest {
//
//    @Autowired
//    JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    JobRepositoryTestUtils jobRepositoryTestUtils;
//
//
//    @After
//    public void tearDown() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }
//
//    private JobParameters defaultJobParameters() {
//        return new JobParametersBuilder()
//                .addString("filePath" , "src/test/resources/input/test_transactions.csv")
//                .addString("fileOutputPath", "src/test/resources/output/test_users.xml")
//                .addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//    }
//
//
//
//    @Test
//    public void givenJob_whenJobIsExecuted_thenSuccess() throws Exception {
//        JobParameters jobParameters = defaultJobParameters();
//        jobLauncherTestUtils.launchJob(jobParameters);
//
//        FileSystemResource expectedOutput = new FileSystemResource("src/test/resources/output/test_users.xml");
//        Assertions.assertTrue(expectedOutput.exists());
//        Assertions.assertTrue(expectedOutput.isReadable());
//        Assertions.assertTrue(expectedOutput.contentLength() > 0);
//    }
//
//}
