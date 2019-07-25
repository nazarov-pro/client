package com.shahinnazarov.client.lsimsms;

import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.MessageStatus;
import com.shahinnazarov.client.lsimsms.container.SmsMessage;
import com.shahinnazarov.client.lsimsms.service.SmsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SmsServiceTest {

    @Mock
    private SmsService smsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setStatus(MessageStatus.SUCCESS);
        Mockito.doCallRealMethod().when(smsService).init();
        Mockito.when(smsService.send(ArgumentMatchers.any(SmsMessage.class)))
                //.thenCallRealMethod();
                .thenReturn(messageResponse);
    }

    @Test
    public void sendSms() {
        smsService.init();
        String message = "Hi we sent it for test";
        String phone = "994708900999";
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setContent(message);
        smsMessage.setReceiver(phone);
        MessageResponse send = smsService.send(smsMessage);
        Assert.assertEquals(send.getStatus(), MessageStatus.SUCCESS);
    }
}
