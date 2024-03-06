package oopGUIExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*
Classe di iteration test per controllare le interazioni tra Logic e Log,
agendo solo sulla Logic e verificando che i metodi del Log vengano chiamati nei momenti giusti.
*/
public class LogicLogTest {
    @Mock Log mockLog = mock(Log.class);
    private Logic logic;

    @BeforeEach
    void init() {
        logic = new LogicImpl(10, mockLog);
    }

    @Test
    void testNewMarkInteraction() {
        logic.hit(new Position(1,1));
        verify(mockLog, times(1)).newMark(new Position(1,1));
    }

    @Test
    void testIsOverInteraction() {
        logic.isOver();
        verify(mockLog, times(0)).isOver();
        logic.hit(new Position(9,9));
        logic.isOver();
        verify(mockLog, times(0)).isOver();
        logic.hit(new Position(8,8));
        logic.isOver();
        verify(mockLog, times(1)).isOver();
    }

    @Test
    void testMovedInteraction() {
        logic.hit(new Position(1,1));
        verify(mockLog, times(1)).newMark(new Position(1,1));
        assertEquals(1, Mockito.mockingDetails(this.mockLog).getInvocations().size());
        logic.hit(new Position(2,2));
        verify(mockLog, times(1)).moved();
    }

}
