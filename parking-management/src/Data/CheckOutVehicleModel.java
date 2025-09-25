package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckOutVehicleModel {

	public String license_plate;
	public String parking_lot_id;
	
	@JsonProperty("license_plate")
	public String getLicense_plate() {
		return license_plate;
	}
	public void setLicense_plate(String license_plate) {
		this.license_plate = license_plate;
	}
	
	@JsonProperty("parking_lot_id")
	public String getParking_lot_id() {
		return parking_lot_id;
	}
	public void setParking_lot_id(String parking_lot_id) {
		this.parking_lot_id = parking_lot_id;
	}
	
	
}
