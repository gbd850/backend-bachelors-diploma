package edu.pollub.messageservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String content;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id",
            referencedColumnName = "id")
    private User sender;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id",
            referencedColumnName = "id")
    private User receiver;
    private Date sentAt;
}
