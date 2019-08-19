import org.scalajs.dom.ext.Ajax
import play.api.libs.json.Json
import sri.mobile.todo.components.Global


import scala.collection.mutable
import scala.util.{Failure, Success}
import com.softwaremill.sttp._
import play.api.libs.json

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js

case class Transaction (title: String,
  body: String, id: Int, link : String
)
val result = mutable.MutableList[String]()
val f = sttp.get(uri"http://localhost:9000/v1/posts")
implicit val backend = HttpURLConnectionBackend()
val response = f.send()
val json = Json.parse(response.unsafeBody)

val s = """[{"id":"1","link":"/v1/posts/1","title":"BUY","body":"Onion"},{"id":"2","link":"/v1/posts/2","title":"BUY","body":"Tomato"}]"""

val t = s.asInstanceOf[Array[Transaction]]
println(t.length)
for (transaction <- json.asInstanceOf[Array[Transaction]]) {
  println(transaction)
  if (transaction.title == Global.BUY.toString) {
    result += transaction.body
  }
}
println(result)
