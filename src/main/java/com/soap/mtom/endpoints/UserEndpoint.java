package com.soap.mtom.endpoints;

import com.soap.mtom.models.user.*;
import com.soap.mtom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.*;

@Endpoint
public class UserEndpoint {

    @Value( "${config.path.root}" )
    private String ROOT_PATH;

    private static final String NAMESPACE_URI = "com/soap/mtom/models/user";

    private UserRepository userRepository;

    @Autowired
    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {

        String fileName = request.getFile();

        GetUserResponse response = new GetUserResponse();
        DataSource source = new FileDataSource(new File(ROOT_PATH + fileName));
        response.setFileUpload(new DataHandler(source));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserUploadRequest")
    @ResponsePayload
    public GetUserUploadResponse getUserUpload(@RequestPayload GetUserUploadRequest request) throws FileNotFoundException {
        String fileName = request.getFileUpload().getName();

        try (OutputStream stream = new FileOutputStream(ROOT_PATH + fileName)) {
            request.getFileUpload().writeTo(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GetUserUploadResponse response = new GetUserUploadResponse();
        response.setFileName("Got file: " + fileName);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMultipleRequest")
    @ResponsePayload
    public RespondMultipleFiles getUserUpload2(@RequestPayload GetMultipleRequest request) throws FileNotFoundException {


        RespondMultipleFiles response = new RespondMultipleFiles();
        // file 1
        DataSource source1 = new FileDataSource(new File(ROOT_PATH + "book+image.rar"));
        response.setFileUploadA(new DataHandler(source1));

        // file 2
        DataSource source2 = new FileDataSource(new File(ROOT_PATH + "book.rar"));
        response.setFileUploadB(new DataHandler(source2));

        // file 3
        DataSource source3 = new FileDataSource(new File(ROOT_PATH + "image.rar"));
        response.setFileUploadC(new DataHandler(source3));
        // file 4

        DataSource source4 = new FileDataSource(new File(ROOT_PATH + "pkpadmin.pdf"));
        response.setFileUploadD(new DataHandler(source4));

        // file 5
        DataSource source5 = new FileDataSource(new File(ROOT_PATH + "Pizigani.jpg"));
        response.setFileUploadE(new DataHandler(source5));

        return response;
    }

}
