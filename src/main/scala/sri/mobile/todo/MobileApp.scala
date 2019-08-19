package sri.mobile.todo

import sri.universal.apis.AppRegistry

object MobileApp {

  def main(args: Array[String]) = {
    //core.setReactElementType
    AppRegistry.registerComponent("sritodo", () => components.root)
  }
}
