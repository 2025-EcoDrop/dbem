package com.example.dbem.batch;

import com.example.dbem.entity.SendEmail;
import com.example.dbem.repository.SendEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Configuration
public class SendEmailBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final SendEmailRepository sendEmailRepository;

    // 전체 Job
    @Bean
    public Job emailVerificationCleanupJob() {
        return new JobBuilder("emailVerificationCleanupJob", this.jobRepository)
                .start(deleteVerifiedStep())
                .next(deleteUnverifiedStep())
                .build();
    }

    // Step 1: 인증 완료 10일 지난 데이터 삭제
    @Bean
    public Step deleteVerifiedStep() {
        return new StepBuilder("deleteVerifiedStep", this.jobRepository)
                .<SendEmail, SendEmail>chunk(1000, this.transactionManager)
                .reader(verifiedReader())
                .writer(deleteWriter())
                .build();
    }

    // Step 2: 인증 미완료 1시간 지난 데이터 삭제
    @Bean
    public Step deleteUnverifiedStep() {
        return new StepBuilder("deleteUnverifiedStep", this.jobRepository)
                .<SendEmail, SendEmail>chunk(1000, this.transactionManager)
                .reader(unverifiedReader())
                .writer(deleteWriter())
                .build();
    }

    // Reader: 인증 완료
    @Bean
    public ItemReader<SendEmail> verifiedReader() {
        return new PagingItemReader(this.sendEmailRepository, true, LocalDateTime.now().minusDays(10));
    }

    // Reader: 인증 미완료
    @Bean
    public ItemReader<SendEmail> unverifiedReader() {
        return new PagingItemReader(this.sendEmailRepository, false, LocalDateTime.now().minusHours(1));
    }

    // Writer: 삭제
    @Bean
    public ItemWriter<SendEmail> deleteWriter() {
        return items -> this.sendEmailRepository.deleteAllInBatch(
                StreamSupport.stream(items.spliterator(), false)
                        .collect(Collectors.toList())
        );
    }
}
