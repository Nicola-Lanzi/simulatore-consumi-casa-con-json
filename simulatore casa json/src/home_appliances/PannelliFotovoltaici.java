package home_appliances;

import exception.MissingArgumentConfigurationException;
import org.json.JSONObject;

public class PannelliFotovoltaici extends Elettrodomestico {
    private final int n_pannelli;
    private double energia_prodotta;
    private final double rendimento;
    private final double area;
    private double fattore_irradiazione;

    public PannelliFotovoltaici(JSONObject pannelliConfig) {
        super(0.0);

        if (!pannelliConfig.has("n_pannelli")) {
            throw new MissingArgumentConfigurationException("n_pannelli non è presente nella configurazione");
        }
        if (!pannelliConfig.has("area")) {
            throw new MissingArgumentConfigurationException("area non è presente nella configurazione");
        }
        if (!pannelliConfig.has("rendimento")) {
            throw new MissingArgumentConfigurationException("rendimento non è presente nella configurazione");
        }

        this.n_pannelli = pannelliConfig.getInt("n_pannelli");
        this.area = pannelliConfig.getDouble("area");
        this.rendimento = pannelliConfig.getDouble("rendimento");
        this.fattore_irradiazione = 1.0;
        this.acceso = true;
    }

    public void setFattoreIrradiazione(double fattore) {
        this.fattore_irradiazione = fattore;
    }

    @Override
    public double getConsumoOrario() {
        double potenza_sole = 1367.0;
        double produzione = n_pannelli * rendimento * (potenza_sole / 1000.0) * area * fattore_irradiazione;
        return -produzione;
    }

    @Override
    public void updateConsumo() {
        if (acceso) {
            double produzioneOraria = -getConsumoOrario();
            energia_prodotta += produzioneOraria;
        }
    }

    public double getEnergiaProdotta() {
        return energia_prodotta;
    }
}