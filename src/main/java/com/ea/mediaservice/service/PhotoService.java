package com.ea.mediaservice.service;

import com.ea.mediaservice.payload.ApiResponse;
import com.ea.mediaservice.payload.PagedResponse;
import com.ea.mediaservice.payload.PhotoRequest;
import com.ea.mediaservice.payload.PhotoResponse;

public interface PhotoService {

	PagedResponse<PhotoResponse> getAllPhotos(int page, int size);

	PhotoResponse getPhoto(Long id);

	PhotoResponse updatePhoto(Long id, PhotoRequest photoRequest);

	PhotoResponse addPhoto(PhotoRequest photoRequest);

	ApiResponse deletePhoto(Long id);

	PagedResponse<PhotoResponse> getAllPhotosByAlbum(Long albumId, int page, int size);

}