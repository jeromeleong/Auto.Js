package com.stardust.autojs.core.mlkit.ocr

import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.stardust.autojs.core.image.ImageWrapper
import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.text.Text.TextBlock
import com.google.android.gms.tasks.OnFailureListener
import com.google.mlkit.vision.text.Text
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.CountDownLatch

class Ocr {
    private var textRecognizer: TextRecognizer? = null
    private var result_view: String? = null
    private fun getLanguage(language: String): TextRecognizer {
        return when (language) {
            "zh" -> TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())
            "sa" -> TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())
            "ja" -> TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())
            "ko" -> TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
            else -> TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        }
    }

    private fun getView(view: String?): String {
        return when (view) {
            "block" -> "block"
            "line" -> "line"
            else -> "element"
        }
    }

    @JvmOverloads
    fun Predictor( image: ImageWrapper?, language: String = "zh", view: String? = result_view): List<OcrResult> {
        val controller = CountDownLatch(1)
        if (image == null) {
            return emptyList()
        }
        val bitmap = image.bitmap
        if (bitmap == null || bitmap.isRecycled) {
            return emptyList()
        }
        textRecognizer = getLanguage(language)
        val result: MutableList<OcrResult> = ArrayList()
        result_view = getView(view)
        textRecognizer!!.process(InputImage.fromBitmap(bitmap, 0))
            .addOnSuccessListener { visionText: Text ->
                Log.d("MlKit", "ocr: success")
                for (block in visionText.textBlocks) {
                    if (result_view == "block") {
                        result.add(
                            OcrResult(
                                block.text,
                                block.boundingBox,
                                block.recognizedLanguage
                            )
                        )
                    } else {
                        for (line in block.lines) {
                            if (result_view == "line") {
                                result.add(
                                    OcrResult(
                                        line.text,
                                        line.boundingBox,
                                        line.recognizedLanguage
                                    )
                                )
                            } else {
                                if (result_view == "element") {
                                    for (element in line.elements) {
                                        result.add(
                                            OcrResult(
                                                element.text,
                                                element.confidence,
                                                element.boundingBox,
                                                element.recognizedLanguage
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
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
}