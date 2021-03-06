package io.agrest.meta.annotation;

import io.agrest.annotation.AgResource;
import io.agrest.meta.LinkType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate that web method is an AgREST resource.
 *
 * @deprecated since 2.10 in favor of {@link AgResource} annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

    /**
     * @return Class of AgREST entity that this resource works with.
     */
    Class<?> entityClass() default Object.class;

    /**
     * @return type of this resource. If not set then type is {@link io.agrest.meta.LinkType#UNDEFINED}.
     */
    LinkType type() default LinkType.UNDEFINED;

}
