package top.icdat.vermilion.annotation;

import java.lang.annotation.*;

/**
 * @author SkyJourney
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonNull {
    boolean value() default true;
}
