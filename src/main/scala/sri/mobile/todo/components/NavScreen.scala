package sri.mobile.todo.components

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import sri.navigation.{NavigationAwareComponent, _}
import sri.universal.components._
import sri.universal._
import sri.core.{JSProps, JSState, ReactElement}
import sri.mobile.todo.components.MyNavScreen.Props
import sri.mobile.todo.models.ShoppingItem
import sri.universal.apis.Alert

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.async.Async.{async, await}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import js.JSConverters._

// State
case class State ( items: js.Array[ShoppingItem],
                   selectedItem : String = ""
                 )

class MyNavScreen extends NavigationAwareComponent[Props, State] {

  /*
  * Initialization setup of props
  */
  initialState(State(items = js.Array()))

  /*
  * Mounting : componentWillMount --> render --> componentDidMount
  */
  override def componentDidMount(): Unit = {
    refreshFromDB

  }

  /*
  * Updation  of Props/State: componentwillRecieveProps --> shouldComponentUpdate --> componentWillUpdate -->
  *  render --> componentDidUpdate
  */
  override def componentWillUpdate (nextJSProps: JSProps { type ScalaProps = Props },
                                    nextJSState: JSState { type ScalaState = State }): Unit = {
    //super.componentWillUpdate(nextJSProps, nextJSState)
    println(s"componentWillUpdate ")
  }

  def update: Unit = {
    refreshFromDB
    forceUpdate()
  }

  def refreshFromDB = {

    // Get all items from database marked for BUY
    val result = mutable.MutableList[ShoppingItem]()
    async {
      val f = await(Ajax.get(url = props.url + "/buylist")
        .map(xhr => js.JSON.parse(xhr.responseText).asInstanceOf[js.Array[js.Dynamic]]))

      for (transaction <- f)
      {
        val item = new ShoppingItem
        item.name = transaction.name.toString
        item.orderDate = transaction.orderDate.toString
        item.boughtDate = transaction.boughtDate.toString
        item.id = transaction.id.toString.toInt

        result += item
      }
      setState((state: State) => state.copy(items = result.toJSArray
        ,selectedItem = ""))



    }
    render()
  }

  def renderBuyRow(info: ListItem[ShoppingItem]): ReactElement = {
    View()(Text(numberOfLines = 2)(s"${info.item.name}\n${info.item.orderDate}"))
  }

  def renderBoughtRow(info: ListItem[ShoppingItem]) = {
    println(s"${state.selectedItem} and ${info.item.name} ")
    TouchableHighlight(onPress = () => onPressOfItem(info.item.name))(
      View(style = if (state.selectedItem == info.item.name) GlobalStyles.ItemSelectedText else GlobalStyles.ItemText)
      (Text(numberOfLines = 2)(s"${info.item.name}\n${info.item.orderDate}")))

  }

  /*
  * Callback methods
   */
  def onPressOfItem(text: String) = {
    //Alert.alert("", text)

    // Update the state with selectedItem
    setState((state: State) => state.copy(items = state.items, selectedItem = text))
  }

  def onChangeInput(text:String) = {
    // Set the new state to selectedItem
    setState((state:State) => state.copy(items = state.items, selectedItem = text))
  }

  def addToBuy = {

    if (state.selectedItem.trim() != "") {
      // Add the selected text to database for BUY
      async {
        val item = state.selectedItem.trim()
        val f = await(Ajax.post(url = props.url + "/buy", data = s"""{"name" : "$item"}""", headers = Map("Content-Type"->"application/json"))
          .map(xhr => js.JSON.parse(xhr.responseText).asInstanceOf[ShoppingItem]))

        // Update the state with items
        val newShoppingItem = new ShoppingItem
        newShoppingItem.name = f.name.toString
        newShoppingItem.orderDate = f.orderDate.toString
        newShoppingItem.boughtDate = f.boughtDate.toString
        newShoppingItem.id = f.id.toString.toInt

        state.items.append(newShoppingItem)
        setState((state: State) => state.copy(items = state.items))
      }
    }
    update
  }

  def addToBought = {
    if (state.selectedItem != "") {

      val id = state.items.filter ( x => x.name.trim() == state.selectedItem.trim()).pop().id

      // Add the selected text to database for BOUGHT
      async {
        val body = state.selectedItem.trim()
        val f = await(Ajax.put(url = props.url + "/bought/" + id.toString)
          .map(xhr => js.JSON.parse(xhr.responseText).asInstanceOf[ShoppingItem]))

        setState((state: State) => state.copy(items = state.items.filter(x => x.id != id), selectedItem = ""))
      }

    }
  }

  def clearDataSource = {
    // Clear from database all items marked for BUYs


    // Update the state with items
    setState((state:State) => state.copy(items = js.Array(), selectedItem = "")
    )
  }

  def render() = {
    println ("Rendering...")
    ScrollView(style = GlobalStyles.navScreenContainer)(

      Text(style = GlobalStyles.HeaderText)(props.banner + (if(state.items.size != 0) "(" + state.items.size + ")" else "")),
      if (props.transactionType == Global.BUY) {
        View() (
          FlatList(data = state.items,
            renderItem = renderBuyRow _,
            keyExtractor = (item:ShoppingItem, index:Int) => index.toString(),
            extraData = state.selectedItem.asInstanceOf[js.Any]
          ),
          TextInput(onChangeText = (text: String) => onChangeInput(text)),
          Button(onPress = () => addToBuy, title = "Buy"),
          //Button(onPress = () => clearDataSource, title = "Clear All")
          Button(onPress = () => update, title = "Update")
        )
      } else {
        View() (
          FlatList(data = state.items,
            renderItem = renderBoughtRow _,
            keyExtractor = (item:ShoppingItem, index:Int) => index.toString(),
            extraData = state.selectedItem.asInstanceOf[js.Any]
          ),
          //ListView[String,String](dataSource = state.datasource,renderRow = renderBoughtRow1 _),

          Button(onPress = () => addToBought, title = "Bought"),
          Button(onPress = () => update, title = "Update")

        )
      }
      /*Button(onPress = () => navigation.navigate[BuyScreen],
             title = "Buy"),
      Button(onPress = () => navigation.navigate[BoughtScreen],
             title = "Bought"),
      Button(onPress = () => navigation.goBack(null), title = "Go Back")*/
    )
  }
}



object MyNavScreen {

  case class Props(banner: String, transactionType: Global.Value, url: String)

  def apply(props: Props) = WithNavigation[MyNavScreen](props)

}
