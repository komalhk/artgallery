package com.example.artgallery;

import com.example.artgallery.controller.GalleryController;
import com.example.artgallery.controller.ArtistController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private GalleryController galleryController;

    @Autowired
    private ArtistController artistController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(galleryController).isNotNull();
        assertThat(artistController).isNotNull();
    }
}
