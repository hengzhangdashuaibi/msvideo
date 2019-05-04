package com.zhangheng.qingcloud.msvideo.controller;

import com.zhangheng.qingcloud.msvideo.util.YHResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 坂田银时 on 2019/5/4.
 * 使用javacv唤起用户摄像头
 */

@RestController
@RequestMapping("/msvideo/javacv")//窄化请求地址
@Api(value = "msvideo", description = "javacv相关")
public class JavacvController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 唤起用户摄像头
     * opencvf
     */
    @ApiOperation(value = "CVF起调用户摄像头", response = String.class, notes = "唤起摄像头", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", required = true, name = "cvfName", dataType = "String", value = "唤起摄像头名称"),
    })
    @RequestMapping(value = "/openCVF", method = RequestMethod.GET)
    public void openCVF(
            @RequestParam(value = "cvfName", required = true) String cvfName,
            HttpServletRequest request
    )throws Exception, InterruptedException{

//        try {

            OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
            grabber.start();   //开始获取摄像头数据
            CanvasFrame canvas = new CanvasFrame(cvfName, CanvasFrame.getDefaultGamma() / grabber.getGamma());//新建一个窗口
            canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            canvas.setAlwaysOnTop(true);

            while(true)
            {
                if(!canvas.isDisplayable())
                {//窗口是否关闭
                    grabber.stop();//停止抓取
                    System.exit(2);//退出
                }
                canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像

                Thread.sleep(50);//50毫秒刷新一次图像
            }
//        }catch (Exception e){
//            log.error(e.getMessage());
//            log.error("openCVF接口 异常!");
//            return YHResult.build(500,"接口异常!");
//        }



    }

}
