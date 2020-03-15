package controllers

import javax.inject._
import models.entities.ShoppingItem
import models.repos.ShoppingRepository
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import util.DBImplicits

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._

import scala.util.{Failure, Success}


@Singleton
class ShoppingController @Inject()(shoppingDAO : ShoppingRepository, dbExecuter: DBImplicits) extends Controller {

  import dbExecuter.executeOperation

  // Implicit definition for Json.toJson
  implicit val shoppingWrites = new Writes[ShoppingItem] {
    def writes(sup: ShoppingItem) = Json.obj(
      "id" -> sup.id,
      "name" -> sup.name,
      "orderDate" -> sup.orderDate,
      "boughtDate" -> sup.boughtDate
    )
  }

  def index() = Action {
    Ok("Hello world")
  }

  def addtoBuy = Action.async(parse.json) {
    request => {
      for {
        name <- (request.body \ "name").asOpt[String]
      } yield {
        shoppingDAO.save(ShoppingItem(None, name, DateTime.now().toString(),"")).mapTo[ShoppingItem] map {
          sup => Created(Json.toJson(sup))
        } recoverWith {
          case _ => Future {
            InternalServerError("Something wrong on server")
          }
        }
      }
    }.getOrElse(Future {
      BadRequest("Wrong json format")
    })
  }

  def deleteBuy(id: Int) = Action.async {
    request => {
      for {
        b <- shoppingDAO.findOne(id).map(x => x.get)
      } yield {
        dbExecuter.executeOperation(shoppingDAO.delete(ShoppingItem(b.id, b.name, b.orderDate, b.boughtDate)))
        Ok(Json.toJson(ShoppingItem(b.id, b.name, b.orderDate, b.boughtDate)))
      }

    }.recoverWith {
      case _ => Future {
        BadRequest("Content not available")
      }
    }
  }


  def getAlltoBuy() = Action.async {
    shoppingDAO.findAll().map(sup => Ok(Json.toJson(sup.filter(x => x.boughtDate == ""))))
  }

  def getAllBought() = Action.async {
    shoppingDAO.findAll().map(sup => Ok(Json.toJson(sup.filter(x => x.boughtDate != ""))))
  }

  def getBuy(id: Int) = Action.async {
    shoppingDAO.findAll().map(sup => Ok(Json.toJson(sup.filter(x => x.id == id))))
  }


  def addtoBought(id: Int) = Action.async {
    request => {
      for {
        b <- shoppingDAO.findOne(id).map(x => x.get.copy(boughtDate = DateTime.now().toString()))
      } yield {
        dbExecuter.executeOperation(shoppingDAO.update(ShoppingItem(b.id, b.name, b.orderDate, b.boughtDate)))
        Ok(Json.toJson(ShoppingItem(b.id, b.name, b.orderDate, b.boughtDate)))
      }

    }.recoverWith {
      case _ => Future {
        BadRequest("Content not available")
      }
    }

  }




  }


