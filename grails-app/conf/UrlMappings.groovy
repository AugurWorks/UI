class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/"(controller: "home", action: "landing")
		"/docs"(controller: "tutorial", action: "index")
        "500"(view:'/error')
	}
}
