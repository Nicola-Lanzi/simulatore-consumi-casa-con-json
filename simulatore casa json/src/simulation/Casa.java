package simulation;

import home_appliances.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Casa {
    private final List<Elettrodomestico> elettrodomestici;
    private final Clock clock;
    private String nome;
    private Double costkwh;
    private double kwhUtilized;
    private Double consume;

    public String getNome() {
        return nome;
    }

    public Casa(String nome){
        this.elettrodomestici = new ArrayList<>();
        this.clock = new Clock();
        this.nome = nome;
        this.costkwh = 0D;
        this.consume = 0D;
        this.kwhUtilized = 0D;
    }

    public void addAppliance(Elettrodomestico appliance) {
        elettrodomestici.add(appliance);
    }

    public Clock getClock() {
        return clock;
    }

    public void impostSimulation(JSONObject simulation,String costName,String durationName) {
        this.costkwh = simulation.getDouble(costName);
        clock.setDuration(simulation.getInt(durationName));
    }

    public int getDurationSimulation() {
        return clock.getDuration();
    }

    public boolean isSimulationRunning() {
        return clock.isSimulationRunning();
    }

    public void clockTick() {
        clock.tick();
    }

    public boolean hasHourPass() {
        return clock.hasHourPassed();
    }

    public List<Elettrodomestico> getAppliances() {
        return elettrodomestici;
    }

    public void updateConsume() {
        elettrodomestici.stream()
                .filter(Elettrodomestico::isAcceso)
                .forEach(Elettrodomestico::updateConsumo);

        double kwUsedThisHour = elettrodomestici.stream()
                .filter(Elettrodomestico::isAcceso)
                .mapToDouble(Elettrodomestico::getConsumoOrario)
                .sum();
        kwhUtilized += kwUsedThisHour;
    }

    public double getKwhUsed() {
        return kwhUtilized;
    }

    public double getconsume() {
        return kwhUtilized * costkwh;
    }

    public double getCostoKwh() {
        return costkwh;
    }
}