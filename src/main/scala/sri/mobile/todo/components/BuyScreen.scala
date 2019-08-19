package sri.mobile.todo.components

import sri.mobile.todo.components.MyNavScreen.Props
import sri.navigation._

class BuyScreen extends NavigationScreenComponentNoPS {

  def render() = MyNavScreen(new Props("Buy - List", Global.BUY, "https://my-json-server.typicode.com/typicode/demo/posts"))
}
