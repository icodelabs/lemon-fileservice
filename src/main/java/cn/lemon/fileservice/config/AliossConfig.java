package cn.lemon.fileservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.base.Strings;

/**
 * 阿里OSS上传服务配置
 * @author lonyee
 *
 */
@ConfigurationProperties(prefix = "alioss")
public class AliossConfig {
	private String bucketName;
	private String endPoint;
	private String baseOssUri;
	private String accessKeyId;
	private String accessKeySecret;

	public String getBaseOssUri() {
		return baseOssUri;
	}

	public void setBaseOssUri(String baseOssUri) {
		this.baseOssUri = baseOssUri;
	}

	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
}
