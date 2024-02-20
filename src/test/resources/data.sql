INSERT INTO artist (name, genre) 
SELECT 'Leonardo da Vinci', 'Renaissance'
WHERE NOT EXISTS (SELECT 1 FROM artist WHERE id = 1);

INSERT INTO artist (name, genre) 
SELECT 'Vincent van Gogh', 'Post-Impressionism'
WHERE NOT EXISTS (SELECT 2 FROM artist WHERE id = 2);

INSERT INTO artist (name, genre) 
SELECT 'Pablo Picasso', 'Cubism'
WHERE NOT EXISTS (SELECT 3 FROM artist WHERE id = 3);

INSERT INTO artist (name, genre) 
SELECT 'Edward Hopper', 'American Modernism'
WHERE NOT EXISTS (SELECT 4 FROM artist WHERE id = 4);

INSERT INTO art (title, theme, artistId)
SELECT 'The Flight Study', 'Studies of Bird Wings', 1
WHERE NOT EXISTS (SELECT 1 FROM art WHERE id = 1);

INSERT INTO art (title, theme, artistId)
SELECT 'Mona Lisa 2.0', 'Renaissance Portrait', 1
WHERE NOT EXISTS (SELECT 2 FROM art WHERE id = 2);

INSERT INTO art (title, theme, artistId)
SELECT 'Starry Countryside', 'Night Landscape', 2
WHERE NOT EXISTS (SELECT 3 FROM art WHERE id = 3);

INSERT INTO art (title, theme, artistId)
SELECT 'Sunflower Impressions', 'Floral', 2
WHERE NOT EXISTS (SELECT 4 FROM art WHERE id = 4);

INSERT INTO art (title, theme, artistId)
SELECT 'Cubist Self-Portrait', 'Abstract Portrait', 3
WHERE NOT EXISTS (SELECT 5 FROM art WHERE id = 5);

INSERT INTO art (title, theme, artistId)
SELECT 'Barcelona Abstracted', 'City Landscape', 3
WHERE NOT EXISTS (SELECT 6 FROM art WHERE id = 6);

INSERT INTO gallery (name, location)
SELECT 'Louvre Museum', 'Paris'
WHERE NOT EXISTS (SELECT 1 FROM gallery WHERE id = 1);

INSERT INTO gallery (name, location)
SELECT 'Van Gogh Museum', 'Amsterdam'
WHERE NOT EXISTS (SELECT 2 FROM gallery WHERE id = 2);

INSERT INTO gallery (name, location)
SELECT 'Museo Picasso', 'Barcelona'
WHERE NOT EXISTS (SELECT 3 FROM gallery WHERE id = 3);

INSERT INTO gallery (name, location)
SELECT 'Whitney Museum of American Art', 'New York'
WHERE NOT EXISTS (SELECT 4 FROM gallery WHERE id = 4);

INSERT INTO artist_gallery (artistId, galleryId)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM artist_gallery WHERE artistId = 1 AND galleryId = 1);

INSERT INTO artist_gallery (artistId, galleryId)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 2 FROM artist_gallery WHERE artistId = 1 AND galleryId = 2);

INSERT INTO artist_gallery (artistId, galleryId)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 3 FROM artist_gallery WHERE artistId = 2 AND galleryId = 2);

INSERT INTO artist_gallery (artistId, galleryId)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 4 FROM artist_gallery WHERE artistId = 3 AND galleryId = 3);

INSERT INTO artist_gallery (artistId, galleryId)
SELECT 3, 4
WHERE NOT EXISTS (SELECT 5 FROM artist_gallery WHERE artistId = 3 AND galleryId = 4);

INSERT INTO artist_gallery (artistId, galleryId)
SELECT 4, 4
WHERE NOT EXISTS (SELECT 6 FROM artist_gallery WHERE artistId = 4 AND galleryId = 4);