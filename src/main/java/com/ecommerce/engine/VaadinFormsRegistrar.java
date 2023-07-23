package com.ecommerce.engine;

import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.service.SearchService;
import com.ecommerce.engine.util.ReflectionUtils;
import com.ecommerce.engine.view.template.AddForm;
import com.ecommerce.engine.view.template.EditForm;
import com.ecommerce.engine.view.template.EntityDataProvider;
import com.ecommerce.engine.view.template.ListForm;
import com.ecommerce.engine.view.user.UserFilter;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class VaadinFormsRegistrar {

    private static final List<ListCrudRepository<?, ?>> ALL_REPOSITORIES = new ArrayList<>();

    public VaadinFormsRegistrar(ApplicationContext applicationContext, EntityManager entityManager, List<ListCrudRepository<?, ?>> listCrudRepositories) {
        ALL_REPOSITORIES.addAll(listCrudRepositories);

        for (ListCrudRepository<?, ?> listCrudRepository : listCrudRepositories) {
            ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
            createForms(listCrudRepository, entityManager, beanFactory);
        }
    }

    public static List<ListCrudRepository<?, ?>> getAllRepositories() {
        return Collections.unmodifiableList(ALL_REPOSITORIES);
    }

    private static <T, ID> void createForms(ListCrudRepository<T, ID> listCrudRepository, EntityManager entityManager, ConfigurableListableBeanFactory beanFactory) {
        Class<T> entityClass = ReflectionUtils.findEntityTypeByRepository(listCrudRepository);
        Class<ID> idClass = ReflectionUtils.findIdTypeByAddForm(listCrudRepository);

        assert entityClass != null;
        assert idClass != null;

        if (!entityClass.equals(User.class)) {
            return;
        }

        var searchService = new SearchService<T>(entityManager);

        var entityDataProvider = new EntityDataProvider<>(searchService, entityClass, idClass);
        beanFactory.registerSingleton("entityDataProvider_" + UUID.randomUUID(), entityDataProvider);

        var editForm = new EditForm<>(listCrudRepository, null, entityClass);
        beanFactory.registerSingleton("editForm_" + UUID.randomUUID(), editForm);

        var addForm = new AddForm<>(listCrudRepository, editForm, entityClass, idClass);
        beanFactory.registerSingleton("addForm_" + UUID.randomUUID(), addForm);

        var listForm = new ListForm<>(listCrudRepository, entityDataProvider, addForm, editForm, new UserFilter(), entityClass, idClass);
        beanFactory.registerSingleton("listForm_" + UUID.randomUUID(), listForm);
    }
}
