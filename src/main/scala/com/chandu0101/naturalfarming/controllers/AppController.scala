package com.chandu0101.naturalfarming.controllers

import com.chandu0101.naturalfarming.components.{SeedTable, BlogReader}
import com.chandu0101.naturalfarming.services.TumblerService
import com.greencatsoft.angularjs.{inject, Controller}
import com.greencatsoft.angularjs.core.Scope
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

/**
 * Created by chandrasekharkode on 11/7/14.
 *
 * Controller used to handle theme and layout
 */
@JSExport
object AppController extends Controller {

  override val name = "appctrl"
  override type ScopeType = AppScope

//  @inject
//  val tService : TumblerService = _

  override def initialize(scope: AppScope) = {
    scope._isMenuOpen = false
//    org.scalajs.dom.setInterval(() => TumblerReader.componet(dom.document.getElementById("tumbler"))
//      , 100)

//    tService.getPosts() onComplete {
//      case Success(posts) => println("sucess*************" + posts)
//
//      case Failure(t) => println("errormessage " + t.getMessage)
//    }
  }

  @JSExport
  def getBodyClass() =
    if (scope._isMenuOpen) "open" else ""

  @JSExport
  def getNavDrawerContainerClass() =
    if (scope._isMenuOpen) "open opened" else ""

  @JSExport
  def closeMenu() = scope._isMenuOpen = false;


  @JSExport
  def toggleMenu() = scope._isMenuOpen = true;

  @JSExport
  def tumblerpost() = {
    val seeds = dom.document.getElementById("seeds")
    if(seeds != null) SeedTable.component(Nil,seeds)
  }



  trait AppScope extends Scope {
    var _isMenuOpen: Boolean
  }


}
