
public class Client {
	private int cID;
	private int arrivalTime;
	private int serviceTime;
	private int waitingTime;
	private int totalTime;
	
	public Client(int ID, int aTime, int sTime) {
		this.cID = ID;
		this.arrivalTime = aTime;
		this.serviceTime = sTime;  
	}

	public int getcID() {
		return cID;
	}


	public int getArrivalTime() {
		return arrivalTime;
	}


	public int getServiceTime() {
		return serviceTime;
	}


	public int getTotalTime() {  
		return totalTime;
	}

	public void setTotalTime() {
		this.totalTime = this.serviceTime + this.waitingTime;
	}
	
	public int getWaitingTime() {
		return this.waitingTime;
	}
	
	public void setWaitingTime(int i) {
		this.waitingTime = i;
	}
	
}
