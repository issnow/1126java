package com.gc.controller;

import com.gc.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

//文件上传和下载
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
  //获取配置文件的配置路径
  @Value("${waimai.path}")
  String basePath;
  @PostMapping("/upload")
  public Result<String> upload(MultipartFile file) {
    //file是个临时文件，我们在断点调试的时候可以看到，但是执行完整个方法之后就消失了
    log.info(file.toString());

    //获取一下传入的原文件名
    String originalFilename = file.getOriginalFilename();
    //我们只需要获取一下格式后缀，取子串，起始点为最后一个.
    String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
    //为了防止出现重复的文件名，我们需要使用UUID
    String filename = UUID.randomUUID().toString() + ext;

    //判断一下当前目录是否存在，不存在则创建
    File dir = new File(basePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    //判断一下当前目录是否存在，不存在则创建
    try {
      //我们将其转存到我们的指定目录下
      file.transferTo(new File(basePath + filename));
    } catch (IOException e) {
      e.printStackTrace();
    }
    //将文件名返回给前端，便于后期的开发
    return Result.success(filename);
  }

  @GetMapping("/download")
  public void download(HttpServletResponse res, String name) {
    try {
      //输入流,读取文件内容
      FileInputStream fis = new FileInputStream(new File(basePath + name));
      //输出流,将文件写回浏览器,展示图片
      ServletOutputStream os = res.getOutputStream();
      res.setContentType("image/jpeg");
      byte[] bytes = new byte[1024];
      int len = 0;
      while ((len = fis.read(bytes)) != -1) {
        os.write(bytes, 0, len);
        os.flush();
      }
      os.close();
      fis.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
