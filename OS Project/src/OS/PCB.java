package OS;
public class PCB {
    private String processId;
    private int priority;
    private int arrivalTime;
    private int cpuBurst;
    private int startTime;
    private int terminationTime;
    private int turnAroundTime;
    private int waitingTime;
    private int responseTime;   

    public PCB(int processNumber, int priority, int arrivalTime, int cpuBurst) {
        this.processId = "P" + processNumber;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.startTime = 0;
        this.terminationTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.responseTime = 0;
    }

    public String toString() {
        return String.format("Process ID: %s, Priority: %d, Arrival Time: %d, CPU Burst: %d, " +
                "Start Time: %d, Termination Time: %d, Turnaround Time: %d, Waiting Time: %d, " +
                "Response Time: %d",
                processId, priority, arrivalTime, cpuBurst, startTime, terminationTime,
                turnAroundTime, waitingTime, responseTime);
    }

    public String getProcessId() {
        return processId;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getTerminationTime() {
        return terminationTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public void calculateMetrics() {
    this.turnAroundTime = terminationTime - arrivalTime; 
    waitingTime = turnAroundTime - cpuBurst;
    responseTime = startTime - arrivalTime;
}
}
