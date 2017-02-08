package com.actions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by aswathyn on 05/12/16.
 */

@Component
public class Configuration {

    @Value("${certificate.path}")
    private String certificatePath;

    @Value("${certificate.password}")
    private String password;

    @Value("${certificate.type}")
    private String type;


    public String getCertificatePath() {
        return certificatePath;
    }

    public String getPassword() {
        return password;
    }

    public String getCertificateType() {
        return type;
    }
}
