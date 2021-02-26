package cn.itcast.core.util;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

public class FastDFSClient {
    private TrackerClient trackerClient=null;
    private TrackerServer trackerServer=null;
    private StorageClient1 storageClient=null;
    private StorageServer storageServer=null;

    public FastDFSClient(String conf) throws Exception {
        if(conf.contains("classpath:")){
            conf.replace("classpath:",this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer =null;
        storageClient = new StorageClient1(trackerServer,storageServer);
    }
    public String uploadFile(byte[] bytes, String originalFilename, long size) throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[3];
        nameValuePair[0] = new NameValuePair("fileName",originalFilename);
        nameValuePair[1] = new NameValuePair("fileSize",String.valueOf(size));
        nameValuePair[2] = new NameValuePair("fileExt", FilenameUtils.getExtension(originalFilename));
        String result = storageClient.upload_file1(originalFilename,FilenameUtils.getExtension(originalFilename),nameValuePair);
        return result;
    }
}
