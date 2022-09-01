package com.stardust.autojs.core.mlkit.detection;

import android.graphics.Rect;

public class DetectionResult {
    public Rect bounds;
    public float confidence;
    public String words;
    public String language;

    public DetectionResult() {
    }

    public DetectionResult(String words, float confidence, Rect bounds) {
        this.bounds = bounds;
        this.confidence = confidence;
        this.words = words;
    }


    public DetectionResult(String words, Rect bounds) {
        this.bounds = bounds;
        this.words = words;
    }
}
