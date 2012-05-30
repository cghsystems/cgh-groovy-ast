package net.cghsystems.something.random

import org.junit.Test




class ValidateableTest {

    @Test
    void shouldBeInvalidAsNoPropetiesSet() {
        assert "The following fields have not been build correctly:  field2, field3, field1" == new ValidateableStub().isValid().message
    }

    @Test
    void shouldBeInvalidAsOnePropetyIsNotSet() {
        def vs = new ValidateableStub(field1: "field1", field2: "field2")
        assert "The following fields have not been build correctly:  field3" == vs.isValid().message
    }

    @Test
    void shouldBeValidAsPropetiesSet() {
        def vs = new ValidateableStub(field1: "field1", field2: "field2", field3: "field1")
        assert true == vs.isValid()
    }
}
