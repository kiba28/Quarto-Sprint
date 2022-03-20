package avaliacao.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import avaliacao.entities.Associate;
import avaliacao.entities.enums.PoliticalOffice;
import avaliacao.exceptions.DefaultException;
import avaliacao.repositories.AssociateRepository;
import avaliacao.repositories.PoliticalPartyRepository;
import avaliacao.services.dto.AssociateDto;
import avaliacao.services.dto.AssociateFormDto;
import avaliacao.services.dto.AssociatePartyFormDto;

@Service
public class AssociateServiceImpl implements AssociateService {

	@Autowired
	private AssociateRepository associateRepository;

	@Autowired
	private PoliticalPartyRepository partyRepository;

	@Autowired
	private ModelMapper mapper;

	public List<AssociateDto> findAll(String politicalOffice) {
		if (politicalOffice == null) {
			List<Associate> list = associateRepository.findAll();
			return list.stream().map(associate -> mapper.map(associate, AssociateDto.class)).collect(Collectors.toList());
		} else {
			List<Associate> list = associateRepository.findBypoliticalOffice(PoliticalOffice.valueOf(politicalOffice));
			return list.stream().map(associate -> mapper.map(associate, AssociateDto.class)).collect(Collectors.toList());
		}	
	}

	public AssociateDto findById(Long id) {
		Associate associate = associateRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Associate with id = " + id + " not found"));
		return mapper.map(associate, AssociateDto.class);
	}

	public AssociateDto insert(AssociateFormDto form) {
		Associate associate = form.convert(partyRepository);
		return mapper.map(associateRepository.save(associate), AssociateDto.class);
	}

	public AssociateDto update(Long id, AssociateFormDto form) {
		Associate associate = form.update(id, associateRepository, partyRepository);
		return mapper.map(associateRepository.save(associate), AssociateDto.class);
	}

	public AssociateDto updatePoliticalParty(AssociatePartyFormDto associateParty) {
		Associate associate = associateParty.updateParty(associateRepository, partyRepository);
		associateRepository.save(associate);
		return mapper.map(associate, AssociateDto.class);
	}

	public void deleteById(long id) {
		Associate associate = associateRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Associate with id = " + id + " not found"));
		associateRepository.delete(associate);
	}

	public void deleteAssociateFromParty(long idAssociate, long idParty) {
		Associate associate = associateRepository.findById(idAssociate).orElseThrow(
				() -> new DefaultException(404, "NOT_FOUND", "Associate with id = " + idAssociate + " not found"));
		if (associate.getPoliticalParty().getId() == idParty) {
			associate.setPoliticalParty(null);
			associateRepository.save(associate);
		} else {
			throw new DefaultException(400, "BAD_REQUEST",
					"The associate is associated with a different political party than the one entered.");
		}
	}

}