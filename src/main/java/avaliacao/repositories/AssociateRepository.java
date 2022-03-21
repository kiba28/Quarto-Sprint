package avaliacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import avaliacao.entities.Associate;
import avaliacao.entities.enums.PoliticalOffice;

public interface AssociateRepository extends JpaRepository<Associate, Long> {

	List<Associate> findByPoliticalOffice(PoliticalOffice politicalOffice);

}
