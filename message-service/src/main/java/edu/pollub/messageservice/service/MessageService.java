package edu.pollub.messageservice.service;

import edu.pollub.messageservice.dto.AccountResponseDto;
import edu.pollub.messageservice.dto.MessageRequestDto;
import edu.pollub.messageservice.model.Message;
import edu.pollub.messageservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;
    private WebClient.Builder webClientBuilder;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message createMessage(MessageRequestDto messageRequestDto) {
        AccountResponseDto sender = webClientBuilder.build().get()
                .uri("http://account-service/api/users/"+messageRequestDto.getSenderId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Sender not found", null, null, null, null))
                )
                .bodyToMono(AccountResponseDto.class)
                .block();

        AccountResponseDto receiver = webClientBuilder.build().get()
                .uri("http://account-service/api/users/"+messageRequestDto.getReceiverId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new WebClientResponseException(HttpStatus.NOT_FOUND, "Receiver not found", null, null, null, null))
                )
                .bodyToMono(AccountResponseDto.class)
                .block();
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
