package italy.accommodations.contoller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import italy.accommodations.model.CityData;
import italy.accommodations.model.CityData.AccommodationData;
import italy.accommodations.model.CityData.AmenityData;
import italy.accommodations.service.CityService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/city")
@Slf4j
public class CityController {

	private CityService cityService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CityData insertCity(@RequestBody CityData cityData) {
		log.info("Creating city with ID= {}", cityData);
		return cityService.saveCity(cityData);
	}

	@GetMapping("/{cityId}")
	public CityData retrieveCityById(@PathVariable Long cityId) {
		log.info("Retrieving city with ID= {}", cityId);
		return cityService.retrieveCityById(cityId);

	}

	@PostMapping("/{cityId}/accommodation")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AccommodationData insertAccommodation(@PathVariable Long cityId,
			@RequestBody AccommodationData accommodationData) {

		log.info("Creating accommodation {} for city with ID= {}", accommodationData, cityId);

		return cityService.saveAccommodation(cityId, accommodationData);
	}

	@PutMapping("/{cityId}/accommodation/{accommodationId}")
	public AccommodationData updateAccommodation(@PathVariable Long accommodationId, @PathVariable Long cityId,
			@RequestBody AccommodationData accommodationData) {
		accommodationData.setAccommodationId(accommodationId);
		log.info("Updating accommodation with ID= {} for city with ID = {}", accommodationId, cityId);
		return cityService.saveAccommodation(cityId, accommodationData);
	}

	/*@GetMapping("/{cityId}/accommodation")
	public List<AccommodationData> retrieveAllAccommodations(@PathVariable Long cityId) {
		log.info("Retrieving all accommodations with city ID= {}", cityId);
		return cityService.retrieveAllAccommodations(cityId); 
	} */

	@GetMapping("/{accommodationId}/amenity")
	public List<AmenityData> retrieveAllAmenities(@PathVariable Long accommodationId) {
		log.info("Retrieving all amenites for accommodation with ID=" + accommodationId);
		return cityService.retrieveAllAmenities(accommodationId);
	}
}