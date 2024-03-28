package italy.accommodations.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import italy.accommodations.entity.City;

public interface CityDao extends JpaRepository<City, Long> {

	Optional<City> findByCityName(String cityName);

}
