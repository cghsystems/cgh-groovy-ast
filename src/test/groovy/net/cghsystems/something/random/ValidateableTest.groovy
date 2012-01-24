package net.cghsystems.something.random

import org.junit.Test




class ValidateableTest {

    @Test
    void shouldBeInvalidAsNoPropetiesSet() {
        assert false == new ValidateableStub().isValid()
    }
}
