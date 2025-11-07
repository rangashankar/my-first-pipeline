package demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmokeTest {
    @Test
    void hello() {
        assertTrue(1 + 1 == 2, "Math still works!");
    }
}
