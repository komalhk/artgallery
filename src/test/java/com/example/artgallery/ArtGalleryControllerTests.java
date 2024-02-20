package com.example.artgallery;

import com.example.artgallery.model.Art;
import com.example.artgallery.model.Artist;
import com.example.artgallery.model.Gallery;
import com.example.artgallery.repository.ArtistJpaRepository;
import com.example.artgallery.repository.ArtJpaRepository;
import com.example.artgallery.repository.GalleryJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class ArtGalleryControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private GalleryJpaRepository galleryJpaRepository;

        @Autowired
        private ArtistJpaRepository artistJpaRepository;

        @Autowired
        private ArtJpaRepository artJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> artHashMap = new HashMap<>(); // Art
        {
                artHashMap.put(1, new Object[] { "The Flight Study", "Studies of Bird Wings", 1 });
                artHashMap.put(2, new Object[] { "Mona Lisa 2.0", "Renaissance Portrait", 1 });
                artHashMap.put(3, new Object[] { "Starry Countryside", "Night Landscape", 2 });
                artHashMap.put(4, new Object[] { "Sunflower Impressions", "Floral", 2 });
                artHashMap.put(5, new Object[] { "Cubist Self-Portrait", "Abstract Portrait", 3 });
                artHashMap.put(6, new Object[] { "Barcelona Abstracted", "City Landscape", 3 });
                artHashMap.put(7, new Object[] { "Downtown Solitude", "Urban Scene", 3 }); // POST
                artHashMap.put(8, new Object[] { "Night Cafe Redux", "Modernist Interior", 4 }); // PUT
        }

        private HashMap<Integer, Object[]> artistsHashMap = new HashMap<>(); // Artist
        {
                artistsHashMap.put(1, new Object[] { "Leonardo da Vinci", "Renaissance", new Integer[] { 1, 2 } });
                artistsHashMap.put(2, new Object[] { "Vincent van Gogh", "Post-Impressionism", new Integer[] { 2 } });
                artistsHashMap.put(3, new Object[] { "Pablo Picasso", "Cubism", new Integer[] { 3, 4 } });
                artistsHashMap.put(4, new Object[] { "Edward Hopper", "American Modernism", new Integer[] { 4 } });
                artistsHashMap.put(5, new Object[] { "Frida Kahlo", "Surrealism", new Integer[] { 4, 5 } }); // POST
                artistsHashMap.put(6, new Object[] { "Claude Monet", "Impressionism", new Integer[] { 5 } }); // PUT
        }

        private HashMap<Integer, Object[]> galleriesHashMap = new HashMap<>(); // Gallery
        {
                galleriesHashMap.put(1, new Object[] { "Louvre Museum", "Paris", new Integer[] { 1 } });
                galleriesHashMap.put(2, new Object[] { "Van Gogh Museum", "Amsterdam", new Integer[] { 1, 2 } });
                galleriesHashMap.put(3, new Object[] { "Museo Picasso", "Barcelona", new Integer[] { 3 } });
                galleriesHashMap.put(4,
                                new Object[] { "Whitney Museum of American Art", "New York", new Integer[] { 3, 4 } });
                galleriesHashMap.put(5, new Object[] { "National Museum of Women in the Arts", "Washington, D.C.",
                                new Integer[] { 4 } }); // POST
                galleriesHashMap.put(6, new Object[] { "Musée d’Orsay", "Paris", new Integer[] { 5 } }); // PUT
        }

        @Test
        @Order(1)
        public void testGetArts() throws Exception {
                mockMvc.perform(get("/galleries/artists/arts")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].artId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].artTitle", Matchers.equalTo(artHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].theme", Matchers.equalTo(artHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].artist.artistId", Matchers.equalTo(artHashMap.get(1)[2])))

                                .andExpect(jsonPath("$[1].artId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].artTitle", Matchers.equalTo(artHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].theme", Matchers.equalTo(artHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].artist.artistId", Matchers.equalTo(artHashMap.get(2)[2])))

                                .andExpect(jsonPath("$[2].artId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].artTitle", Matchers.equalTo(artHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].theme", Matchers.equalTo(artHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].artist.artistId", Matchers.equalTo(artHashMap.get(3)[2])))

                                .andExpect(jsonPath("$[3].artId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].artTitle", Matchers.equalTo(artHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].theme", Matchers.equalTo(artHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].artist.artistId", Matchers.equalTo(artHashMap.get(4)[2])))

                                .andExpect(jsonPath("$[4].artId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].artTitle", Matchers.equalTo(artHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[4].theme", Matchers.equalTo(artHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[4].artist.artistId", Matchers.equalTo(artHashMap.get(5)[2])))

                                .andExpect(jsonPath("$[5].artId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].artTitle", Matchers.equalTo(artHashMap.get(6)[0])))
                                .andExpect(jsonPath("$[5].theme", Matchers.equalTo(artHashMap.get(6)[1])))
                                .andExpect(jsonPath("$[5].artist.artistId", Matchers.equalTo(artHashMap.get(6)[2])));
        }

        @Test
        @Order(2)
        public void testGetArtNotFound() throws Exception {
                mockMvc.perform(get("/galleries/artists/arts/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(3)
        public void testGetArtById() throws Exception {
                mockMvc.perform(get("/galleries/artists/arts/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(1)[2])));

                mockMvc.perform(get("/galleries/artists/arts/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(2)[2])));

                mockMvc.perform(get("/galleries/artists/arts/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(3)[2])));

                mockMvc.perform(get("/galleries/artists/arts/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(4)[2])));

                mockMvc.perform(get("/galleries/artists/arts/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(5)[2])));

                mockMvc.perform(get("/galleries/artists/arts/6")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(6)[2])));
        }

        @Test
        @Order(4)
        public void testPostArt() throws Exception {
                String content = "{\n    \"artTitle\": \"" + artHashMap.get(7)[0] + "\",\n    \"theme\": \""
                                + artHashMap.get(7)[1] + "\",\n    \"artist\": {\n        \"artistId\": "
                                + artHashMap.get(7)[2] + "\n    }\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/galleries/artists/arts")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(7)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(7)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(7)[2])));
        }

        @Test
        @Order(5)
        public void testAfterPostArt() throws Exception {
                mockMvc.perform(get("/galleries/artists/arts/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(7)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(7)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(7)[2])));
        }

        @Test
        @Order(6)
        @Transactional
        public void testDbAfterPostArt() throws Exception {
                Art art = artJpaRepository.findById(7).get();

                assertEquals(art.getArtId(), 7);
                assertEquals(art.getArtTitle(), artHashMap.get(7)[0]);
                assertEquals(art.getTheme(), artHashMap.get(7)[1]);
                assertEquals(art.getArtist().getArtistId(), artHashMap.get(7)[2]);
        }

        @Test
        @Order(7)
        public void testPutArtNotFound() throws Exception {
                String content = "{\n    \"artTitle\": \"" + artHashMap.get(8)[0] + "\",\n    \"theme\": \""
                                + artHashMap.get(8)[1] + "\",\n    \"artist\": {\n        \"artistId\": "
                                + artHashMap.get(8)[2] + "\n    }\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/artists/arts/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(8)
        public void testPutArt() throws Exception {
                String content = "{\n    \"artTitle\": \"" + artHashMap.get(8)[0] + "\",\n    \"theme\": \""
                                + artHashMap.get(8)[1] + "\",\n    \"artist\": {\n        \"artistId\": "
                                + artHashMap.get(8)[2] + "\n    }\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/artists/arts/7")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(8)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(8)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(8)[2])));
        }

        @Test
        @Order(9)
        public void testAfterPutArt() throws Exception {
                mockMvc.perform(get("/galleries/artists/arts/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artId", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.artTitle", Matchers.equalTo(artHashMap.get(8)[0])))
                                .andExpect(jsonPath("$.theme", Matchers.equalTo(artHashMap.get(8)[1])))
                                .andExpect(jsonPath("$.artist.artistId", Matchers.equalTo(artHashMap.get(8)[2])));
        }

        @Test
        @Order(10)
        @Transactional
        public void testDbAfterPutArt() throws Exception {
                Art art = artJpaRepository.findById(7).get();

                assertEquals(art.getArtId(), 7);
                assertEquals(art.getArtTitle(), artHashMap.get(8)[0]);
                assertEquals(art.getTheme(), artHashMap.get(8)[1]);
                assertEquals(art.getArtist().getArtistId(), artHashMap.get(8)[2]);
        }

        @Test
        @Order(11)
        public void testDeleteArtNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                                .delete("/galleries/artists/arts/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(12)
        public void testDeleteArt() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/galleries/artists/arts/7");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(13)
        public void testAfterDeleteArt() throws Exception {
                mockMvc.perform(get("/galleries/artists/arts")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].artId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].artTitle", Matchers.equalTo(artHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].theme", Matchers.equalTo(artHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].artist.artistId", Matchers.equalTo(artHashMap.get(1)[2])))

                                .andExpect(jsonPath("$[1].artId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].artTitle", Matchers.equalTo(artHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].theme", Matchers.equalTo(artHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].artist.artistId", Matchers.equalTo(artHashMap.get(2)[2])))

                                .andExpect(jsonPath("$[2].artId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].artTitle", Matchers.equalTo(artHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].theme", Matchers.equalTo(artHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].artist.artistId", Matchers.equalTo(artHashMap.get(3)[2])))

                                .andExpect(jsonPath("$[3].artId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].artTitle", Matchers.equalTo(artHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].theme", Matchers.equalTo(artHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].artist.artistId", Matchers.equalTo(artHashMap.get(4)[2])))

                                .andExpect(jsonPath("$[4].artId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].artTitle", Matchers.equalTo(artHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[4].theme", Matchers.equalTo(artHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[4].artist.artistId", Matchers.equalTo(artHashMap.get(5)[2])))

                                .andExpect(jsonPath("$[5].artId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].artTitle", Matchers.equalTo(artHashMap.get(6)[0])))
                                .andExpect(jsonPath("$[5].theme", Matchers.equalTo(artHashMap.get(6)[1])))
                                .andExpect(jsonPath("$[5].artist.artistId", Matchers.equalTo(artHashMap.get(6)[2])));
        }

        @Test
        @Order(14)
        public void testGetArtArtist() throws Exception {
                mockMvc.perform(get("/arts/1/artist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/arts/2/artist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/arts/3/artist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/arts/4/artist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/arts/5/artist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/arts/6/artist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[1])));
        }

        @Test
        @Order(15)
        public void testGetArtistArts() throws Exception {
                mockMvc.perform(get("/artists/1/arts")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                                .andExpect(jsonPath("$[0].artId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].artTitle", Matchers.equalTo(artHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].theme", Matchers.equalTo(artHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].artist.artistId", Matchers.equalTo(artHashMap.get(1)[2])))
                                .andExpect(jsonPath("$[1].artId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].artTitle", Matchers.equalTo(artHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].theme", Matchers.equalTo(artHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].artist.artistId", Matchers.equalTo(artHashMap.get(2)[2])));

                mockMvc.perform(get("/artists/2/arts")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                                .andExpect(jsonPath("$[0].artId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[0].artTitle", Matchers.equalTo(artHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[0].theme", Matchers.equalTo(artHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[0].artist.artistId", Matchers.equalTo(artHashMap.get(3)[2])))
                                .andExpect(jsonPath("$[1].artId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[1].artTitle", Matchers.equalTo(artHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[1].theme", Matchers.equalTo(artHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[1].artist.artistId", Matchers.equalTo(artHashMap.get(4)[2])));

                mockMvc.perform(get("/artists/3/arts")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                                .andExpect(jsonPath("$[0].artId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[0].artTitle", Matchers.equalTo(artHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[0].theme", Matchers.equalTo(artHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[0].artist.artistId", Matchers.equalTo(artHashMap.get(5)[2])))
                                .andExpect(jsonPath("$[1].artId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[1].artTitle", Matchers.equalTo(artHashMap.get(6)[0])))
                                .andExpect(jsonPath("$[1].theme", Matchers.equalTo(artHashMap.get(6)[1])))
                                .andExpect(jsonPath("$[1].artist.artistId", Matchers.equalTo(artHashMap.get(6)[2])));
        }

        @Test
        @Order(16)
        public void testGetGalleries() throws Exception {
                mockMvc.perform(get("/galleries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].galleryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].galleryName", Matchers.equalTo(galleriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].location", Matchers.equalTo(galleriesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].galleryId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].galleryName", Matchers.equalTo(galleriesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].location", Matchers.equalTo(galleriesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].galleryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].galleryName", Matchers.equalTo(galleriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].location", Matchers.equalTo(galleriesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].galleryId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].galleryName", Matchers.equalTo(galleriesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].location", Matchers.equalTo(galleriesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(17)
        public void testGetArtists() throws Exception {
                mockMvc.perform(get("/galleries/artists")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].artistId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].artistName", Matchers.equalTo(artistsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].genre", Matchers.equalTo(artistsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].artistId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].artistName", Matchers.equalTo(artistsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].genre", Matchers.equalTo(artistsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].artistId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].artistName", Matchers.equalTo(artistsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].genre", Matchers.equalTo(artistsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].artistId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].artistName", Matchers.equalTo(artistsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].genre", Matchers.equalTo(artistsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(18)
        public void testGetGalleryNotFound() throws Exception {
                mockMvc.perform(get("/galleries/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(19)
        public void testGetGalleryById() throws Exception {
                mockMvc.perform(get("/galleries/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/galleries/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/galleries/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/galleries/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(20)
        public void testGetArtistNotFound() throws Exception {
                mockMvc.perform(get("/galleries/artists/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(21)
        public void testGetArtistById() throws Exception {
                mockMvc.perform(get("/galleries/artists/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/galleries/artists/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/galleries/artists/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/galleries/artists/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(22)
        public void testPostGallery() throws Exception {
                String content = "{\n    \"galleryName\": \"" + galleriesHashMap.get(5)[0]
                                + "\",\n    \"location\": \""
                                + galleriesHashMap.get(5)[1]
                                + "\",\n    \"artists\": [\n        {\n            \"artistId\": "
                                + ((Integer[]) galleriesHashMap.get(5)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/galleries")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(23)
        public void testAfterPostGallery() throws Exception {
                mockMvc.perform(get("/galleries/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(5)[2])[0])));
        }

        @Test
        @Order(24)
        @Transactional
        public void testDbAfterPostGallery() throws Exception {
                Gallery gallery = galleryJpaRepository.findById(5).get();

                assertEquals(gallery.getGalleryId(), 5);
                assertEquals(gallery.getGalleryName(), galleriesHashMap.get(5)[0]);
                assertEquals(gallery.getLocation(), galleriesHashMap.get(5)[1]);
                assertEquals(gallery.getArtists().get(0).getArtistId(), ((Integer[]) galleriesHashMap.get(5)[2])[0]);

                Artist artist = artistJpaRepository.findById(((Integer[]) galleriesHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < artist.getGalleries().size(); i++) {
                        if (artist.getGalleries().get(i).getGalleryId() == 5) {
                                break;
                        }
                }
                if (i == artist.getGalleries().size()) {
                        throw new AssertionError("Assertion Error: Artist " + artist.getArtistId()
                                        + " has no gallery with galleryId 5");
                }
        }

        @Test
        @Order(25)
        public void testPostArtistBadRequest() throws Exception {
                String content = "{\n    \"artistName\": \"" + artistsHashMap.get(5)[0] + "\",\n    \"genre\": \""
                                + artistsHashMap.get(5)[1]
                                + "\",\n    \"galleries\": [\n        {\n            \"galleryId\": "
                                + ((Integer[]) artistsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"galleryId\": " + 48 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/galleries/artists")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(26)
        public void testPostArtist() throws Exception {
                String content = "{\n    \"artistName\": \"" + artistsHashMap.get(5)[0] + "\",\n    \"genre\": \""
                                + artistsHashMap.get(5)[1]
                                + "\",\n    \"galleries\": [\n        {\n            \"galleryId\": "
                                + ((Integer[]) artistsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"galleryId\": "
                                + ((Integer[]) artistsHashMap.get(5)[2])[1]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/galleries/artists")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(27)
        public void testAfterPostArtist() throws Exception {
                mockMvc.perform(get("/galleries/artists/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(28)
        @Transactional
        public void testDbAfterPostArtist() throws Exception {
                Artist artist = artistJpaRepository.findById(5).get();

                assertEquals(artist.getArtistId(), 5);
                assertEquals(artist.getArtistName(), artistsHashMap.get(5)[0]);
                assertEquals(artist.getGenre(), artistsHashMap.get(5)[1]);
                assertEquals(artist.getGalleries().get(0).getGalleryId(), ((Integer[]) artistsHashMap.get(5)[2])[0]);
                assertEquals(artist.getGalleries().get(1).getGalleryId(), ((Integer[]) artistsHashMap.get(5)[2])[1]);

                Gallery gallery = galleryJpaRepository.findById(((Integer[]) artistsHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < gallery.getArtists().size(); i++) {
                        if (gallery.getArtists().get(i).getArtistId() == 5) {
                                break;
                        }
                }
                if (i == gallery.getArtists().size()) {
                        throw new AssertionError("Assertion Error: Gallery " + gallery.getGalleryId()
                                        + " has no artist with artistId 5");
                }

                gallery = galleryJpaRepository.findById(((Integer[]) artistsHashMap.get(5)[2])[1]).get();
                for (i = 0; i < gallery.getArtists().size(); i++) {
                        if (gallery.getArtists().get(i).getArtistId() == 5) {
                                break;
                        }
                }
                if (i == gallery.getArtists().size()) {
                        throw new AssertionError("Assertion Error: Gallery " + gallery.getGalleryId()
                                        + " has no artist with artistId 5");
                }
        }

        @Test
        @Order(29)
        public void testPutGalleryNotFound() throws Exception {
                String content = "{\n    \"galleryName\": \"" + galleriesHashMap.get(6)[0]
                                + "\",\n    \"location\": \""
                                + galleriesHashMap.get(6)[1]
                                + "\",\n    \"artists\": [\n        {\n            \"artistId\": "
                                + ((Integer[]) galleriesHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(30)
        public void testPutGallery() throws Exception {
                String content = "{\n    \"galleryName\": \"" + galleriesHashMap.get(6)[0]
                                + "\",\n    \"location\": \""
                                + galleriesHashMap.get(6)[1]
                                + "\",\n    \"artists\": [\n        {\n            \"artistId\": "
                                + ((Integer[]) galleriesHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(31)
        public void testAfterPutGallery() throws Exception {
                mockMvc.perform(get("/galleries/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.galleryId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.galleryName", Matchers.equalTo(galleriesHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.location", Matchers.equalTo(galleriesHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(32)
        @Transactional
        public void testDbAfterPutGallery() throws Exception {
                Gallery gallery = galleryJpaRepository.findById(5).get();

                assertEquals(gallery.getGalleryId(), 5);
                assertEquals(gallery.getGalleryName(), galleriesHashMap.get(6)[0]);
                assertEquals(gallery.getLocation(), galleriesHashMap.get(6)[1]);
                assertEquals(gallery.getArtists().get(0).getArtistId(), ((Integer[]) galleriesHashMap.get(6)[2])[0]);

                Artist artist = artistJpaRepository.findById(((Integer[]) galleriesHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < artist.getGalleries().size(); i++) {
                        if (artist.getGalleries().get(i).getGalleryId() == 5) {
                                break;
                        }
                }
                if (i == artist.getGalleries().size()) {
                        throw new AssertionError("Assertion Error: Artist " + artist.getArtistId()
                                        + " has no gallery with galleryId 5");
                }
        }

        @Test
        @Order(33)
        public void testPutArtistNotFound() throws Exception {
                String content = "{\n    \"artistName\": \"" + artistsHashMap.get(6)[0] + "\",\n    \"genre\": \""
                                + artistsHashMap.get(6)[1]
                                + "\",\n    \"galleries\": [\n        {\n            \"galleryId\": "
                                + ((Integer[]) artistsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/artists/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(34)
        public void testPutArtistBadRequest() throws Exception {
                String content = "{\n    \"artistName\": \"" + artistsHashMap.get(6)[0] + "\",\n    \"genre\": \""
                                + artistsHashMap.get(6)[1]
                                + "\",\n    \"galleries\": [\n        {\n            \"galleryId\": "
                                + 48 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/artists/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(35)
        public void testPutArtist() throws Exception {
                String content = "{\n    \"artistName\": \"" + artistsHashMap.get(6)[0] + "\",\n    \"genre\": \""
                                + artistsHashMap.get(6)[1]
                                + "\",\n    \"galleries\": [\n        {\n            \"galleryId\": "
                                + ((Integer[]) artistsHashMap.get(6)[2])[0]
                                + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/galleries/artists/5")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(36)
        public void testAfterPutArtist() throws Exception {

                mockMvc.perform(get("/galleries/artists/5")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.artistId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$.artistName", Matchers.equalTo(artistsHashMap.get(6)[0])))
                                .andExpect(jsonPath("$.genre", Matchers.equalTo(artistsHashMap.get(6)[1])))
                                .andExpect(jsonPath("$.galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(6)[2])[0])));
        }

        @Test
        @Order(37)
        @Transactional
        public void testDbAfterPutArtist() throws Exception {
                Artist artist = artistJpaRepository.findById(5).get();

                assertEquals(artist.getArtistId(), 5);
                assertEquals(artist.getArtistName(), artistsHashMap.get(6)[0]);
                assertEquals(artist.getGenre(), artistsHashMap.get(6)[1]);
                assertEquals(artist.getGalleries().get(0).getGalleryId(), ((Integer[]) artistsHashMap.get(6)[2])[0]);

                Gallery gallery = galleryJpaRepository.findById(((Integer[]) artistsHashMap.get(6)[2])[0]).get();

                int i;
                for (i = 0; i < gallery.getArtists().size(); i++) {
                        if (gallery.getArtists().get(i).getArtistId() == 5) {
                                break;
                        }
                }
                if (i == gallery.getArtists().size()) {
                        throw new AssertionError("Assertion Error: Gallery " + gallery.getGalleryId()
                                        + " has no artist with artistId 5");
                }
        }

        @Test
        @Order(38)
        public void testDeleteArtistNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/galleries/artists/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(39)
        public void testDeleteArtist() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/galleries/artists/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(40)
        @Transactional
        @Rollback(false)
        public void testAfterDeleteArtist() throws Exception {
                mockMvc.perform(get("/galleries/artists")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].artistId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].artistName", Matchers.equalTo(artistsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].genre", Matchers.equalTo(artistsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].artistId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].artistName", Matchers.equalTo(artistsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].genre", Matchers.equalTo(artistsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(2)[2])[0])))

                                .andExpect(jsonPath("$[2].artistId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].artistName", Matchers.equalTo(artistsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].genre", Matchers.equalTo(artistsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[1])))

                                .andExpect(jsonPath("$[3].artistId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].artistName", Matchers.equalTo(artistsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].genre", Matchers.equalTo(artistsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].galleries[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(4)[2])[0])));

                Gallery gallery = galleryJpaRepository.findById(((Integer[]) artistsHashMap.get(6)[2])[0]).get();

                for (Artist artist : gallery.getArtists()) {
                        if (artist.getArtistId() == 5) {
                                throw new AssertionError("Assertion Error: Artist " + artist.getArtistId()
                                                + " and Gallery 5 are still linked");
                        }
                }
        }

        @Test
        @Order(41)
        public void testDeleteGalleryNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/galleries/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(42)
        public void testDeleteGallery() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/galleries/5");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(43)
        public void testAfterDeleteGallery() throws Exception {
                mockMvc.perform(get("/galleries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(4)))

                                .andExpect(jsonPath("$[0].galleryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].galleryName", Matchers.equalTo(galleriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].location", Matchers.equalTo(galleriesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(1)[2])[0])))

                                .andExpect(jsonPath("$[1].galleryId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].galleryName", Matchers.equalTo(galleriesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].location", Matchers.equalTo(galleriesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].galleryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].galleryName", Matchers.equalTo(galleriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].location", Matchers.equalTo(galleriesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(3)[2])[0])))

                                .andExpect(jsonPath("$[3].galleryId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].galleryName", Matchers.equalTo(galleriesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].location", Matchers.equalTo(galleriesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[3].artists[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(44)
        public void testGetGalleryByArtistId() throws Exception {
                mockMvc.perform(get("/artists/1/galleries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(1)[2])[1])));

                mockMvc.perform(get("/artists/2/galleries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(2)[2])[0])));

                mockMvc.perform(get("/artists/3/galleries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(3)[2])[1])));

                mockMvc.perform(get("/artists/4/galleries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].galleryId",
                                                hasItem(((Integer[]) artistsHashMap.get(4)[2])[0])));
        }

        @Test
        @Order(45)
        public void testGetArtistByGalleryId() throws Exception {
                mockMvc.perform(get("/galleries/1/artists")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(1)[2])[0])));

                mockMvc.perform(get("/galleries/2/artists")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/galleries/3/artists")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(3)[2])[0])));

                mockMvc.perform(get("/galleries/4/artists")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$[*].artistId",
                                                hasItem(((Integer[]) galleriesHashMap.get(4)[2])[1])));
        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table artist_gallery");
                jdbcTemplate.execute("drop table art");
                jdbcTemplate.execute("drop table artist");
                jdbcTemplate.execute("drop table gallery");
        }

}