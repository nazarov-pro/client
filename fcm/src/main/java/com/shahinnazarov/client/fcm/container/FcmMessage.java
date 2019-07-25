package com.shahinnazarov.client.fcm.container;

import com.shahinnazarov.client.common.container.TransportableMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FcmMessage extends TransportableMessage implements Serializable {
    private static final long serialVersionUID = -4586925719860429969L;

    private Notification notification;
    private NotificationData data;
    private FcmMessageType type;
}
