package com.example.lpd;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LicensePlateDetector {
    private static final int MAX_RESULTS = 5;
    private static final float MIN_CONFIDENCE = 0.5f;

    private final int INPUT_IMAGE_WIDTH;
    private final int INPUT_IMAGE_HEIGHT;
    private final Interpreter interpreter;

    public LicensePlateDetector(Context context) throws IOException {
        AssetManager assetManager = context.getApplicationContext().getAssets();

        // Load the model from the assets directory
        InputStream inputStream = assetManager.open("model.tflite");
        MappedByteBuffer modelBuffer = loadModelFromInputStream(inputStream);

        // Initialize the interpreter
        Interpreter.Options options = new Interpreter.Options();
        options.setNumThreads(Runtime.getRuntime().availableProcessors());
        interpreter = new Interpreter(modelBuffer, options);

        // Get the input image width and height from the model metadata
        int[] inputShape = interpreter.getInputTensor(0).shape();
        INPUT_IMAGE_WIDTH = inputShape[2];
        INPUT_IMAGE_HEIGHT = inputShape[1];
    }

    private MappedByteBuffer loadModelFromInputStream(InputStream inputStream) throws IOException {
        File file = File.createTempFile("model", null);
        file.deleteOnExit();

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        }
    }

    public List<Recognition> detectLicensePlates(Bitmap image) {
        // Preprocess the input image
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, INPUT_IMAGE_WIDTH, INPUT_IMAGE_HEIGHT, true);
        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedImage);

        // Run the license plate detection model
        float[][][][] outputLocations = new float[1][MAX_RESULTS][1][4];
        float[][][] outputClasses = new float[1][MAX_RESULTS][1];
        float[][][] outputScores = new float[1][MAX_RESULTS][1];
        float[][][][] outputLandmarks = new float[1][MAX_RESULTS][1][2];

        Object[] inputs = {inputBuffer};
        Map<Integer, Object> outputs = new HashMap<>();
        outputs.put(0, outputLocations);
        outputs.put(1, outputClasses);
        outputs.put(2, outputScores);
        outputs.put(3, outputLandmarks);

        interpreter.runForMultipleInputsOutputs(inputs, outputs);

        // Postprocess the inference results
        List<Recognition> recognitions = new ArrayList<>();
        for (int i = 0; i < MAX_RESULTS; i++) {
            float confidence = outputScores[0][i][0];
            if (confidence >= MIN_CONFIDENCE) {
                float left = outputLocations[0][i][0][1] * INPUT_IMAGE_WIDTH;
                float top = outputLocations[0][i][0][0] * INPUT_IMAGE_HEIGHT;
                float right = outputLocations[0][i][0][3] * INPUT_IMAGE_WIDTH;
                float bottom = outputLocations[0][i][0][2] * INPUT_IMAGE_HEIGHT;
                RectF location = new RectF(left, top, right, bottom);
                String label = "License Plate"; // Assign a generic label
                recognitions.add(new Recognition(Integer.toString(i), label, confidence, location));
            }
        }

        return recognitions;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * INPUT_IMAGE_WIDTH * INPUT_IMAGE_HEIGHT * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[INPUT_IMAGE_WIDTH * INPUT_IMAGE_HEIGHT];
        bitmap.getPixels(pixels, 0, INPUT_IMAGE_WIDTH, 0, 0, INPUT_IMAGE_WIDTH, INPUT_IMAGE_HEIGHT);

        for (int pixelValue : pixels) {
            float normalizedValueR = (Color.red(pixelValue) - 127.5f) / 127.5f;
            float normalizedValueG = (Color.green(pixelValue) - 127.5f) / 127.5f;
            float normalizedValueB = (Color.blue(pixelValue) - 127.5f) / 127.5f;

            byteBuffer.putFloat(normalizedValueR);
            byteBuffer.putFloat(normalizedValueG);
            byteBuffer.putFloat(normalizedValueB);
        }

        byteBuffer.rewind();
        return byteBuffer;
    }

    public static class Recognition {
        private final String id;
        private final String label;
        private final float confidence;
        private final RectF location;

        public Recognition(String id, String label, float confidence, RectF location) {
            this.id = id;
            this.label = label;
            this.confidence = confidence;
            this.location = location;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public float getConfidence() {
            return confidence;
        }

        public RectF getLocation() {
            return new RectF(location);
        }
    }
}
