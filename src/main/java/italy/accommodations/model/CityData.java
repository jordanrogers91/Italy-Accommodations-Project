package italy.accommodations.model;

import java.util.HashSet;
import java.util.Set;

import italy.accommodations.entity.Accommodation;
import italy.accommodations.entity.Amenity;
import italy.accommodations.entity.City;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityData {
	private Long cityId;
	private String cityName;
	private String cityArea;
	private Set<AccommodationData> accommodations = new HashSet<>();

	public CityData(City city) {
		cityId = city.getCityId();
		cityName = city.getCityName();
		cityArea = city.getCityArea();

		for (Accommodation accommodation : city.getAccommodations()) {
			accommodations.add(new AccommodationData(accommodation));
		}
	}

	@Data
	@NoArgsConstructor
	public static class AccommodationData {
		private Long accommodationId;
		private String accommodationName;
		private String accommodationAddress;
		private String accommodationPhoneNumber;
		private Double accommodationRating;
		
		private Set<String> amenities = new HashSet<>();

		public AccommodationData (Accommodation accommodation) {
			accommodationId = accommodation.getAccommodationId();
			accommodationName = accommodation.getAccommodationName();
			accommodationAddress = accommodation.getAccommodationAddress();
			accommodationPhoneNumber = accommodation.getAccommodationPhoneNumber();
			accommodationRating = accommodation.getAccommodationRating();
			// set the amenities
			for (Amenity amenity : accommodation.getAmenities()) {
				amenities.add(amenity.getAmenityName());
			}
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AmenityData {
		private Long amenityId;
		private String amenityName;
		
		public AmenityData (Amenity amenity) {
			amenityId = amenity.getAmenityId();
			amenityName = amenity.getAmenityName();
		}
	}
}
