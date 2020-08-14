# sissy university 移动课程中心

sissy university 移动课程中心。来自`抖喵家`的在线XXXX和XXX的单机游戏安卓移植本地版本。

没有任何网络请求权限，隐私万岁。

## Build

你需要一份原版的sissy university数据副本来构建该软件。你可以从原网站下载或从TG群组下载由Star依原网站进行封装的桌面版。

请将`css`,`js`,`img`目录拷贝到`app/src/main/assets/public`目录下，注意，请勿覆盖`app/src/main/assets/public/index.html`。

构建前你可能需要创建自己的签名证书，详情请见`app/build.gradle`。

## Use

该App会请求本地存储权限用于数据导出，禁止该权限不影响其他功能。