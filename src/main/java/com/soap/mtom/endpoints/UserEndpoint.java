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
import javax.imageio.ImageIO;
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

        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.getUserById(request.getId()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserUploadRequest")
    @ResponsePayload
    public GetUserResponse getUserUpload(@RequestPayload GetUserUploadRequest request) throws FileNotFoundException {
        String fileName = request.getFileUpload().getName();
        try (OutputStream stream = new FileOutputStream(ROOT_PATH + fileName)) {
            request.getFileUpload().writeTo(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GetUserResponse response = new GetUserResponse();

        User user = userRepository.getUserById(request.getId());

        ProfilePicture pic = new ProfilePicture();
        try {
            pic.setName(user.getId() + "_" + user.getFirstname() + ".jpeg");
            DataSource source = new FileDataSource(new File(ROOT_PATH + fileName));
            pic.setContent(new DataHandler(source));
            user.setProfilePicture(pic);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        response.setUser(user);

        return response;
    }

}
