package home_appliances;

public class PannelliFotovoltaici extends Elettrodomestico {
    private int n_pannelli;
    private double energia_prodotta;
    private double rendimento;
    private double area;
    private int potenza_sole;

    public PannelliFotovoltaici() {
        super("Pannelli Fotovoltaici", 0.0);

    }


    public void setParameters(float area, int n_pannelli, double rendimento, int potenza_sole) {
        this.area = area;
        this.n_pannelli = n_pannelli;
        this.rendimento = rendimento;
        this.potenza_sole = potenza_sole;
    }


    public void aggiornaConsumo() {
        energia_prodotta += getConsumoOrario() / 60.0;
    }

    @Override
    public double getConsumoOrario() {
        return ( n_pannelli * this.rendimento * (this.potenza_sole / 1000.0) * this.area) ;
    }

    @Override
    public double getConsumoAccumulato() {
        return -energia_prodotta;
    }

    @Override
    public String toString() {
        return String.format("%s - Produzione orario: %.2f kW - Energia prodotta: %.2f kW",
                getNome(), getConsumoOrario(), energia_prodotta);
    }

    public void setRendimento(double rendimento) {
        this.rendimento =  rendimento;
    }

    public void setPotenzaSole(int potenza_sole) {
        this.potenza_sole = potenza_sole;
    }

    public void setNPannelli(int n_pannelli) {
        this.n_pannelli = n_pannelli;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
