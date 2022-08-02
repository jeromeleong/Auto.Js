"ui";
ui.layout(
    <vertical>
        <horizontal bg="#c7edcc" gravity="center" h="auto">
            <button text="后退" id="backBtn" style="Widget.AppCompat.Button.Colored" w="64" />
            <button text="刷新" id="reloadBtn" style="Widget.AppCompat.Button.Colored" w="64" />
            <button text="PC模式" id="desktopModeBtn" style="Widget.AppCompat.Button.Colored" w="72" />
            <button text="日志" id="logBtn" style="Widget.AppCompat.Button.Colored" w="64" />
        </horizontal>
        <vertical h="*" w="*">
            <webview id="webView" layout_below="title" w="*" h="*" />
        </vertical>
    </vertical>
);

let url = "https://0x3.com/"
ui.webView.loadUrl(url)
// 延时执行，必须等待页面加载完成后才能正常获取页面元素内容
setTimeout(function () {
    ui.webView.evaluateJavascript("javascript:function a(){return document.title};a();", new JavaAdapter(android.webkit.ValueCallback, {
        onReceiveValue: (val) => {
            toastLog("当前网页标题：" + val)
        }
    }));
}, 3 * 1000);
ui.reloadBtn.on("click", () => {
    ui.webView.reload()
});
ui.desktopModeBtn.on('click', () => {
    // 设置是否启用PC模式的两个方法：setIsDesktopMode()，getIsDesktopMode()。
    ui.webView.setIsDesktopMode(!ui.webView.getIsDesktopMode())
    ui.webView.reload()
});
ui.logBtn.on("click", () => {
    app.startActivity("console");
});
ui.backBtn.on("click", () => {
    if (ui.webView.canGoBack()) {
        ui.webView.goBack();
    } else {
        exit();
    }
});



