import scala.util._
import scala.util.control.NonFatal
import org.scalajs.dom.ext.Ajax

import scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.concurrent._
import scala.concurrent.duration._
import scala.collection.mutable
import scala.scalajs.js
import scala.util.{Failure, Success}

val result = mutable.MutableList[String]()

val url = "http://shoppingwebapp-env-1.p2v2cgebas.ap-south-1.elasticbeanstalk.com"
val f = Ajax.get(url = url + "/buylist")
  .map (xhr => js.JSON.parse(xhr.responseText).asInstanceOf[js.Array[js.Dynamic]])
f.onComplete {
  case Success(lst) =>
    for (transaction <- lst)
      result += transaction.name.toString
    println(result)
  case Failure(e) =>
    println("Failed Request" + e)
}

println(f)