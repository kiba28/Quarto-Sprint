package avaliacao.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DefaultException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String error;
	private String message;

	public DefaultException(Integer status, String error, String message) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
	}
}
