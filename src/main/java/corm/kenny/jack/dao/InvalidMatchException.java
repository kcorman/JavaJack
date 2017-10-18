package corm.kenny.jack.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid Match")  // 400
public class InvalidMatchException extends RuntimeException {

	public InvalidMatchException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidMatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidMatchException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidMatchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidMatchException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
