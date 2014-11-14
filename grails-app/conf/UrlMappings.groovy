class UrlMappings {

	static mappings = {
		"/token" (controller: "token") {
			action = [GET: "show"]
		}
		
		"/authorize" (controller: "authorize") {
			action = [GET: "show"]
		}

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
