package sri.mobile.todo.components

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import sri.navigation.{NavigationAwareComponent, _}
import sri.universal.components._
import sri.universal._
import sri.core.ReactElement
import sri.universal.apis._
import sri.mobile.todo.components.MyNavScreen.Props
import sri.mobile.todo.models.Transaction

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.util.{Failure, Success}

import scala.async.Async.{async, await}
import scala.concurrent._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import js.JSConverters._


case class State ( items: js.Array[String],
                   selectedItem : String = "",
                   datasource : ListViewDataSource[String, String] = createListViewDataSource[String,String]((r1: String, r2: String) => r1 != r2))

class MyNavScreen extends NavigationAwareComponent[Props, State] {

  override def componentDidMount(): Unit = {
    refreshFromDB

  }

  initialState(State(items = js.Array()))

  def randomString(length: Int) = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.nextPrintableChar)
    }
    sb.toString
  }

  def renderBuyRow(text: String ,
                sectionID: String | Int,
                rowID: String | Int,
                highlightRow: js.Function2[String | Int, String | Int, _]): ReactElement = {
    View()(Text(style = GlobalStyles.ItemText)(text))
  }

  def renderBoughtRow(text: String ,
                   sectionID: String | Int,
                   rowID: String | Int,
                   highlightRow: js.Function2[String | Int, String | Int, _]): ReactElement = {
    View()(Text(style = GlobalStyles.ItemText,onPress = () => onPressOfItem(text))(text))
  }

  def onPressOfItem(text: String) = {
    Alert.alert("", text)

    // Update the state with selectedItem
    setState((state: State) => state.copy(datasource = state.datasource, items = state.items, selectedItem = text))
  }

  def onChangeInput(text:String) = {
    // Set the new state to selectedItem
    setState((state:State) => state.copy(datasource = state.datasource, items = state.items, selectedItem = text))
  }


  def getDataSource(lst: js.Array[String]) = {
    state.datasource.cloneWithRows(lst)
  }

  def refreshFromDB = {
    // Get all items from database marked for BUY

    /*val result = mutable.MutableList[String]()

    val f = Ajax.get(url = props.url)
      .map(xhr => js.JSON.parse(xhr.responseText).asInstanceOf[js.Array[js.Dynamic]])
    f.onComplete {
      case Success(lst) =>
        setState((state: State) => state.copy(datasource = getDataSource(f.value.get.get.flatMap (x=> x.body.toString :: List[String]()).toJSArray)
          , items = f.value.get.get.flatMap (x=> x.body.toString :: List[String]()).toJSArray))
        render()
      case Failure(e) =>
        println("Failed Request")
    }
    // Delay time
    def delay(milliseconds: Int): Future[Unit] = {
      val p = Promise[Unit]()
      js.timers.setTimeout(milliseconds) {
        p.success(())
      }
      p.future
    }
    for {
      delayed <- delay(100)
    } yield {
      setState((state: State) => state.copy(datasource = getDataSource(f.value.get.get.flatMap (x=> x.body.toString :: List[String]()).toJSArray)
        , items = f.value.get.get.flatMap (x=> x.body.toString :: List[String]()).toJSArray))
      render()
    }*/



    val result = mutable.MutableList[String]()
    async {
      val f = await(Ajax.get(url = props.url)
        .map(xhr => js.JSON.parse(xhr.responseText).asInstanceOf[js.Array[js.Dynamic]]))
    for (transaction <- f)
      result += transaction.title.toString
      setState((state: State) => state.copy(datasource = getDataSource(result.toJSArray)
        ,items = result.toJSArray))

    }
    render()
  }

  def addToBuy = {

    if (state.selectedItem.trim() != "") {
      // Add the selected text to database for BUY


      // Update the state with items
      state.items.append(state.selectedItem)
      setState((state: State) => state.copy(datasource = getDataSource(state.items), items = state.items))
    }
   }
  def addToBought = {
    if (state.selectedItem != "") {
      // Add the selected text to database for BOUGHT


      // Update the state with items
      state.items -= state.selectedItem

      setState((state: State) => state.copy(datasource = getDataSource(state.items), items = state.items, selectedItem = ""))
    }
  }



  def clearDataSource = {
    // Clear from database all items marked for BUY


    // Update the state with items
     setState((state:State) => state.copy(
       datasource = createListViewDataSource[String,String]((r1: String, r2: String) => r1 != r2),
       items = js.Array(),
       selectedItem = ""
     ))
  }

  def render() = {
    ScrollView(style = GlobalStyles.navScreenContainer)(

      Text(style = GlobalStyles.HeaderText)(props.banner + (if(state.items.size != 0) "(" + state.items.size + ")" else "")),
      if (props.transactionType == Global.BUY) {
        View() (
          ListView[String,String](dataSource = state.datasource,renderRow = renderBuyRow _),
          TextInput(onChangeText = (text: String) => onChangeInput(text)),
          Button(onPress = () => addToBuy, title = "Buy it"),
          Button(onPress = () => clearDataSource, title = "Clear All")
        )
      } else {
        View() (
          ListView[String,String](dataSource = state.datasource,renderRow = renderBoughtRow _),
          Button(onPress = () => addToBought, title = "I have Bought it")
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
