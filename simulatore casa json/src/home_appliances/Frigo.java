package home_appliances;

import exception.MissingArgumentConfigurationException;
import org.json.JSONObject;

public class Frigo extends Elettrodomestico {
    private final double temperatura;

    public Frigo(JSONObject refrigeratorConfig, String keyName) {
        super(costoEffettivo(refrigeratorConfig, keyName));

        if (!refrigeratorConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        this.temperatura = refrigeratorConfig.getInt(keyName);
        acceso = true;
    }

    private static double costoEffettivo(JSONObject refrigeratorConfig, String keyName) {
        if (!refrigeratorConfig.has("consumo_orario")) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", "consumo_orario"));
        }
        double consume = refrigeratorConfig.getDouble("consumo_orario");

        if (!refrigeratorConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        double temperatura = refrigeratorConfig.getDouble(keyName);

        return consume * (8 / temperatura);

    }

}