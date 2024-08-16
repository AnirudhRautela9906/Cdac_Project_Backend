package com.seeker.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seeker.exception.BackendException;

@Service
public class FileServices {
	
	
	public List<String> uploadImages(String path, MultipartFile[] images) {
		
		//Ensure the upload directory exists
		File uploadDir = new File(path);
		if(!uploadDir.exists()) {
			boolean dirCreated = uploadDir.mkdirs();
			if(!dirCreated) {
				throw new BackendException("Error creating upload directory");
			}
		}
		
		//Save images and get paths
		List<String> imagePaths = new ArrayList<String>();
		
		
		return null;
	}
}
