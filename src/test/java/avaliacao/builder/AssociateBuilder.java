package avaliacao.builder;

import java.time.LocalDate;

import avaliacao.entities.Associate;
import avaliacao.entities.enums.Gender;
import avaliacao.entities.enums.PoliticalOffice;
import avaliacao.services.dto.AssociateDto;
import avaliacao.services.dto.AssociateFormDto;
import avaliacao.services.dto.AssociatePartyFormDto;

public final class AssociateBuilder {
	public static Associate getAssociate() {
		Associate associate = new Associate();
		associate.setId(1L);
		associate.setName("Tarcy");
		associate.setPoliticalOffice(PoliticalOffice.Presidente);
		associate.setBirthDate(LocalDate.now());
		associate.setGender(Gender.Feminino);
		associate.setPoliticalParty(PoliticalPartyBuilder.getPoliticalParty());
		
		return associate;
	}

	public static AssociateDto getAssociateDto() {
		AssociateDto associateDto = new AssociateDto();
		associateDto.setId(1L);
		associateDto.setName("Tarcy");
		associateDto.setPoliticalOffice(PoliticalOffice.Presidente);
		associateDto.setBirthDate(LocalDate.now());
		associateDto.setGender(Gender.Feminino);

		return associateDto;
	}

	public static AssociateFormDto getAssociateFormDto() {
		AssociateFormDto associateFormDto = new AssociateFormDto();
		associateFormDto.setName("Tarcy");
		associateFormDto.setPoliticalOffice(PoliticalOffice.Presidente);
		associateFormDto.setBirthDate(LocalDate.now());
		associateFormDto.setGender(Gender.Feminino);
		associateFormDto.setPoliticalParty(1L);

		return associateFormDto;
	}

	public static AssociatePartyFormDto getAssociatePartyFormDto() {
		AssociatePartyFormDto associatePartyFormDto = new AssociatePartyFormDto();
		associatePartyFormDto.setIdAssociado(1L);
		associatePartyFormDto.setIdPartido(2L);

		return associatePartyFormDto;
	}
}
