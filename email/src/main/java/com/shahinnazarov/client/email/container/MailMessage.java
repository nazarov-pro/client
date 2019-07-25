package com.shahinnazarov.client.email.container;

import com.shahinnazarov.client.common.container.TransportableMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MailMessage extends TransportableMessage implements Serializable {
    private static final long serialVersionUID = -8048567162647248359L;

    private String subject;
    private List<String> attachments = Collections.emptyList();
}
