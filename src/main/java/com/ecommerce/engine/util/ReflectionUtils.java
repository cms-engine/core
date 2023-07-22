package com.ecommerce.engine.util;

import com.ecommerce.engine.service.ApplicationContextProvider;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.ListCrudRepository;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class ReflectionUtils {

    private static final ApplicationContext CONTEXT = ApplicationContextProvider.getApplicationContext();

    public static boolean isToOneColumn(Field field) {
        return field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class);
    }

    @SuppressWarnings("rawtypes")
    public static ListCrudRepository<?, ?> findListCrudRepositoryByGenericType(Class<?> entityType) {
        Map<String, ListCrudRepository> beansOfType = CONTEXT.getBeansOfType(ListCrudRepository.class);

        for (ListCrudRepository<?, ?> bean : beansOfType.values()) {
            Class<?>[] resolveTypeArguments = GenericTypeResolver.resolveTypeArguments(bean.getClass(), ListCrudRepository.class);
            Class<?> entityGenericType = resolveTypeArguments != null ? resolveTypeArguments[0] : null;

            if (entityGenericType != null && entityGenericType.equals(entityType)) {
                return bean;
            }
        }

        return null;
    }

    @SneakyThrows
    public static Object getEntityId(Object entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                return field.get(entity);
            }
        }

        throw new IllegalArgumentException("No field annotated with @Id found.");
    }

}
