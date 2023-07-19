package com.ecommerce.engine.util;

import com.ecommerce.engine.service.ApplicationContextProvider;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.ListCrudRepository;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class ReflectionUtils {

    ApplicationContext context = ApplicationContextProvider.getApplicationContext();

    public static boolean isToOneColumn(Field field) {
        return field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class);
    }

    @SuppressWarnings("rawtypes")
    public static ListCrudRepository<?, ?> findListCrudRepositoryByGenericType(Class<?> entityType) {
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

}
