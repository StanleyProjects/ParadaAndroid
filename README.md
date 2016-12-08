![compileSdkVersion 25](https://img.shields.io/badge/compileSdkVersion-25-yellow.svg?style=true) ![buildToolsVersion 25.0.0](https://img.shields.io/badge/buildToolsVersion-25.0.0-blue.svg?style=true) ![minSdkVersion 15](https://img.shields.io/badge/minSdkVersion-15-red.svg?style=true) ![targetSdkVersion 25](https://img.shields.io/badge/targetSdkVersion-25-green.svg?style=true)

# ParadaAndroid
App for [paradaplastika.ru](http://paradaplastika.ru/)

<img src="media/icon.png" width="128" height="128" />

## Architecture
- [MVP](https://github.com/StanleyProjects/ParadaAndroid/tree/master/java/ru/parada/app/contracts) - MVP architecture
- [DAO](https://github.com/StanleyProjects/ParadaAndroid/blob/master/java/ru/parada/app/db/DAO.java) - DAO layer
- [DI](https://github.com/StanleyProjects/ParadaAndroid/tree/master/java/ru/parada/app/di) - DI architecture
- [Connection](https://github.com/StanleyProjects/ParadaAndroid/tree/master/java/ru/parada/app/connection) - Connection system for REST
- [Image cache](https://github.com/StanleyProjects/ParadaAndroid/blob/master/java/ru/parada/app/utils/IUtil.java) - Image cache system
- [Navigation Drawer](https://github.com/StanleyProjects/ParadaAndroid/blob/master/java/ru/parada/app/units/DrawerContainer.java) - custom Navigation Drawer

## Screenshots

<img src="media/phone_doctors.png" width="320" height="548" /> 

<img src="media/tablet_prices.png" width="640" height="464" /> 

# Build information
## defaultConfig
	applicationId "ru.parada.app"
	versionBase 1611270230
	versionCode 1611282025
	versionName "0.964"
## dependencies
	com.android.support:support-fragment:25.0.1
	com.android.support:recyclerview-v7:25.0.1
	com.google.firebase:firebase-messaging:9.8.0
	com.google.android.gms:play-services-maps:9.8.0
## gradle
    2.2.0
## google-services
    3.0.0