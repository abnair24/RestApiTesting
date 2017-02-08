package com.util;


import org.apache.http.HttpResponse;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpParams;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class MySSLSocketFactory extends SSLSocketFactory {

    SSLContext sslContext = SSLContext.getInstance("TLS");

    public MySSLSocketFactory(String algorithm , KeyStore keyStore, String keyPassword, KeyStore truststore, SecureRandom random, TrustStrategy trustStrategy, X509HostnameVerifier hostnameVerifier) throws UnrecoverableKeyException, NoSuchAlgorithmException,KeyStoreException,KeyManagementException {
        super(algorithm,keyStore,keyPassword,truststore,random,trustStrategy,hostnameVerifier);
        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore,keyPassword!= null ? keyPassword.toCharArray() : null);

        sslContext.init(keyManagerFactory.getKeyManagers(),new TrustManager[]{tm}, null);
    }

    public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,KeyStoreException,UnrecoverableKeyException {
        super(truststore);

        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[] {tm},null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket,host,port,autoClose);
    }

    @Override
    public Socket createSocket(HttpParams context) throws  IOException {
        SSLSocket socket = (SSLSocket) sslContext.getSocketFactory().createSocket();
        this.prepareSocket(socket);
        return socket;
    }

    @Override
    public boolean isSecure(Socket socket) {
        return true;
    }

}
