package com.github.bulkemailsender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


@Configuration
public class SslConfig {

    @Bean
    public SSLContext sslContext() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null; // Trust all issuers
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        // Trust all clients
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        // Trust all servers
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        return sslContext;
    }
}
