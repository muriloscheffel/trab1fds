package com.t1fsd.trab1fds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

    @Test
    void testaAssentoForaDosLimites() {
        Barca b = new Barca(relogio, 100);
        double rEsp = -1;
        double rObs = b.defineAssento("F61A01"); // Fileira 61 não existe
        assertEquals(rEsp, rObs);
    }

    @Test
    void testaAssentoJaOcupado() {
        Barca b = new Barca(relogio, 100);
        b.defineAssento("F10A10"); // Ocupa o assento
        double rEsp = -2;
        double rObs = b.defineAssento("F10A10"); // Tenta ocupar novamente
        assertEquals(rEsp, rObs);
    }

    @Test
    void testaCondicaoPassageirosFileira() {
        when(relogio.getHora()).thenReturn(15);
        Barca b = new Barca(relogio, 100);
        for (int i = 0; i < 101; i++) {
            b.defineAssento(String.format("F%02dA01", i % 60 + 1)); // Simula ocupação
        }
        double rEsp = -3;
        double rObs = b.defineAssento("F21A01"); // Fileira 21 com mais de 100 passageiros
        assertEquals(rEsp, rObs);
    }

    @ParameterizedTest
    @CsvSource({
            "F61A01, -1", // Assento fora dos limites
            "F00A01, -1", // Fileira inválida
            "F10A21, -1", // Assento inválido
    })
    void testaCasosDeErro(String assento, double resultadoEsperado) {
        Barca b = new Barca(relogio, 100);
        double rObs = b.defineAssento(assento);
        assertEquals(resultadoEsperado, rObs);
    }

    @Test
    void testaLimitePassageiros() {
        Barca b = new Barca(relogio, 100);
        for (int i = 0; i < 100; i++) {
            b.defineAssento(String.format("F%02dA01", i % 60 + 1)); // Simula ocupação
        }
        double rEsp = -3;
        double rObs = b.defineAssento("F21A01"); // Fileira 21 com 100 passageiros
        assertEquals(rEsp, rObs);
    }
}
