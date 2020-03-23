package com.oliynyk.play.web.user;

import com.oliynyk.play.model.User;
import lombok.Data;

@Data
public class UserView {
    long id;
    String firstName;
    String lastName;

    public static UserView from(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setFirstName(user.getFirstName());
        userView.setLastName(user.getLastName());
        return userView;
    }
}
