package net.cghsystems.groovy.transform



import static org.codehaus.groovy.transform.AbstractASTTransformUtil.getInstanceNonPropertyFields
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.getInstancePropertyFields
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.hasDeclaredMethod
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.isZeroExpr

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.objectweb.asm.Opcodes


/**
 * Transformation class that adds the isValid method required by {@link Validateable} to the Groovy syntax tree.
 * 
 * @author chris
 *
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ValidateableASTTransformation extends AbstractASTTransformation {

    private static final String RETURN_STRATEGY = "rt"

    /* (non-Javadoc)
     * @see org.codehaus.groovy.transform.ASTTransformation#visit(org.codehaus.groovy.ast.ASTNode[], org.codehaus.groovy.control.SourceUnit)
     */
    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        def annotationNode = astNodes[0] // represents the Annotation that triggered the AST
        def annotatedNode = astNodes[1] //The class that is annotated

        def returnStrategy = annotationNode.getMember("value") //The value taken from annotationNode

        annotatedNode.addField(new FieldNode(RETURN_STRATEGY, Opcodes.ACC_PRIVATE,
                ClassHelper.OBJECT_TYPE, annotatedNode,returnStrategy))

        annotatedNode.addMethod(new MethodNode("isValid", ACC_PUBLIC,
                ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body()[0]))
    }

    /**
     * @return the {@link BlockStatement} containing the body of the isValidMethod
     */
    def body() {
        new AstBuilder().buildFromCode {
            final errorFields = []
            final ignore = ["class", "metaClass"]
            properties.keySet().collect{ it }.findAll{
                !ignore.contains(it)
            }.each {
                if(properties.get(it) == null) {
                    errorFields << it
                }
            }

            if(errorFields.size() == 0) {
                return true
            }else {
                return rt.validatableReturnStrategy(errorFields)
            }
        }
    }
}
