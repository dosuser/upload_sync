package dosuser.com.android_lessons_04_01_saving_key_and_value;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        local_load();
    }


    EditText ftpAddress = null;
    EditText ftpId = null;
    EditText ftpPassword = null;
    EditText ftpRemote = null;
    EditText ftpLocal = null;

    FtpBO ftpBO = new FtpBO();

    public void initComponents(){
        ftpAddress = (EditText) findViewById(R.id.ftp_address);
        ftpId = (EditText) findViewById(R.id.ftp_id);
        ftpPassword = (EditText) findViewById(R.id.ftp_password);
        ftpRemote = (EditText) findViewById(R.id.ftp_remote_path);
        ftpLocal = (EditText) findViewById(R.id.ftp_local_path);
    }

    public void save(View view) {
        local_save(ftpAddress.getText().toString() //
                ,ftpId.getText().toString() //
                ,ftpPassword.getText().toString() //
                ,ftpRemote.getText().toString() //
                ,ftpLocal.getText().toString() //
        );
        File[] files = find(ftpLocal.getText().toString());
        for(int r = 0; r<files.length; r++) {
            ftpBO.upload(getApplicationContext()
                    , ftpAddress.getText().toString()
                    , ftpId.getText().toString()
                    , ftpPassword.getText().toString()
                    , ftpRemote.getText().toString()
                    , files[r].getAbsolutePath()
                    );
        }
    }
    public File[] find(String localPath){
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/" + localPath;
        Toast.makeText(MainActivity.this, basePath, Toast.LENGTH_SHORT).show();
        File file = new File(basePath);
        Log.i("dosuser","access file : " + basePath +", " + file.exists());
        File[] targets = file.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File file, String s) {
                if ( s.lastIndexOf("dcf") < 0){
                    Log.i("dosuser","lookup file : " + s);
                    return false;
                }
                return true;
            }
        });
        return targets;
    }

    public void local_save(String ftpAddress,String ftpId, String ftpPassword, String ftpRemote, String ftpLocal){
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("FTP_ADDRESS",ftpAddress);
        editor.putString("FTP_ID",ftpId);
        editor.putString("FTP_PASSWORD",ftpPassword);
        editor.putString("FTP_REMOTE",ftpRemote);
        editor.putString("FTP_LOCAL",ftpLocal);
        editor.commit();
    }

    public void local_load(){
        SharedPreferences reader = getPreferences(MODE_PRIVATE);
        ftpAddress.setText(reader.getString("FTP_ADDRESS",""));
        ftpId.setText(reader.getString("FTP_ID",""));
        ftpPassword.setText(reader.getString("FTP_PASSWORD",""));
        ftpRemote.setText(reader.getString("FTP_REMOTE",""));
        ftpLocal.setText(reader.getString("FTP_LOCAL",""));
    }



}
