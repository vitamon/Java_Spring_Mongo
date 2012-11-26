package in.ua.vitamon.helpers;
/**
 * Indicates Logger of appropriate type to
 * be supplied at runtime to the annotated field.
 *
 * The injected logger is an appropriate implementation
 * of org.slf4j.Logger.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface Logger {
}