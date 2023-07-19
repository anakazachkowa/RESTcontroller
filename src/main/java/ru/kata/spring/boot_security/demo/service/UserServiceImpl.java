package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        Optional<User> optional = userDAO.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        userDAO.save(passwordCoder(user));
    }

    @Override
    public void editUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userDAO.deleteById(id);
    }

    @Override
    public User getUserByName(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @PostConstruct
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDAO.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDAO.findById(1L).orElse(null));
        roles2.add(roleDAO.findById(2L).orElse(null));
        User user1 = new User("Verkina", "vera@mail.com", "user", "user", roles1);
        User user2 = new User("Varova", "vara@mail.com", "admin", "admin", roles2);
        addUser(user1);
        addUser(user2);
    }
}

