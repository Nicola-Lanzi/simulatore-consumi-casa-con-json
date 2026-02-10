import simulation.*;

void main() {
        Casa casa1 = new Casa("4DI");
        String fileJson = "config.json";
        Simulatore simulatore = new Simulatore();
        simulatore.esegui(casa1, fileJson);
}

