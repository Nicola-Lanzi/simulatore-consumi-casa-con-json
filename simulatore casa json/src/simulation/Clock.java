package simulation;

public class Clock {
    private int time;
    private int duration;
    private int elapsedTime;


    public Clock(){
        this.elapsedTime = 0;
        time = 0;
    }

    public void tick() {
        elapsedTime++;
        time = elapsedTime / 60;
    }



    public int getTime() {
        return time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isSimulationRunning() {
        return duration > elapsedTime;
    }

    public boolean hasHourPassed() {
        return elapsedTime % 60 == 0;
    }
}
