package com.ecommerce.engine.view.user;

import com.ecommerce.engine.repository.UserRepository;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.view.MainLayout;
import com.ecommerce.engine.view.template.ListForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users")
public class UserList extends ListForm<User, Integer> {

    public UserList(UserRepository userRepository, UserDataProvider userDataProvider, UserAdd userAdd, UserEdit userEdit) {
        super(userRepository, User.class, User::getId, userDataProvider, userAdd, userEdit, new UserFilter());
    }
}
