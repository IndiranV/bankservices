package org.in.com.controller.io;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ResponseIO<T> implements Serializable {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private int status;
	// private String errorCode;
	private String errorDesc;
	private String datetime;
	private T data;

	public ResponseIO() {
		super();
	}

	public ResponseIO(T data) {
		super();
		this.data = data;
	}

	public static <T> ResponseIO<T> failure(String errorMessage) {
		ResponseIO<T> response = new ResponseIO<T>();
		response.setStatus(0);
		// response.setErrorCode(errorCode);
		response.setErrorDesc(errorMessage);
		Date date = new Date();
		response.setDatetime(dateFormat.format(date));
		return response;
	}

	public static <T> ResponseIO<T> success(T t) {
		ResponseIO<T> response = new ResponseIO<T>(t);
		response.setStatus(1);
		Date date = new Date();
		response.setDatetime(dateFormat.format(date));
		return response;
	}

	public static ResponseIO<BaseIO> success() {
		ResponseIO<BaseIO> response = new ResponseIO<BaseIO>();
		response.setStatus(1);
		Date date = new Date();
		response.setDatetime(dateFormat.format(date));
		return response;
	}
}
