In this project, we will build a Spring Boot application to serve as the backend for the 'Art Gallery' platform. 'Art Gallery' is a digital space where art enthusiasts can delve deep into the world of art pieces, their creators, and the galleries that showcase them.

Within 'Art Gallery', there are three key entities: `Art`, `Artist`, and `Gallery`. The `Art` entity establishes a Many-to-One relationship with the `Artist`, signifying that an artist can create multiple art pieces, but each art piece is associated with a single artist. Additionally, the `Artist` entity forms a Many-to-Many relationship with the `Gallery` entity, indicating that an artist can exhibit their artworks in multiple galleries, and similarly, a gallery can showcase works from multiple artists.

<details>
<summary>**Implementation Files**</summary>

Use these files to complete the implementation:

- `ArtController.java`
- `ArtRepository.java`
- `ArtJpaService.java`
- `ArtJpaRepository.java`
- `Art.java`
- `ArtistController.java`
- `ArtistRepository.java`
- `ArtistJpaService.java`
- `ArtistJpaRepository.java`
- `Artist.java`
- `GalleryController.java`
- `GalleryRepository.java`
- `GalleryJpaService.java`
- `GalleryJpaRepository.java`
- `Gallery.java`

</details>

Create a database that contains four tables `art`, `artist`, `gallery`, and `artist_gallery` using the given database schema.

