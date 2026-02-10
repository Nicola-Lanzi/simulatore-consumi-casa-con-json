package home_appliances;

public abstract class Elettrodomestico {
    protected boolean acceso;
    protected double consumoOrario;
    protected double consumoAccumulato;
    protected int remainOn;

    public Elettrodomestico(double consumoOrario) {
        this.consumoOrario = consumoOrario;
        this.acceso = false;
        this.consumoAccumulato = 0;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public double getConsumoOrario() {
        return consumoOrario;
    }

    public void turnOn(int timeRemainOn) {
        this.remainOn = timeRemainOn;
        this.acceso = true;
    }

    public void updateConsumo() {
        if (acceso && remainOn > 0) {
            consumoAccumulato += consumoOrario;
            remainOn--;
            if (remainOn <= 0) {
                turnOff();
            }
        }
    }

    public void turnOff() {
        acceso = false;
        remainOn = 0;
    }
}