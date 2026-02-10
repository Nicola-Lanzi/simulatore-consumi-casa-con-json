package simulation;

import java.util.Random;

public class Stagione {
    private final double irradiazione_media;
    private final double irrradiazione_min;
    private final double irradiazione_max;
    private final int tramonto;
    private final int alba;
    private final double maxNuvoloso;
    private final double maxPioggia;
    private final double maxSereno;
    private final Random r;

    public Stagione(String tipo) {
        if (tipo.equalsIgnoreCase("estate")) {
            this.irradiazione_media = 5.125;
            this.irradiazione_max = 7.5;
            this.irrradiazione_min = 2.0;
            this.tramonto = 20;
            this.alba = 6;
            this.maxSereno = 4.25;
            this.maxPioggia = 3.25;
            this.maxNuvoloso = 2.5;
        } else {
            this.irradiazione_media = 3.0;
            this.irradiazione_max = 5.0;
            this.irrradiazione_min = 1.0;
            this.tramonto = 17;
            this.alba = 7;
            this.maxSereno = 4.25;
            this.maxPioggia = 4.2;
            this.maxNuvoloso = 3.25;
        }
        this.r = new Random();
    }

    public double calcIrradiazione(int oraTotale) {
        int oraDelGiorno = oraTotale % 24;

        double prob_nuvoloso = r.nextDouble(0, maxNuvoloso);
        double prob_sereno = r.nextDouble(0, maxSereno);
        double prob_pioggia = r.nextDouble(0, maxPioggia);

        double irradiazioneMeteo = irradiazione_media + (prob_sereno - prob_nuvoloso - prob_pioggia);

        irradiazioneMeteo = Math.max(irrradiazione_min, Math.min(irradiazione_max, irradiazioneMeteo));

        double fattore_orario = 0;
        if (oraDelGiorno > alba && oraDelGiorno < tramonto) {
            fattore_orario = Math.sin(Math.PI * (double)(oraDelGiorno - alba) / (tramonto - alba));
        }

        double risultato = irradiazioneMeteo * fattore_orario;
        return Math.max(0, risultato);
    }
}