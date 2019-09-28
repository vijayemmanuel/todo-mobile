package sri.mobile.todo.components

import sri.mobile.todo.components.MyNavScreen.Props
import sri.navigation._

class BoughtScreen extends NavigationScreenComponentNoPS {

  def render() = MyNavScreen(new Props("Bought - List", Global.BOUGHT,"http://ShoppingWebApp-env.y8xqfz2fui.ap-south-1.elasticbeanstalk.com"))

}
