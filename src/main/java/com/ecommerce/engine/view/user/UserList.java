package com.ecommerce.engine.view.user;

import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.view.MainLayout;
import com.ecommerce.engine.view.template.AddForm;
import com.ecommerce.engine.view.template.ListForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users")
public class UserList extends ListForm<User, Integer> {

    public UserList(ListCrudRepository<User, Integer> userRepository, UserDataProvider userDataProvider, List<AddForm<User, Integer>> addForms, UserEdit userEdit) {
        super(userRepository, User.class, User::getId, userDataProvider, addForms, userEdit, new UserFilter());
    }
}
