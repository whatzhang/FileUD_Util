package zy.com.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(UploadController.class);

	@RequestMapping("/uploadFileAjax")
	@ResponseBody
	public String uploadFileAjax(@RequestParam("upfile") CommonsMultipartFile file) {

		return "SUCCESS";
	}
}
