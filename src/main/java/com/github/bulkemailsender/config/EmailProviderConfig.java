package com.github.bulkemailsender.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Properties;

@Configuration
public class EmailProviderConfig {

    @Value("${email.provider.properties.mail.smtp.host}")
    private String host;

    @Value("${email.provider.properties.mail.smtp.port}")
    private int port;

    @Value("${email.provider.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${email.provider.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;

    @Value("${email.provider.auth.username}")
    private String username;

    @Value("${email.provider.auth.password}")
    private String password;

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));
        properties.put("mail.smtp.auth", String.valueOf(auth));
        properties.put("mail.smtp.starttls.enable", String.valueOf(starttlsEnable));
        return properties;
    }

    public Auth getAuth() {
        return new Auth(username, password);
    }

    public static class Auth {
        private final String username;
        private final String password;

        public Auth(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
