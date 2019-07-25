package com.shahinnazarov.client.common.container;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageResponse implements Serializable {
    private static final long serialVersionUID = 1330486713368858279L;

    private String id;
    private LocalDateTime executed;
    private MessageStatus status;
    private String errorCode;
    private String errorMessage;
}
