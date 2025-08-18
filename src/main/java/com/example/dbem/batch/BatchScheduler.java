package com.example.dbem.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job emailVerificationCleanupJob;

//    @Scheduled(cron = "*/10 * * * * ?") // 10초마다 실행
//    @Scheduled(cron = "0 0 */1 * * ?") // 1시간마다 실행
//    @Scheduled(cron = "0 0,30 * * * ?") // 30분마다 실행
    @Scheduled(cron = "0 0 3 0 * ?") // 매일 새벽 3시 실행
    public void runCleanupJob() throws Exception {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(emailVerificationCleanupJob, params);
        } catch (Exception e) {
            log.error("***** Email cleanup job failed *****\n", e);
        }
    }
}
