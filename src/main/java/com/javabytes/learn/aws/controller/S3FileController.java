package com.javabytes.learn.aws.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.javabytes.learn.aws.models.PersonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/rest/s3")
public class S3FileController {

    @Autowired
    AmazonS3 amazonS3;

    @PostMapping
    public String createAndStoreFileInS3() {
        PersonRecord r1 = new PersonRecord();
        r1.setName("John");
        r1.setPhoneNumber("3028889999");
        r1.setCountry("USA");

        PersonRecord r2 = new PersonRecord();
        r2.setName("John");
        r2.setPhoneNumber("3028889999");
        r2.setCountry("USA");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(r1.toString().getBytes());
            baos.write("\n".getBytes());
            baos.write(r2.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        InputStream inputstream = new ByteArrayInputStream(baos.toByteArray());
        String bucketName = "java-bytes";
        String fileName = "java-bytes-08272022_"+System.currentTimeMillis()+".csv";
        amazonS3.putObject(bucketName, fileName, inputstream, new ObjectMetadata());
        return "ok";
    }

}
