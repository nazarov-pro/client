package com.shahinnazarov.client.email.utils;

public final class MailConstants {
    private MailConstants() {
    }
    public static final String PROPERTIES_PATH = "mail.properties";
    public static final String PROPERTIES_PATH_GMAIL = "gmail.properties";
    public static final String PROPERTIES_PATH_YANDEX = "yandex.properties";
    public static final String K_AUTH = "mail.auth";
    public static final String K_SOCKET_PORT = "mail.socketFactory.port";
    public static final String K_SOCKET_CLASS = "mail.socketFactory.class";
    public static final String K_HOST = "mail.host";
    public static final String K_PORT = "mail.port";
    public static final String K_USER = "mail.user";
    public static final String K_PASSWORD = "mail.password";
    public static final String K_TLS_ENABLED = "mail.starttls.enable";
    public static final String K_PROTOCOL = "mail.transport.protocol";
}
