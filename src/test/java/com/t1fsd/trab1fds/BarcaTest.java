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
        b.defineAssento("F10A10");
        double rEsp = -2;
        double rObs = b.defineAssento("F10A10");
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

    @Test
    void testaAssentoJaOcupadoComOcupacaoArbitraria() {
        Barca b = new Barca(relogio, 100);
        b.ocupacaoArbitraria("F10A10");
        double rEsp = -2;
        double rObs = b.defineAssento("F10A10");
        assertEquals(rEsp, rObs);
    }

    @Test
    void testaDistribuicaoDePeso() {
        Barca b = new Barca(relogio, 100);

        // Simula os primeiros 100 passageiros
        for (int i = 0; i < 100; i++) {
            b.defineAssento(String.format("F%02dA01", (i % 20) + 1)); // Fileiras 1 a 20
        }

        // Tenta ocupar fileira 21 com o 101º passageiro (deve falhar)
        double rEsp = -3;
        double rObs = b.defineAssento("F21A01");
        assertEquals(rEsp, rObs);

        // Simula os próximos 100 passageiros
        for (int i = 0; i < 100; i++) {
            b.defineAssento(String.format("F%02dA01", (i % 20) + 40)); // Fileiras 40 a 60
        }

        // Tenta ocupar fileira 39 com o 201º passageiro (deve ser permitido)
        rEsp = -3;
        rObs = b.defineAssento("F39A01");
        assertEquals(rEsp, rObs);
    }

    @ParameterizedTest
    @CsvSource({
            "F01A01, 100.0", // Válido
            "F60A20, 100.0", // Válido
            "F00A01, -1.0", // Inválido
            "F61A01, -1.0", // Inválido
            "F01A00, -1.0", // Inválido
            "F01A21, -1.0" // Inválido
    })
    void testaValoresLimites(String assento, double resultadoEsperado) {
        // when(relogio.getHora()).thenReturn(9);
        Barca b = new Barca(relogio, 100);
        double rObs = b.defineAssento(assento);
        assertEquals(resultadoEsperado, rObs);
    }

    @ParameterizedTest
    @CsvSource({
            "8, 0, 100.0", // Preço base
            "12, 30, 110.0", // 10% acima do preço base
            "20, 0, 120.0", // 20% acima do preço base
            "0, 0, 150.0" // 50% acima do preço base
    })
    void testaPrecosPorHorario(int hora, int minuto, double precoEsperado) {
        when(relogio.getHora()).thenReturn(hora);
        when(relogio.getMinuto()).thenReturn(minuto);
        Barca b = new Barca(relogio, 100);
        double rObs = b.defineAssento("F01A01");
        assertEquals(precoEsperado, rObs);
    }
}
