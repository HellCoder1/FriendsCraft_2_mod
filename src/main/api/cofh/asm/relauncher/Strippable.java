package cofh.asm.relauncher;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When used on a class, methods from referenced interfaces will not be removed <br>
 * When using this annotation on methods, ensure you do not switch on an enum inside that method. JavaC implementation details means this will cause crashes.
 * <p>
 * Can also strip on modid using "mod:CoFHCore" as a value <br>
 * Can also strip on API using "api:CoFHAPI|energy" as a value
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE })
public @interface Strippable {

	public String[] value();

	/**
	 * The side from which these interfaces will *always* be stripped.
	 */
	public CoFHSide side() default CoFHSide.NONE;
}
