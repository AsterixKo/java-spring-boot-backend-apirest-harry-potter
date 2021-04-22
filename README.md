# Prueba Java - Spring boot - Apirest - Harry Potter

El supuesto va a basarse en la API de OMDb.

API: http://www.omdbapi.com/

API Key: TU_API_KEY

El sistema resultante tiene que cumplir los siguientes requisitos, plantea en qué orden afrontas las tareas, cómo y cuál sería el resultado. El resultado se puede plantear como pseudocódigo, colección de postman, modelo teórico de DB dibujado, etc. Cuéntanos qué tecnologías/frameworks usarías y por qué.

1. Obtener de la API toda la información relativa a las películas de Harry Potter.
2. De la información obtenida, guardar en una base de datos local:
  * ID en IMDB
  * Título
  * Año
  * Valoración personal del 1 al 10 // este es un dato inventado que no está en la API, se añade en nuestro sistema y puedes poner el valor que quieras
3. Ofrecer la información de nuestra DB a través de un Web Service.

Recuerda cambiarle el nombre al archivo aplication.ejemplo.properties por application.properties y escribir los parámetros de tu base de datos y tu api.

## ¿Cómo y cuál sería el resultado?

Hacemos una petición a nuestra Api:

localhost:8080/api/searchAndPopulate/Harry Potter

1. Busca en la api externa.
2. Introduce las películas en nuestra base de datos.
3. Muestra en el resultado de la petición las películas introducidas.

También hay otro endpoint que nos lista las películas de nuestra base de datos:

localhost:8080/api/movies

## Listado de endpoints:

ConsultarPeliculasApiExterna (GET):
http://www.omdbapi.com/?apikey=TU_API_KEY&s=Harry Potter&type=movie&r=json

Get Movies (GET):
localhost:8080/api/movies

Get Movie por Id (GET):
localhost:8080/api/movies/4

Crear Movie (POST):
localhost:8080/api/movies

{
        "imdbID": "ttbbbbb111",
        "Title": "Titulo 111",
        "Year": 2050,
        "stars": 10
    }

Actualizar Movie (PUT):
localhost:8080/api/movies/4

{
        "imdbID": "tt1201222",
        "Title": "Titulo Modificado 4",
        "Year": 2044,
        "stars": 5
    }

Delete Movie (DELETE):
localhost:8080/api/movies/1

Search and populate DB(GET):
localhost:8080/api/searchAndPopulate/Harry Potter
