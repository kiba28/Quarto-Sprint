package avaliacao.services;

import java.util.List;

import avaliacao.services.dto.AssociateDto;
import avaliacao.services.dto.AssociateFormDto;
import avaliacao.services.dto.AssociatePartyFormDto;

public interface AssociateService {

	public List<AssociateDto> findAll(String politicalOffice);

	public AssociateDto findById(Long id);

	public AssociateDto insert(AssociateFormDto form);

	public AssociateDto update(Long id, AssociateFormDto form);

	public AssociateDto updatePoliticalParty(AssociatePartyFormDto associateParty);

	public void deleteById(long id);

	public void deleteAssociateFromParty(long idAssociate, long idParty);

}
