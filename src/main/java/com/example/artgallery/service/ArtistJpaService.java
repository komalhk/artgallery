/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.artgallery.service;

import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

@Service
public class ArtistJpaService implements ArtistRepository {
    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @Autowired
    private GalleryJpaRepository galleryJpaRepository;

    @Autowired
    private ArtJpaRepository artJpaRepository;

    @Override
    public ArrayList<Artist> getArtists() {
        List<Artist> artistList = artistJpaRepository.findAll();
        ArrayList<Artist> artists = new ArrayList<>(artistList);
        return artists;
    }

    @Override
    public Artist getArtistById(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist addArtist(Artist artist) {
        List<Integer> galleryIds = new ArrayList<>();
        for (Gallery gallery : artist.getGalleries()) {
            galleryIds.add(gallery.getGalleryId());
        }

        List<Gallery> galleries = galleryJpaRepository.findAllById(galleryIds);

        if (galleries.size() != galleryIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        artist.setGalleries(galleries);
        return artistJpaRepository.save(artist);
    }

    @Override
    public Artist updateArtist(int artistId, Artist artist) {
        try {
            Artist newArtist = artistJpaRepository.findById(artistId).get();

            if (artist.getArtistName() != null) {
                newArtist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                newArtist.setGenre(artist.getGenre());
            }
            if (artist.getGalleries() != null) {
                List<Integer> galleryIds = new ArrayList<>();
                for (Gallery gallery : artist.getGalleries()) {
                    galleryIds.add(gallery.getGalleryId());
                }

                List<Gallery> galleries = galleryJpaRepository.findAllById(galleryIds);
                if (galleries.size() != galleryIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                newArtist.setGalleries(galleries);
            }
            return artistJpaRepository.save(newArtist);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {
        try {
            artistJpaRepository.deleteById(artistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Art> getArtistArts(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artJpaRepository.findByArtist(artist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Gallery> getArtistGalleries(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artist.getGalleries();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}