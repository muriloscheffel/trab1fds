package com.t1fsd.trab1fds;

public class Barca {
    private boolean[][] ocupados;
    private Relogio relogio;
    private double precoBase;
    private int passageiros;

    public Barca(Relogio relogio, double precoBase) {
        this.relogio = relogio;
        this.precoBase = precoBase;
        ocupados = new boolean[60][20];
        passageiros = 0;
    }

    public double defineAssento(String assentoInformado) {
        int fileira = Integer.parseInt(assentoInformado.substring(1, 3)) - 1;
        int assento = Integer.parseInt(assentoInformado.substring(4, 6)) - 1;

        if (fileira < 0 || fileira >= 60 || assento < 0 || assento >= 20)
            return -1;

        if (ocupados[fileira][assento])
            return -2;

        if (relogio.getHora() >= 8 && relogio.getHora() <= 12)
            return precoBase;

        if (relogio.getHora() > 12 && relogio.getHora() < 14)
            return precoBase * 0.8;

        if (passageiros >= 0 && passageiros < 100 && fileira >= 20)
            return -3;

        if (passageiros >= 100 && passageiros < 200 && (fileira < 40 || fileira >= 60))
            return -3;

        ocupados[fileira][assento] = true;
        passageiros++;
        return precoBase * 1.2;

    }

    public void ocupacaoArbitraria(String assentoInformado) {
    }

    public void printBarca() {
        for (int i = 0; i < ocupados.length; i++) {
            for (int j = 0; j < ocupados[1].length; j++) {
                System.out.print(ocupados[i][j] + " ");
            }
            System.out.println();
        }
    }
}
