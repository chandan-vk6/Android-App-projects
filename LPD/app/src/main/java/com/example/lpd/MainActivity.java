package com.example.lpd;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 123;
    private static final int CAMERA_CAPTURE = 456;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 789;


    InputImage inputImage;

    ImageView imageView2;

    TextRecognizer recognizer;

    TextView textView;

    MaterialButton image, speech;

    TextToSpeech t;

    public Bitmap textImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);


        image = findViewById(R.id.image);
        imageView2 = findViewById(R.id.image_view);
        textView = findViewById(R.id.text);
        speech = findViewById(R.id.speech);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Image Source")
                        .setItems(new CharSequence[]{"Gallery", "Camera"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    openGallery();
                                } else if (i == 1) {
                                    openCamera();
                                }
                            }
                        })
                        .show();
            }
        });


        t = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t.setLanguage(Locale.US);
//                    t.setLanguage(new Locale("bn_BD"));
                }
            }
        });

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    try {
                        inputImage = InputImage.fromFilePath(this, imageUri);
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        LicensePlateDetector licensePlateDetector = new LicensePlateDetector(MainActivity.this);
                        List<LicensePlateDetector.Recognition> recognitions = licensePlateDetector.detectLicensePlates(imageBitmap);
                        if (!recognitions.isEmpty()) {
                            LicensePlateDetector.Recognition licensePlate = recognitions.get(0);
                            RectF location = licensePlate.getLocation();

                            // Crop the license plate from the original image
                            int left = (int) (location.left * imageBitmap.getWidth());
                            int top = (int) (location.top * imageBitmap.getHeight());
                            int right = (int) (location.right * imageBitmap.getWidth());
                            int bottom = (int) (location.bottom * imageBitmap.getHeight());

                            Bitmap croppedImage = Bitmap.createBitmap(imageBitmap, left, top, right - left, bottom - top);

                            imageView2.setImageBitmap(croppedImage);

                            performOCR();
                        } else {
                            Toast.makeText(MainActivity.this, "No license plate found.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    if (imageBitmap != null) {
                        inputImage = InputImage.fromBitmap(imageBitmap, 0);
                        LicensePlateDetector licensePlateDetector;
                        try {
                            licensePlateDetector = new LicensePlateDetector(MainActivity.this);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        List<LicensePlateDetector.Recognition> recognitions = licensePlateDetector.detectLicensePlates(imageBitmap);
                        if (!recognitions.isEmpty()) {
                            LicensePlateDetector.Recognition licensePlate = recognitions.get(0);
                            RectF location = licensePlate.getLocation();

                            // Crop the license plate from the original image
                            int left = (int) (location.left * imageBitmap.getWidth());
                            int top = (int) (location.top * imageBitmap.getHeight());
                            int right = (int) (location.right * imageBitmap.getWidth());
                            int bottom = (int) (location.bottom * imageBitmap.getHeight());

                            Bitmap croppedImage = Bitmap.createBitmap(imageBitmap, left, top, right - left, bottom - top);

                            imageView2.setImageBitmap(croppedImage);
                            performOCR();
                        } else {
                            Toast.makeText(MainActivity.this, "No license plate found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }


    private void performOCR() {

        Task<Text> result = recognizer.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        processTextBlock(visionText);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // Handle the failure
                    }
                });
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void openCamera() {

        if (checkCameraPermission()) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_CAPTURE);

        } else {
            requestCameraPermission();
        }

    }

    private void processTextBlock(Text result) {
        // [START mlkit_process_text_block]
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            textView.append("\n");
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                //
                //  textView.append("\n");
                Point[] lineCornerPoints = line.getCornerPoints();

                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    textView.append(" ");
                    String elementText = element.getText();
                    textView.append(elementText);
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        if (!t.isSpeaking()) {
            super.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    // Check camera permission
    private boolean checkCameraPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    // Request camera permission
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the camera
                openCamera();
            } else {
                // Permission denied, show a message or handle it accordingly
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}