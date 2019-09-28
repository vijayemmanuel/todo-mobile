# sri-todo
A Simple SRI based BuyBought(ToDo) Application


This is based from the Sri project (https://github.com/scalajs-react-interface/sri)
You can run the project with following commands

Init React- Native after cloning project and cd to location
>cd sritodo

>react-native init sritodo --version react-native@0.46.0


// Move the sub folders 'android' and 'ios' from 'sritodo' folder to parent 'sritodo'

// Delete the folder 'sritodo'

> rm -rf sritodo

// Update NPM packages

> npm install


Building for Android
>sbt android:dev

>sbt android:prod

Running Android Virtual Device
>react-native run-android

Logging Android Virtual Device
>react-native log-android

Clean up
>watchman watch-del-all && rm -rf node_modules && npm install && npm start -- --reset-cache



