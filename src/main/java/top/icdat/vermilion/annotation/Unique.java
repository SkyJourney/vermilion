package top.icdat.vermilion.annotation;


import java.lang.annotation.*;

/**
 * @author lyz-4dou
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {
    boolean value() default true;
}
