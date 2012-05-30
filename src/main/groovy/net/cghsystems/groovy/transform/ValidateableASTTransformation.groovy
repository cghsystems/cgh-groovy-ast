


package net.cghsystems.groovy.transform



import static org.codehaus.groovy.transform.AbstractASTTransformUtil.getInstanceNonPropertyFields
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.getInstancePropertyFields
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.hasDeclaredMethod
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.isZeroExpr

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ValidateableASTTransformation extends AbstractASTTransformation {

    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        astNodes[1].addMethod(new MethodNode("isValid", ACC_PUBLIC,
                ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body()[0]))
    }

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
                return new net.cghsystems.groovy.transform.NotValid(preMessage: "The following fields have not been build correctly: ", invalidFields: errorFields)
            }
        }
    }
}
