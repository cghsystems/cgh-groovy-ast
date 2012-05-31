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
}
