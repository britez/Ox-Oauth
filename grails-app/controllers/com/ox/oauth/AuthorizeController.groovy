package com.ox.oauth

import grails.converters.JSON

class AuthorizeController {
	
	def googleProviderService

    def show(){
		if (params.code) {
			render googleProviderService.token(params.code) as JSON
		}
		redirect url: googleProviderService.authorize()
	}
}
