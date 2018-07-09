package zy.com.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import zy.com.util.FileUtil;

@Controller
@RequestMapping("/Operate")
public class AllOperateController {

	private static final Log logger = LogFactory.getLog(AllOperateController.class);

	@RequestMapping("/getFileList")
	public String getAllFiles(@RequestParam("upfile") CommonsMultipartFile file, HttpSession session, Model model) {

		String dir = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar;
		logger.info(dir);
		model.addAttribute("filesList",FileUtil.getAllFiles(dir));
		return "main";
	}
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFileAjax(@RequestParam("upfile") CommonsMultipartFile file, HttpSession session, Model model) {

		String dir = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar;
		String path = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar
				+ file.getOriginalFilename();
		String toFilePath = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar
				+ FileUtil.reNameToZip(file.getOriginalFilename());
		logger.info(path + "\n" + toFilePath);
		File newFile = new File(path);
		try {
			file.transferTo(newFile);
			// 压缩
			FileUtil.zipFile(path, toFilePath);
		} catch (IllegalStateException | IOException e) {
			logger.error("UploadFileToLocal_error");
			return "NO";
		}
		model.addAttribute("filesList",FileUtil.getAllFiles(dir));
		return "YES";
	}

	/**
	 * 删除选中文件
	 * 
	 * @param id
	 * @param type
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/DeleteFile", method = RequestMethod.GET)
	@ResponseBody
	public String DeleteFile(@RequestParam("filename") String fileName, HttpSession session, Model model) {
		String dir = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar;
		logger.info("DeleteFile++" + fileName);
		String path = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar + fileName;
		String re = FileUtil.deleteFile(path);
		model.addAttribute("filesList",FileUtil.getAllFiles(dir));
		if (!re.equals("NO_SUCH_FILE")) {
			return "YES";
		} else {
			return "NO_SUCH_FILE";
		}
	}

	/**
	 * 下载不同类型的数据Excel
	 * 
	 * @param type
	 * @param session
	 * @return
	 */
	@RequestMapping("/DownloadFile")
	public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String fileName, HttpSession session) {

		logger.info("downloadFile+" + fileName);
		HttpHeaders headers = new HttpHeaders();
		String dir = session.getServletContext().getRealPath("/FileRepertory") + File.separatorChar;

		try {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("downloadTypeExcl_error");
		}
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ResponseEntity<byte[]> entity = null;
		try {
			entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(getDownloadFile(fileName, dir)), headers,
					HttpStatus.CREATED);
		} catch (IOException e) {
			logger.error("downloadFile_ResponseEntity_error");
		}
		return entity;
	}

	private File getDownloadFile(String fileName, String dir) {

		String path = dir + fileName;
		logger.info(path);
		File file = new File(path);
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

}
