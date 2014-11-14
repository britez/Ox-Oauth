package com.ox.oauth

import grails.transaction.Transactional

import com.ox.oauth.exception.ExpiredException
import com.ox.oauth.exception.InvalidTokenException

@Transactional
class AuthService {
	
	def validate(def header){
		if(!header){
			throw new InvalidTokenException()
		}
		def accessToken = header.replaceAll("Bearer\\s","")
		User result = User.findByAccessToken(accessToken)
		if (!result) {
			throw new ExpiredException()
		}
		if (result.isExpired()){
			throw new ExpiredException()
		}
		return result;
	}

}
