package avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Ideology;

public interface PoliticalPartyRepository extends JpaRepository<PoliticalParty, Long> {

	List<PoliticalParty> findByIdeology(Ideology ideology);

	PoliticalParty findByName(String politicalParty);

}
