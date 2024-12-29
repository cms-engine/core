package com.ecommerce.engine._admin.annotation;

import com.ecommerce.engine.enumeration.SeoUrlType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SeoUrlRemove {
    SeoUrlType value();
}
