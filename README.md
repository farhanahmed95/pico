# Pico
android multiple images and video picker.

## Installation
### add these lines to build.gradle
```
repositories {
    maven {
        url 'https://dl.bintray.com/farhanahmed95/maven/'
    }
}
dependencies {
    compile 'com.github.farhanahmed95:pico:0.0.1@aar'
}

```
### add permissions to AndroidManifest.xml
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
## Usage
### Open multiple images
```java
Pico.openMultipleFiles(MainActivity.this,Pico.TYPE_IMAGE);
```
### Open multiple videos
```java
Pico.openMultipleFiles(MainActivity.this,Pico.TYPE_VIDEO);
```
### Override onActivityResult method
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Pico.onActivityResult(this,requestCode,resultCode,data,new Pico.onActivityResultHandler(){

            @Override
            public void onActivityResult(List<File> files) {
                for (File file : files){
                    Log.i("FILE",file.getAbsolutePath());
                }
            }

            @Override
            public void onFailure(Exception error) {
                Log.e("ERROR",error.getMessage());

            }
        });
}
```
<br />
<br />
<br />
Copyright 2016 Farhan Ahmed

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
