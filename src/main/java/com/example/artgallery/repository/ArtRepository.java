/*
 * You can use the following import statements
 *
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here

package com.example.artgallery.repository;

import com.example.artgallery.model.*;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ArtRepository {
    ArrayList<Art> getArts();

    Art getArtById(int artId);

    Art addArt(Art art);

    Art updateArt(int artId, Art art);

    void deleteArt(int artId);

    Artist getArtArtist(int artId);
}