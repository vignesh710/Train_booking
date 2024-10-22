package Project;

public class Passenger {
	private String name;
	private int pId;
	private String prefBerth;
	private String allocatedBerth;
	@Override
	public String toString() {
		return "Name : " + name + "\n Passenger ID : " +pId  + "\n Preffered Berth : " + prefBerth  + "\n Allocated Berth : "
				+ allocatedBerth ;
	}
	public Passenger(String name, int pId, String prefBerth) {
		this.name = name;
		this.pId = pId;
		this.prefBerth = prefBerth.toUpperCase();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getPrefBerth() {
		return prefBerth;
	}
	public void setPrefBerth(String prefBerth) {
		this.prefBerth = prefBerth;
	}
	public String getAllocatedBerth() {
		return allocatedBerth;
	}
	public void setAllocatedBerth(String allocatedBerth) {
		this.allocatedBerth = allocatedBerth;
	}

}
