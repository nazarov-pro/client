package com.shahinnazarov.client.fcm.container;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationData implements Serializable {
    private static final long serialVersionUID = 3191045156773622666L;

    private String detail;
    private String title;
    private int requestType;
    private String data;
}
