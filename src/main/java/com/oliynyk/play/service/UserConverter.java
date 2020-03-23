package com.oliynyk.play.service;

import com.oliynyk.play.dao.UserEntity;
import com.oliynyk.play.model.User;

public interface UserConverter {
     User fromEntity(UserEntity userEntity);
}
