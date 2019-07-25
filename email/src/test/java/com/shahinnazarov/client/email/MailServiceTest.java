package com.shahinnazarov.client.email;

import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.MessageStatus;
import com.shahinnazarov.client.email.container.MailMessage;
import com.shahinnazarov.client.email.service.MailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MailServiceTest {

    @Mock
    private MailService mailService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setStatus(MessageStatus.SUCCESS);
        Mockito.doCallRealMethod().when(mailService).init();
        Mockito.when(mailService.send(ArgumentMatchers.any(MailMessage.class))).thenCallRealMethod();
//                .thenReturn(messageResponse);
    }

    @Test
    public void sendMail() {
        mailService.init();
        MailMessage mailMessage = new MailMessage();
        mailMessage.setReceiver("me@shahinnazarov.com");
        mailMessage.setSubject("Test");
        mailMessage.setContent("Test Content <a href='http://google.com' > Goggle it</a>");
//        mailMessage.setAttachments(Collections.singletonList("C:\\Users\\s0552\\Desktop\\celkos-sql-for-smarties-2005.pdf"));
        MessageResponse send = mailService.send(mailMessage);
        Assert.assertEquals(send.getStatus(), MessageStatus.SUCCESS);
    }
}
