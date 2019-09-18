package sri.mobile.todo.components

import sri.mobile.todo.components.MyNavScreen.Props
import sri.navigation._

class BuyScreen extends NavigationScreenComponentNoPS {

  def render() = MyNavScreen(new Props("Buy - List", Global.BUY, "http://ShoppingWebApp-env.f93um5svdx.ap-south-1.elasticbeanstalk.com"))
}
