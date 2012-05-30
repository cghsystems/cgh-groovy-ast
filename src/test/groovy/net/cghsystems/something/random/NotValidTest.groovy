package net.cghsystems.something.random


import static org.junit.Assert.*
import net.cghsystems.groovy.transform.NotValid

import org.junit.Test

class NotValidTest {

    NotValid unit

    @Test
    void shouldBuildMessage() {
        unit = new NotValid(preMessage: "PreMessage", invalidFields: ["field1", "field2", "field3"])
        assert unit.getMessage() == "PreMessage field1, field2, field3"
    }
}
