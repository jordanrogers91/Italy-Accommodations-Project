package italy.accommodations.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import italy.accommodations.entity.Amenity;

public interface AmenityDao extends JpaRepository<Amenity, Long> {

	Set<Amenity> findAllByAmenityId(Set<String> amenities);

}
