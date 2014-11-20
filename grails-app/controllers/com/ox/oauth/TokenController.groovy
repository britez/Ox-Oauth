package com.ox.oauth

import grails.converters.JSON

import com.ox.oauth.exception.ExpiredException
import com.ox.oauth.exception.InvalidTokenException

class TokenController {

	static final String AUTHORIZATION = "Authorization"
	
	def authService;

    def show() {
		try {
			render authService.validate(request.getHeader(AUTHORIZATION)) as JSON
			return
		}catch (InvalidTokenException e) { 
			render(status: 400, text:"{message: 'Invalid token'}")
			return
		}catch (ExpiredException e) {
			render(status: 403, text:"{message: 'Forbidden'}")
			return
		}
	}
}
