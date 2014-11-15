package com.chandu0101.naturalfarming.components

import com.chandu0101.naturalfarming.model.BlogPost
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.ReactVDom.ReactAttrExt
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.{HTMLAnchorElement, HTMLInputElement, Node}

/**
 * Created by chandrasekharkode on 11/8/14.
 */
object BlogReader {

  case class State(postsPerPage: Int, offset: Int, filterText: String, filtered: List[BlogPost])

  case class html(__html: String)

  class Backend(t: BackendScope[List[BlogPost], State]) {
    def onPreviousClick(e: SyntheticEvent[HTMLAnchorElement]) = {
      t.modState(s => s.copy(offset = s.offset - s.postsPerPage))
    }

    def onNextClick(e: SyntheticEvent[HTMLAnchorElement]) = {
      t.modState(s => s.copy(offset = s.offset + s.postsPerPage))
    }

    def onTextChange(e: SyntheticEvent[HTMLInputElement]) =
      t.modState(_.copy(offset = 0, filterText = e.target.value, filtered = filteredPosts(t.props, e.target.value)))
  }


  val Post = ReactComponentB[BlogPost]("Post")
    .render(post => {
    li(`class` := "list-group item")(
      div(`class` := "panel panel-default")(
        div(`class` := "panel-heading")(
          strong(post.title)
        ),
        div(`class` := "panel-body", dangerouslySetInnerHtml(post.body))
      )
    )
  }).build

  val SearchBar = ReactComponentB[Backend]("SearchBar")
    .render(B => {
    div(`class` := "form-group")(
      input(`class` := "form-control", `type` := "text", placeholder := "Search..", onchange ==> B.onTextChange)
    )
  }).build


  val Posts = ReactComponentB[(State)]("Posts")
    .render(S => {
    val rows = S.filtered.slice(S.offset, S.offset + S.postsPerPage) map { p => Post.withKey(p.title)(p)}
    ul(`class` := "list-group")(rows)
  }).build


  def filteredPosts(posts: List[BlogPost], filterText: String) =
    if (filterText == "") posts
    else posts.filter(p => (p.title.indexOf(filterText) != -1 || p.body.indexOf(filterText) != -1))

  def componet(data: List[BlogPost], mountNode: Node, postPerPage: Int = 5) = {
    val TumblerApp = ReactComponentB[List[BlogPost]]("TumblerApp")
      .initialState(State(postsPerPage = postPerPage, 0, "", data))
      .backend(new Backend(_))
      .render((P, S, B) => {
      filteredPosts(posts = P, filterText = "")
      div(
        SearchBar(B),
        Posts((S)),
        Pager.component(S.postsPerPage, S.filtered.length, S.offset, B.onNextClick, B.onPreviousClick)
      )
    }).build
    React.renderComponent(TumblerApp(data), mountNode)
  }

}
