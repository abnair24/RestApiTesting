package com.actions;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.CertificateAuthSettings;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.util.MySSLSocketFactory;
import org.apache.commons.io.FileUtils;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;


public abstract class Actions {

    private RequestSpecification requestSpecification = null;
    private KeyStore keyStore = null;
    private SSLConfig sslConfig = null;
    private CertificateAuthSettings settings = null;
    Response response = null;

    protected static Logger logger = Logger.getLogger(Actions.class);

    public RequestSpecification givenAuthentication(Configuration configuration) {
        try {
            keyStore = KeyStore.getInstance(configuration.getCertificateType());
            keyStore.load(new FileInputStream(FileUtils.toFile(ClassLoader.getSystemResource(configuration.getCertificatePath()))),
                    configuration.getPassword().toCharArray());

            MySSLSocketFactory sslSocketFactory = new MySSLSocketFactory("SSLv3",keyStore,configuration.getPassword(),null,new SecureRandom(),null,
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            settings= new CertificateAuthSettings().keystoreType(configuration.getCertificateType()).sslSocketFactory(sslSocketFactory);

        } catch (Exception exception) {
            logger.error("Certificate loading failed");
            exception.printStackTrace();
        }

        requestSpecification = RestAssured.given().auth().certificate(FileUtils.toFile(ClassLoader.getSystemResource(configuration.getCertificatePath()))
                .getAbsolutePath(),configuration.getPassword(),settings);

        return requestSpecification;
    }
}
