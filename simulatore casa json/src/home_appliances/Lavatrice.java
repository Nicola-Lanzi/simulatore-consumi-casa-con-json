package home_appliances;

import exception.MissingArgumentConfigurationException;
import org.json.JSONObject;

public class Lavatrice extends Elettrodomestico {
    private final int velocitaCentrifuga;

    public Lavatrice(JSONObject washingMachineConfig,String keyName) {
        super(consumoEffettivo(washingMachineConfig,keyName));

        if (!washingMachineConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        this.velocitaCentrifuga = washingMachineConfig.getInt(keyName);
    }
    private static double consumoEffettivo(JSONObject washingMachineConfig,String keyName) {
        if (!washingMachineConfig.has("consumo_orario")) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", "consumo_orario"));
        }
        double consume = washingMachineConfig.getDouble("consumo_orario");

        if (!washingMachineConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        int velocitaCentrifuga = washingMachineConfig.getInt(keyName);
        return consume * (velocitaCentrifuga / 1200.0);
    }
}