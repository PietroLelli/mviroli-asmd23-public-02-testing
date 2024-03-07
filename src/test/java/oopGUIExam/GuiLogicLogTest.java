package oopGUIExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
/*
Integration test class to check the interactions between GUI, Logic and Log,
acting only on the GUI and checking that the Log methods are called at the right times.
*/
public class GuiLogicLogTest {

    @Mock Log mockLog = mock(Log.class);
    private GUI gui;

    @BeforeEach
    public void init() {
        Logic logic = new LogicImpl(10, mockLog);
        gui = new GUI(10, logic);
    }

    @Test
    void testNewMarkLog() {
        gui.clickButton(new Position(1,1));
        //Checks that the newMark log is called if I click on a cell.
        verify(mockLog, times(1)).newMark(new Position(1,1));
    }

    @Test
    void testNotNewMarkLog() {
        gui.clickButton(new Position(1,1));
        verify(mockLog, times(1)).newMark(new Position(1,1));
        gui.clickButton(new Position(1,2));
        //Check that the newMark log is not called if I click on a cell adjacent to one with the marker.
        verify(mockLog, times(0)).newMark(new Position(1,2));
    }

    @Test
    void testMovedLog() {
        gui.clickButton(new Position(7,7));
        gui.clickButton(new Position(8,8));
        //Checks that the log moved is called if I click on a cell adjacent to one with the marker.
        //and then the marks will move
        verify(mockLog, times(1)).moved();
    }
}
