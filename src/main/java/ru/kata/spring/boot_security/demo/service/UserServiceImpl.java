package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public void create(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userDao.create(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getEmail(String email) {
        return userDao.getEmail(email);
    }

    @Override
    @Transactional
    public void edit(long id, User user) {
        userDao.edit(id, user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getEmail(username);
    }
}
