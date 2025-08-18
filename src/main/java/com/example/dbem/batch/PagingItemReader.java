package com.example.dbem.batch;

import com.example.dbem.entity.SendEmail;
import com.example.dbem.repository.SendEmailRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public class PagingItemReader implements ItemReader<SendEmail> {
    private final SendEmailRepository sendEmailRepository;
    private final boolean verification;
    private final LocalDateTime threshold;

    private final int PAGE_SIZE = 1000;

    private int page = 0;
    private int index = 0;
    private Page<SendEmail> currentBatch;

    public PagingItemReader(SendEmailRepository repository, boolean verification, LocalDateTime threshold) {
        this.sendEmailRepository = repository;
        this.verification = verification;
        this.threshold = threshold;
    }

    @Override
    public SendEmail read() {
        if (currentBatch == null || index >= currentBatch.getContent().size()) {
            currentBatch = this.sendEmailRepository.findByVerificationAndCreatedAtBefore(
                    this.verification,
                    this.threshold,
                    PageRequest.of(page++, PAGE_SIZE)
            );
            index = 0;
        }
        if (currentBatch.isEmpty()) return null;
        return currentBatch.getContent().get(index++);
    }
}
