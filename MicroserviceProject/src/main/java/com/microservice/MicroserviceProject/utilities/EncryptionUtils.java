package com.microservice.MicroserviceProject.utilities;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    private BasicTextEncryptor textEncryptor;

    public EncryptionUtils(){
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("mySecretEncryptionKeyBlaBla1234");
    }

    public String encrypt(String data){
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encriptedData){
        return textEncryptor.decrypt(encriptedData);
    }
}
