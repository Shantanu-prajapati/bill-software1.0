package in.shani.billingsoftware.service;

import in.shani.billingsoftware.io.UserRequest;
import in.shani.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}