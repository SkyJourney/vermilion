package top.icdat.vermilion.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sort {

    String[] value();

    SortType type() default SortType.ASC;

}
