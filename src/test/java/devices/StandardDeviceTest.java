package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StandardDeviceTest {

    private Device device;

    @Test
    @DisplayName("Device must specify a strategy")
    void testNonNullStrategy() {
        assertThrows(NullPointerException.class, () -> new StandardDevice(null));
    }

    @Nested
    class ShowcaseDummies {
        @Test
        @DisplayName("Device is initially off")
        void testInitiallyOff() {
            // an unused reference, a sort of "empty implementation"
            FailingPolicy dummyFailingPolicy = mock(FailingPolicy.class);
            device = new StandardDevice(dummyFailingPolicy);
            // checking that a device is on will not affect the strategy
            assertFalse(device.isOn());
        }
    }

    @Nested
    class ShowcaseStubs {
        private FailingPolicy stubFailingPolicy;

        @BeforeEach
        void init(){
            this.stubFailingPolicy = mock(FailingPolicy.class);
            device = new StandardDevice(this.stubFailingPolicy);
        }

        @Test
        @DisplayName("Device can be switched on")
        void testCanBeSwitchedOn() {
            // stubbing the test double, indicating "default behaviour"
            when(this.stubFailingPolicy.attemptOn()).thenReturn(true);
            device.on();
            assertTrue(device.isOn());
        }

        @Test
        @DisplayName("Device won't switch on if failing")
        void testWontSwitchOn() {
            // multiple stubbing
            when(this.stubFailingPolicy.attemptOn()).thenReturn(false);
            when(this.stubFailingPolicy.policyName()).thenReturn("mock");
            assertThrows(IllegalStateException.class, () -> device.on());
            assertEquals("StandardDevice{policy=mock, on=false}", device.toString());
        }
    }

    @Nested
    class ShowcaseFakes {
        private FailingPolicy fakeFailingPolicy;

        @BeforeEach
        void init(){
            this.fakeFailingPolicy = mock(FailingPolicy.class);
            device = new StandardDevice(this.fakeFailingPolicy);
            // faking is more than stubbing: this object pretends to be the real one
            when(this.fakeFailingPolicy.attemptOn()).thenReturn(true, true, false);
            when(this.fakeFailingPolicy.policyName()).thenReturn("mock");
        }

        @Test
        @DisplayName("Device switch on and off until failing")
        void testSwitchesOnAndOff() {
            IntStream.range(0, 2).forEach(i -> {
                device.on();
                assertTrue(device.isOn());
                device.off();
                assertFalse(device.isOn());
            });
            assertThrows(IllegalStateException.class, () -> device.on());
        }
    }

    @Nested
    class ShowcaseSpies {
        private FailingPolicy spyFailingPolicy;

        @BeforeEach
        void init(){
            // the spy is essentially a proxy to the DOC, used to capture events
            this.spyFailingPolicy = spy(new RandomFailing());
            device = new StandardDevice(this.spyFailingPolicy);
        }

        @Test
        @DisplayName("AttemptOn is called as expected")
        void testReset() {
            device.isOn();
            // no interactions with the spy yet
            verifyNoInteractions(this.spyFailingPolicy);
            try{
                device.on();
            } catch (IllegalStateException e){}
            // has attemptOn been called?
            verify(this.spyFailingPolicy).attemptOn();
            device.reset();
            // have at least two method invocations be made?
            assertEquals(2,
                Mockito.mockingDetails(this.spyFailingPolicy).getInvocations().size());
            //  Mockito.mockingDetails gives very powerful mechanisms...
        }
    }

    @Nested
    class ShowcaseMocks {
        private FailingPolicy mockFailingPolicy;

        @BeforeEach
        void init(){
            this.mockFailingPolicy = spy(mock(FailingPolicy.class));
            device = new StandardDevice(this.mockFailingPolicy);
            // the mock is a TD used to check you are collaborating as expected
            when(mockFailingPolicy.attemptOn()).thenReturn(true, true, false);
            when(mockFailingPolicy.policyName()).thenReturn("mock");
        }

        @Test
        @DisplayName("attemptOn is called as expected")
        void testAttemptOn() {
            verify(this.mockFailingPolicy, times(0)).attemptOn();
            device.on();
            verify(this.mockFailingPolicy, times(1)).attemptOn();
            assertTrue(device.isOn());

            device.off();
            verify(this.mockFailingPolicy, times(1)).attemptOn();
            device.on();
            verify(this.mockFailingPolicy, times(2)).attemptOn();
            assertTrue(device.isOn());

            device.off();
            verify(this.mockFailingPolicy, times(2)).attemptOn();
            assertThrows(IllegalStateException.class, () -> device.on());
            verify(this.mockFailingPolicy, times(3)).attemptOn();
        }
    }
}