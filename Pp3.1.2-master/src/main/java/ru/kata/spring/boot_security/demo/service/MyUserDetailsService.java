package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final EntityManager entityManager;

    @Autowired
    public MyUserDetailsService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.surName=:username", User.class);
        User userDb = query.setParameter("username", username).getSingleResult();
        return userDb;
    }
}
