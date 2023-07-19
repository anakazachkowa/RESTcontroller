package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    void addUser(User user);

    void deleteUser(long id);

    User getUserByName(String username);

    void addDefaultUser();

    void editUser(User user);

    User passwordCoder(User user);
}