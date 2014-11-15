package com.chandu0101.naturalfarming.services

import com.chandu0101.naturalfarming.model.BlogPost
import com.greencatsoft.angularjs.core.{HttpConfig, HttpService}
import com.greencatsoft.angularjs.{Factory, inject, injectable}
import prickle.Unpickle
import upickle._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Failure, Success, Try}

/**
 * Created by chandrasekharkode on 11/8/14.
 */

@injectable("$tumblerService")
class TumblerService(val http: HttpService) {

  require(http != null, "Missing argument 'http'.")

  val turl = "http://api.tumblr.com/v2/blog/chandu0101.tumblr.com"
  val API_KEY = "PPFLh8r2Jv171VKvsitDZS3wdPTjlTAmyb6DJtzPaVGhtmT0oX"


  def getPosts(): Future[List[BlogPost]] = {
    val url = parameterizeUrl(turl + "/posts/text", Map("api_key" -> API_KEY ,"callback" -> "JSON_CALLBACK"))
    val future: Future[js.Dynamic] = http.jsonp(url,HttpConfig.apply)
    future
      .map(j => j.response.posts)
      .map(JSON.stringify(_))
      .map(read[List[BlogPost]](_))
  }


  protected def parameterizeUrl(url: String, parameters: Map[String, Any]): String = {
    require(url != null, "Missing argument 'url'.")
    require(parameters != null, "Missing argument 'parameters'.")

    parameters.foldLeft(url)((base, kv) =>
      base ++ {
        if (base.contains("?")) "&" else "?"
      } ++ kv._1 ++ "=" + kv._2)
  }

  protected def flatten[T](future: Future[Try[T]]): Future[T] = future flatMap {
    _ match {
      case Success(s) => Future.successful(s)
      case Failure(f) => Future.failed(f)
    }
  }
}


object TumblerServiceFactory extends Factory[TumblerService] {

  override val name = "$tumblerService"
  @inject
  var http: HttpService = _

  override def apply(): TumblerService = new TumblerService(http)

}