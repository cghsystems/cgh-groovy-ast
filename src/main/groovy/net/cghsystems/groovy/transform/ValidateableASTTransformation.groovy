


package net.cghsystems.groovy.transform



import static org.codehaus.groovy.transform.AbstractASTTransformUtil.getInstanceNonPropertyFields
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.getInstancePropertyFields
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.hasDeclaredMethod
import static org.codehaus.groovy.transform.AbstractASTTransformUtil.isZeroExpr

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.BooleanExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.FieldExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.transform.AbstractASTTransformUtil as ASTUtil
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ValidateableASTTransformation extends AbstractASTTransformation {

    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        AnnotatedNode parent = (AnnotatedNode) astNodes[1]
        def instancePropertyFields = ASTUtil.getInstancePropertyFields(parent)

        astNodes[1].addMethod(new MethodNode("isValid", ACC_PUBLIC,
                ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body(instancePropertyFields)))
    }

    def body(fields) {
        final COMPARE_EQUAL = Token.newSymbol(Types.COMPARE_EQUAL, -1, -1)
        BlockStatement body = new BlockStatement()
        fields.each {
            def fieldExpression = new FieldExpression(it)
            body.addStatement(ASTUtil.returnFalseIfNull(new BooleanExpression(new BinaryExpression(fieldExpression, COMPARE_EQUAL, ConstantExpression.NULL))))
        }
        body
    }
}
