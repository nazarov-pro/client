package com.shahinnazarov.client.common.service;

import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.TransportableMessage;

public interface TransportableService<T extends TransportableMessage> {
    MessageResponse send(T message);
}
