package home_appliances;

import exception.MissingArgumentConfigurationException;
import org.json.JSONObject;


public class Lavastoviglie extends Elettrodomestico {
    private int programma;
   public Lavastoviglie(JSONObject dishwasherConfig,String keyName){
       super(consumoEffettivo(dishwasherConfig,keyName));

       this.programma = dishwasherConfig.getInt(keyName);
   }
    private static double consumoEffettivo(JSONObject dishwasherConfig,String keyName){
        if (!dishwasherConfig.has("consumo_orario")) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", "consumo_orario"));
        }
        double consume = dishwasherConfig.getDouble("consumo_orario");

        if (!dishwasherConfig.has(keyName)) {
            throw new MissingArgumentConfigurationException(String.format("%s non è presente nella configurazione", keyName));
        }
        int programma = dishwasherConfig.getInt(keyName);
        return switch (programma) {
            case 1 -> // Eco
                    1D;
            case 2 -> // Intensivo
                    1.5;
            case 3 -> // Rapido
                    1.0;
            default -> throw new MissingArgumentConfigurationException("programma sbagliato");
        };
    }
}