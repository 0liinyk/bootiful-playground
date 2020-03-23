package com.oliynyk.play.service;

import com.oliynyk.play.web.user.UserAddRequest;
import com.oliynyk.play.dao.UserEntity;
import com.oliynyk.play.dao.UserRepository;
import com.oliynyk.play.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Lazy
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    public static final String DEFAULT_FALLBACK_MESSAGE = "Hello unknown user";
    public static final String MESSAGE_PATTERN = "Welcome %s! How are you?";

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public String generatePersonalizedGreeting(String userName) {
        List<UserEntity> usersByFirstName = userRepository.findByFirstName(userName);
        if(CollectionUtils.isEmpty(usersByFirstName)){
            return DEFAULT_FALLBACK_MESSAGE;
        }
        String firstName = usersByFirstName.get(0).getFirstName();
        return String.format(MESSAGE_PATTERN, firstName);
    }

    @Override
    public User save(UserAddRequest userAddRequest) {
        UserEntity createdUser = userRepository.save(UserEntity.of(userAddRequest.getFirstName(), userAddRequest.getLastName()));
        return userConverter.fromEntity(createdUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Requesting user by id: {}", id);
        return userRepository.findById(id)
                              .map(userConverter::fromEntity);
    }

    @PostConstruct
    private void initLog(){
      log.info("creating bean from: {}", this.getClass().getSimpleName());
    }
}
