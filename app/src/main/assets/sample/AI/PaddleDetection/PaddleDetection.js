let img = images.read("./0.jpeg")
result = paddle.detection(img)
j = 0
for (let i = 0; i < result.length; i++) {
    let obj = result[i]
    if (obj.words == "人"){
        j++;
    }
}
toastLog("共有"+ j + "個人")