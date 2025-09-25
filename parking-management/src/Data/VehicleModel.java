package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleModel {


	public String license_plate;
    public String type;
    public String owner_name;
    public String parking_lot_id; 
    
    public VehicleModel() {}

    
    @JsonProperty("license_plate")
	public String getLicense_plate() {
		return license_plate;
	}

	public void setLicense_plate(String license_plate) {
		this.license_plate = license_plate;
	}

	 @JsonProperty("type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	 @JsonProperty("owner_name")
	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	@JsonProperty("parking_lot_id")
	public String getParking_lot_id() {
		return parking_lot_id;
	}


	public void setParking_lot_id(String parking_lot_id) {
		this.parking_lot_id = parking_lot_id;
	}



	
	
    

    

    
    
}
