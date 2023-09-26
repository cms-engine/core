package com.ecommerce.engine.validation;

import com.ecommerce.engine.service.EntityPresenceService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityPresenceValidator implements ConstraintValidator<EntityPresence, Object> {

    private final Map<EntityType, EntityPresenceService<?>> typeEntityPresenceServiceMap;
    private Function<Object, Boolean> getFunction;

    public EntityPresenceValidator(List<EntityPresenceService<?>> entityPresenceServices) {
        typeEntityPresenceServiceMap = entityPresenceServices.stream()
                .collect(Collectors.toMap(EntityPresenceService::getEntityType, Function.identity()));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void initialize(EntityPresence constraintAnnotation) {
        EntityType entityType = constraintAnnotation.value();
        EntityPresenceService entityPresenceService = typeEntityPresenceServiceMap.get(entityType);
        if (entityPresenceService == null) {
            throw new RuntimeException("Cannot find entity presence service for type %s".formatted(entityType));
        }
        getFunction = entityPresenceService::exists;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return getFunction.apply(value);
    }
}
