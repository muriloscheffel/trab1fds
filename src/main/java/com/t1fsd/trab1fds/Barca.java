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

    public int size() {
        return passageiros;
    }

    public double defineAssento(String assentoInformado) {
        int fileira = Integer.parseInt(assentoInformado.substring(1, 3));
        int assento = Integer.parseInt(assentoInformado.substring(4, 6));
        int hora = relogio.getHora();
        int minuto = relogio.getMinuto();

        if (fileira < 0 || fileira >= 60 || assento < 0 || assento >= 20) {
            return -1; 
        }

        if (ocupados[fileira][assento]) {
            return -2;
        }

        if (passageiros < 100 && fileira >= 20) {
            return -3;
        }

        if (passageiros >= 100 && passageiros < 200 && (fileira < 40 || fileira >= 60)) 
            return -3;
        
            
        ocupados[fileira][assento] = true;
        passageiros++;

        if ((hora == 12 && minuto > 0) || hora == 13)
            return precoBase * 1.1;

        if ((hora == 18 && minuto > 0) || hora == 19)
            return precoBase * 1.1;

        if ((hora >= 8 && hora <= 12) || (hora >= 14 && hora <= 18))
            return precoBase;

        if (hora >= 18 && hora < 20)
            return precoBase * 1.1;

        if (hora >= 20 && hora <= 23)
            return precoBase * 1.2;

        if (hora >= 0 && hora <= 7) {
            return precoBase * 1.5;
        }

        return precoBase;
    }

    public void ocupacaoArbitraria(String assentoInformado) {
        int fileira = Integer.parseInt(assentoInformado.substring(1, 3)) - 1;
        int assento = Integer.parseInt(assentoInformado.substring(4, 6)) - 1;

        ocupados[fileira][assento] = true;
        passageiros++;
    }

    public void printBarca() {
        for (int i = 0; i < ocupados.length; i++) {
            for (int j = 0; j < ocupados[1].length; j++) {
                System.out.print(ocupados[i][j] + " ");
            }
            System.out.println("Fileira" + i);
        }
    }
}
