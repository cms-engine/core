package com.ecommerce.engine._admin.annotation;

import com.ecommerce.engine._admin.service.SeoUrlRemover;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@RequiredArgsConstructor
public class SeoUrlRemoveAspect {
    private final SeoUrlRemover seoUrlRemover;

    @Before("@annotation(seoUrlRemove)")
    public void intercept(JoinPoint joinPoint, SeoUrlRemove seoUrlRemove) {
        Object[] args = joinPoint.getArgs();
        Object arg1 = args[0];

        if (arg1 instanceof Long recordId) {
            seoUrlRemover.remove(seoUrlRemove.value(), recordId);
        } else if (arg1 instanceof Collection<?> recordIds) {
            Set<Long> convertedIds = recordIds.stream().map(Long.class::cast).collect(Collectors.toSet());
            seoUrlRemover.remove(seoUrlRemove.value(), convertedIds);
        }
    }
}
