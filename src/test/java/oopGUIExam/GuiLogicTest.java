package oopGUIExam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
/*
Classe di iteration test per controllare le interazioni tra GUI e Logic,
agendo solo sulla GUI e verificando che i metodi della Logic vengano chiamati.
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
        //Verifica che al click di un bottone venga chiamato il metodo hit della Logic.
        verify(mockLogic, times(1)).hit(new Position(1,1));
        //Verifica che non venga chiamato il metodo hit della Logic su una posizione diversa da quella cliccata.
        verify(mockLogic, times(0)).hit(new Position(2,1));
    }
}
