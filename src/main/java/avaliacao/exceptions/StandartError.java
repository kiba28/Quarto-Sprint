package avaliacao.exceptions;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandartError {

	private Integer status;

	private String error;

	private String message;

	private Instant timestamp;

}
