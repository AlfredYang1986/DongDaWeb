package controllers

import play.api._
import play.api.mvc._
import controllers.common.requestArgsQuery._
import play.api.libs.json.Json
import play.api.libs.json.Json.{toJson}
import play.api.libs.json.JsValue

import util.dao.from
import util.dao._data_connection
import util.errorcode.ErrorCode
import java.util.Date
import com.mongodb.casbah.Imports._

object Application extends Controller {

	def index = Action {
		Ok(views.html.index("Your new application is ready."))
	}

	def lynn = Action {
		Ok(views.html.lynn("Your new application is ready."))
	}	

	def register = Action (request => requestArgs(request)(this.registerImpl))
	def registerImpl(data : JsValue) : JsValue = {
		def createDBObject : MongoDBObject = {
			val builder = MongoDBObject.newBuilder
			builder += "name" -> (data \ "name").asOpt[String].map (x => x).getOrElse(throw new Exception(""))
			builder += "dob" -> (data \ "dob").asOpt[String].map (x => x).getOrElse(throw new Exception(""))
			builder += "city" -> (data \ "city").asOpt[String].map (x => x).getOrElse(throw new Exception(""))
			builder += "reg-type" -> (data \ "reg-type").asOpt[String].map (x => x).getOrElse(throw new Exception(""))
			builder += "phone" -> (data \ "phone").asOpt[String].map (x => x).getOrElse("")
			builder += "email" -> (data \ "email").asOpt[String].map (x => x).getOrElse("")
			builder += "date" -> new Date().getTime
			
			builder.result
		}

		_data_connection.getCollection("users") += createDBObject
		toJson(Map("status" -> toJson("ok"), "result" -> toJson("register users success")))
	}
	
	def DB2JsValue(x : MongoDBObject) : JsValue =
		toJson(Map("name" -> toJson(x.getAs[String]("name").get),
				   "dob" -> toJson(x.getAs[String]("dob").get),
				   "city" -> toJson(x.getAs[String]("city").get),
				   "reg-type" -> toJson(x.getAs[String]("reg-type").get),
				   "phone" -> toJson(x.getAs[String]("phone").get),
				   "email" -> toJson(x.getAs[String]("email").get),
				   "date" -> toJson(x.getAs[Number]("date").get.longValue)))
	
	def queryRegisterImpl(data : JsValue) : JsValue =
		toJson(Map("status" -> toJson("ok"), "result" -> toJson(
			(from db() in "users").selectSkipTop(
				(data \ "skip").asOpt[Int].map (x => x).getOrElse(0))(
					(data \ "take").asOpt[Int].map (x => x).getOrElse(50))("date")(DB2JsValue(_)).toList)))
}