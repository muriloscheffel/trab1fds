package com.t1fsd.trab1fds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BarcaTest {
    @Mock
    private Relogio relogio;

    @Test
    void testaValorManha() {
        when(relogio.getHora()).thenReturn(9);
        Barca b = new Barca(relogio, 100);
        double rEsp = 100.0;
        double rObs = b.defineAssento("F12A15");
        assertEquals(rEsp, rObs);
    }

    @ParameterizedTest
    @CsvSource({ "10, 100, 100",
            "13, 100, 80",
            "16, 100, 120" })
    public void testaAoLongoDoDia(int hora, double precoBase, double preco) {
        when(relogio.getHora()).thenReturn(hora);
        Barca b = new Barca(relogio, precoBase);
        double rEsp = preco;
        double rObs = b.defineAssento("F12A15");
        assertEquals(rEsp, rObs);
    }
}
