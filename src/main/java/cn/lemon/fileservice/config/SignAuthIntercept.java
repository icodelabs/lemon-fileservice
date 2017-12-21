package cn.lemon.fileservice.config;

import cn.lemon.framework.response.ResultMessage;
import cn.lemon.framework.response.ServiceException;
import cn.lemon.framework.utils.SignatureUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 签名授权拦截
 * 
 * @author lonyee
 *
 */
@Component
public class SignAuthIntercept extends HandlerInterceptorAdapter {
	@Resource
	private AppIdConfig appIdConfig;

	public SignAuthIntercept() { }


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");

		String appId = request.getParameter("appId");
		if (Strings.isNullOrEmpty(appId)) {
			throw new ServiceException(ResultMessage.F4030);
		}

		//验证签名
		boolean isVerify = appIdConfig.getAppIds().contains(appId);
		if (!isVerify) {
			throw new ServiceException(ResultMessage.F4030);
		}
		return true;
	}
}
