package models.entities

import com.byteslounge.slickrepo.meta.{Entity, Keyed}

case class ShoppingItem(override val id: Option[Int], name: String, orderDate: String, boughtDate: String) extends Entity[ShoppingItem, Int]{
  def withId(id: Int): ShoppingItem = this.copy(id = Some(id))
}