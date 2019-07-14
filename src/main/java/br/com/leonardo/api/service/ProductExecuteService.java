package br.com.leonardo.api.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;

import br.com.leonardo.commons.infrastruture.AmazonS3Client;
import br.com.leonardo.commons.infrastruture.FileService;
import br.com.leonardo.commons.model.FilePathData;
import br.com.leonardo.commons.queue.RabbitMQSender;

@Component
public class ProductExecuteService {

	@Autowired
	private AmazonS3Client amazonS3Service;

	@Autowired
	private FileService fileManager;

	@Autowired
	private RabbitMQSender rabbitMQSender;

	public void handleFileUpload(MultipartFile multipartFile) throws IOException, AmazonClientException, InterruptedException {
		
		File file = fileManager.getFile(multipartFile.getOriginalFilename(),multipartFile.hashCode(),multipartFile.getBytes());
		
		FilePathData filePathData = amazonS3Service.upload(file);
		
		rabbitMQSender.send(filePathData);
		
		fileManager.removeFile(file);
		
	}
	
	

}
