package com.stardust.autojs.core.mlkit.ocr;

import android.graphics.Rect;

public class OcrResult {
    public Rect bounds;
    public float confidence;
    public String words;
    public String language;

    public OcrResult() {
    }

    public OcrResult(String words, float confidence, Rect bounds, String language) {
        this.bounds = bounds;
        this.confidence = confidence;
        this.words = words;
        this.language = language;
    }

    public OcrResult(String words, Rect bounds, String language) {
        this.bounds = bounds;
        this.confidence = -1f;
        this.words = words;
        this.language = language;
    }

}
