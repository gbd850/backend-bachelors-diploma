package edu.pollub.messageservice.service;

import edu.pollub.messageservice.dto.MessageRequestDto;
import edu.pollub.messageservice.model.Message;
import edu.pollub.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message createMessage(MessageRequestDto messageRequestDto) {
        Message message = Message.builder()
                .content(messageRequestDto.getContent())
                .senderId(messageRequestDto.getSenderId())
                .receiverId(messageRequestDto.getReceiverId())
                .sentAt(new Date())
                .build();
        messageRepository.save(message);
        return message;
    }

    public Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        return message.get();
    }

    public List<Message> getMessagesBySenderOrReceiver(Long id) {
        List<Message> messages = messageRepository.findBySenderIdOrReceiverId(id, id);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No messages found for this user id");
        }
        return messages;
    }
}
