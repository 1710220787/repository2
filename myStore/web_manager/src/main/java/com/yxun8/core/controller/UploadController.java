package com.yxun8.core.controller;

import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.utils.FastDFSUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {
    /*注入properties里面的属性*/
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;


    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) throws Exception {
        try {
            System.out.println(file.getOriginalFilename());
            FastDFSUtils fastDFSUtils = new FastDFSUtils("classpath:FastFDS/fdfs_client.conf");
            //上传文件
            String path = fastDFSUtils.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
            //拼接最新的路径
            String newFile = FILE_SERVER_URL + path;
            System.out.println(newFile);
            return new Result(true, newFile);
        }catch (Exception e){
            return new Result(false, "上传失败");
        }
    }

    @RequestMapping("deleteImage")
    public Result deleteImage(String url) throws Exception {
        FastDFSUtils fastDFSUtils = new FastDFSUtils("classpath:FastFDS/fdfs_client.conf");
        String path = url.substring(FILE_SERVER_URL.length());   /*截取*/
        System.out.println(path);
        Integer index = fastDFSUtils.delete_file(path);
        System.out.println(index);
        if (index == 0){
            return new Result(true,"删除成功");
        }else {
            return new Result(false,"删除失败");
        }
    }


    /*富文本*/
    @RequestMapping("/uploadImage")
    public Map uploadImage(MultipartFile upfile) throws Exception {
        try {
            FastDFSUtils fastDFSUtils = new FastDFSUtils("classpath:FastFDS/fdfs_client.conf");
            //上传文件返回文件保存的路径和文件名
            String path = fastDFSUtils.uploadFile(upfile.getBytes(), upfile.getOriginalFilename(), upfile.getSize());
            //拼接上服务器的地址返回给前端
            String url  = FILE_SERVER_URL + path;
            System.out.println(url);
            Map<String ,Object > result = new HashMap<>();
            result.put("state","SUCCESS");
            result.put("url",url);
            result.put("title",upfile.getOriginalFilename());
            result.put("original",upfile.getOriginalFilename());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
