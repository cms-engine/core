package com.ecommerce.engine.view.user;

import com.ecommerce.engine.repository.UserGroupRepository;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.repository.entity.UserGroup;
import com.ecommerce.engine.service.SaveDeleteService;
import com.ecommerce.engine.view.MainLayout;
import com.ecommerce.engine.view.template.AddForm;
import com.ecommerce.engine.view.template.FormatDatePicker;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "users/create", layout = MainLayout.class)
@PageTitle("User create")
public class UserAdd extends AddForm<User> {

    public UserAdd(UserGroupRepository userGroupRepository, SaveDeleteService<User> saveDeleteService) {
        super(saveDeleteService, User::getId, User.class, UserEdit.class);

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        EmailField email = new EmailField("Email");
        IntegerField age = new IntegerField("Age");
        DatePicker dateOfBirth = new FormatDatePicker("Date of birth");
        ComboBox<UserGroup> group = new ComboBox<>();
        group.setLabel("Group");
        group.setItems(userGroupRepository.findAll());
        group.setItemLabelGenerator(userGroup -> "%s (%d)".formatted(userGroup.getName(), userGroup.getId()));

        binder.forField(username).asRequired()
                .withValidator(new RegexpValidator("Username can contain only word character [a-zA-Z0-9_]", "^\\w+$")).bind("username");
        binder.forField(password).asRequired().bind("password");
        binder.forField(email)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address", true)).bind("email");
        binder.forField(age).withValidator(new IntegerRangeValidator("0-100", 0, 100)).bind("age");
        binder.bind(dateOfBirth, "dateOfBirth");
        binder.bind(group, "group");

        addComponents(username, password, email, age, dateOfBirth, group);
        //binder.bindInstanceFields(this);
    }

    @Override
    public User getNewBean() {
        return new User();
    }
}
