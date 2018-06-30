package controllers

import javax.inject._

import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
    extends AbstractController(cc) {

    def index = Action {
//        Ok(views.html.index("Your new application is ready"))
        Ok(views.html.index(assetsFinder))
    }

    def privacy = Action {
        Ok(views.html.privacy(assetsFinder))
    }
}
