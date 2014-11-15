package com.chandu0101.naturalfarming.model

import upickle.Js

import scala.scalajs.js

/**
 * Created by chandrasekharkode on 11/8/14.
 */
class Tumbler {

}

case class BlogPost(title:String,body:String)


object BlogPost {

//  implicit  val postReader = upickle.Reader {
//
//    case Js.Obj(item) => new BlogPost(item)
//  }
}