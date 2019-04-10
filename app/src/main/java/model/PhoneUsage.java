package model;

public class PhoneUsage {

    private long startTime;
    private long endTime;

    public PhoneUsage() {}

    public PhoneUsage(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PhoneUsage{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}