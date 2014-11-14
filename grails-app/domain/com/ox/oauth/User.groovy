package com.ox.oauth

class User {
	
	String mail
	Long expire
	String accessToken
	String refreshToken
	String displayName
	String image
	Date createdDate
	
    static constraints = {}
	
	Boolean isExpired(){
		return !((createdDate.time+expire)<(new Date().time))
	}
}
