package com.t1fsd.trab1fds;

public class Barca {
    
    private Relogio relogio;
    private double precoBase;

    public Barca(Relogio relogio, double precoBase) {
        this.relogio = relogio;
        this.precoBase = precoBase;
    }

    public double defineAssento(String assentoInformado) {
        if (relogio.getHora() >= 8 && relogio.getHora() <= 12) {
            return precoBase;
        }
        if (relogio.getHora() > 12 && relogio.getHora() < 14) {
            return precoBase * 0.8;
        }
        return precoBase * 1.2;
    }

    public void ocupacaoArbitraria(String assentoInformado) {

    }
}
