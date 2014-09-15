package com.ox.oauth.provider

import grails.transaction.Transactional

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.client.auth.oauth2.StoredCredential
import com.google.api.client.http.BasicAuthentication
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory

@Transactional
class GoogleProviderService {
	
	String CLIENT_ID = "160004877545-9ket7bm8pevbv656inkff9cduu0plijn.apps.googleusercontent.com"
	String CLIENT_SECRET = "xXlcqQWfO6IVRg2zjJ4FIjF2"
	Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));

    def authorize() {
		def auth = flow().newAuthorizationUrl()
		auth.setRedirectUri("http://localhost:8080/Ox-Oauth/callback")
		auth.setScopes(["https://www.googleapis.com/auth/userinfo.email"])
		auth.toString()
    }
	
	private def flow(){
		AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
			new NetHttpTransport(),
			new JacksonFactory(),
			new GenericUrl("http://localhost:8080/Ox-Oauth/token"),
			new BasicAuthentication(CLIENT_ID, CLIENT_SECRET),
			CLIENT_ID,
			"https://accounts.google.com/o/oauth2/auth").setCredentialDataStore(
				StoredCredential.getDefaultDataStore(
					new FileDataStoreFactory(new File("datastoredir"))))
			.build();
	}
}
