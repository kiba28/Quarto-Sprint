package avaliacao.services.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Gender;
import avaliacao.entities.enums.PoliticalOffice;
import lombok.Data;

@Data
public class AssociateDto {

	private long id;
	private String name;
	private PoliticalOffice politicalOffice;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate birthDate;
	private Gender gender;
	private PoliticalParty PoliticalParty;

}
