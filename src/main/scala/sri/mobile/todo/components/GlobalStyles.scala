package sri.mobile.todo.components

import sri.platform.SriPlatform
import sri.universal.styles.InlineStyleSheetUniversal

import scala.scalajs.js

object GlobalStyles extends InlineStyleSheetUniversal {
  import dsl._
  val navScreenContainer = style(
    marginTop := (if (SriPlatform.isIOS) 20 else 0),
      flex:= 1,
      flexDirection:= "column")

  val Justify = style(paddingVertical:= 20)

  val ItemText =
    style(margin := 14, shadowOffset := js.Dynamic.literal(height = 10))

  val ItemSelectedText =
    style(margin := 14,backgroundColor := "red", shadowOffset := js.Dynamic.literal(height = 10))


  val HeaderText =
    style(margin := 14, shadowOffset := js.Dynamic.literal(height = 10), fontWeight.bold)

  def dynamicColor(c: String) = styleUR(color := c)
}
