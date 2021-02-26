package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVICE;

    @RequestMapping("/uploadFile")
    public Result upload(MultipartFile file){
        try {
            String confPath = "classpath:fastDFS/fdfs_client.conf";
            FastDFSClient fastDFSClient = new FastDFSClient(confPath);
            String filePath = fastDFSClient.uploadFile(file.getBytes(),file.getOriginalFilename(),file.getSize());
            return new Result(true,FILE_SERVICE+filePath);
        }catch (Exception e){
            return new Result(false,"上传失败");
        }
    }
}
