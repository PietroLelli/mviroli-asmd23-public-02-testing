package reorganise;
import devices.Device;
import devices.FailingPolicy;
import devices.StandardDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

public class ReorganiseDeviceTest {
    private Device device;
    @Mock private FailingPolicy failingPolicy = mock(FailingPolicy.class);

    @BeforeEach
    void init() {
        device = new StandardDevice(failingPolicy);
    }

    @Test
    void testCreateDevice() {
        assertNotNull(device);
    }

    @Test
    void testInitiallyOff() {
        assertFalse(device.isOn());
        verifyNoInteractions(this.failingPolicy);
    }

    @Test
    void testCanBeSwitchedOn() {
        when(this.failingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
        verify(this.failingPolicy).attemptOn();
    }

    @Test
    void testCantBeSwitchedOn() {
        when(this.failingPolicy.attemptOn()).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> device.on());
        verify(this.failingPolicy).attemptOn();
    }

    @Test
    void canBeSwitchedOff() {
        when(this.failingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
        device.off();
        assertFalse(device.isOn());
    }

    @Test
    void testReset() {
        when(this.failingPolicy.attemptOn()).thenReturn(true);
        device.on();
        device.reset();
        assertEquals(2, Mockito.mockingDetails(this.failingPolicy).getInvocations().size());
    }

    @Test
    void testSwitchesOnAndOff() {
        when(this.failingPolicy.attemptOn()).thenReturn(true, true, false);
        IntStream.range(0, 2).forEach(i -> {
            device.on();
            assertTrue(device.isOn());
            device.off();
            assertFalse(device.isOn());
        });
        assertThrows(IllegalStateException.class, () -> device.on());
    }
}
