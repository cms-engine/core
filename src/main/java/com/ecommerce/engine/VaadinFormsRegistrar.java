package com.ecommerce.engine;

import com.ecommerce.engine.config.VaadinErrorHandler;
import com.ecommerce.engine.repository.entity.User;
import com.ecommerce.engine.service.SearchService;
import com.ecommerce.engine.util.ReflectionUtils;
import com.ecommerce.engine.view.MainLayout;
import com.ecommerce.engine.view.template.AddForm;
import com.ecommerce.engine.view.template.EditForm;
import com.ecommerce.engine.view.template.EntityDataProvider;
import com.ecommerce.engine.view.template.ListForm;
import com.ecommerce.engine.view.user.UserFilter;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import jakarta.persistence.EntityManager;
import org.slf4j.LoggerFactory;
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
public class VaadinFormsRegistrar implements VaadinServiceInitListener  {

    private static final List<ListCrudRepository<?, ?>> ALL_REPOSITORIES = new ArrayList<>();

    private static final List<EditForm<?, ?>> EDIT_FORMS = new ArrayList<>();

    private static final List<ListForm<?, ?>> LIST_FORMS = new ArrayList<>();

    @Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addSessionInitListener(
                initEvent -> {
                    LoggerFactory.getLogger(getClass())
                            .info("A new Session has been initialized!");

                    initEvent.getSession().setErrorHandler(new VaadinErrorHandler());
                });

        //VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());

        event.getSource().addUIInitListener(
                initEvent -> LoggerFactory.getLogger(getClass())
                        .info("A new UI has been initialized!"));

        LIST_FORMS.forEach(listForm -> RouteConfiguration.forApplicationScope().setRoute(listForm.getTableName(), listForm.getClass(), MainLayout.class));
        EDIT_FORMS.forEach(editForm -> RouteConfiguration.forApplicationScope().setRoute(editForm.getTableName() + "/:id", editForm.getClass(), MainLayout.class));
    }

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

        String tableName = ReflectionUtils.getTableName(entityClass);

        var searchService = new SearchService<T>(entityManager);

        var entityDataProvider = new EntityDataProvider<>(searchService, entityClass, idClass);

        var editForm = new EditForm<>(listCrudRepository, null, entityClass, idClass, tableName);
        beanFactory.registerSingleton("editForm_" + UUID.randomUUID(), editForm);
        EDIT_FORMS.add(editForm);

        var addForm = new AddForm<>(listCrudRepository, entityClass, idClass, tableName);

        var listForm = new ListForm<>(listCrudRepository, entityDataProvider, addForm, new UserFilter(), entityClass, idClass, tableName);
        beanFactory.registerSingleton("listForm_" + UUID.randomUUID(), listForm);
        LIST_FORMS.add(listForm);
    }
}
