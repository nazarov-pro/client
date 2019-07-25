package com.shahinnazarov.client.lsimsms.service;

import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.MessageStatus;
import com.shahinnazarov.client.common.service.TransportableService;
import com.shahinnazarov.client.lsimsms.container.SmsMessage;
import com.shahinnazarov.client.lsimsms.utils.CustomHttpClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Properties;

import static com.shahinnazarov.client.lsimsms.utils.SmsConstants.*;

public class SmsService implements TransportableService<SmsMessage> {

    private HttpClient httpClient;

    private String password;
    private String login;
    private String sender;
    private String endpoint;

    /**
     * Initialize SMS Service
     */
    @PostConstruct
    public void init() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);
            assert resource != null;

            Properties properties = new Properties();
            properties.load(resource);

            this.password = properties.getProperty(K_PASSWORD);
            this.login = properties.getProperty(K_LOGIN);
            this.sender = properties.getProperty(K_SENDER);
            this.endpoint = properties.getProperty(K_URL);

            this.httpClient = CustomHttpClient.getHttpClient();

        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong when initializing sms client. Details: " + e.getMessage());
        }
    }


    /**
     * Send message
     * @param message
     * @return
     */
    @Override
    public MessageResponse send(SmsMessage message) {
        try {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setExecuted(LocalDateTime.now());
            String url = getUrl(message);
            HttpGet getRequest = new HttpGet(url);
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.isNull(P_ERROR_CODE)) {
                    messageResponse.setId(jsonObject.getBigDecimal(P_MESSAGE_ID).toString());
                    messageResponse.setStatus(MessageStatus.SUCCESS);
                } else {
                    messageResponse.setStatus(MessageStatus.NOT_VALID);
                    messageResponse.setErrorCode(String.valueOf(jsonObject.getInt(P_ERROR_CODE)));
                    messageResponse.setErrorMessage(jsonObject.getString(P_ERROR_MESSAGE));
                }
            } else {
                messageResponse.setStatus(MessageStatus.FAIL);
                messageResponse.setErrorCode(String.valueOf(response.getStatusLine().getStatusCode()));
                messageResponse.setErrorMessage("Something went wrong (check network or sms provider server).");
            }
            return messageResponse;
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong when sending sms. Details: " + e.getMessage());
        }
    }


    private String getUrl(SmsMessage smsMessage) throws UnsupportedEncodingException {
        String key = DigestUtils.md5Hex(
                String.format("%s%s%s%s%s",
                        password,
                        login,
                        smsMessage.getContent(),
                        smsMessage.getReceiver(),
                        sender
                ).getBytes("UTF-8")
        );

        return String.format("%s?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                endpoint,
                P_LOGIN, login,
                P_NUMBER, smsMessage.getReceiver(),
                P_TEXT, URLEncoder.encode(smsMessage.getContent(), "UTF-8"),
                P_SENDER, URLEncoder.encode(sender, "UTF-8"),
                P_KEY, key);
    }
}
