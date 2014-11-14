package com.ox.oauth.provider

import static groovyx.net.http.ContentType.URLENC
import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder

import com.ox.oauth.User


@Transactional
class GoogleProviderService {
	
	String BASE_URL = "https://accounts.google.com"
	String PATH_URL = "/o/oauth2/auth"

	String REDIRECT_URI = "http://localhost:8090/Ox-Oauth/authorize"
	String CLIENT_ID = "160004877545-9ket7bm8pevbv656inkff9cduu0plijn.apps.googleusercontent.com"
	String CLIENT_SECRET = "xXlcqQWfO6IVRg2zjJ4FIjF2"
	String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email"
	
    def authorize() {
		String url = "$BASE_URL$PATH_URL?"
		url +="access_type=offline"
		url +="&approval_prompt=force"
		url +="&response_type=code"
		url +="&state=security_token"
		url +="&client_id=$CLIENT_ID"
		url +="&redirect_uri=$REDIRECT_URI"
		url +="&scope=$EMAIL_SCOPE"
	}
	
	def refreshToken() {
		
	}
	
	def token(String code) {
		def http = new HTTPBuilder(BASE_URL)
		def body = [:]
		body.put("grant_type", "authorization_code")
		body.put("client_id", CLIENT_ID)
		body.put("client_secret", CLIENT_SECRET)
		body.put("redirect_uri", REDIRECT_URI)
		body.put("code", code)
		def resp = http.post(path : '/o/oauth2/token',
							 body : body,
							 requestContentType : URLENC)
	 
		User user = new User()
		user.accessToken = resp.access_token
		user.expire = resp.expires_in
		user.refreshToken = resp.refresh_token
		def mail = mail(user.accessToken)
		user.mail = mail.emails[0].value
		user.displayName = mail.displayName
		user.image = mail.image.url
		user.createdDate = new Date()
		if(!(user.save(flush:true))){
			println user.errors
		}
		user
	}
	
	def mail(String accessToken) {
		def http = new HTTPBuilder("https://www.googleapis.com")
		def parameters = [:]
		parameters.put("access_token", accessToken)
		def resp = http.get(path : '/plus/v1/people/me',
						 	query : parameters,
							requestContentType : URLENC)
		resp
	}
}
