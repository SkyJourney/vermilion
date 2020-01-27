package top.icdat.vermilion.annotation;

import java.lang.annotation.*;

/**
 * @author SkyJourney
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Primary {
    String value() default "";
}
