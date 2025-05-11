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
        when(relogio.getHora()).thenReturn(12);
        when(relogio.getMinuto()).thenReturn(1);
        Barca b = new Barca(relogio, 100);
        double rEsp = 110.0;
        double rObs = b.defineAssento("F12A15");
        assertEquals(rEsp, rObs, 0.00001);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 150.0",
            "0, 1, 150.0",
            "7, 59, 150.0",
            "8, 0, 100.0",
            "11, 59, 100.0",
            "12, 0, 100.0",
            "12, 1, 110.0",
            "13, 0, 110.0",
            "13, 59, 110.0",
            "14, 0, 100.0",
            "17, 59, 100.0",
            "18, 0, 100.0",
            "18, 1, 110.0",
            "19, 0, 110.0",
            "19, 59, 110.0",
            "20, 0, 120.0",
            "20, 1, 120.0",
            "23, 59, 120.0",
    })
    void testaPrecoAoLongoDoDia(int hora, int minuto, double precoEsperado) {
        when(relogio.getHora()).thenReturn(hora);
        when(relogio.getMinuto()).thenReturn(minuto);
        Barca b = new Barca(relogio, 100.0);
        double rObs = b.defineAssento("F12A15");
        assertEquals(precoEsperado, rObs, 0.00001);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, -1",
            "21, -1",
            "00, 100.0",
            "01, 100.0",
            "11, 100.0",
            "20, 100.0"
    })
    void testeLimiteAssento(String assento, double resp) {
        when(relogio.getHora()).thenReturn(9);
        when(relogio.getMinuto()).thenReturn(1);
        Barca b = new Barca(relogio, 100.0);
        double rObs = b.defineAssento("F02" + "A" + assento);
        assertEquals(resp, rObs, 0.00001);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, -1",
            "00, 100.0",
            "01, 100.0",
            "10, 100.0",
            "20, 100.0",
            "21, -3",
            "59, -3",
            "60, -1",
            "61, -1"

    })
    void testaLimiteFileiraCemIniciais(String fileira, double resp) {
        when(relogio.getHora()).thenReturn(9);
        when(relogio.getMinuto()).thenReturn(1);
        Barca b = new Barca(relogio, 100.0);
        double rObs = b.defineAssento("F" + fileira + "A01");
        assertEquals(resp, rObs, 0.0001);
    }

    @Test
    void testaAssentoOcupado() {
        Barca b = new Barca(relogio, 100.0);
        b.defineAssento("F02A01");
        double rObs = b.defineAssento("F02A01");
        assertEquals(-2, rObs, 0.0001);
    }

    @Test
    void testaDistribuicaoDePeso() {
        when(relogio.getHora()).thenReturn(15);
        Barca b = new Barca(relogio, 100);

        // Simula os primeiros 100 passageiros (fileiras 1 a 20)
        for (int i = 0; i < 100; i++) {
            int fileira = i / 5; // Cada fileira tem 5 assentos
            int assento = i % 5; // Assentos de 0 a 4 em cada fileira
            String assentoStr = String.format("F%02dA%02d", fileira, assento);
            b.defineAssento(assentoStr);
        }

        System.out.println(b.size());
        // Verifica que fileiras 21 a 60 estão bloqueadas para o 101º passageiro
        double rEsp = -3;
        double rObs = b.defineAssento("F21A01");
        assertEquals(rEsp, rObs);

        // Simula os próximos 100 passageiros (fileiras 40 a 60)
        for (int i = 0; i < 100; i++) {
            int fileira = (i / 5) + 40; // Fileiras de 40 a 60
            int assento = (i % 5) + 1; // Assentos de 1 a 5
            String assentoStr = String.format("F%02dA%02d", fileira, assento);
            double resultado = b.defineAssento(assentoStr);
            if (resultado < 0) { // Assento rejeitado
                System.out.println("Assento rejeitado: " + assentoStr + " -> Resultado: " + resultado);
            }
        }

        // b.printBarca();
        System.out.println(b.size());

        // Verifica que passageiros acima de 200 podem ocupar qualquer fileira livre
        rObs = b.defineAssento("F39A01");
        assertEquals(100.0, rObs); // Fileira 39 deve estar disponível

        rObs = b.defineAssento("F21A01");
        assertEquals(100.0, rObs); // Fileira 21 deve estar disponível
    }

}
