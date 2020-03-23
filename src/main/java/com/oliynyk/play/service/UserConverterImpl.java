package com.oliynyk.play.service;

import com.oliynyk.play.dao.UserEntity;
import com.oliynyk.play.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverterImpl implements UserConverter {

    @Override
    public User fromEntity(UserEntity userEntity) {
        return User.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .id(userEntity.getId())
                .build();
    }
}
