package com.shark.controller;

import com.example.magisk_mount_bridge.utils.LocalServiceLite;
import com.google.gson.Gson;
import com.shark.activity.ActivityRoute;
import com.shark.activity.RouteResult;
import com.shark.activity.RouteSetting;
import com.shark.app.AppSetting;
import com.shark.app.SubController;
import com.shark.common.GlobalConfig;
import com.shark.common.SharkController;
import com.shark.hook.ContactsHook;
import com.shark.http.HttpResponse;
import com.shark.http.HttpUtils;
import com.shark.log.FileLog;
import com.shark.param.Param;
import com.shark.param.SuccessResult;
import com.shark.task.TaskSignal;
import com.shark.tool.ScreenShot;
import com.shark.web.WebViewHook;
import com.shark.zip.ZipDownLoad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

@AppSetting(value = "com.tencent.mm", timeOut = 30 * 60000)
public class ViewController extends SharkController {


    private int test;

    @ActivityRoute("com.tencent.mm.ui.LauncherUI")
    @RouteSetting(reentry = false, sleepTime = 15000)
    public void enterPay() throws InterruptedException {

        sharkLog("点击+");
        viewHelperImp.clickById(0x7f09279a);
        Thread.sleep(5000);

        sharkLog("点击扫一扫");
        viewHelperProxy.clickXyByText("扫一扫");
        Thread.sleep(5000);

        sharkLog("点击相册");
        viewHelperImp.clickById(0x7f092e30);
        Thread.sleep(5000);

        sharkLog("点击第一张图片");
        viewHelperImp.clickFirstById(0x7f092c5f);
        Thread.sleep(10000);

        if(viewHelperImp.hasShowViewByIdText(0x7f0904c9,"关注")){
            sharkLog("用户未关注");
            viewHelperImp.clickIdByText(0x7f0904c9,"关注");
            Thread.sleep(8000);
        }
        if(viewHelperImp.hasShowViewByIdText(0x7f0908c0,"点击")){
            sharkLog("进入公众号成功");
        }else {
            sharkLog("进入公众号失败");
        }
        while (true){
            if(viewHelperImp.hasViewByIdText(0x7f0908c0,"点击")){
                viewHelperImp.clickIdByText(0x7f0908c0,"点击");
                Thread.sleep(5000);
                if(viewHelperImp.hasShowViewByText("点击链接")) {
                    viewHelperImp.clickByText("点击链接");
                    sharkLog("hasShowViewByText点击链接");
                }
                if(viewHelperImp.hasViewByText("点击链接")){
                    viewHelperImp.clickXyByText("点击链接");
                    sharkLog("hasViewByText点击链接");
                }
            }
        