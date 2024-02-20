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
public class GalleryJpaService implements GalleryRepository {
	@Autowired
	private ArtistJpaRepository artistJpaRepository;

	@Autowired
	private GalleryJpaRepository galleryJpaRepository;

	public ArrayList<Gallery> getGalleries() {
		List<Gallery> galleryList = galleryJpaRepository.findAll();
		ArrayList<Gallery> galleries = new ArrayList<>(galleryList);
		return galleries;
	}

	public Gallery getGalleryById(int galleryId) {
		try {
			Gallery gallery = galleryJpaRepository.findById(galleryId).get();
			return gallery;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public Gallery addGallery(Gallery gallery) {
		List<Integer> artistIds = new ArrayList<>();
		for (Artist artist : gallery.getArtists()) {
			artistIds.add(artist.getArtistId());
		}
		List<Artist> artists = artistJpaRepository.findAllById(artistIds);
		gallery.setArtists(artists);

		for (Artist artist : artists) {
			artist.getGalleries().add(gallery);
		}

		Gallery savedGallery = galleryJpaRepository.save(gallery);
		artistJpaRepository.saveAll(artists);

		return savedGallery;
	}

	public Gallery updateGallery(int galleryId, Gallery gallery) {
		try {
			Gallery newGallery = galleryJpaRepository.findById(galleryId).get();
			if (gallery.getGalleryName() != null) {
				newGallery.setGalleryName(gallery.getGalleryName());
			}
			if (gallery.getLocation() != null) {
				newGallery.setLocation(gallery.getLocation());
			}
			if (gallery.getArtists() != null) {
				List<Artist> artists = newGallery.getArtists();
				for (Artist artist : artists) {
					artist.getGalleries().remove(newGallery);
				}
				artistJpaRepository.saveAll(artists);
				List<Integer> newArtistIds = new ArrayList<>();
				for (Artist artist : gallery.getArtists()) {
					newArtistIds.add(artist.getArtistId());
				}
				List<Artist> newArtists = artistJpaRepository.findAllById(newArtistIds);
				for (Artist artsit : newArtists) {
					artsit.getGalleries().add(newGallery);
				}
				artistJpaRepository.saveAll(newArtists);
				newGallery.setArtists(newArtists);
			}
			return galleryJpaRepository.save(newGallery);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public void deleteGallery(int galleryId) {
		try {
			Gallery gallery = galleryJpaRepository.findById(galleryId).get();
			List<Artist> artists = gallery.getArtists();
			for (Artist artist : artists) {
				artist.getGalleries().remove(gallery);
			}
			artistJpaRepository.saveAll(artists);
			galleryJpaRepository.deleteById(galleryId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	public List<Artist> getGalleryArtists(int galleryId) {
		try {
			Gallery gallery = galleryJpaRepository.findById(galleryId).get();
			return gallery.getArtists();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}