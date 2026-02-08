package simulation;

import exception.ApplianceDoesntExistException;
import home_appliances.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Simulatore {

    private void parseAppliance(JSONObject appliance,Casa casa) {
        String type = appliance.getString("tipo");
        switch (type.toLowerCase()) {
            case "lavatrice":
                Lavatrice washingMachine = new Lavatrice(appliance,"velocita_centrifuga");
                casa.addAppliance(washingMachine);
                break;
            case "lavastoviglie":
                Lavastoviglie dishwasher = new Lavastoviglie(appliance,"programma");
                casa.addAppliance(dishwasher);
                break;
            case "asciugatrice":
                Asciugatrice dryer = new Asciugatrice(appliance,"temperatura_asciugatura");
                casa.addAppliance(dryer);
                break;
            case "frigorifero":
                Frigo refrigerator = new Frigo(appliance,"temperatura");
                casa.addAppliance(refrigerator);
                break;
            case "pannellifotovoltaici":
                PannelliFotovoltaici pannelli = new PannelliFotovoltaici(appliance);
                casa.addAppliance(pannelli);
                break;
            default:
                throw new ApplianceDoesntExistException("L'elettrodomestico non esiste");
        }
    }

    public void esegui(Casa casa,String fileJson) {

        var path = Paths.get(fileJson);
        byte[] configList;

        try{
            configList = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(fileJson);
            throw new RuntimeException(e);
        }

        String configString = new String(configList);
        JSONObject config = new JSONObject(configString);

        var simulation = config.getJSONObject("simulazione");

        JSONArray appliancesList = config.getJSONArray("elettrodomestici");

        casa.impostSimulation(simulation,"costo_kwh","durata_minuti");

        for (int i = 0; i < appliancesList.length(); i++) {
            JSONObject appliance = appliancesList.getJSONObject(i);
            parseAppliance(appliance,casa);
        }
        System.out.println(String.format("SIMULAZIONE: %s",casa.getNome()));
        System.out.println(String.format("Durata simulazione: %s",casa.getDurationSimulation()));

        if(simulation.has("stagione")) {
            System.out.println("Stagione: " + simulation.getString("stagione"));
        }

        System.out.println("\nINIZIO SIMULAZIONE");
        System.out.println("Inizio simulazione: " + casa.getClock().getTime());

        while(casa.isSimulationRunning()){
            casa.clockTick();

            if (casa.hasHourPass()){
                casa.updateConsume();
                scheduleActivation(casa);
            }
        }
        System.out.println("\nFINE SIMULAZIONE");
        System.out.println("Tempo finale: " + casa.getClock().getTime() + " (ore)");
        double consumoNetto = casa.getKwhUsed() - casa.getEnergiaProdotta();
        System.out.println("Consumo totale elettrodomestici: " + String.format("%.2f", casa.getKwhUsed()) + " kWh");
        System.out.println("Energia prodotta dai pannelli: " + String.format("%.2f", casa.getEnergiaProdotta()) + " kWh");
        System.out.println("Consumo netto (consumo - produzione): " + String.format("%.2f", Math.max(0, consumoNetto)) + " kWh");
        System.out.println("Costo stimato (" + String.format("%.2f", casa.getCostoKwh()) + " €/kWh): " + String.format("%.2f", casa.getconsume()) + " €");

        double risparmio = casa.getEnergiaProdotta() * casa.getCostoKwh();
        System.out.println("Risparmio grazie ai pannelli: " + String.format("%.2f", risparmio) + " €");
    }

    private void scheduleActivation(Casa casa) {
        int currentHour = casa.getClock().getTime();
        int currentDay = currentHour / 24;

        casa.getAppliances().stream()
                .filter(appliance -> !(appliance instanceof Frigo) && !(appliance instanceof PannelliFotovoltaici))
                .filter(appliance -> !appliance.isAcceso())
                .forEach(appliance -> {
                    if (appliance instanceof Lavastoviglie) {
                        if (currentHour % 24 == 8 || currentHour % 24 == 20) {
                            appliance.turnOn(180);
                        }
                    } else if (appliance instanceof Lavatrice || appliance instanceof Asciugatrice) {
                        if (currentDay % 7 == 0 || currentDay % 7 == 3) {
                            if (currentHour % 24 == 10) {
                                appliance.turnOn(180);
                            }
                        }
                    }
                });

        casa.getAppliances().stream()
                .filter(appliance -> !(appliance instanceof Frigo))
                .filter(Elettrodomestico::isAcceso)
                .forEach(Elettrodomestico::updateConsumo);
    }
}