package com.ecommerce.engine;

import com.ecommerce.engine.view.template.AddForm;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AddFormRegistrar {

    public AddFormRegistrar(ApplicationContext applicationContext, List<ListCrudRepository<?, ?>> listCrudRepositories) {

        //for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.your.package")) {
        for (ListCrudRepository<?, ?> listCrudRepository : listCrudRepositories) {
                AddForm<?, ?> addForm = new AddForm<>(listCrudRepository, null, listCrudRepositories);

                ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
                beanFactory.registerSingleton("addForm_" + UUID.randomUUID(), addForm);
        }
        //}
    }
}
