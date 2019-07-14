package br.com.leonardo.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import br.com.leonardo.api.service.ProductExecuteService;
import br.com.leonardo.commons.infrastruture.AmazonS3Client;
import br.com.leonardo.commons.infrastruture.FileService;
import br.com.leonardo.commons.model.FilePathData;
import br.com.leonardo.commons.queue.RabbitMQSender;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductExecuteServiceTest {
	
	@Mock
	private AmazonS3Client amazonS3Service;
	
	@Mock
	private FileService fileManager;
	
	@Mock
	private RabbitMQSender rabbitMQSender;

	@InjectMocks
	private ProductExecuteService productExecuteService;
	
	@Test
	public void testHandleFileUpload() throws Throwable {
		
        MockMultipartFile multipartFile =  new MockMultipartFile("files", "filename.txt", "text/plain", "hello".getBytes(StandardCharsets.UTF_8));
				
		File value = new File("some");
		
		when(fileManager.getFile(multipartFile.getOriginalFilename(),multipartFile.hashCode(),multipartFile.getBytes())).thenReturn(value);
		
		FilePathData filePathData = new FilePathData("some", "some");
		
		when(amazonS3Service.upload(any())).thenReturn(filePathData);
		
		doNothing().when(rabbitMQSender).send(filePathData);
		
		productExecuteService.handleFileUpload(multipartFile);
		
		verify(fileManager,times(1)).getFile(multipartFile.getOriginalFilename(),multipartFile.hashCode(),multipartFile.getBytes());
		
		verify(amazonS3Service,times(1)).upload(any());
		
		verify(rabbitMQSender,times(1)).send(filePathData);
	
	}
	
}
