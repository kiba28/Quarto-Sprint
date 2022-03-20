package avaliacao.services.dto;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import avaliacao.entities.Associate;
import avaliacao.entities.PoliticalParty;
import avaliacao.repositories.AssociateRepository;
import avaliacao.repositories.PoliticalPartyRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociatePartyFormDto {

	@NotNull(message = "Associate id is required")
	private long idAssociado;
	@NotNull(message = "Political party id is required")
	private long idPartido;

	public Associate updateParty(AssociateRepository associateRepository, PoliticalPartyRepository partyRepository) {
		Optional<Associate> optionalAssociate = associateRepository.findById(idAssociado);
		Optional<PoliticalParty> optionalParty = partyRepository.findById(idPartido);
		optionalAssociate.get().setPoliticalParty(optionalParty.get());
		return optionalAssociate.get();
	}
}
