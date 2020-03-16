package sri.mobile.todo

import sri.navigation._
import sri.navigation.navigators._
import sri.platform.SriPlatform
import sri.vector.icons.{Ionicons, IoniconsList}
package object components {

  val root = TabNavigator(
    TabNavigatorConfig(
      tabBarOptions = TabBarOptions(
          activeTintColor = if (SriPlatform.isIOS) "#e91e63" else "#fff", showIcon = true)

    ),
    registerTabScreen[BuyScreen](
        navigationOptions = NavigationTabScreenOptions(
        tabBarIcon = (iconOptions: IconOptions) => {
          Ionicons(
            name =
              if (SriPlatform.isAndroid) IoniconsList.IOS_CART
              else IoniconsList.IOS_CART_OUTLINE,
            size = 27,
            style = GlobalStyles.dynamicColor(iconOptions.tintColor)
          )
        },
        tabBarLabel = "Buy"
      )),
    registerTabScreen[BoughtScreen](
      navigationOptions = NavigationTabScreenOptions(
        tabBarIcon = (iconOptions: IconOptions) => {
          Ionicons(
            name =
              if (iconOptions.focused) IoniconsList.IOS_BASKET
              else IoniconsList.IOS_BASKET_OUTLINE,
            size = 26,
            style = GlobalStyles.dynamicColor(iconOptions.tintColor)
          )
        },
        tabBarLabel = "Bought"
      ))
  )
}
