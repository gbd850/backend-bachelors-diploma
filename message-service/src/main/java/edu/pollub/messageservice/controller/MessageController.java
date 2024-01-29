package edu.pollub.messageservice.controller;

import edu.pollub.messageservice.dto.MessageRequestDto;
import edu.pollub.messageservice.model.Message;
import edu.pollub.messageservice.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody MessageRequestDto messageRequestDto) {
        return new ResponseEntity<>(messageService.createMessage(messageRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.getMessageById(id), HttpStatus.OK);
    }

    @GetMapping("chat/{id}")
    public ResponseEntity<List<Message>> getMessagesBySender(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.getMessagesBySenderOrReceiver(id), HttpStatus.OK);
    }
}
