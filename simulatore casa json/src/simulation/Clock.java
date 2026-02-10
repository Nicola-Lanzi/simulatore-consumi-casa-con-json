package simulation;

public class Clock {
    private int hour;
    private int day;
    private int totalTick;
    private int simulationDuration;
    private float temperature;
    private Season season;

    public Clock(int simulationDuration) {
        this.hour = 0;
        this.day = 0;
        this.simulationDuration = simulationDuration;
        this.temperature = 15;
        season = Season.SPRING;
        totalTick = 0;
    }

    public void tick() {
        increment();
        totalTick++;
    }

    private void increment() {
        hour += 1;
        if (hour >= 24) {
            hour = 0;
            day++;
            simulationDuration -= 1;
        }
        if (hour == 0 && day % 10 == 0 && day != 0){
            switch (season) {
                case SPRING -> season = Season.SUMMER;
                case SUMMER -> season = Season.AUTUMN;
                case AUTUMN -> season = Season.WINTER;
                case WINTER -> season = Season.SPRING;
            }
        }
        switch (season) {
            case SPRING : if (hour > 7 && hour <= 12) {
                temperature += 0.5f;
            } else if (hour > 12 && hour <= 17) {
                temperature += 1.2f;
            } else if (hour > 17 && hour <= 21) {
                temperature -= 0.3f;
            } else {
                temperature -= 0.7f;
            }
                break;
            case SUMMER : if (hour > 7 && hour <= 12) {
                temperature += 1f;
            } else if (hour > 12 && hour <= 17) {
                temperature += 2f;
            } else if (hour > 17 && hour <= 21) {
                temperature -= 0.4f;
            } else {
                temperature -= 0.8f;
            }
                break;
            case AUTUMN : if (hour > 7 && hour <= 12) {
                temperature += 0.35f;
            } else if (hour > 12 && hour <= 17) {
                temperature += 0.7f;
            } else if (hour > 17 && hour <= 21) {
                temperature -= 0.7f;
            } else {
                temperature -= 1.2f;
            }
                break;
            case WINTER : if (hour > 7 && hour <= 12) {
                temperature += 0.2f;
            } else if (hour > 12 && hour <= 17) {
                temperature += 0.55f;
            } else if (hour > 17 && hour <= 21) {
                temperature -= 0.9f;
            } else {
                temperature -= 1.5f;
            }
                break;
        }
    }

    public boolean isSimulationRunning() {
        return simulationDuration > 0;
    }

    public int getTime() {
        return day * 24 + hour;
    }

    public int getDay() {
        return day;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getDuration() {
        return simulationDuration + day;
    }

    public void setDuration(int simulationDuration) {
        this.simulationDuration = simulationDuration;
    }

    public boolean hasHourPassed() {
        return totalTick % 1 == 0;
    }

    @Override
    public String toString() {
        return "Giorno " + day + " - " + String.format("%02d", hour) + ":00";
    }
}