You can refer to this [session](https://learning.ccbp.in/course?c_id=e345dfa4-f5ce-406e-b19a-4ed720c54136&s_id=6a60610e-79c2-4e15-b675-45ddbd9bbe82&t_id=f880166e-2f51-4403-81a0-d2430694dae8), for creating a database.

_Create the SQL files and compose accurate queries to run the application. Inaccurate SQL files will result in test case failures._

<details>
<summary>**Database Schema**</summary>

#### Artist Table

| Columns |                 Type                  |
| :-----: | :-----------------------------------: |
|   id    | INTEGER (Primary Key, Auto Increment) |
|  name   |                 TEXT                  |
|  genre  |                 TEXT                  |

#### Art Table

| Columns  |                 Type                  |
| :------: | :-----------------------------------: |
|    id    | INTEGER (Primary Key, Auto Increment) |
|  title   |                 TEXT                  |
|  theme   |                 TEXT                  |
| artistId |         INTEGER (Foreign Key)         |

#### Gallery Table

| Columns  |                 Type                  |
| :------: | :-----------------------------------: |
|    id    | INTEGER (Primary Key, Auto Increment) |
|   name   |                 TEXT                  |
| location |                 TEXT                  |

#### Junction Table

|  Columns  |         Type          |
| :-------: | :-------------------: |
| artistId  | INTEGER (Foreign Key) |
| galleryId | INTEGER (Foreign Key) |

The columns `artistId` and `galleryId` can be combinedly declared as Primary Keys.

You can use the given sample data to populate the tables.

<details>
<summary>**Sample Data**</summary>

#### Artist Data

|  id   |       name        |       genre        |
| :---: | :---------------: | :----------------: |
|   1   | Leonardo da Vinci |    Renaissance     |
|   2   | Vincent van Gogh  | Post-Impressionism |
|   3   |   Pablo Picass    |       Cubism       |
|   4   |   Edward Hopper   | American Modernism |

#### Art Data

|  id   |         title         |         theme         | artistId |
| :---: | :-------------------: | :-------------------: | :------: |
|   1   |   The Flight Study    | Studies of Bird Wings |    1     |
|   2   |     Mona Lisa 2.0     | Renaissance Portrait  |    1     |
|   3   |  Starry Countryside   |    Night Landscape    |    2     |
|   4   | Sunflower Impressions |        Floral         |    2     |
|   5   | Cubist Self-Portrait  |   Abstract Portrait   |    3     |
|   6   | Barcelona Abstracted  |    City Landscape     |    3     |
|   7   |   Downtown Solitude   |      Urban Scene      |    4     |
|   8   |   Night Cafe Redux    |  Modernist Interior   |    4     |

#### Gallery Data

|  id   |              name              | location  |
| :---: | :----------------------------: | :-------: |
|   1   |         Louvre Museum          |   Paris   |
|   2   |        Van Gogh Museum         | Amsterdam |
|   3   |         Museo Picasso          | Barcelona |
|   4   | Whitney Museum of American Art | New York  |

#### Junction Table

| artistId | galleryId |
| :------: | :-------: |
|    1     |     1     |
|    1     |     2     |
|    2     |     2     |
|    3     |     3     |
|    3     |     4     |
|    4     |     4     |

</details>
</details>

<MultiLineNote>

Use only `art`, `artist`, `gallery`, and `artist_gallery` as the table names in your code.

</MultiLineNote>

### Completion Instructions

- `Artist.java`: The `Artist` class should contain the following attributes.

    | Attribute  |      Type      |
    | :--------: | :------------: |
    |  artistId  |      int       |
    | artistName |     String     |
    |   genre    |     String     |
    | galleries  | List\<Gallery> |

- `ArtistRepository.java`: Create an `interface` containing the required methods.
- `ArtistJpaService.java`: Update the service class with logic for managing artist data.
- `ArtistController.java`: Create the controller class to handle HTTP requests.
- `ArtistJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.
  
- `Art.java`: The `Art` class should contain the following attributes.

    | Attribute |  Type  |
    | :-------: | :----: |
    |   artId   |  int   |
    | artTitle  | String |
    |   theme   | String |
    | artist  |  Artist   |

- `ArtRepository.java`: Create an `interface` containing the required methods.
- `ArtJpaService.java`: Update the service class with logic for managing art data.
- `ArtController.java`: Create the controller class to handle HTTP requests.
- `ArtJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

- `Gallery.java`: The `Gallery` class should contain the following attributes.

    |  Attribute  |     Type      |
    | :---------: | :-----------: |
    |  galleryId  |      int      |
    | galleryName |    String     |
    |  location   |    String     |
    |   artists   | List\<Artist> |

- `GalleryRepository.java`: Create an `interface` containing the required methods.
- `GalleryJpaService.java`: Update the service class with logic for managing gallery data.
- `GalleryController.java`: Create the controller class to handle HTTP requests.
- `GalleryJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

Implement the following APIs.

<details>
<summary>**API 1: GET /galleries**</summary>

#### Path: `/galleries`

#### Method: `GET`

#### Description:

Returns a list of all galleries in the `gallery` table.

#### Response

```json
[
    {
        "galleryId": 1,
        "galleryName": "Louvre Museum",
        "location": "Paris",
        "artists": [
            {
                "artistId": 1,
                "artistName": "Leonardo da Vinci",
                "genre": "Renaissance"
            }
        ]
    },
    ...
]
```

</details>

<details>
<summary>**API 2: POST /galleries**</summary>

#### Path: `/galleries`

#### Method: `POST`

#### Description:

Creates a new gallery in the `gallery` table. Also, create an association between the gallery and artists in the `artist_gallery` table based on the `artistId`s provided in the `artists` field. The `galleryId` is auto-incremented.

#### Request

```json
{
    "galleryName": "National Museum of Women in the Arts",
    "location": "Washington DC",
    "artists": [
        {
            "artistId": 4
        }
    ]
}
```

#### Response

```json
{
    "galleryId": 5,
    "galleryName": "National Museum of Women in the Arts",
    "location": "Washington DC",
    "artists": [
        {
            "artistId": 4,
            "artistName": "Georgia O’Keeffe",
            "genre": "American Modernism"
        }
    ]
}
```

</details>

<details>
<summary>**API 3: GET /galleries/{galleryId}**</summary>

#### Path: `/galleries/{galleryId}`

#### Method: `GET`

#### Description:

Returns a gallery based on the `galleryId`. If the given `galleryId` is not found in the `gallery` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
{
    "galleryId": 1,
    "galleryName": "Louvre Museum",
    "location": "Paris",
    "artists": [
        {
            "artistId": 1,
            "artistName": "Leonardo da Vinci",
            "genre": "Renaissance"
        }
    ]
}
```

</details>

<details>
<summary>**API 4: PUT /galleries/{galleryId}**</summary>

#### Path: `/galleries/{galleryId}`

#### Method: `PUT`

#### Description:

Updates the details of a gallery based on the `galleryId` and returns the updated gallery details. Also update the associations between the gallery and artists, if the `artists` field is provided. If the given `galleryId` is not found in the `gallery` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "location": "Washington, D.C.",
    "artists": []
}
```

#### Success Response

```json
{
    "galleryId": 5,
    "galleryName": "National Museum of Women in the Arts",
    "location": "Washington, D.C.",
    "artists": []
}
```

</details>

<details>
<summary>**API 5: DELETE /galleries/{galleryId}**</summary>

#### Path: `/galleries/{galleryId}`

#### Method: `DELETE`

#### Description:

Deletes a gallery from the `gallery` table and its associations from the `artist_gallery` table based on the `galleryId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `galleryId` is not found in the `gallery` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 6: GET /galleries/{galleryId}/artists**</summary>

