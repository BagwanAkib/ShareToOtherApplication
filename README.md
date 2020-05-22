
# Use Share Lib

> Add it in your root build.gradle at the end of repositories:


```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

> Add the dependency


```gradle
dependencies {
        implementation 'com.github.BagwanAkib:ShareContent:0.0.2'
}
```


```java
/**
 * package names of other applications
 */

ApplicationName.FACEBOOK  - "com.facebook.katana"
ApplicationName.WHATSAPP - "com.whatsapp"
ApplicationName.PINTEREST - "com.pinterest"
ApplicationName.INSTAGRAM - "com.instagram.android"
ApplicationName.TWITTER - "com.twitter.android"
```
Use as given below
```java
Share.shareFileOnSelectedApp(ApplicationName.FACEBOOK, context, text, uri,pictureFile);
/**
* URI or file name is required
* */
```
![Screenshot_20200522-161817.png](img/Screenshot_20200522-161817.png)

![Screenshot_20200522-161657.png](img/Screenshot_20200522-161657.png)

![Screenshot_20200522-161729.png](img/Screenshot_20200522-161729.png)
