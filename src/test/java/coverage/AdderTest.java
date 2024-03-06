package coverage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdderTest {

    @Test
    void testAddition() {
        var adder = new Adder();
        assertEquals(30, adder.add(10, 20));
        assertEquals(-1, adder.add(-1, 20)); // test needed for full coverage
        assertEquals(-1, adder.add(20, -1)); // test needed for full coverage
    }
}