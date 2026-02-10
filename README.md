# Simulatore di Consumo Energetico Domestico

Un'applicazione Java per simulare il consumo energetico di una casa, inclusi elettrodomestici, sistemi di riscaldamento e pannelli fotovoltaici.

## Descrizione

Questo progetto simula il consumo energetico di una abitazione nel tempo, tenendo conto di:
- Vari elettrodomestici con comportamenti diversi
- Sistemi di riscaldamento che si attivano in base alla temperatura
- Pannelli fotovoltaici che producono energia in base alla stagione e all'irradiazione solare
- Variazioni stagionali della temperatura e dell'irradiazione solare

## Struttura del Progetto

### Package Principal

- **`simulation`**: Contiene le classi core della simulazione
- **`home_appliances`**: Contiene le classi degli elettrodomestici
- **`exception`**: Contiene le eccezioni personalizzate

### Classi Principali

#### Package `simulation`
- **`Casa`**: Classe principale che rappresenta una casa con tutti i suoi componenti
- **`Simulatore`**: Gestisce l'esecuzione della simulazione
- **`Clock`**: Gestisce il tempo della simulazione (ore, giorni, stagioni)
- **`Stagione`**: Gestisce l'irradiazione solare in base alla stagione
- **`Season`**: Enumerazione delle stagioni

#### Package `home_appliances`
- **`Elettrodomestico`**: Classe astratta base per tutti gli elettrodomestici
- **`Lavatrice`**: Rappresenta una lavatrice
- **`Lavastoviglie`**: Rappresenta una lavastoviglie
- **`Asciugatrice`**: Rappresenta un'asciugatrice
- **`Frigo`**: Rappresenta un frigorifero
- **`PannelliFotovoltaici`**: Rappresenta pannelli solari fotovoltaici
- **`Riscaldamento`**: Classe astratta per i sistemi di riscaldamento

#### Package `exception`
- **`ApplianceDoesntExistException`**: Eccezione per elettrodomestici non riconosciuti
- **`MissingArgumentConfigurationException`**: Eccezione per parametri di configurazione mancanti

## Come Eseguire

### Prerequisiti
- Java 11 o superiore
- Libreria JSON (org.json)

### Compilazione ed Esecuzione

1. Compilare tutte le classi Java:
```bash
javac -cp ".;json-java.jar" simulation/*.java home_appliances/*.java exception/*.java
```

2. Eseguire il programma principale:
```bash
java -cp ".;json-java.jar" Main
```

## Configurazione

Il sistema utilizza un file JSON (`config.json`) per configurare la simulazione:

### Struttura del file di configurazione:
```json
{
  "simulazione": {
    "durata_ore": 24,
    "costo_kwh": 0.20,
    "stagione": "estate"
  },
  "elettrodomestici": [
    {
      "tipo": "Lavatrice",
      "velocita_centrifuga": 1400,
      "consumo_orario": 2.0
    },
    ...
  ],
  "riscaldamento": [
    {
      "tipo": "Termoventilatore",
      "consumo_orario": 1.6,
      "temperatura_desiderata": 15.0
    },
    ...
  ]
}
```

### Parametri supportati:

#### Elettrodomestici:
- **Lavatrice**: `tipo`, `velocita_centrifuga`, `consumo_orario`
- **Lavastoviglie**: `tipo`, `programma`, `consumo_orario`
- **Asciugatrice**: `tipo`, `temperatura_asciugatura`, `consumo_orario`
- **Frigorifero**: `tipo`, `temperatura`, `consumo_orario`
- **PannelliFotovoltaici**: `tipo`, `n_pannelli`, `area`, `rendimento`, `consumo_orario`

#### Riscaldamento:
- `tipo`: Nome del sistema di riscaldamento
- `consumo_orario`: Consumo in kWh all'ora
- `temperatura_desiderata`: Temperatura alla quale si attiva il sistema

## Output della Simulazione

La simulazione produce un output che include:
- Informazioni iniziali sulla simulazione
- Stato della simulazione durante l'esecuzione
- Risultati finali:
  - Consumo totale degli elettrodomestici
  - Energia prodotta dai pannelli fotovoltaici
  - Consumo netto (consumo - produzione)
  - Costo stimato
  - Risparmio grazie ai pannelli fotovoltaici
  - Costo finale

## Funzionalità

### Gestione del Tempo
- Simulazione di ore, giorni e stagioni
- Variazione della temperatura in base alla stagione e all'ora del giorno
- Cambi stagionali automatici

### Elettrodomestici
- Accensione/spegnimento programmato
- Consumo variabile in base ai parametri (es. velocità centrifuga, programma)
- Frigo sempre acceso
- Pannelli fotovoltaici con produzione variabile in base all'irradiazione

### Riscaldamento
- Attivazione automatica quando la temperatura scende sotto la soglia desiderata
- Consumo orario costante quando attivi

### Schedulazione Automatica
- Lavastoviglie: attivazione alle 8:00 e 20:00
- Lavatrice e Asciugatrice: attivazione il giorno 0 e 3 di ogni settimana alle 10:00

## Esempio di Configurazione

Vedi il file `config.json` incluso per un esempio completo con:
- 7 elettrodomestici diversi
- 17 sistemi di riscaldamento di vari tipi
- Pannelli fotovoltaici
- Simulazione di 24 ore in estate

## Calcoli Energetici

1. **Consumo elettrodomestici**: Somma dei consumi orari di tutti gli elettrodomestici accesi
2. **Consumo riscaldamento**: Somma dei consumi dei sistemi attivi
3. **Produzione pannelli**: Calcolata in base a:
   - Numero di pannelli
   - Area
   - Rendimento
   - Irradiazione solare (dipendente da stagione e ora del giorno)
4. **Consumo netto**: Consumo totale - produzione pannelli
5. **Costo totale**: Consumo netto × costo per kWh

## Risoluzione Problemi

### Errori Comuni:
1. **File JSON non trovato**: Verificare il percorso del file di configurazione
2. **Parametri mancanti**: Tutti i parametri richiesti devono essere presenti nel JSON
3. **Tipo elettrodomestico non riconosciuto**: Verificare l'ortografia nel campo "tipo"


