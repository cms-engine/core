package com.ecommerce.engine.annotation;

import com.ecommerce.engine.enums.SeoUrlEntity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SeoUrlRemove {
    SeoUrlEntity value();
}
