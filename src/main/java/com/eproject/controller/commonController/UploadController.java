package com.eproject.controller.commonController;

import com.eproject.common.Contants;
import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.util.MallUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 文件上传控制器
 */
@Controller
@RequestMapping("/admin")
public class UploadController {

    @RequestMapping({"upload/file"})
    @ResponseBody
    public R upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException{
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(Contants.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(Contants.FILE_UPLOAD_DIC + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
//            if (fileDirectory.exists()){
//                return R.error(-1,"文件已存在");
//            }
            file.transferTo(destFile);
            R resultSuccess = R.genSuccessResult();
            resultSuccess.setData(MallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName);
            return R.ok("上传成功");
        }catch (IOException e){
            e.printStackTrace();
            return R.error(-1,"文件上传失败");
        }
    }

}
