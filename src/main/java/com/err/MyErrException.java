package com.err;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyErrException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;

	public MyErrException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}