package net.codemetrix.funwithfrida2;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(detectRoot()) {
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Root detected");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("This phone is rooted. The app will exit in 5 seconds.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MainActivity.this.finish();
                        }
                    });
            alertDialog.show();

            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                   alertDialog.setMessage("This phone is rooted. The app will exit in " + millisUntilFinished / 1000 + " seconds.");
                }

                public void onFinish() {
                    alertDialog.cancel();
                    MainActivity.this.finish();
                }
            }.start();
        } else {
            TextView tv = (TextView) findViewById(R.id.sample_text);
            tv.setText("Hello, Frida!\n\nYou successfully bypassed root detection!");
        }
    }

    //Simple root detection
    private boolean detectRoot() {

        String[] a = new String[8];
        a[0] = "/system/xbin/su";
        a[1] = "/system/app/Superuser.apk";
        a[2] = "/system/xbin/daemonsu";
        a[3] = "/system/etc/init.d/99SuperSUDaemon";
        a[4] = "/system/bin/.ext/.su";
        a[5] = "/system/etc/.has_su_daemon";
        a[6] = "/system/etc/.installed_su_daemon";
        a[7] = "/dev/com.koushikdutta.superuser.daemon/";

        int i = 0;
        while (i < a.length) {
            if (new java.io.File(a[i]).exists()) {
                return true;
            }
            i++;
        }
        return false;
    }
}
