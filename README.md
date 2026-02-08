# Simulatore di Consumo Energetico Domestico

Un simulatore che modella il consumo energetico di una casa, inclusi elettrodomestici comuni e pannelli fotovoltaici, con supporto per diverse stagioni e condizioni meteorologiche.

## Panoramica

Il progetto simula il consumo energetico di una casa nel tempo, considerando:
- **Elettrodomestici** con comportamenti realistici
- **Pannelli fotovoltaici** che producono energia in base all'irradiazione solare
- **Variazioni stagionali** (estate/inverno) che influenzano la produzione solare
- **Condizioni meteorologiche** casuali (sereno, nuvoloso, pioggia)
- **Scheduling automatico** degli elettrodomestici

## Caratteristiche

### Elettrodomestici Supportati
- **Lavatrice** - consumo varia con la velocità di centrifuga
- **Lavastoviglie** - diversi programmi (Eco, Intensivo, Rapido)
- **Asciugatrice** - consumo dipende dalla temperatura di asciugatura
- **Frigorifero** - sempre acceso, consumo dipende dalla temperatura impostata
- **Pannelli Fotovoltaici** - producono energia in base all'irradiazione solare

### Sistema di Simulazione
- **Clock interno** che gestisce il passare del tempo (in minuti/ore)
- **Gestione delle stagioni** con parametri specifici per estate e inverno
- **Calcolo dell'irradiazione solare** basato su ora del giorno e condizioni meteorologiche
- **Scheduling automatico** degli elettrodomestici in base al giorno e all'ora

## Struttura del Progetto

```
src/
├── home_appliances/
│   ├── Elettrodomestico.java (classe base astratta)
│   ├── Lavatrice.java
│   ├── Lavastoviglie.java
│   ├── Asciugatrice.java
│   ├── Frigo.java
│   └── PannelliFotovoltaici.java
├── simulation/
│   ├── Simulatore.java (logica principale)
│   ├── Casa.java (gestione della casa)
│   ├── Clock.java (gestione del tempo)
│   └── Stagione.java (gestione stagioni e meteo)
├── exception/
│   ├── ApplianceDoesntExistException.java
│   └── MissingArgumentConfigurationException.java
└── Main.java (entry point)
```

## Configurazione

Il progetto utilizza un file JSON di configurazione (`config.json`) che deve contenere:

```json
{
  "simulazione": {
    "costo_kwh": 0.20,
    "durata_minuti": 10080,
    "stagione": "estate"
  },
  "elettrodomestici": [
    {
      "tipo": "lavatrice",
      "consumo_orario": 1.5,
      "velocita_centrifuga": 1200
    },
    {
      "tipo": "pannellifotovoltaici",
      "n_pannelli": 10,
      "area": 1.6,
      "rendimento": 0.18
    }
  ]
}
```

### Parametri di Configurazione

**Simulazione:**
- `costo_kwh`: costo per kWh in euro
- `durata_minuti`: durata totale della simulazione in minuti
- `stagione`: "estate" o "inverno" (opzionale)

**Elettrodomestici:**
- Lavatrice: `consumo_orario`, `velocita_centrifuga`
- Lavastoviglie: `consumo_orario`, `programma` (1=Eco, 2=Intensivo, 3=Rapido)
- Asciugatrice: `consumo_orario`, `temperatura_asciugatura`
- Frigo: `consumo_orario`, `temperatura`
- Pannelli: `n_pannelli`, `area`, `rendimento`

## Logica di Scheduling

Gli elettrodomestici vengono accesi automaticamente secondo questo schema:

- **Lavastoviglie**: ogni giorno alle 8:00 e 20:00
- **Lavatrice e Asciugatrice**: ogni domenica (giorno 0) e mercoledì (giorno 3) alle 10:00
- **Frigo**: sempre acceso
- **Pannelli Fotovoltaici**: sempre "accesi", producono in base alla luce solare

## Output della Simulazione

La simulazione produce un report che include:
- Durata totale della simulazione
- Consumo totale degli elettrodomestici (kWh)
- Energia prodotta dai pannelli fotovoltaici (kWh)
- Consumo netto (consumo - produzione)
- Costo stimato in euro
- Risparmio grazie ai pannelli fotovoltaici

## Eccezioni Personalizzate

- `ApplianceDoesntExistException`: quando si tenta di creare un elettrodomestico non supportato
- `MissingArgumentConfigurationException`: quando mancano parametri obbligatori nel file JSON

## Esempio di Output

```
SIMULAZIONE: nicola,diego,francesca,manuel
Durata simulazione: 10080
Stagione: estate

INIZIO SIMULAZIONE
Inizio simulazione: 0

FINE SIMULAZIONE
Tempo finale: 168 (ore)
Consumo totale elettrodomestici: 45.67 kWh
Energia prodotta dai pannelli: 28.34 kWh
Consumo netto (consumo - produzione): 17.33 kWh
Costo stimato (0.20 €/kWh): 3.47 €
Risparmio grazie ai pannelli: 5.67 €
```
