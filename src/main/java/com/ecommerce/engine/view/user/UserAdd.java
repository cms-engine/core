package com.ecommerce.engine.view.user;

import com.ecommerce.engine.model.VaadinInputFactory;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.view.TextUtils;
import com.ecommerce.engine.view.template.AddForm;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserAdd extends AddForm<User, Integer> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public UserAdd(ListCrudRepository<User, Integer> userRepository, ApplicationContext applicationContext) {
        super(userRepository, User::getId, User.class, UserEdit.class);

        List<HasValue<?, ?>> createdFields = new ArrayList<>();

        Field[] declaredFields = User.class.getDeclaredFields();

        for (Field field : declaredFields) {
            HasValue<?, ?> inputFromClass = VaadinInputFactory.createVaadinInput(field);
            if (inputFromClass != null) {
                if (field.getType().isPrimitive()) {
                    binder.forField(inputFromClass).asRequired().bind(field.getName());
                } else {
                    binder.bind(inputFromClass, field.getName());
                }
                createdFields.add(inputFromClass);
            } else if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class)) {
                var listCrudRepositoryByGenericType = findListCrudRepositoryByGenericType(applicationContext, field.getType());
                if (listCrudRepositoryByGenericType != null) {
                    ComboBox comboBox = new ComboBox<>();
                    comboBox.setLabel(TextUtils.convertCamelCaseToNormalText(field.getName()));
                    comboBox.setItems(listCrudRepositoryByGenericType.findAll());
                    comboBox.setItemLabelGenerator(Object::toString);

                    binder.bind(comboBox, field.getName());
                    createdFields.add(comboBox);
                }
            }
        }

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

    @SuppressWarnings("rawtypes")
    public static ListCrudRepository<?, ?> findListCrudRepositoryByGenericType(ApplicationContext context, Class<?> entityType) {
        Map<String, ListCrudRepository> beansOfType = context.getBeansOfType(ListCrudRepository.class);

        for (ListCrudRepository<?, ?> bean : beansOfType.values()) {
            Class<?>[] resolveTypeArguments = GenericTypeResolver.resolveTypeArguments(bean.getClass(), ListCrudRepository.class);
            Class<?> entityGenericType = resolveTypeArguments != null ? resolveTypeArguments[0] : null;

            if (entityGenericType != null && entityGenericType.equals(entityType)) {
                return bean;
            }
        }

        return null;
    }

    @Override
    public User getNewBean() {
        return new User();
    }
}
