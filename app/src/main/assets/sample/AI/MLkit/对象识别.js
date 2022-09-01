//mlkit 只能識別5个对象
let img = images.read("./1.jpeg")
result = mlkit.detection(img);
for (let i = 0; i < result.length; i++) {
    let obj = result[i]
    log(obj.words + " " + obj.confidence)
}
