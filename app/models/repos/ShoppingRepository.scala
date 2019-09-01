package models.repos

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import javax.inject._
import models.entities.ShoppingItem
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile

class ShoppingRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[ShoppingItem, Int](dbConfigProvider.get[JdbcProfile].profile) {
  import driver.api._
  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[ShoppingItems]
  type TableType = ShoppingItems

  class ShoppingItems(tag: slick.lifted.Tag) extends Table[ShoppingItem](tag, "SHOPPING-ITEMS") with Keyed[Int] {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def orderDate = column[String]("orderDate")
    def boughtDate = column[String]("boughtDate")
    def * = (id.?, name, orderDate, boughtDate) <> ((ShoppingItem.apply _).tupled, ShoppingItem.unapply)
  }


}
