package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingLotModel {

    public String lot_id;
    public String location;
    public int capacity;
    public int occupied_spaces;
    public double cost_per_minute;
    
    @JsonProperty("lot_id")
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	
	 @JsonProperty("location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@JsonProperty("capacity")
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	@JsonProperty("occupied_spaces")
	public int getOccupied_spaces() {
		return occupied_spaces;
	}
	public void setOccupied_spaces(int occupied_spaces) {
		this.occupied_spaces = occupied_spaces;
	}
	
	@JsonProperty("cost_per_minute")
	public double getCost_per_minute() {
		return cost_per_minute;
	}
	public void setCost_per_minute(double cost_per_minute) {
		this.cost_per_minute = cost_per_minute;
	}
    


    
    

    
    
}
