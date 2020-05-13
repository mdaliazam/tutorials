package com.github.mdaliazam.graphql.web.rest;

import java.net.URISyntaxException;

/**
 * REST API specific exception to state the reason
 * 
 * @author <a href="mailto:softx.it@gmail.com">Mohammad Ali Azam</a>
 *
 */
public class ApiException extends URISyntaxException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiException(String input, String reason) {
		super(input, reason);
	}

}
