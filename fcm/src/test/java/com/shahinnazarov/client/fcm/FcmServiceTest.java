package com.shahinnazarov.client.fcm;

import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.MessageStatus;
import com.shahinnazarov.client.fcm.container.FcmMessage;
import com.shahinnazarov.client.fcm.container.FcmMessageType;
import com.shahinnazarov.client.fcm.container.Notification;
import com.shahinnazarov.client.fcm.service.FcmService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FcmServiceTest {

    @Mock
    private FcmService fcmService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setStatus(MessageStatus.SUCCESS);
        Mockito.doCallRealMethod().when(fcmService).init();
        Mockito.when(fcmService.send(ArgumentMatchers.any(FcmMessage.class)))
//                .thenCallRealMethod();
                .thenReturn(messageResponse);
    }

    @Test
    public void sendNotification() {
        fcmService.init();
        FcmMessage fcmMessage = new FcmMessage();
        fcmMessage.setReceiver("dkGsp8VCklI:APA91bHxPaSNUeXlXhHxqb7OLOpZXUpQm_YJ2djU2F1UmzR3f-JyZX5li1H9DQMexWTy4JxACxOCWRcBsypbIlfCvrwuuNTFCn-q1SM9ssC_7SuWYTqGLLPQQ368iji-lOhhgQpdOC9D_w-hqsA0OxM18aNnac_7kw");
        Notification notification = new Notification();
        notification.setTitle("Hi SHahin");
        notification.setBody("Just Test");
        fcmMessage.setNotification(notification);
        fcmMessage.setType(FcmMessageType.STANDARD_TYPE);
        MessageResponse send = fcmService.send(fcmMessage);
        Assert.assertEquals(send.getStatus(), MessageStatus.SUCCESS);
    }
}
