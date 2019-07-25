package com.shahinnazarov.client.fcm.container;

import lombok.Data;

import java.io.Serializable;

@Data
public class Notification implements Serializable {
    private static final long serialVersionUID = 4716883379613049967L;

    private String title;
    private String body;
}