#### Path: `/galleries/{galleryId}/artists`

#### Method: `GET`

#### Description:

Returns all artists associated with the gallery based on the `galleryId`. If the given `galleryId` is not found in the `gallery` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "artistId": 1,
        "artistName": "Leonardo da Vinci",
        "genre": "Renaissance",
        "galleries": [
            {
                "galleryId": 1,
                "galleryName": "Louvre Museum",
                "location": "Paris"
            },
            {
                "galleryId": 2,
                "galleryName": "Van Gogh Museum",
                "location": "Amsterdam"
            }
        ]
    }
]
```

</details>

<details>
<summary>**API 7: GET /galleries/artists**</summary>

#### Path: `/galleries/artists`

#### Method: `GET`

#### Description:

Returns a list of all artists in the `artist` table.

#### Response

```json
[
    {
        "artistId": 1,
        "artistName": "Leonardo da Vinci",
        "genre": "Renaissance",
        "galleries": [
            {
                "galleryId": 1,
                "galleryName": "Louvre Museum",
                "location": "Paris"
            },
            {
                "galleryId": 2,
                "galleryName": "Van Gogh Museum",
                "location": "Amsterdam"
            }
        ]
    },
    ...
]
```

</details>

<details>
<summary>**API 8: POST /galleries/artists**</summary>

#### Path: `/galleries/artists`

#### Method: `POST`

#### Description:

Creates a new artist in the `artist` table, if all the `galleryId`s in the `galleries` field exist in the `gallery` table. Also, create an association between the artist and galleries in the `artist_gallery` table. The `artistId` is auto-incremented. If any given `galleryId` is not found in the `gallery` table, raise `ResponseStatusException` with `HttpStatus.BAD_REQUEST`.

#### Request

```json
{
    "artistName": "Frida Kahlo",
    "genre": "Surrealism",
    "galleries": [
        {
            "galleryId": 4
        }
    ]
}
```

#### Success Response

```json
{
    "artistId": 5,
    "artistName": "Frida Kahlo",
    "genre": "Surrealism",
    "galleries": [
        {
            "galleryId": 4,
            "galleryName": "Whitney Museum of American Art",
            "location": "New York"
        }
    ]
}
```

</details>

<details>
<summary>**API 9: GET /galleries/artists/{artistId}**</summary>

#### Path: `/galleries/artists/{artistId}`

#### Method: `GET`

#### Description:

Returns an artist based on the `artistId`. If the given `artistId` is not found in the `artist` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "artistId": 1,
    "artistName": "Leonardo da Vinci",
    "genre": "Renaissance",
    "galleries": [
        {
            "galleryId": 1,
            "galleryName": "Louvre Museum",
            "location": "Paris"
        },
        {
            "galleryId": 2,
            "galleryName": "Van Gogh Museum",
            "location": "Amsterdam"
        }
    ]
}
```

</details>

<details>
<summary>**API 10: PUT /galleries/artists/{artistId}**</summary>

#### Path: `/galleries/artists/{artistId}`

