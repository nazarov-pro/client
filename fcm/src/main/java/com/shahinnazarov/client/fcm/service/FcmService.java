package com.shahinnazarov.client.fcm.service;

import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.MessageStatus;
import com.shahinnazarov.client.common.service.TransportableService;
import com.shahinnazarov.client.fcm.container.FcmMessage;
import com.shahinnazarov.client.fcm.utils.CustomHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import static com.shahinnazarov.client.fcm.utils.FcmConstants.*;

public class FcmService implements TransportableService<FcmMessage> {

    private String key;
    private String endpoint;
    private HttpClient httpClient;

    /**
     * Initialize FCM client and properties
     */
    @PostConstruct
    public void init() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);
            assert resource != null;

            Properties properties = new Properties();
            properties.load(resource);

            this.endpoint = properties.getProperty(K_SERVER_ENDPOINT);
            this.key = properties.getProperty(K_SERVER_KEY);

            this.httpClient = CustomHttpClient.getHttpClient();

        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong when initializing sms client. Details: " + e.getMessage());
        }
    }

    /**
     * Send notification to FCM USER
     * @param message
     * @return
     */
    @Override
    public MessageResponse send(FcmMessage message) {
        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.addHeader("Authorization", "key=" + key);
        httpPost.setHeader("Content-Type", "application/json; UTF-8");
        httpPost.setHeader("Accept", "application/json; UTF-8");
        JSONObject object = new JSONObject(message);
        object.put("to", message.getReceiver());
        object.remove("receiver");
        object.remove("content");
        object.remove("type");

        switch (message.getType()) {
            case DATA_TYPE:
                object.remove("notification");
                break;
            case STANDARD_TYPE:
                object.remove("data");
                break;
            default:
                object.remove("data");
        }
        StringEntity requestEntity = new StringEntity(
                object.toString(),
                "UTF-8");

        httpPost.setEntity(requestEntity);

        try {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setExecuted(LocalDateTime.now());
            HttpResponse response = httpClient.execute(httpPost);
            String json = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(json);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (jsonObject.getInt(P_SUCCESS) == 1) {
                    messageResponse.setStatus(MessageStatus.SUCCESS);
                    String id = jsonObject
                            .getJSONArray(P_RESULTS)
                            .getJSONObject(0)
                            .getString(P_MESSAGE_ID);
                    messageResponse.setId(id);
                } else if (jsonObject.getInt(P_FAILURE) == 1) {
                    messageResponse.setStatus(MessageStatus.NOT_VALID);
                    String errorMessage = jsonObject
                            .getJSONArray(P_RESULTS)
                            .getJSONObject(0)
                            .getString(P_ERROR);
                    messageResponse.setErrorMessage(errorMessage);
                    messageResponse.setErrorCode("1");
                } else {
                    messageResponse.setStatus(MessageStatus.ERROR);
                    messageResponse.setErrorCode("0");
                    messageResponse.setErrorMessage(json);
                }
            } else {
                messageResponse.setStatus(MessageStatus.FAIL);
                messageResponse.setErrorCode(String.valueOf(response.getStatusLine().getStatusCode()));
                messageResponse.setErrorMessage("Something went wrong (check network or provider's server).");
            }
            return messageResponse;
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong when initializing sms client. Details: " + e.getMessage());
        }
    }
}
