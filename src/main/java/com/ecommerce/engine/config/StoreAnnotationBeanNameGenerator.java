package com.ecommerce.engine.config;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class StoreAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Nonnull
    @Override
    public String generateBeanName(@Nonnull BeanDefinition definition, @Nonnull BeanDefinitionRegistry registry) {
        String beanName = super.generateBeanName(definition, registry);
        String beanClassName = definition.getBeanClassName();
        if (beanClassName != null && beanClassName.contains(".store.")) {
            return beanName + "Store";
        } else {
            return beanName;
        }
    }
}
