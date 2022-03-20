package avaliacao.services;

import java.util.List;

import avaliacao.services.dto.PoliticalPartyDto;
import avaliacao.services.dto.PoliticalPartyFormDto;
import avaliacao.services.dto.PoliticalPartyWithAssociatesDto;

public interface PoliticalPartyService {

	public List<PoliticalPartyDto> findAll();
	
	public List<PoliticalPartyDto> findByIdeology(String ideology);
	
	public PoliticalPartyDto findById(Long id);
	
	public PoliticalPartyWithAssociatesDto findByIdWithAssociates(Long id);
	
	public PoliticalPartyDto insert(PoliticalPartyFormDto form);
	
	public PoliticalPartyDto update(long id, PoliticalPartyFormDto form);
	
	public void deleteById(long id);
}
