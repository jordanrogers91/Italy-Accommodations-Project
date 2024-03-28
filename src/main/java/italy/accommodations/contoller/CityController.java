package italy.accommodations.contoller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import italy.accommodations.model.AccommodationData;
import italy.accommodations.model.AccommodationData.AmenityData;
import italy.accommodations.model.CityData;
import italy.accommodations.service.CityService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/italy_accommodations")
@Slf4j
public class CityController {

	@Autowired
	private CityService cityService;

	@PostMapping("/city")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CityData insertCity(@RequestBody CityData cityData) {
		log.info("Creating city {}", cityData);
		return cityService.saveCity(cityData);
	}

	@GetMapping("city/{cityId}")
	public CityData retrieveCityById(@PathVariable Long cityId) {
		log.info("Retrieving city with ID= {}", cityId);
		return cityService.retrieveCityById(cityId);

	}

	@PostMapping("/city/{cityId}/accommodation")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AccommodationData insertAccommodation(@PathVariable Long cityId,
			@RequestBody AccommodationData accommodationData) {

		log.info("Creating accommodation {} for city with ID= {}", accommodationData, cityId);

		return cityService.saveAccommodation(cityId, accommodationData);
	}

	@PutMapping("/city/{cityId}/accommodation/{accommodationId}")
	public AccommodationData updateAccommodation(@PathVariable Long cityId, @PathVariable Long accommodationId,
			@RequestBody AccommodationData accommodationData) {
		accommodationData.setAccommodationId(accommodationId);
		log.info("Updating accommodation with ID= {} for city with ID= {}", accommodationId, cityId);
		return cityService.saveAccommodation(cityId, accommodationData);
	}

	@GetMapping("city/{cityId}/accommodation")
	public Set<AccommodationData> retrieveAllAccommodationsForACity(@PathVariable Long cityId) {
		log.info("Retrieving all accommodations for city with ID= {}", cityId);
		return cityService.retrieveAllAccommodationsForACity(cityId);
	}

	@GetMapping("/city/{cityId}/accommodation/{accommodationId}")
	public AccommodationData retrieveAccommodationById(@PathVariable Long cityId, @PathVariable Long accommodationId) {
		log.info("Retrieving accommodation with ID= {} for city with ID= {}", accommodationId, cityId);
		return cityService.retrieveAccommodationById(cityId, accommodationId);
	}

	@DeleteMapping("/accommodation/{accommodationId}")
	public Map<String, String> deleteAccommodationById(@PathVariable Long accommodationId) {
		log.info("Deleting accommodation with ID= {}", accommodationId);
		cityService.deleteAccommodationById(accommodationId);
		return Map.of("message", "Deletion of accommodation with ID= " + accommodationId + " was successful.");
	}

	@GetMapping("/{accommodationId}/amenity")
	public Set<AmenityData> retrieveAmenitiesforAccommodation(@PathVariable Long accommodationId) {
		log.info("Retrieving amenitites from accommodation with ID= {}", accommodationId);
		return cityService.retrieveAllAmenitiesForAccommodation(accommodationId);
		
	}

}
