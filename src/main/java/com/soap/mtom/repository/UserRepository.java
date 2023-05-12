package com.soap.mtom.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

    @Value( "${config.path.root}" )
    private String ROOT_PATH;

//    private static final Map<Integer, User> USER_MAP = new HashMap<>();

//    @PostConstruct
//    public void init() {
//        User user = new User();
//        ProfilePicture pic = new ProfilePicture();
//        user.setId(1);
//        user.setFirstname("Toni");
//        user.setLastname("Kroos");
//        try {
//            pic.setName(user.getId() + "_" + user.getFirstname() + ".jpeg");
//            DataSource source = new FileDataSource(new File(ROOT_PATH + "upload.jpeg"));
//            pic.setContent(new DataHandler(source));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        USER_MAP.put(user.getId(), user);
//    }
//
//    public User getUserById(int id) {
//        return USER_MAP.get(id);
//    }
}
