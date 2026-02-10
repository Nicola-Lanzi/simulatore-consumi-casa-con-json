package home_appliances;

import simulation.Clock;

import java.util.Objects;

public abstract class Riscaldamento {
    private final String nome;
    private boolean acceso;
    private final double consumoOrario;
    private double consumoAccumulato;
    private final float desiredTemperature;

    public Riscaldamento(String nome, double consumoOrario, float desiredTemperature) {
        this.nome = nome;
        this.consumoOrario = consumoOrario;
        this.acceso = false;
        this.consumoAccumulato = 0;
        this.desiredTemperature = desiredTemperature;
    }

    public void aggiornaConsumo() {
        if (acceso) {
            consumoAccumulato += consumoOrario;
        }
    }

    public void ChangeStatus (Clock clock) {
        setAcceso(clock.getTemperature() < getDesiredTemperature());
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public double getConsumoOrario() {
        return consumoOrario;
    }

    public float getDesiredTemperature() {
        return desiredTemperature;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - Consumo orario: %.2f kW - Consumo accumulato: %.2f kWh",
                nome, acceso ? "ACCESO" : "SPENTO", consumoOrario, consumoAccumulato);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, acceso, consumoOrario, consumoAccumulato, desiredTemperature);
    }
}