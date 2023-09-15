package com.example.detector;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private CascadeClassifier plateCascade;

    private ImageView imageView;
    private Button captureButton;
    private Button detectButton;

    private Mat plateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        captureButton = findViewById(R.id.captureButton);
        detectButton = findViewById(R.id.detectButton);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    captureImage();
                }
            }
        });

        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectLicensePlate();
            }
        });

        // Load the OpenCV library
        OpenCVLoader.initDebug();

        // Load the Haar cascade xml file for license plate detection
        loadPlateCascade();
    }

    private void loadPlateCascade() {
        try {
            InputStream is = getResources().openRawResource(R.raw.haarcascade_russian_plate_number);
            File cascadeDir = getDir("cascade", MODE_PRIVATE);
            File cascadeFile = new File(cascadeDir, "haarcascade_russian_plate_number.xml");
            FileOutputStream os = new FileOutputStream(cascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            plateCascade = new CascadeClassifier(cascadeFile.getAbsolutePath());
            cascadeFile.delete();
            cascadeDir.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            plateImage = new Mat();
            Utils.bitmapToMat(imageBitmap, plateImage);
        }
    }

    private void detectLicensePlate() {
        if (plateCascade != null && plateImage != null) {
            Mat gray = new Mat();
            Imgproc.cvtColor(plateImage, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect plates = new MatOfRect();
            plateCascade.detectMultiScale(gray, plates, 1.1, 5, 0, new Size(30, 30), new Size());

            Rect[] platesArray = plates.toArray();

            for (Rect rect : platesArray) {
                Imgproc.rectangle(plateImage, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
            }

            Bitmap resultBitmap = Bitmap.createBitmap(plateImage.cols(), plateImage.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(plateImage, resultBitmap);
            imageView.setImageBitmap(resultBitmap);

        } else {
            Toast.makeText(this, "No image captured or cascade not loaded", Toast.LENGTH_SHORT).show();
        }
    }
}
