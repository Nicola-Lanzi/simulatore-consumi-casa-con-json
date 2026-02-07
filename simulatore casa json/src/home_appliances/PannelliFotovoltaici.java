package fotovoltaico;

import home_appliances.Elettrodomestico;

public class PannelliFotovoltaici extends Elettrodomestico {
    private int n_pannelli;
    private double energia_prodotta;
    private double rendimento;
    private double area;
    private int sun;

    public PannelliFotovoltaici() {
        super("Pannelli Fotovoltaici", 0.0);

    }


    public void setParameters(float area, int n_pannelli, double rendimento, int sun) {
        this.area = area;
        this.n_pannelli = n_pannelli;
        this.rendimento = rendimento;
        this.sun = sun;
    }


    public void aggiornaConsumo() {
        energia_prodotta += getConsumoOrario() / 60.0;
    }

    @Override
    public double getConsumoOrario() {
        return ( n_pannelli * this.rendimento * (this.sun / 1000.0) * this.area) ;
    }

    @Override
    public double getConsumoAccumulato() {
        return -energia_prodotta;
    }

    @Override
    public String toString() {
        return String.format("%s - Produzione orario: %.2f kW - Energia prodotta: %.2f kW",
                getNome(), produzioneOraria, energia_prodotta);
    }

    public void setRendimento() {
        this.rendimento =  rendimento
    }

    public void setSun(int sun) {
        this.sun = sun;
    }
}
