import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestList {
    @Test
    public void test() {
        List<String> listMock = mock(List.class);
        when(listMock.size()).thenReturn(5);
        assertEquals(5, listMock.size());

        listMock.add("5");
        listMock.clear();

        verify(listMock).clear();
        verify(listMock).add("5");

    }
}
