package com.shahinnazarov.client.fcm.container;

import lombok.Data;

import java.io.Serializable;

@Data
public class FcmNotification implements Serializable {
    private static final long serialVersionUID = 4933660965331728348L;

    private String to;
    private Notification notification;
    private NotificationData data;
}
