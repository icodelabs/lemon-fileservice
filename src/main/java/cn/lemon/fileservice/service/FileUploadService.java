package cn.lemon.fileservice.service;

import cn.lemon.fileservice.config.AliossConfig;
import cn.lemon.framework.encrypt.Md5Util;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.base.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云oss文件服务器上传
 * Created by lonyee on 2017/8/2.
 *
 */
@Service
public class FileUploadService {
	private Log logger = LogFactory.getLog(this.getClass());

	@Resource(name="aliossConfig")
	private AliossConfig ossConfig;
	
    /**
     * 上传文件
     */
	public String uploadFile(byte[] fileBytes, long size, String path, String ext) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(fileBytes);
		String filePath = Strings.isNullOrEmpty(path) ? "upload": path;
		String fileId = Md5Util.getMD5(fileBytes); //UUID.randomUUID().toString().replace("-", "");
		String fileName = String.format("%s/%s.%s", filePath, fileId, ext);
		this.upload(inputStream, size, fileName);
		return ossConfig.getBaseOssUri() + fileName;
	}

	/**
	 * 文件上传OSS
	 */
	private void upload(final InputStream inputStream, final long size, final String fileName) {
		OSSClient ossClient = new OSSClient(ossConfig.getEndPoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
		try {
			this.createBucket(ossClient, ossConfig.getBucketName(), true);

			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(size);
			meta.setHeader("Content-Disposition", "inline");
			PutObjectResult result = ossClient.putObject(ossConfig.getBucketName(), fileName, inputStream, meta);
			logger.info("upload oss file " + fileName + " success, tag: " +result.getETag());
		} catch (Exception e) {
			logger.error("upload oss file " + fileName + " error. ", e);
			throw new RuntimeException("upload oss file error", e);
		} finally {
			try {
				inputStream.close();
				ossClient.shutdown();
			} catch (IOException e) {}
			logger.info(String.format("thread %d finished", this.hashCode()));
		}
	}

	/**
	 * 创建bucket，阿里设定最多10个bucketName
	 * @param bucketName bucket名称
	 * @param isPublic 是否可以公开访问
	 */
	public void createBucket(OSSClient ossClient, String bucketName, Boolean isPublic){
		boolean hasExists = ossClient.doesBucketExist(bucketName);
		if (!hasExists) {
			// 新建一个Bucket
			ossClient.createBucket(bucketName);
			// 设置操作权限对外公开
			ossClient.setBucketAcl(bucketName, isPublic? CannedAccessControlList.PublicRead: CannedAccessControlList.Private);
		}
	}

}
