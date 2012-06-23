package net.cghsystems.groovy.transform

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import org.codehaus.groovy.transform.GroovyASTTransformationClass



/**
 * A rudimentary Groovy AST transformation that will decorate the the target class with an isValid method. 
 * This signature of the method is dynamic in nature in that is the target class is deemed valid 
 * then true will be returned. If the target class is deemed false then NotValid  class will be returned 
 * detailing the problems. A class is deemed to be valid if all of its field are not null. 
 * 
 * @author chris
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@GroovyASTTransformationClass(["net.cghsystems.groovy.transform.ValidateableASTTransformation"])
public @interface Validateable {

    /** 
     * Determines the return type of the isValid method for the type annotated with this.
     * The types supported are defined in {@link ValidatableReturnTypes}. Defaults to {@link ValidatableReturnTypes#NOT_VALID_FOR_INVALID}
     * 
     * */
    ValidatableReturnTypes value() default ValidatableReturnTypes.NOT_VALID_FOR_INVALID

    /**
     * Determines the return behaviour of the isValid method created by {@link Validateable}
     * 
     * @author chris
     *
     */
    static final enum ValidatableReturnTypes {

        /** Ensures that isValid returns true for a valid state and false for an invalid state. */
        BOOLEAN_FOR_INVALID({ return false }),

        /** Ensures that isValid returns true for a valid state and a detailed {@link NotValid} value object for an invalid state */ 
        NOT_VALID_FOR_INVALID({ errorFields ->
            return new NotValid(preMessage: "The following fields have not been set correctly: ", invalidFields: errorFields)
        })

        private final Closure validatableReturnStrategy

        private ValidatableReturnTypes(Closure validatableReturnStrategy) {
            this.validatableReturnStrategy = validatableReturnStrategy
        }
    }
}

