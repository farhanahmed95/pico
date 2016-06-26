package com.farhanahmed.pico;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by farhanahmed on 26/06/16.
 */
public class Pico {

    public static final int REQ_CODE = 3443;
    private static String TYPE = "";
    public static String TYPE_IMAGE = "image/*";
    public static String TYPE_VIDEO = "video/*";

    public Pico() {
        super();
    }

    public static void openMultipleFiles(Activity activity, String type) {
        TYPE = type;
        createIntent(activity, type);
    }

    public static void openMultipleFiles(Fragment fragment, String type) {
        TYPE = type;
        createIntent(fragment.getActivity(), type);
    }

    private static void createIntent(Activity activity, String type) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(type);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Pictures"), REQ_CODE);
    }


    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, onActivityResultHandler onActivityResultHandler) {
        willHandleResult(activity, requestCode, resultCode, data, TYPE, onActivityResultHandler);
    }

    public static void onActivityResult(Fragment fragment, int requestCode, int resultCode, Intent data, onActivityResultHandler onActivityResultHandler) {
        willHandleResult(fragment.getActivity(), requestCode, resultCode, data, TYPE, onActivityResultHandler);
    }

    private static void willHandleResult(Activity activity, int requestCode, int resultCode, Intent data, String type, onActivityResultHandler onActivityResultHandler) {
        if (requestCode == REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                try {
                    if (data.getClipData() != null) {
                        int count = 0;
                        ClipData clipData = data.getClipData();
                        count = clipData.getItemCount();
                        Uri[] uris = new Uri[count];
                        for (int i = 0; i < count; i++) {
                            uris[i] = clipData.getItemAt(i).getUri();
                        }
                        onActivityResultHandler.onActivityResult(fromUri(activity, uris));

                    } else if (data.getData() != null) {
                        Log.d("clip", "null");
                        Log.d("data", data.getData().getPath());
                        onActivityResultHandler.onActivityResult(fromUri(activity, new Uri[]{data.getData()}));

                    }
                } catch (Exception e) {
                    onActivityResultHandler.onFailure(e);
                }
            }
        }
    }

    private static List<File> fromUri(Activity activity, Uri[] uris) {
        String column[] = null;
        Uri contentURI = null;
        String selCol = "";
        if (TYPE == TYPE_IMAGE)
        {
            contentURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            selCol = MediaStore.Images.Media._ID;
            column= new String[]{ MediaStore.Images.Media.DATA };
        }else {
            contentURI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            selCol = MediaStore.Video.Media._ID;
            column= new String[]{ MediaStore.Video.Media.DATA };
        }
        int count = uris.length;
        String ids[] = new String[count];
        StringBuilder sel = new StringBuilder(selCol + " IN (");
        for (int i = 0; i < count; i++) {
            String uri = uris[i].getPath();
            int index = uri.lastIndexOf(":");
            ids[i] = uri.substring(index + 1, uri.length());
            sel.append(" ? ,");
        }
        sel.replace(sel.length() - 1, sel.length(), "");
        sel.append(")");

        Cursor cursor = activity.getContentResolver().query(contentURI,column, sel.toString(), ids, null);


        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);
        List<File> files = new ArrayList<>(count);
        while (cursor.moveToNext()) {
            filePath = cursor.getString(columnIndex);
            files.add(new File(filePath));
        }
        cursor.close();

        return files;
    }

    public interface onActivityResultHandler {
        void onActivityResult(List<File> files);

        void onFailure(Exception error);
    }
}

