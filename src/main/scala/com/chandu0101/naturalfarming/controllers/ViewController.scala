package com.chandu0101.naturalfarming.controllers

import com.chandu0101.naturalfarming.components.{SeedTable, BlogReader}
import org.scalajs.dom
import prickle.{Pickle, Unpickle}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import com.chandu0101.naturalfarming.model.BlogPost
import com.chandu0101.naturalfarming.services.TumblerService
import com.greencatsoft.angularjs.core.Scope
import com.greencatsoft.angularjs.{Controller, inject}

import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}
import upickle._

/**
 * Created by chandrasekharkode on 11/9/14.
 */
@JSExport
object ViewController extends Controller {

  override val name = "viewctrl"
  override type ScopeType = ViewScope

  @inject
  var tService: TumblerService = _


  override def initialize(scope: ScopeType) = {
    tService.getPosts() onComplete {
      case Success(posts) => scope.posts = posts.toJSArray
        renderReactComponents()
      case Failure(t) => println("error getting posts : " + t.getMessage)
    }
  }



  @JSExport
  def renderReactComponents() = {
    val blogReader = dom.document.getElementById("tumbler")
    val seeds = dom.document.getElementById("seeds")
    if(blogReader != null && scope.posts != js.undefined ) BlogReader.componet(scope.posts.to,blogReader)
    if(seeds != null) SeedTable.component(Nil,seeds)
  }

  trait ViewScope extends Scope {
    var name: String
    var posts: js.Array[BlogPost]
    var response : js.String
  }

}
