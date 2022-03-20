package avaliacao.services.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import avaliacao.entities.Associate;
import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Gender;
import avaliacao.entities.enums.PoliticalOffice;
import avaliacao.exceptions.DefaultException;
import avaliacao.repositories.AssociateRepository;
import avaliacao.repositories.PoliticalPartyRepository;
import lombok.Data;

@Data
public class AssociateFormDto {

	@NotEmpty(message = "Name is required")
	private String name;
	@NotNull(message = "Political office is required")
	private PoliticalOffice politicalOffice;
	@NotNull(message = "Birth date is required")
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate birthDate;
	@NotNull(message = "Gender is required")
	private Gender gender;
	private long politicalParty;

	public Associate convert(PoliticalPartyRepository partyRepository) {
		PoliticalParty politicalParty = partyRepository.findById(this.politicalParty)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Political Party not found"));
		return new Associate(this.name, this.politicalOffice, this.birthDate, this.gender, politicalParty);
	}

	public Associate update(Long id, AssociateRepository associateRepository,
			PoliticalPartyRepository partyRepository) {
		Associate associate = associateRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Associate with id = " + id + " not found"));
		associate.setName(this.name);
		associate.setPoliticalOffice(this.politicalOffice);
		associate.setBirthDate(this.birthDate);
		associate.setGender(this.gender);
		PoliticalParty politicalParty = partyRepository.findById(this.politicalParty)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Political Party not found"));
		associate.setPoliticalParty(politicalParty);
		return associate;
	}
}
