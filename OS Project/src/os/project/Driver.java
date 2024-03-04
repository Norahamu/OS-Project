package os.project;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Driver {
    private static Queue Q1 = new Queue();
    private static Queue Q2 = new Queue();
    private static List<PCB> completedProcesses = new ArrayList<>();

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
        int numOfProcesses = scanner.nextInt();
        for (int i = 1; i <= numOfProcesses; i++) {
            System.out.println("Enter process details " + i);
            System.out.print("Priority (1 or 2): ");
            int priority = scanner.nextInt();
            System.out.print("Arrival time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("CPU burst time: ");
            int cpuBurst = scanner.nextInt();

            PCB process = new PCB(i, priority, arrivalTime, cpuBurst);
            if (priority == 1) {
                Q1.enqueue(process);
            } else if (priority == 2) {
                Q2.enqueue(process);
            } else {
                System.out.println("Invalid priority, please enter 1 or 2.");
                i--; 
            }
        }
    }

private static void displaySchedulingReport() {
        PCB process;
        int currentTime = 0;//cuurentTime is progression of time 
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int totalResponseTime = 0;
        int processesCount = 0;
        String averageReport;
        
        String schedulingOrder = "Scheduling Order: [";
        while ((process = getNextProcess(Q1, Q2)) != null) {
             if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }
            process.setStartTime(currentTime);
            process.setTerminationTime(currentTime + process.getCpuBurst());
            process.setTurnAroundTime(process.getTerminationTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnAroundTime() - process.getCpuBurst());
            process.setResponseTime(process.getStartTime() - process.getArrivalTime());
         //update
            totalTurnaroundTime += process.getTurnAroundTime();
            totalWaitingTime += process.getWaitingTime();
            totalResponseTime += process.getResponseTime();
            processesCount++;
            completedProcesses.add(process);
            schedulingOrder += "P" + process.getProcessId() + " | ";
             currentTime = process.getTerminationTime();
        }
        
          if (schedulingOrder.endsWith(" | ")) {
            schedulingOrder = schedulingOrder.substring(0, schedulingOrder.length() - 3);  //remove last |
        }
        schedulingOrder += "]";
        //averages
        double avgTurnaroundTime = (double) totalTurnaroundTime / processesCount;
        double avgWaitingTime = (double) totalWaitingTime / processesCount;
        double avgResponseTime = (double) totalResponseTime / processesCount;
        //write report to the file 
         try (FileWriter writer = new FileWriter("schedulingReport.txt")) {
            writer.write(schedulingOrder + "\n");
            System.out.println(schedulingOrder);

            for (PCB completedProcess : completedProcesses) {
                String report = generateProcessReport(completedProcess);
                writer.write(report + "\n");
                System.out.println(report);
            }

            
            averageReport = String.format("Average Turnaround Time: %.2f\n", avgTurnaroundTime) +
                            String.format("Average Waiting Time: %.2f\n", avgWaitingTime) +
                            String.format("Average Response Time: %.2f\n", avgResponseTime);

            writer.write(averageReport);
            System.out.print(averageReport);

        } catch (IOException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
}  

 private static PCB getNextProcess(Queue q1, Queue q2) {
      if (!q1.isEmpty()) {
        return q1.dequeue();
    } else if (!q2.isEmpty()) {
        return q2.dequeue();
    }
    return null; 
 }
 private static String generateProcessReport(PCB process) {
    return "Process ID: " + process.getProcessId() +
           ", Priority: " + process.getPriority() +
           ", Arrival Time: " + process.getArrivalTime() +
           ", CPU Burst: " + process.getCpuBurst() +
           ", Start Time: " + process.getStartTime() +
           ", Termination Time: " + process.getTerminationTime() +
           ", Turnaround Time: " + process.getTurnAroundTime() +
           ", Waiting Time: " + process.getWaitingTime() +
           ", Response Time: " + process.getResponseTime();
}

 
 }

