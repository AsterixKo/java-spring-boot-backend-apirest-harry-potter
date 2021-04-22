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