package com.ecommerce.engine.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE_USE, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EntityPresenceValidator.class)
public @interface EntityPresence {

    String message() default "value was not found";

    Class<?>[] groups() default {};

    EntityType value();

    Class<? extends Payload>[] payload() default {};
}
