package avaliacao.services.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import avaliacao.entities.Associate;
import avaliacao.entities.enums.Ideology;
import lombok.Data;

@Data
public class PoliticalPartyWithAssociatesDto {

	private long id;
	private String name;
	private String acronym;
	private Ideology ideology;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate foundationDate;
	private List<Associate> associates;
}
