package avaliacao.builder;

import java.time.LocalDate;

import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Ideology;
import avaliacao.services.dto.PoliticalPartyDto;
import avaliacao.services.dto.PoliticalPartyFormDto;
import avaliacao.services.dto.PoliticalPartyWithAssociatesDto;

public final class PoliticalPartyBuilder {
	public static PoliticalParty getPoliticalParty() {
		PoliticalParty politicalParty = new PoliticalParty();
		politicalParty.setId(1L);
		politicalParty.setName("PT");
		politicalParty.setIdeology(Ideology.Centro);
		politicalParty.setFoundationDate(LocalDate.now());
		
		return politicalParty;
	}
	
	public static PoliticalPartyDto getPoliticalPartyDto() {
		PoliticalPartyDto politicalParty = new PoliticalPartyDto();
		politicalParty.setId(1L);
		politicalParty.setName("PT");
		politicalParty.setIdeology(Ideology.Centro);
		politicalParty.setFoundationDate(LocalDate.now());
		
		return politicalParty;
	}
	
	public static PoliticalPartyFormDto getPoliticalPartyFormDto() {
		PoliticalPartyFormDto politicalParty = new PoliticalPartyFormDto();
		politicalParty.setName("PT");
		politicalParty.setIdeology(Ideology.Centro);
		politicalParty.setFoundationDate(LocalDate.now());
		
		return politicalParty;
	}
	
	public static PoliticalPartyWithAssociatesDto getPoliticalPartyWithAssociatesDto() {
		PoliticalPartyWithAssociatesDto politicalParty = new PoliticalPartyWithAssociatesDto();
		politicalParty.setId(1L);
		politicalParty.setName("PT");
		politicalParty.setIdeology(Ideology.Centro);
		politicalParty.setFoundationDate(LocalDate.now());
		
		return politicalParty;
	}
}
