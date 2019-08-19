package sri.mobile.todo.components

import sri.mobile.todo.components.MyNavScreen.Props
import sri.navigation._

class BoughtScreen extends NavigationScreenComponentNoPS {

  def render() = MyNavScreen(new Props("Bought - List", Global.BOUGHT,"https://my-json-server.typicode.com/typicode/demo/posts"))
}
