package italy.accommodations.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Accommodation {

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long accommodationId;
	
	private String accommodationName;
	
	@Column(unique = true)
	private String accommodationAddress;
	
	private String accommodationPhoneNumber;
	private int accommodationRating;
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "city_id", nullable = false)
	private City city;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "accommodation_amenity", 
	joinColumns = @JoinColumn(name = "accommodation_id"),
	inverseJoinColumns = @JoinColumn(name = "amenity_id"))
	private Set<Amenity> amenities = new HashSet<>();

	
}
