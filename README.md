# pico
android multiple images and video picker.

### Installation
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

## Open multiple images
```
Pico.openMultipleFiles(MainActivity.this,Pico.TYPE_IMAGE);
```
## Open multiple videos
```
Pico.openMultipleFiles(MainActivity.this,Pico.TYPE_VIDEO);
```
## onActivityResult
```
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
