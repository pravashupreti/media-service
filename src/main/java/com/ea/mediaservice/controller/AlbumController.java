package com.ea.mediaservice.controller;

import com.ea.mediaservice.exception.ResponseEntityErrorException;
import com.ea.mediaservice.model.Album;
import com.ea.mediaservice.payload.AlbumResponse;
import com.ea.mediaservice.payload.ApiResponse;
import com.ea.mediaservice.payload.PagedResponse;
import com.ea.mediaservice.payload.PhotoResponse;
import com.ea.mediaservice.payload.AlbumRequest;
import com.ea.mediaservice.security.CurrentUser;
import com.ea.mediaservice.security.UserPrincipal;
import com.ea.mediaservice.service.AlbumService;
import com.ea.mediaservice.service.PhotoService;
import com.ea.mediaservice.utils.AppConstants;
import com.ea.mediaservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
	@Autowired
	private AlbumService albumService;

	@Autowired
	private PhotoService photoService;

	@ExceptionHandler(ResponseEntityErrorException.class)
	public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
		return exception.getApiResponse();
	}

	@GetMapping
	public PagedResponse<AlbumResponse> getAllAlbums(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		AppUtils.validatePageNumberAndSize(page, size);

		return albumService.getAllAlbums(page, size);
	}

	@PostMapping
	public ResponseEntity<Album> addAlbum(@Valid @RequestBody AlbumRequest albumRequest, @CurrentUser UserPrincipal currentuser) {
		return albumService.addAlbum(albumRequest,currentuser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Album> getAlbum(@PathVariable(name = "id") Long id) {
		return albumService.getAlbum(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AlbumResponse> updateAlbum(@PathVariable(name = "id") Long id, @Valid @RequestBody AlbumRequest newAlbum
			) {
		return albumService.updateAlbum(id, newAlbum);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteAlbum(@PathVariable(name = "id") Long id) {
		return albumService.deleteAlbum(id);
	}

	@GetMapping("/{id}/photos")
	public ResponseEntity<PagedResponse<PhotoResponse>> getAllPhotosByAlbum(@PathVariable(name = "id") Long id,
                                                                            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<PhotoResponse> response = photoService.getAllPhotosByAlbum(id, page, size);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
