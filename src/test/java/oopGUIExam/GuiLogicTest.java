package oopGUIExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
/*
Integration test class to check the interactions between the GUI and Logic,
acting only on the GUI and checking that Logic methods are called.
*/
public class GuiLogicTest {
    @Mock Logic mockLogic = mock(Logic.class);
    private GUI gui;
    
    @BeforeEach
    public void init() {
        gui = new GUI(10, mockLogic);
    }

    @Test
    public void testGuiHit() {
        gui.clickButton(new Position(1, 1));
        //Checks that when a button is clicked, the Logic hit method is called.
        verify(mockLogic, times(1)).hit(new Position(1,1));
        //Checks that the Logic hit method is not called on a position other than the one clicked.
        verify(mockLogic, times(0)).hit(new Position(2,1));
    }
}
