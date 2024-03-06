package oopGUIExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
/*
Classe di iteration test per controllare le interazioni tra GUI, Logic e Log,
agendo solo sulla GUI e verificando che i metodi del Log vengano chiamati nei momenti giusti.
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
        //Controlla che venga chiamato il log newMark se clicco su una cella
        verify(mockLog, times(1)).newMark(new Position(1,1));
    }

    @Test
    void testNotNewMarkLog() {
        gui.clickButton(new Position(1,1));
        verify(mockLog, times(1)).newMark(new Position(1,1));
        gui.clickButton(new Position(1,2));
        //Controlla che non venga chiamato il log newMark se clicco su una cella adiacente ad una con il mark
        verify(mockLog, times(0)).newMark(new Position(1,2));
    }

    @Test
    void testMovedLog() {
        gui.clickButton(new Position(7,7));
        gui.clickButton(new Position(8,8));
        //Controlla che venga chiamato il log moved se clicco su una cella adiacente ad una con il mark e quindi i mark si muoveranno
        verify(mockLog, times(1)).moved();
    }
}
