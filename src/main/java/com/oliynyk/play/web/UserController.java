package com.oliynyk.play.web;

import com.oliynyk.play.web.api.ResourceNotFoundException;
import com.oliynyk.play.web.user.UserAddRequest;
import com.oliynyk.play.web.user.UserView;
import com.oliynyk.play.model.User;
import com.oliynyk.play.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    @Lazy
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(UriComponentsBuilder uriComponentsBuilder,
                                            @RequestBody @Valid UserAddRequest userAddRequest ){
        User user = userService.save(userAddRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserView> findUser(@PathVariable Long id){
       User user = userService.findById(id).orElseThrow(ResourceNotFoundException::new);
       return ResponseEntity.ok(UserView.from(user));
    }
}
