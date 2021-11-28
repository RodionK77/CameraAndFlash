package kashirin.rodion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    private final int REQUEST_CODE_PERMISSION_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
        }else cameraWork();

        showNotify();

    }
    private void cameraWork(){
        Camera camera = Camera.open();
        SurfaceView surfaceView = findViewById(R.id.cameraView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }
    private void showNotify(){
        Button button = findViewById(R.id.NotifyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new
                        NotificationCompat.Builder(MainActivity.this, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Практическая работа 4")
                        .setContentText("Каширин Родион Сергеевич ИКБО-06-20")
                        .setStyle(new NotificationCompat.BigTextStyle())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                Random random = new Random();
                createNotificationChannel(notificationManager);
                notificationManager.notify(random.nextInt(), builder.build());
            }
        });
    }
    private void createNotificationChannel(NotificationManagerCompat manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, importance);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraWork();
                } else {

                }
                return;
            }
        }
    }
}