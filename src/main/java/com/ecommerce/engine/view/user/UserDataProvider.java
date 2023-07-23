package com.ecommerce.engine.view.user;

import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.service.SearchService;
import com.ecommerce.engine.view.template.EntityDataProvider;
import org.springframework.stereotype.Component;

@Component
public class UserDataProvider extends EntityDataProvider<User> {

    public UserDataProvider(SearchService<User> searchService) {
        super(searchService, user -> user.getId(), User.class);
    }
}
