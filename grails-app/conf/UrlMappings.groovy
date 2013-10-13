class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "/controllers"(view:"/index")
		"/"(controller:"home")
        "500"(view:'/error')
	}
}
