package avaliacao.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import avaliacao.entities.enums.Gender;
import avaliacao.entities.enums.PoliticalOffice;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Associate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PoliticalOffice politicalOffice;
	@Column(nullable = false)
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate birthDate;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@JsonIgnore
	@ManyToOne
	private PoliticalParty politicalParty;

	public Associate(String name, PoliticalOffice politicalOffice, LocalDate birthDate, Gender gender,
			PoliticalParty politicalParty) {
		this.name = name;
		this.politicalOffice = politicalOffice;
		this.birthDate = birthDate;
		this.gender = gender;
		this.politicalParty = politicalParty;
	}
}
