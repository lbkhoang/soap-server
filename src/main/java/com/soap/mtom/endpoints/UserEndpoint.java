package com.soap.mtom.endpoints;

import com.soap.mtom.models.user.GetUserUploadRequest;
import com.soap.mtom.repository.UserRepository;
import com.soap.mtom.models.user.GetUserRequest;
import com.soap.mtom.models.user.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "com/soap/mtom/models/user";

    private UserRepository userRepository;

    @Autowired
    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {

        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.getUserById(request.getId()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserUploadRequest")
    @ResponsePayload
    public GetUserResponse getUserUpload(@RequestPayload GetUserUploadRequest request) {

        // Note preferred way of declaring an array variable
        try (OutputStream stream = new FileOutputStream("D:\\A_IMG\\abc.jpeg")) {
            stream.write(request.getFileUpload());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.getUserById(request.getId()));

        return response;
    }

}
