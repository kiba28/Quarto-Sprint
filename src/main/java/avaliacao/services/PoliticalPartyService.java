package avaliacao.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Ideology;
import avaliacao.exceptions.DefaultException;
import avaliacao.repositories.PoliticalPartyRepository;
import avaliacao.services.dto.PoliticalPartyDto;
import avaliacao.services.dto.PoliticalPartyFormDto;
import avaliacao.services.dto.PoliticalPartyWithAssociatesDto;

@Service
public class PoliticalPartyService {

	@Autowired
	private PoliticalPartyRepository partyRepository;

	@Autowired
	private ModelMapper mapper;

	public List<PoliticalPartyDto> findAll() {
		List<PoliticalParty> list = partyRepository.findAll();
		return list.stream().map(politicalParty -> mapper.map(politicalParty, PoliticalPartyDto.class))
				.collect(Collectors.toList());
	}

	public List<PoliticalPartyDto> findByIdeology(String ideology) {
		List<PoliticalParty> list = partyRepository.findByIdeology(Ideology.valueOf(ideology));
		return list.stream().map(politicalParty -> mapper.map(politicalParty, PoliticalPartyDto.class))
				.collect(Collectors.toList());
	}

	public PoliticalPartyDto findById(Long id) {
		PoliticalParty politicalParty = partyRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Political Party not found"));
		return mapper.map(politicalParty, PoliticalPartyDto.class);
	}

	public PoliticalPartyWithAssociatesDto findByIdWithAssociates(Long id) {
		PoliticalParty politicalParty = partyRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Political Party not found"));
		return mapper.map(politicalParty, PoliticalPartyWithAssociatesDto.class);
	}

	public PoliticalPartyDto insert(PoliticalPartyFormDto form) {
		PoliticalParty politicalParty = form.convert();
		return mapper.map(partyRepository.save(politicalParty), PoliticalPartyDto.class);
	}

	public PoliticalPartyDto update(long id, PoliticalPartyFormDto form) {
		PoliticalParty politicalParty = form.update(id, partyRepository);
		partyRepository.save(politicalParty);
		return mapper.map(politicalParty, PoliticalPartyDto.class);
	}

	public void deleteById(long id) {
		try {
		PoliticalParty politicalParty = partyRepository.findById(id)
				.orElseThrow(() -> new DefaultException(404, "NOT_FOUND", "Political Party not found"));
		partyRepository.delete(politicalParty);
		} catch (Exception ex) {
			throw new DefaultException(409, "CONFLICT", "Cannot delete political party with associates.");
		}
	}
}