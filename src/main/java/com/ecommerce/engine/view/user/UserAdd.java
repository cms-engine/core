package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.InputClassMapping;
import com.ecommerce.engine.repository.entity.Group;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.view.TextUtils;
import com.ecommerce.engine.view.template.AddForm;
import com.vaadin.flow.component.HasValue;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserAdd extends AddForm<User, Integer> {

    public UserAdd(ListCrudRepository<Group, Integer> groupRepository, ListCrudRepository<User, Integer> userRepository) {
        super(userRepository, User::getId, User.class, UserEdit.class);

        List<HasValue<?, ?>> createdFields = new ArrayList<>();

        Field[] declaredFields = User.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            HasValue<?, ?> inputFromClass = createInputFromClass(field);
            if (inputFromClass != null) {
                binder.bind(inputFromClass, field.getName());
                createdFields.add(inputFromClass);
            }
        });

        addComponents(createdFields.stream().map(hasValue -> (com.vaadin.flow.component.Component) hasValue).toList());
        /*TextField username = new TextField("Username");
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

        addComponents(username, password, email, age, dateOfBirth, group);*/
        //binder.bindInstanceFields(this);
    }

    private static HasValue<?, ?> createInputFromClass(Field field) {
        try {
        Class<? extends HasValue<?, ?>> inputClass = InputClassMapping.getInputClass(field.getType());
        if (inputClass == null) {
            return null;
        }
        Constructor<? extends HasValue<?, ?>> constructor = inputClass.getConstructor(String.class);
        return constructor.newInstance(TextUtils.convertCamelCaseToNormalText(field.getName()));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getNewBean() {
        return new User();
    }
}
