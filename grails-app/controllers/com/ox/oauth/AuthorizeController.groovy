package com.ox.oauth

class AuthorizeController {
	
	def googleProviderService

    def authorize(){
		redirect url: googleProviderService.authorize()
	}
}
