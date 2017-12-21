package cn.lemon.fileservice.controller;

import java.net.URLEncoder;

import javax.annotation.Resource;

import cn.lemon.fileservice.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.lemon.framework.core.BasicController;
import cn.lemon.framework.encrypt.Base64Util;
import cn.lemon.framework.response.ResultMessage;
import cn.lemon.framework.response.ResultResponse;
import cn.lemon.framework.utils.FileUtil;

/**
 * 图片上传服务接口
 * Created by lonyee on 2017/4/12.
 */
@RestController
@RequestMapping(value = "/img")
public class ImageUploadController extends BasicController {
    Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
    @Resource
    private FileUploadService fileUploadService;

    /**
     * 上传图片文件
     */
    @RequestMapping(value = "/upload", method = { RequestMethod.POST })
    public ResultResponse upload(MultipartFile file, String filePath) {
        // 上传图片的方法
        try {
            // 获取扩展名
            String ext = FileUtil.getExtension(file.getOriginalFilename());
            String fileId = fileUploadService.uploadFile(file.getBytes(), file.getSize(), filePath, ext);
            logger.debug("upload image: "+ fileId);
            return resultResponse.success(fileId);
        } catch (Exception e) {
            logger.error("图片上传失败. {}", e.getMessage());
            return resultResponse.failure(ResultMessage.F4070);
        }
    }

    /**
     * 上传BASE64加密图片文件
     */
    @RequestMapping(value = "/base64/upload", method = { RequestMethod.POST })
    public ResultResponse uploadBase64(String data, String fileName, String filePath) {
        // 上传图片的方法
        try {
            logger.debug("base64 image: "+ fileName);
            // 获取扩展名
            String ext = FileUtil.getExtension(fileName);
            byte[] bt = Base64Util.decode(data.substring(data.indexOf(",")+1));
            String fileId = fileUploadService.uploadFile(bt, bt.length, filePath, ext);
            logger.debug("upload image: "+ fileId);
            return resultResponse.success(fileId);
        } catch (Exception e) {
            logger.error("图片上传失败. {}", e.getMessage());
            return resultResponse.failure(ResultMessage.F4070);
        }

    }
}
