//package com.sr.datagen.jobs;
//
//import com.sr.datagen.exceptions.FileNotValidException;
//import com.sr.datagen.processors.CardGenerationProcessor;
//import com.sr.datagen.readers.ListTransactionReader;
//import com.sr.datagen.readers.TransactionReader;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.JobRepositoryTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBatchTest
//@EnableAutoConfiguration
//@ContextConfiguration(classes = {CardGenerationJob.class, TransactionReader.class, CardGenerationProcessor.class,
//        ListTransactionReader.class})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@TestPropertySource(properties = "spring.batch.job.name=cardJob")
//public class CardGenerationJobTest {
//
//    @Autowired
//    JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    JobRepositoryTestUtils jobRepositoryTestUtils;
//
//
//
//
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
//                .addString("fileOutputPath", "src/test/resources/output/test_cards.xml")
//                .addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//    }
//
//
//
//    @Test
//    public void givenJob_whenJobIsExecuted_OutputSuccess(){
//        JobParameters jobParameters = defaultJobParameters();
//        try{
//            jobLauncherTestUtils.launchJob(jobParameters);
//        } catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//
//        try {
//            FileSystemResource expectedOutput = new FileSystemResource("src/test/resources/output/test_cards.xml");
//            Assertions.assertTrue(expectedOutput.exists());
//            Assertions.assertTrue(expectedOutput.isReadable());
//            Assertions.assertTrue(expectedOutput.contentLength() > 0);
//        } catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
//    }
//
//}
