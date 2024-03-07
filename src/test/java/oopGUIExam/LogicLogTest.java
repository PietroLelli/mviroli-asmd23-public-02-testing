package oopGUIExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*
Integration test class to check the interactions between Logic and Log,
acting only on Logic and verifying that the Logic methods are called at the right times.
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
        //Checks that when the Logic hit method is called, the Log class makes a print of type newMark.
        verify(mockLog, times(1)).newMark(new Position(1,1));
    }

    @Test
    void testIsOverInteraction() {
        logic.isOver();
        //Checks that when the Logic isOver method is called, the Log class does not print isOver because no marker has left the borders yet
        verify(mockLog, times(0)).isOver();
        logic.hit(new Position(9,9));
        logic.isOver();
        //Checks that when the Logic isOver method is called, the Log class does not print isOver because no marker has left the borders yet
        verify(mockLog, times(0)).isOver();
        logic.hit(new Position(8,8));
        logic.isOver();
        //Checks that when the Logic isOver method is called, the Log class does an isOver printout because the marker has left the borders
        verify(mockLog, times(1)).isOver();
    }

    @Test
    void testMovedInteraction() {
        logic.hit(new Position(1,1));
        verify(mockLog, times(1)).newMark(new Position(1,1));
        logic.hit(new Position(2,2));
        //Checks that the Log class did not print newMark, because a position adjacent to one with a marker was selected.
        verify(mockLog, times(0)).newMark(new Position(2,2));
        //Checks that the Log class has made a moved print, meaning the markers have moved.
        verify(mockLog, times(1)).moved();
    }

}
