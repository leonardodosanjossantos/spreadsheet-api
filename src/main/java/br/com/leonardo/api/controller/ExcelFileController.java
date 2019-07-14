package br.com.leonardo.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;

import br.com.leonardo.api.service.ProductExecuteService;

@Controller
@RequestMapping("file")
public class ExcelFileController {
		
	@Autowired
	private Environment environment;
	
	@Autowired
	private ProductExecuteService productExecuteService; 
	
	@GetMapping("/")
	public String upload(Model model) {
		return "upload";
	}
    
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile multipartFile) {
    	String messageError = null;
    	try {
			productExecuteService.handleFileUpload(multipartFile);
			return ResponseEntity.ok().body("File transfer ok");
		} catch (AmazonClientException e) {
			messageError = environment.getProperty("upload.message.error.amazon.client.exception");
			e.printStackTrace();
		} catch (IOException e) {
			messageError = environment.getProperty("upload.message.error.io.exception");
			e.printStackTrace();
		} catch (InterruptedException e) {
			messageError = environment.getProperty("upload.message.error.interrupted.exception");
			e.printStackTrace();
		}
    	
    	return ResponseEntity.unprocessableEntity().body(messageError);
    }

}
