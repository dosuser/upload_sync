package dosuser.com.android_lessons_04_01_saving_key_and_value;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import net.gotev.uploadservice.BinaryUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.ftp.FTPUploadRequest;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 * Created by korea on 2016-09-05.
 */
public class FtpBO {

    static {
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        UploadService.UPLOAD_POOL_SIZE = 0;
    }

    public static UploadNotificationConfig notificationConfig = new UploadNotificationConfig()
            .setTitle("File upload")
            .setInProgressMessage("Uploading at [[UPLOAD_RATE]] ([[PROGRESS]])")
            .setErrorMessage("Error while uploading")
            .setInProgressMessage("i")
            .setCompletedMessage("Upload completed successfully in [[ELAPSED_TIME]]");


    public void upload(final Context context,String address, String id, String password,String remote,String path) {
        FTPUploadRequest request = new FTPUploadRequest(context,address.split(":")[0],Integer.parseInt(address.split(":")[1]));
        request.setAutoDeleteFilesAfterSuccessfulUpload(true);
        request.setUsernameAndPassword(id,password);
        request.setNotificationConfig(notificationConfig);
        request.setMaxRetries(1);
        try {
            Log.i("dosuser","upload file : " + path);
            request.addFileToUpload(path,remote);
            request.startUpload();
        } catch (MalformedURLException | FileNotFoundException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
