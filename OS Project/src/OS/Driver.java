    package OS;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;


public class Driver {
    private static  int MAX_PROCESSES ;
    private static PCB[] q1 ;
    private static PCB[] q2 ;
    private static PCB[] completedProcesses=new PCB[MAX_PROCESSES];
    private static int q1Size = 0;
    private static int q2Size = 0;
    private static int completedProcessCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Enter processes");
            System.out.println("2. Display scheduling report");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    enterProcesses(scanner);
                    break;
                case 2:
                    displaySchedulingReport();
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 3);
        scanner.close();
    }

    private static void enterProcesses(Scanner scanner) {
       System.out.print("Enter the total number of processes: ");
         MAX_PROCESSES = scanner.nextInt();
          q1 = new PCB[MAX_PROCESSES];
          q2 = new PCB[MAX_PROCESSES];
        for (int i = 0; i < MAX_PROCESSES; i++) {
            System.out.println("Enter process information " + (i + 1));
            System.out.print("Priority (1 or 2): ");
            int priority = scanner.nextInt();
            System.out.print("Arrival time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("CPU burst time: ");
            int cpuBurst = scanner.nextInt();

            PCB process = new PCB(i + 1, priority, arrivalTime, cpuBurst);
            if (priority == 1 && q1Size < MAX_PROCESSES) {
                q1[q1Size++] = process;
            } else if (priority == 2 && q2Size < MAX_PROCESSES) {
                q2[q2Size++] = process;
            } else {
                System.out.println("Invalid priority or queue is full, enter 1 or 2 and ensure queue is not full.");
                i--;
            }
        }
    }

    private static void displaySchedulingReport() {
        PCB process;
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int totalResponseTime = 0;
        int processesCount = 0;
        StringBuilder schedulingOrder = new StringBuilder("Scheduling Order: [");

        while ((process = getNextProcess()) != null) {
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }
            process.setStartTime(currentTime);
            process.setTerminationTime(currentTime + process.getCpuBurst());
            process.calculateMetrics();

            // Update aggregated values for reporting
            totalTurnaroundTime += process.getTurnAroundTime();
            totalWaitingTime += process.getWaitingTime();
            totalResponseTime += process.getResponseTime();
            processesCount++;
     
            // Save completed process
            if (completedProcessCount < MAX_PROCESSES) {
                completedProcesses[completedProcessCount++] = process;
            } else {
                System.out.println("Maximum number of completed processes reached");
                break;
            }

            schedulingOrder.append("P").append(process.getProcessId()).append(" | ");
            currentTime = process.getTerminationTime();
        }

        if (schedulingOrder.toString().endsWith(" | ")) {
            schedulingOrder = new StringBuilder(schedulingOrder.substring(0, schedulingOrder.length() - 3));
        }
        schedulingOrder.append("]");

        // Calculate averages
        double avgTurnaroundTime = (double) totalTurnaroundTime / processesCount;
        double avgWaitingTime = (double) totalWaitingTime / processesCount;
        double avgResponseTime = (double) totalResponseTime / processesCount;

        // Write report 
        try (FileWriter writer = new FileWriter("schedulingReport.txt")) {
            writer.write(schedulingOrder.toString() + "\n");
            writer.write("Average Turnaround Time: " + avgTurnaroundTime + "\n");
            writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
            writer.write("Average Response Time: " + avgResponseTime + "\n");
            writer.flush();


            System.out.println(schedulingOrder);
            System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
            System.out.println("Average Waiting Time: " + avgWaitingTime);
            System.out.println("Average Response Time: " + avgResponseTime);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the scheduling report: " + e.getMessage());
        }

 //Display scheduling order
    System.out.println("Scheduling Order: " + schedulingOrder);

    //Display detailed information about each process
    System.out.println("Detailed Information:");

    for (int i = 0; i < completedProcessCount; i++) {
        PCB completedProcess = completedProcesses[i];
        System.out.println("Process ID: " + completedProcess.getProcessId());
        System.out.println("Start Time: " + completedProcess.getStartTime());
        System.out.println("Termination Time: " + completedProcess.getTerminationTime());
        System.out.println("CPU Burst: " + completedProcess.getCpuBurst());
        System.out.println("Turnaround Time: " + completedProcess.getTurnAroundTime());
        System.out.println("Waiting Time: " + completedProcess.getWaitingTime());
        System.out.println("Response Time: " + completedProcess.getResponseTime());
        System.out.println();
    }

    // Part 3: Display different scheduling criteria
    System.out.println("Different Scheduling Criteria:");

    // Sort completed processes by turnaround time
    Arrays.sort(completedProcesses, 0, completedProcessCount, Comparator.comparingInt(PCB::getTurnAroundTime));
    System.out.println("Scheduling by Turnaround Time:");
    for (int i = 0; i < completedProcessCount; i++) {
        System.out.println(completedProcesses[i]);
    }
    System.out.println();

    // Sort completed processes by waiting time
    Arrays.sort(completedProcesses, 0, completedProcessCount, Comparator.comparingInt(PCB::getWaitingTime));
    System.out.println("Scheduling by Waiting Time:");
    for (int i = 0; i < completedProcessCount; i++) {
        System.out.println(completedProcesses[i]);
    }
    System.out.println();

    // Sort completed processes by response time
    Arrays.sort(completedProcesses, 0, completedProcessCount, Comparator.comparingInt(PCB::getResponseTime));
    System.out.println("Scheduling by Response Time:");
    for (int i = 0; i < completedProcessCount; i++) {
        System.out.println(completedProcesses[i]);
    }

    }

    private static PCB getNextProcess() {
        
        if (q1Size > 0) {
            return dequeue(q1, --q1Size);
        } else if (q2Size > 0) {
            return dequeue(q2, --q2Size);
        }
        return null;
    }

    private static PCB dequeue(PCB[] queue, int size) {
        if (size == 0) {
        return null;}
        
        PCB process = queue[0];
         for (int i = 0; i < size; i++) {
        queue[i] = queue[i + 1];
    }
         if (size < queue.length) {
        queue[size] = null;
    }
    
    return process;
    }
}
