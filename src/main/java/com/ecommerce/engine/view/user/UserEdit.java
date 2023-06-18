package com.ecommerce.engine.view.user;

import com.ecommerce.engine.repository.GroupRepository;
import com.ecommerce.engine.repository.UserRepository;
import com.ecommerce.engine.repository.entity.Group;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.view.MainLayout;
import com.ecommerce.engine.view.template.EditForm;
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
import org.springframework.stereotype.Component;

@Route(value = "users", layout = MainLayout.class)
@Component
@PageTitle("User edit")
public class UserEdit extends EditForm<User, Integer> {

    public UserEdit(GroupRepository groupRepository, UserRepository saveDeleteService) {
        super(saveDeleteService, User.class, UserList.class);

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        EmailField email = new EmailField("Email");
        IntegerField age = new IntegerField("Age");
        DatePicker dateOfBirth = new FormatDatePicker("Date of birth");
        ComboBox<Group> group = new ComboBox<>();
        group.setLabel("Group");
        group.setItems(groupRepository.findAll());
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
}
