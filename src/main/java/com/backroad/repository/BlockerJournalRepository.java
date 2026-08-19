package com.backroad.repository;

import com.backroad.model.BlockerJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockerJournalRepository
        extends JpaRepository<BlockerJournal, Long> {
}