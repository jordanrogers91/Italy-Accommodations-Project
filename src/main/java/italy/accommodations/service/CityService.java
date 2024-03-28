package italy.accommodations.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import italy.accommodations.dao.AccommodationDao;
import italy.accommodations.dao.AmenityDao;
import italy.accommodations.dao.CityDao;
import italy.accommodations.entity.Accommodation;
import italy.accommodations.entity.Amenity;
import italy.accommodations.entity.City;
import italy.accommodations.model.AccommodationData;
import italy.accommodations.model.AccommodationData.AmenityData;
import italy.accommodations.model.CityData;

@Service
public class CityService {

	@Autowired
	private AccommodationDao accommodationDao;

	@Autowired
	private CityDao cityDao;

	@Autowired
	private AmenityDao amenityDao;

	@Transactional(readOnly = false)
	public CityData saveCity(CityData cityData) {
		Long cityId = cityData.getCityId();

		City city = findOrCreateCity(cityId, cityData.getCityName());

		setFieldsInCity(city, cityData);
		return new CityData(cityDao.save(city));
	}

	private void setFieldsInCity(City city, CityData cityData) {
		city.setCityName(cityData.getCityName());
		city.setCityArea(cityData.getCityArea());

	}

	private City findOrCreateCity(Long cityId, String cityName) {
		City city;
		if (Objects.isNull(cityId)) {
			Optional<City> optCity = cityDao.findByCityName(cityName);
			if (optCity.isPresent()) {
				throw new DuplicateKeyException("city with name " + cityName + "already exists.");
			}
			city = new City();
		} else {
			city = findCityById(cityId);
		}
		return city;
	}

	private City findCityById(Long cityId) {
		return cityDao.findById(cityId)
				.orElseThrow(() -> new NoSuchElementException("City with ID=" + cityId + " was not found."));
	}

	@Transactional(readOnly = true)
	public CityData retrieveCityById(Long cityId) {
		City city = findCityById(cityId);
		return new CityData(city);
	}

	private Accommodation findAccommodationById(Long accommodationId) {
		return accommodationDao.findById(accommodationId).orElseThrow(
				() -> new NoSuchElementException("Accommodation with ID=" + accommodationId + " does not exist."));
	}

	@Transactional(readOnly = false)
	public AccommodationData saveAccommodation(Long cityId, AccommodationData accommodationData) {
		City city = findCityById(cityId);

		Set<Amenity> amenities = amenityDao.findAllByAmenityIn(accommodationData.getAmenities());

		Accommodation accommodation = findOrCreateAccommodation(accommodationData.getAccommodationId());

		setAccommodationFields(accommodation, accommodationData);

		accommodation.setCity(city);
		city.getAccommodations().add(accommodation);

		for (Amenity amenity : amenities) {
			amenity.getAccommodations().add(accommodation);
			accommodation.getAmenities().add(amenity);
		}

		Accommodation dbAccommodation = accommodationDao.save(accommodation);
		return new AccommodationData(dbAccommodation);
	}

	private void setAccommodationFields(Accommodation accommodation, AccommodationData accommodationData) {
		accommodation.setAccommodationAddress(accommodationData.getAccommodationAddress());
		accommodation.setAccommodationName(accommodationData.getAccommodationName());
		accommodation.setAccommodationPhoneNumber(accommodationData.getAccommodationPhoneNumber());
		accommodation.setAccommodationRating(accommodationData.getAccommodationRating());
		accommodation.setAccommodationId(accommodationData.getAccommodationId());

	}

	private Accommodation findOrCreateAccommodation(Long accommodationId) {
		Accommodation accommodation;

		if (Objects.isNull(accommodationId)) {
			accommodation = new Accommodation();
		} else {
			accommodation = findAccommodationById(accommodationId);
		}
		return accommodation;
	}

	@Transactional(readOnly = true)
	public Set<AccommodationData> retrieveAllAccommodationsForACity(Long cityId) {
		City city = findCityById(cityId);
		Set<Accommodation> accommodations = city.getAccommodations();
		Set<AccommodationData> response = new HashSet<>();
		for (Accommodation accommodation : accommodations) {
			response.add(new AccommodationData(accommodation));
		}
		return response;

	}

	@Transactional(readOnly = true)
	public AccommodationData retrieveAccommodationById(Long cityId, Long accommodationId) {
		findCityById(cityId);
		Accommodation accommodation = findAccommodationById(accommodationId);
		if (accommodation.getCity().getCityId() != cityId) {
			throw new IllegalStateException(
					"accommodation with ID= " + accommodationId + "is not in city with ID = " + cityId);
		}
		return new AccommodationData(accommodation);
	}

	@Transactional(readOnly = false)
	public void deleteAccommodationById(Long accommodationId) {
		Accommodation accommodation = findAccommodationById(accommodationId);
		accommodationDao.delete(accommodation);

	}

	@Transactional(readOnly = true)
	public Set<AmenityData> retrieveAllAmenitiesForAccommodation(Long accommodationId) {
		Accommodation accommodation = findAccommodationById(accommodationId);
		Set<Amenity> amenities = accommodation.getAmenities();
		Set<AmenityData> response = new HashSet<>();
		for(Amenity amenity : amenities) {
			response.add(new AmenityData(amenity));
		}
		return response;
	}


}
