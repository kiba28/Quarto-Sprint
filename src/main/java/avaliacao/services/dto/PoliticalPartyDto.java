package avaliacao.services.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import avaliacao.entities.enums.Ideology;
import lombok.Data;

@Data
public class PoliticalPartyDto {

	private long id;
	private String name;
	private String acronym;
	private Ideology ideology;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate foundationDate;
}
