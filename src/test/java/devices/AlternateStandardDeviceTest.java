package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AlternateStandardDeviceTest {

    private Device device;
    @Mock FailingPolicy stubFailingPolicy;
    @Spy RandomFailing spyRandomPolicy;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMock() {
        device = new StandardDevice(this.stubFailingPolicy);
        // multiple stubbing
        when(this.stubFailingPolicy.attemptOn()).thenReturn(false);
        when(this.stubFailingPolicy.policyName()).thenReturn("mock");
        assertThrows(IllegalStateException.class, () -> device.on());
        assertEquals("StandardDevice{policy=mock, on=false}", device.toString());
    }

    @Test
    void testSpy() {
        device = new StandardDevice(this.spyRandomPolicy);
        // no interactions with the spy yet
        verifyNoInteractions(this.spyRandomPolicy);
        try{
            device.on();
        } catch (IllegalStateException e){}
        // has attemptOn been called?
        verify(this.spyRandomPolicy).attemptOn();
    }
}
