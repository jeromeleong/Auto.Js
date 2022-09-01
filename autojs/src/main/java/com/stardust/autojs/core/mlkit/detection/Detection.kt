package com.stardust.autojs.core.mlkit.detection

import com.stardust.autojs.core.image.ImageWrapper
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import java.util.ArrayList
import java.util.concurrent.CountDownLatch

class Detection {
    private var customModel: CustomObjectDetectorOptions? = null

    fun Predictor( image: ImageWrapper?): List<DetectionResult> {
        val controller = CountDownLatch(1)
        val detector: ObjectDetector;

        if (image == null) {
            return emptyList()
        }
        val bitmap = image.bitmap
        if (bitmap == null || bitmap.isRecycled) {
            return emptyList()
        }

        if (customModel != null){
            detector = ObjectDetection.getClient(customModel!!)
        }else{
            detector = ObjectDetection.getClient(ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()  // Optional
                        .build())
        }
        val result: MutableList<DetectionResult> = ArrayList()
        detector.process(InputImage.fromBitmap(bitmap, 0))
            .addOnSuccessListener { detectorResult ->
                detectorResult.map {
                    var text = "Unknown"
                    var confidence = -1f
                    // We will show the top confident detection result if it exist
                    if (it.labels.isNotEmpty()) {
                        val firstLabel = it.labels.first()
                        text = firstLabel.text
                        confidence = firstLabel.confidence
                    }
                    result.add(
                        DetectionResult(
                            text,
                            confidence,
                            it.boundingBox
                        )
                    )
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
            .addOnCompleteListener {
                controller.countDown()
            }
        controller.await()
        return result
    }

    fun Predictor( image: ImageWrapper?, modelPath:String): List<DetectionResult> {
        try{
            customModel =
                CustomObjectDetectorOptions.Builder(LocalModel.Builder().setAbsoluteFilePath(modelPath).build())
                    .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
                    .enableMultipleObjects()
                    .enableClassification()
                    .setClassificationConfidenceThreshold(0.5f)
                    .setMaxPerObjectLabelCount(3)
                    .build()
        }catch (e:Exception){
            customModel=null;
        }
        return Predictor(image)
    }
}