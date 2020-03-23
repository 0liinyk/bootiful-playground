package com.oliynyk.play.service;

import com.oliynyk.play.web.user.UserAddRequest;
import com.oliynyk.play.model.User;

import java.util.Optional;

public interface UserService {

    String generatePersonalizedGreeting(String userName);


    User save(UserAddRequest userAddRequest);

    Optional<User> findById(Long id);
}
