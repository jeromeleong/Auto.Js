package com.stardust.autojs.runtime.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.paddle.lite.demo.detection.bean.DetectionResult;
import com.baidu.paddle.lite.demo.ocr.OcrResult;
import com.baidu.paddle.lite.demo.ocr.Predictor;
import com.stardust.app.GlobalAppContext;
import com.stardust.autojs.core.image.ImageWrapper;

import java.util.Collections;
import java.util.List;

import com.baidu.paddle.lite.demo.detection.DetectionPredictor;

public class Paddle {
    private Predictor mOcrPredictor = new Predictor();
    private DetectionPredictor mDetectionPredictor = new DetectionPredictor();

    public boolean initOcr(Context context) {
        return mOcrPredictor.init(context);
    }

    public boolean initOcr(Context context, int cpuThreadNum) {
        return mOcrPredictor.init(context, cpuThreadNum);
    }

    public boolean initOcr(Context context, int cpuThreadNum, Boolean useSlim, Boolean useOpencl) {
        return mOcrPredictor.init(context, cpuThreadNum, useSlim, useOpencl);
    }

    public boolean initOcr(Context context, int cpuThreadNum, String myModelPath, Boolean useOpencl) {
        return mOcrPredictor.init(context, cpuThreadNum, myModelPath, useOpencl);
    }

    public List<OcrResult> ocr(ImageWrapper image, int cpuThreadNum, String myModelPath, Boolean useOpencl,
                               int detLongSize, float scoreThreshold) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mOcrPredictor.isLoaded()) {
            mOcrPredictor.init(GlobalAppContext.get(), cpuThreadNum, myModelPath, useOpencl, detLongSize, scoreThreshold);
        }
        return mOcrPredictor.ocr(bitmap);
    }

    public List<OcrResult> ocr(ImageWrapper image, int cpuThreadNum, Boolean useSlim, Boolean useOpencl,
                               int detLongSize, float scoreThreshold) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mOcrPredictor.isLoaded()) {
            mOcrPredictor.init(GlobalAppContext.get(), cpuThreadNum, useSlim, useOpencl, detLongSize, scoreThreshold);
        }
        return mOcrPredictor.ocr(bitmap);
    }

    public List<OcrResult> ocr(ImageWrapper image, int cpuThreadNum, Boolean useSlim, Boolean useOpencl) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mOcrPredictor.isLoaded()) {
            mOcrPredictor.init(GlobalAppContext.get(), cpuThreadNum, useSlim, useOpencl);
        }
        return mOcrPredictor.ocr(bitmap);
    }

    public List<OcrResult> ocr(ImageWrapper image, int cpuThreadNum, String myModelPath, Boolean useOpencl) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mOcrPredictor.isLoaded()) {
            mOcrPredictor.init(GlobalAppContext.get(), cpuThreadNum, myModelPath, useOpencl);
        }
        return mOcrPredictor.ocr(bitmap);
    }

    public List<OcrResult> ocr(ImageWrapper image, int cpuThreadNum, String myModelPath) {
        return ocr(image, cpuThreadNum, myModelPath, false);
    }

    public List<OcrResult> ocr(ImageWrapper image) {
        return ocr(image, 4, true, false);
    }

    public String[] ocrText(ImageWrapper image, int cpuThreadNum, Boolean useSlim, Boolean useOpencl) {
        if (!mOcrPredictor.isLoaded()) {
            mOcrPredictor.init(GlobalAppContext.get(), cpuThreadNum, useSlim, useOpencl);
        }
        List<OcrResult> words_result = ocr(image, cpuThreadNum, useSlim, useOpencl);
        String[] outputResult = new String[words_result.size()];
        for (int i = 0; i < words_result.size(); i++) {
            outputResult[i] = words_result.get(i).words;
            Log.i("outputResult", outputResult[i].toString()); // show LOG in Logcat panel
        }
        return outputResult;
    }

    public String[] ocrText(ImageWrapper image, int cpuThreadNum, String myModelPath, Boolean useOpencl) {
        List<OcrResult> words_result = ocr(image, cpuThreadNum, myModelPath, useOpencl);
        String[] outputResult = new String[words_result.size()];
        for (int i = 0; i < words_result.size(); i++) {
            outputResult[i] = words_result.get(i).words;
            Log.i("outputResult", outputResult[i].toString()); // show LOG in Logcat panel
        }
        return outputResult;
    }

    public String[] ocrText(ImageWrapper image, int cpuThreadNum, Boolean useSlim) {
        return ocrText(image, cpuThreadNum, useSlim, false);
    }

    public String[] ocrText(ImageWrapper image, int cpuThreadNum, String myModelPath) {
        return ocrText(image, cpuThreadNum, myModelPath, false);
    }

    public String[] ocrText(ImageWrapper image) {
        return ocrText(image, 4, true, false);
    }

    public String[] ocrText(ImageWrapper image, int cpuThreadNum) {
        return ocrText(image, cpuThreadNum, true, false);
    }

    public void releaseOcr() {
        mOcrPredictor.releaseModel();
    }

    public void release() {
        mOcrPredictor.releaseModel();
    }

    public boolean initOd() {
        try {
            mDetectionPredictor.init(GlobalAppContext.get(), "", "", new long[]{1, 3, 320, 320}, 4, 0.5f);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean initOd(int cpuThreadNum) {
        try {
            mDetectionPredictor.init(GlobalAppContext.get(), "", "", new long[]{1, 3, 320, 320}, cpuThreadNum, 0.5f);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean initOcr(String modelPath, String LabelPath) {
        try {
            mDetectionPredictor.init(GlobalAppContext.get(), modelPath, LabelPath, new long[]{1, 3, 320, 320}, 4, 0.5f);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean initOcr(String modelPath, String LabelPath, int cpuThreadNum) {
        try {
            mDetectionPredictor.init(GlobalAppContext.get(), modelPath, LabelPath, new long[]{1, 3, 320, 320}, cpuThreadNum, 0.5f);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<DetectionResult> detection(ImageWrapper image, String modelPath, String LabelPath) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mDetectionPredictor.isLoaded()) {
            try {
                mDetectionPredictor.init(GlobalAppContext.get(), modelPath, LabelPath, new long[]{1, 3, 320, 320}, 4, 0.5f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDetectionPredictor.predictImage(bitmap);
    }

    public List<DetectionResult> detection(ImageWrapper image,String modelPath, String LabelPath, Integer CPUTreads) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mDetectionPredictor.isLoaded()) {
            try {
                mDetectionPredictor.init(GlobalAppContext.get(), modelPath, LabelPath, new long[]{1, 3, 320, 320}, CPUTreads, 0.5f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDetectionPredictor.predictImage(bitmap);
    }

    public List<DetectionResult> detection(ImageWrapper image, Integer CPUTreads) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mDetectionPredictor.isLoaded()) {
            try {
                mDetectionPredictor.init(GlobalAppContext.get(), "", "", new long[]{1, 3, 320, 320}, CPUTreads, 0.5f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDetectionPredictor.predictImage(bitmap);
    }

    public List<DetectionResult> detection(ImageWrapper image) {
        if (image == null) {
            return Collections.emptyList();
        }
        Bitmap bitmap = image.getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return Collections.emptyList();
        }
        if (!mDetectionPredictor.isLoaded()) {
            try {
                mDetectionPredictor.init(GlobalAppContext.get(), "", "", new long[]{1, 3, 320, 320}, 4, 0.5f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDetectionPredictor.predictImage(bitmap);
    }

}
