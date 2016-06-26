# pico
android multiple images and video picker.
## Open multiple images
```
    Pico.openMultipleFiles(MainActivity.this,Pico.TYPE_IMAGE);
```
## Open multiple videos
    Pico.openMultipleFiles(MainActivity.this,Pico.TYPE_VIDEO);
    
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
