package com.at.srk.rest.service;

import com.at.srk.rest.controller.dto.MessageInput;
import com.at.srk.rest.model.Message;
import com.at.srk.rest.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public Message create(MessageInput messageInput, String userId) {
        Message newMesage = new Message();
        newMesage.setContent(messageInput.content());
        newMesage.setUserId(userId);
        return messageRepository.save(newMesage);
    }

    public void deleteAll() {
        messageRepository.deleteAll();
    }
}
