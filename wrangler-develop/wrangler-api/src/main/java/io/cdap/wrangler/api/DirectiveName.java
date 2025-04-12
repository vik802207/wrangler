package io.cdap.wrangler.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define metadata for a directive.
 * Includes the directive's name, usage instructions, and description.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DirectiveName {

    /**
     * The name used to invoke the directive in recipes.
     */
    String name();

    /**
     * How the directive should be used.
     */
    String usage();

    /**
     * A human-readable description of what the directive does.
     */
    String description() default "";
}
