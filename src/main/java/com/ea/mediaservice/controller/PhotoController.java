package com.ea.mediaservice.controller;

import com.ea.mediaservice.payload.ApiResponse;
import com.ea.mediaservice.payload.PagedResponse;
import com.ea.mediaservice.payload.PhotoRequest;
import com.ea.mediaservice.payload.PhotoResponse;
import com.ea.mediaservice.service.PhotoService;
import com.ea.mediaservice.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
	@Autowired
	private PhotoService photoService;

	@GetMapping
	public PagedResponse<PhotoResponse> getAllPhotos(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		return photoService.getAllPhotos(page, size);
	}

	@PostMapping
	public ResponseEntity<PhotoResponse> addPhoto(@Valid @RequestBody PhotoRequest photoRequest
			) {
		PhotoResponse photoResponse = photoService.addPhoto(photoRequest);

		return new ResponseEntity< >(photoResponse, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PhotoResponse> getPhoto(@PathVariable(name = "id") Long id) {
		PhotoResponse photoResponse = photoService.getPhoto(id);

		return new ResponseEntity< >(photoResponse, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PhotoResponse> updatePhoto(@PathVariable(name = "id") Long id,
			@Valid @RequestBody PhotoRequest photoRequest) {

		PhotoResponse photoResponse = photoService.updatePhoto(id, photoRequest);

		return new ResponseEntity< >(photoResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePhoto(@PathVariable(name = "id") Long id) {
		ApiResponse apiResponse = photoService.deletePhoto(id);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}
}
