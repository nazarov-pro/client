package com.shahinnazarov.client.common.container;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransportableMessage implements Serializable {
    private static final long serialVersionUID = 6201923689306456462L;

    private String content;
    private String receiver;
}
