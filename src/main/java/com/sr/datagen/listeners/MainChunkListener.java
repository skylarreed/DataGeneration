package com.sr.datagen.listeners;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
@Slf4j(topic = "MainChunkListener")
public class MainChunkListener implements ChunkListener {
    private int count = 0;
    @Override
    public void beforeChunk(ChunkContext chunkContext) {
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        if(count % 5 == 0 && count != 0) {
            log.info("Writing: " + chunkContext.getStepContext().getStepName() + " with " + chunkContext.getStepContext().getStepExecution().getWriteCount() +
                    " records" + " for " + chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName());
        }
        if(count % 10 == 0 && count != 0) {
            log.info("Reading: " + chunkContext.getStepContext().getStepName() + " with " + chunkContext.getStepContext().getStepExecution().getReadCount() +
                    " records" + " for " + chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName());
        }
        count++;
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        log.error("Error in chunk: " + chunkContext.getStepContext().getStepName());
    }
}
