package com.at.srk.rest.repository;

import com.at.srk.rest.model.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
