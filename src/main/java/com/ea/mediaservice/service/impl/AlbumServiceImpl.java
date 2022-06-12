package com.ea.mediaservice.service.impl;

import com.ea.mediaservice.exception.BlogapiException;
import com.ea.mediaservice.exception.ResourceNotFoundException;
import com.ea.mediaservice.model.Album;

import com.ea.mediaservice.payload.AlbumResponse;
import com.ea.mediaservice.payload.ApiResponse;
import com.ea.mediaservice.payload.PagedResponse;
import com.ea.mediaservice.payload.AlbumRequest;
import com.ea.mediaservice.repository.AlbumRepository;
import com.ea.mediaservice.service.AlbumService;
import com.ea.mediaservice.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ea.mediaservice.utils.AppConstants.ID;

@Service
public class AlbumServiceImpl implements AlbumService {
    private static final String CREATED_AT = "createdAt";

    private static final String ALBUM_STR = "Album";

    private static final String YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION = "You don't have permission to make this operation";

    @Autowired
    private AlbumRepository albumRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PagedResponse<AlbumResponse> getAllAlbums(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Album> albums = albumRepository.findAll(pageable);

        if (albums.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), albums.getNumber(), albums.getSize(), albums.getTotalElements(),
                    albums.getTotalPages(), albums.isLast());
        }

        List<AlbumResponse> albumResponses = Arrays.asList(modelMapper.map(albums.getContent(), AlbumResponse[].class));

        return new PagedResponse<>(albumResponses, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(),
                albums.isLast());
    }

    @Override
    public ResponseEntity<Album> addAlbum(AlbumRequest albumRequest) {

        Album album = new Album();

        modelMapper.map(albumRequest, album);

        Album newAlbum = albumRepository.save(album);
        return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Album> getAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));

        album.setTitle(newAlbum.getTitle());
        Album updatedAlbum = albumRepository.save(album);

        AlbumResponse albumResponse = new AlbumResponse();

        modelMapper.map(updatedAlbum, albumResponse);

        return new ResponseEntity<>(albumResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ApiResponse> deleteAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));

        albumRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted album"), HttpStatus.OK);

    }

    @Override
    public PagedResponse<Album> getUserAlbums(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Album> albums = albumRepository.findByUserId(userId, pageable);

        List<Album> content = albums.getNumberOfElements() > 0 ? albums.getContent() : Collections.emptyList();

        return new PagedResponse<>(content, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
    }
}
