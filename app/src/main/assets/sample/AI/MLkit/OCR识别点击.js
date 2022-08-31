let img = images.read("./0.jpg")
result = mlkit.ocr(img,"zh","element");
//result = mlkit.ocr(img,"zh","block");
//result = mlkit.ocr(img,"zh","line");
for (let i = 0; i < result.length; i++) {
    let obj = result[i]
    log(obj.words)
}
