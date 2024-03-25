package italy.accommodations.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import italy.accommodations.entity.Accommodation;

public interface AccommodationDao extends JpaRepository<Accommodation, Long> {

	Optional<Accommodation> findByAccommodationAddress(String accommodationAddress);

}
