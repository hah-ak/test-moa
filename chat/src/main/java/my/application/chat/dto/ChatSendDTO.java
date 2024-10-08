package my.application.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatSendDTO {
    private String sender;
    private String message;
}