#### Method: `PUT`

#### Description:

Updates the details of an artist based on the `artistId` and returns the updated artist details. Also update the associations between the artist and galleries, if the `galleries` field is provided. If the given `artistId` is not found in the `artist` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`. If any given `galleryId` is not found in the `gallery` table, raise `ResponseStatusException` with `HttpStatus.BAD_REQUEST`.

#### Request

```json
{
    "galleries": [
        {
            "galleryId": 5
        }
    ]
}
```

#### Success Response

```json
{
    "artistId": 5,
    "artistName": "Frida Kahlo",
    "genre": "Surrealism",
    "galleries": [
        {
            "galleryId": 5,
            "galleryName": "National Museum of Women in the Arts",
            "location": "Washington, D.C."
        }
    ]
}
```

</details>

<details>
<summary>**API 11: DELETE /galleries/artists/{artistId}**</summary>

#### Path: `/galleries/artists/{artistId}`

#### Method: `DELETE`

#### Description:

Deletes an artist from the `artist` table based on the `artistId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). Also, remove the association with the arts by keeping a _null_ value in the `art` table. 

If the given `artistId` is not found in the `artist` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`. 

#### Sample Art object when its corresponding Artist is deleted

```json
{
    "artId": 1,
    "artTitle": "The Flight Study",
    "theme": "Studies of Bird Wings",
    "artist": null
}
```

</details>

<details>
<summary>**API 12: GET /artists/{artistId}/arts**</summary>

#### Path: `/artists/{artistId}/arts`

#### Method: `GET`

#### Description:

Returns all arts associated with the artist based on the `artistId`. If the given `artistId` is not found in the `artist` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "artId": 1,
        "artTitle": "The Flight Study",
        "theme": "Studies of Bird Wings",
        "artist": {
            "artistId": 1,
            "artistName": "Leonardo da Vinci",
            "genre": "Renaissance",
            "galleries": [
                {
                    "galleryId": 1,
                    "galleryName": "Louvre Museum",
                    "location": "Paris"
                },
                {
                    "galleryId": 2,
                    "galleryName": "Van Gogh Museum",
                    "location": "Amsterdam"
                }
            ]
        }
    },
    {
        "artId": 2,
        "artTitle": "Mona Lisa 2.0",
        "theme": "Renaissance Portrait",
        "artist": {
            "artistId": 1,
            "artistName": "Leonardo da Vinci",
            "genre": "Renaissance",
            "galleries": [
                {
                    "galleryId": 1,
                    "galleryName": "Louvre Museum",
                    "location": "Paris"
                },
                {
                    "galleryId": 2,
                    "galleryName": "Van Gogh Museum",
                    "location": "Amsterdam"
                }
            ]
        }
    }
]
```

</details>

<details>
<summary>**API 13: GET /artists/{artistId}/galleries**</summary>

#### Path: `/artists/{artistId}/galleries`

#### Method: `GET`

#### Description:

