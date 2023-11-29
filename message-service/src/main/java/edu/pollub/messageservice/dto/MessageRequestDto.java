package edu.pollub.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {
    private String content;
    private Long senderId;
    private Long receiverId;
}
