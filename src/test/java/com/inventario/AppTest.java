
package com.inventario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testMain() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

    @Test
    void testConstructor() {
        assertNotNull(new App());
    }
}
