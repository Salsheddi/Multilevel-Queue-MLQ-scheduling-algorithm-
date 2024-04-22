import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */





public class PCB {
    String PId;
	int priority;
	int ArrivalTime;
	int CPU_burst;  
	int StartTime;
	int terminationTime;
	int TurnArroundTime; 
	int WaitingTime; 
	int ResponseTime;
        int executionTime;
	boolean check ; //flag

	 public PCB(String PId, int priority, int ArrivalTime, int CPU_burst) {
		this.PId = "P"+PId;
		this.priority = priority;
		this.ArrivalTime = ArrivalTime;
		this.CPU_burst = CPU_burst;
		executionTime=0; 
		StartTime = 0;
		terminationTime = 0;
		TurnArroundTime = 0; 
		WaitingTime = 0; 
		ResponseTime = 0;
		this.check = false ;
	}
	 
	public void setPId(String pId) {
		PId = pId;
	}	
	
	 public String getPId() {
		return PId;
	}

	public int getPriority() {
			return priority;
		}

	public void setArrivalTime(int arrivalTime) {
		ArrivalTime = arrivalTime;
	}    
	
	public int getArrivalTime() {
		return ArrivalTime;
	}

	public void setCPU_burst(int cPU_burst) {
		CPU_burst = cPU_burst;
	}

	public int getCPU_burst() {
		return CPU_burst;
	}
	
	public void setStartTime(int startTime) {
		StartTime = startTime;
	}	

	public int getStartTime() {
		return StartTime;
	}

	public void setTerminationTime(int terminationTime) {
		this.terminationTime = terminationTime;
	}

	public int getTerminationTime() {
		return terminationTime;
	}

	public void setTurnArroundTime(int turnArroundTime) {
		TurnArroundTime = turnArroundTime;
	}

	public int getTurnArroundTime() {
		return TurnArroundTime;
	}

	public void setWaitingTime(int waitingTime) {
		WaitingTime = waitingTime;
	}

	public int getWaitingTime() {
		return WaitingTime;
	}

	public void setResponseTime(int responseTime) {
		ResponseTime = responseTime;
	}

	public int getResponseTime() {
		return ResponseTime;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isCheck() {
		return check;
	}
	public void execute(int executionTime) {
        this.executionTime += executionTime;
    }
    
    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }
    public boolean isCompleted() {
        return executionTime >= CPU_burst;
    }

        
	@Override
	 public String toString() {
		return "Process Id: "+PId+ " | Priority: " +priority+ " | CPU burst: "+CPU_burst+ " | Arrival time: "+ArrivalTime+ " | Start time: "+StartTime+ 
				" | Termination time: "+terminationTime+ " | Turnarround time: "+TurnArroundTime+ " | Waiting time: "+WaitingTime+" | Response time: "+ResponseTime +"|  executionTime=" + executionTime ;
	}
	
 
}
