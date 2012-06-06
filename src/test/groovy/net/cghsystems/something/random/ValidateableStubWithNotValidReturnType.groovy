package net.cghsystems.something.random

import net.cghsystems.groovy.transform.Validateable
import net.cghsystems.groovy.transform.Validateable.ValidatableReturnTypes



@Validateable(ValidatableReturnTypes.NOT_VALID_FOR_INVALID)
class ValidateableStubWithNotValidReturnType {
    def field1, field2, field3
}

@Validateable(ValidatableReturnTypes.BOOLEAN_FOR_INVALID)
class ValidateableStubWithBooleanReturnType {
    def field1, field2, field3
}

