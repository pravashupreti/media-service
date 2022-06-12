package com.ea.mediaservice.service;

import com.ea.mediaservice.model.Album;
import com.ea.mediaservice.payload.AlbumResponse;
import com.ea.mediaservice.payload.ApiResponse;
import com.ea.mediaservice.payload.PagedResponse;
import com.ea.mediaservice.payload.AlbumRequest;
import org.springframework.http.ResponseEntity;

public interface AlbumService {

	PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

	ResponseEntity<Album> addAlbum(AlbumRequest albumRequest);

	ResponseEntity<Album> getAlbum(Long id);

	ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum);

	ResponseEntity<ApiResponse> deleteAlbum(Long id );

	PagedResponse<Album> getUserAlbums(Long userId, int page, int size);

}
