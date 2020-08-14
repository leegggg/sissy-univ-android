package net.linyz.site.sissyuniv;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaScriptInterface {
    private Context context;

    public JavaScriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void getBase64FromBlobData(String base64Data) {
        try {
            convertBase64StringToPdfAndStoreIt(base64Data);
        } catch (IOException e) {
            String msg = e.getMessage();
            Toast.makeText(context, context.getString(R.string.save_failed_hint) + msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static String getBase64StringFromBlobUrl(String blobUrl) {
        if (blobUrl.startsWith("blob")) {
            return "javascript: var xhr = new XMLHttpRequest();" +
                    "xhr.open('GET', '" + blobUrl + "', true);" +
                    "xhr.setRequestHeader('Content-type','application/octet-stream');" +
                    "xhr.responseType = 'blob';" +
                    "xhr.onload = function(e) {" +
                    "    if (this.status == 200) {" +
                    "        var blobPdf = this.response;" +
                    "        var reader = new FileReader();" +
                    "        reader.readAsDataURL(blobPdf);" +
                    "        reader.onloadend = function() {" +
                    "            base64data = reader.result;" +
                    "            Android.getBase64FromBlobData(base64data);" +
                    "        }" +
                    "    }" +
                    "};" +
                    "xhr.send();";
        }
        return "javascript: console.log('It is not a Blob URL');";
    }

    private void convertBase64StringToPdfAndStoreIt(String base64PDf) throws IOException {
        final int notificationId = 1;
        SimpleDateFormat dateFmt= new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String currentDateTime = dateFmt.format(new Date());
        final File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS) + "/suSave_" + currentDateTime + "_.suSave");
        byte[] pdfAsBytes = Base64.decode(base64PDf.replaceFirst("^data:.{0,100};base64,", ""), 0);
        FileOutputStream os;
        os = new FileOutputStream(dwldsPath, false);
        os.write(pdfAsBytes);
        os.flush();
        Toast.makeText(context, "Save to " + dwldsPath.getPath(), Toast.LENGTH_LONG).show();
    }
}