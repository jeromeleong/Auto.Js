package com.stardust.autojs.runtime.api;

import android.util.Log;

import com.stardust.autojs.core.image.ImageWrapper;

import com.stardust.autojs.core.mlkit.ocr.Ocr;
import com.stardust.autojs.core.mlkit.ocr.OcrResult;

import com.stardust.autojs.core.mlkit.detection.Detection;
import com.stardust.autojs.core.mlkit.detection.DetectionResult;

import java.util.List;

public class MlKit {
    private final Ocr mOcr = new Ocr();
    private final Detection mDetection = new Detection();

    public List<OcrResult> ocr(ImageWrapper image , String language, String view) { return mOcr.Predictor(image,language,view);}

    public List<OcrResult> ocr(ImageWrapper image , String language) { return mOcr.Predictor(image,language);}

    public List<OcrResult> ocr(ImageWrapper image) {
        return mOcr.Predictor(image);
    }

    public String[] ocrTotext(List<OcrResult> words_result) {
        String[] outputResult = new String[words_result.size()];
        for (int i = 0; i < words_result.size(); i++) {
            outputResult[i] = words_result.get(i).words;
            Log.i("outputResult", outputResult[i].toString()); // show LOG in Logcat panel
        }
        return outputResult;
    }

    public String[] ocrText(ImageWrapper image, String language, String view) {return ocrTotext(mOcr.Predictor(image,language,view));}

    public String[] ocrText(ImageWrapper image, String language) { return ocrTotext(mOcr.Predictor(image,language));}

    public String[] ocrText(ImageWrapper image) {
        return ocrTotext(mOcr.Predictor(image));
    }


    public List<DetectionResult> detection(ImageWrapper image) {return mDetection.Predictor(image);}

    public List<DetectionResult> detection(ImageWrapper image,String modelPath) {return mDetection.Predictor(image,modelPath);}
}
