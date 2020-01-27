package top.icdat.vermilion.annotation;

import java.lang.annotation.*;

/**
 * @author SkyJourney
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String value() default "";
}
