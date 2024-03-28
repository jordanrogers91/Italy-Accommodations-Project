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
public class AccommodationData {

	private Long accommodationId;
	private String accommodationName;
	private String accommodationAddress;
	private String accommodationPhoneNumber;
	private int accommodationRating;

	private CityAccommodation city;

	private Set<String> amenities = new HashSet<>();

	public AccommodationData(Accommodation accommodation) {
		accommodationId = accommodation.getAccommodationId();
		accommodationName = accommodation.getAccommodationName();
		accommodationAddress = accommodation.getAccommodationAddress();
		accommodationPhoneNumber = accommodation.getAccommodationPhoneNumber();
		accommodationRating = accommodation.getAccommodationRating();
		// set the amenities

		city = new CityAccommodation(accommodation.getCity());

		for (Amenity amenity : accommodation.getAmenities()) {
			amenities.add(amenity.getAmenity());
		}
	}

	@Data
	@NoArgsConstructor
	public static class CityAccommodation {

		private Long cityId;
		private String cityName;
		private String cityArea;

		public CityAccommodation(City city) {
			cityId = city.getCityId();
			cityName = city.getCityName();
			cityArea = city.getCityArea();
		}

	}

	@Data
	@NoArgsConstructor

	public static class AmenityData {

		private Long amenityId;
		private String amenity;

		public AmenityData(Amenity amenity) {

			this.amenityId = amenity.getAmenityId();
			this.amenity = amenity.getAmenity();
			

		}
	}

}
