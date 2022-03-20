package avaliacao.services.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Ideology;
import avaliacao.exceptions.DefaultException;
import avaliacao.repositories.PoliticalPartyRepository;
import lombok.Data;

@Data
public class PoliticalPartyFormDto {

	@NotEmpty(message = "Name is required")
	private String name;
	@NotNull(message = "Ideology is required")
	private Ideology ideology;
	@NotNull(message = "Foundation date is required")
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate foundationDate;

	public PoliticalParty convert() {
		return new PoliticalParty(this.name, this.ideology, this.foundationDate);
	}

	public PoliticalParty update(long id, PoliticalPartyRepository partyRepository) {
		PoliticalParty politicalParty = partyRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Political Party not found"));
		politicalParty.setName(this.name);
		politicalParty.setIdeology(this.ideology);
		politicalParty.setFoundationDate(this.foundationDate);
		return politicalParty;
	}
}