Returns all galleries associated with the artist based on the `artistId`. If the given `artistId` is not found in the `artist` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "galleryId": 1,
        "galleryName": "Louvre Museum",
        "location": "Paris",
        "artists": [
            {
                "artistId": 1,
                "artistName": "Leonardo da Vinci",
                "genre": "Renaissance"
            }
        ]
    },
    {
        "galleryId": 2,
        "galleryName": "Van Gogh Museum",
        "location": "Amsterdam",
        "artists": [
            {
                "artistId": 1,
                "artistName": "Leonardo da Vinci",
                "genre": "Renaissance"
            },
            {
                "artistId": 2,
                "artistName": "Vincent van Gogh",
                "genre": "Post-Impressionism"
            }
        ]
    }
]
```

</details>

<details>
<summary>**API 14: GET /galleries/artists/arts**</summary>

#### Path: `/galleries/artists/arts`

#### Method: `GET`

#### Description:

Returns a list of all arts in the `art` table.

#### Response

```json
[
    {
        "artId": 1,
        "artTitle": "The Flight Study",
        "theme": "Studies of Bird Wings",
        "artist": {
            "artistId": 1,
            "artistName": "Leonardo da Vinci",
            "genre": "Renaissance",
            "galleries": [
                {
                    "galleryId": 1,
                    "galleryName": "Louvre Museum",
                    "location": "Paris"
                },
                {
                    "galleryId": 2,
                    "galleryName": "Van Gogh Museum",
                    "location": "Amsterdam"
                }
            ]
        }
    },
    ...
]
```

</details>

<details>
<summary>**API 15: POST /galleries/artists/arts**</summary>

#### Path: `/galleries/artists/arts`

#### Method: `POST`

#### Description:

Creates a new art in the `art` table and create an association between the art and the artist based on the `id` of the `artist` field. The `artId` is auto-incremented.

#### Request

```json
{
    "artTitle": "Downtown",
    "theme": "Urban",
    "artist": {
        "artistId": 3
    }
}
```

#### Response

```json
{
    "artId": 7,
    "artTitle": "Downtown",
    "theme": "Urban",
    "artist": {
        "artistId": 3,
        "artistName": "Pablo Picasso",
        "genre": "Cubism",
        "galleries": [
            {
                "galleryId": 3,
                "galleryName": "Museo Picasso",
                "location": "Barcelona"
            },
            {
                "galleryId": 4,
                "galleryName": "Whitney Museum of American Art",
                "location": "New York"
            }
        ]
    }
}
```

</details>

<details>
<summary>**API 16: GET /galleries/artists/arts/{artId}**</summary>

#### Path: `/galleries/artists/arts/{artId}`

#### Method: `GET`

#### Description:

Returns an art based on the `artId`. If the given `artId` is not found in the `art` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "artId": 1,
    "artTitle": "The Flight Study",
    "theme": "Studies of Bird Wings",
    "artist": {
        "artistId": 1,
        "artistName": "Leonardo da Vinci",
        "genre": "Renaissance",
        "galleries": [
            {
                "galleryId": 1,
                "galleryName": "Louvre Museum",
                "location": "Paris"
            },
            {
                "galleryId": 2,
                "galleryName": "Van Gogh Museum",
                "location": "Amsterdam"
            }
        ]
    }
}
```

</details>

<details>
<summary>**API 17: PUT /galleries/artists/arts/{artId}**</summary>

#### Path: `/galleries/artists/arts/{artId}`

#### Method: `PUT`

#### Description:

Updates the details of an art based on the `artId` and returns the updated art details. If the `id` in the `artist` field is provided, update the association between the art and the artist based on the `id`. If the given `artId` is not found in the `art` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "artTitle": "Downtown Solitude",
    "theme": "Urban Scene",
    "artist": {
        "artistId": 4
    }
}
```

#### Success Response

```json
{
    "artId": 7,
    "artTitle": "Downtown Solitude",
    "theme": "Urban Scene",
    "artist": {
        "artistId": 4,
        "artistName": "Edward Hopper",
        "genre": "American Modernism",
        "galleries": [
            {
                "galleryId": 4,
                "galleryName": "Whitney Museum of American Art",
                "location": "New York"
            }
        ]
    }
}
```

</details>

<details>
<summary>**API 18: DELETE /galleries/artists/arts/{artId}**</summary>

#### Path: `/galleries/artists/arts/{artId}`

#### Method: `DELETE`

#### Description:

Deletes an art from the `art` table based on the `artId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `artId` is not found in the `art` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 19: GET /arts/{artId}/artist**</summary>

#### Path: `/arts/{artId}/artist`

#### Method: `GET`

#### Description:

Returns the artist of art based on the `artId`. If the given `artId` is not found in the `art` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
{
    "artistId": 1,
    "artistName": "Leonardo da Vinci",
    "genre": "Renaissance",
    "galleries": [
        {
            "galleryId": 1,
            "galleryName": "Louvre Museum",
            "location": "Paris"
        },
        {
            "galleryId": 2,
            "galleryName": "Van Gogh Museum",
            "location": "Amsterdam"
        }
    ]
}
```

</details>

**Do not modify the code in `ArtGalleryApplication.java`**