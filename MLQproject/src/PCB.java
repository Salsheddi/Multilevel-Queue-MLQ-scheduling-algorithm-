





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
	int timeInCPU;
       

	 public PCB(String PId, int priority, int ArrivalTime, int CPU_burst) {
		this.PId = PId;
		this.priority = priority;
		this.ArrivalTime = ArrivalTime;
		this.CPU_burst = CPU_burst; 
		StartTime = 0;
		terminationTime = 0;
		TurnArroundTime = 0; 
		WaitingTime = 0; 
		ResponseTime = 0;
		this.timeInCPU = 0;
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

	

   

	
	@Override
	public String toString() {
		return "Process Id: " + PId +
				" || Priority: " + priority +
				" || CPU burst: " + CPU_burst +
				" || Arrival time: " + ArrivalTime +
				" || Start time: " + StartTime +
				" || Termination time: " + terminationTime +
				" || Turnaround time: " + TurnArroundTime +
				" || Waiting time: " + WaitingTime +
				" || Response time: " + ResponseTime;
	}
	
}
