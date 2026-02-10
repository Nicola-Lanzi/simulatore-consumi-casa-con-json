import simulation.*;

void main() {
        Casa casa1 = new Casa("nicola,diego,francesca,manuel");
        String fileJson = "config.json";
        Simulatore simulatore = new Simulatore();
        simulatore.esegui(casa1, fileJson);
}
