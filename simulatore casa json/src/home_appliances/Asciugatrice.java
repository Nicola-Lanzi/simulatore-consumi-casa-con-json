package home_appliances;

import exception.MissingArgumentConfigurationException;
import org.json.JSONObject;

public class Asciugatrice extends Elettrodomestico {
    private int temperaturaAsciugatura;

    public Asciugatrice(JSONObject dryerConfig,String keyName) {
        super(consumoEffettivo(dryerConfig,keyName));

        if (!dryerConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        this.temperaturaAsciugatura = dryerConfig.getInt(keyName);
    }

    private static double consumoEffettivo(JSONObject dryerConfig,String keyName) {
        if (!dryerConfig.has("consumo_orario")) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", "consumo_orario"));
        }
        double consume = dryerConfig.getDouble("consumo_orario");

        if (!dryerConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        int temperaturaAsciugatura = dryerConfig.getInt(keyName);
        return consume * (temperaturaAsciugatura / 60.0);
    }

}