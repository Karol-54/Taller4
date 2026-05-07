
Taller 4 - MovieLibrary

Video de demostración
[Taller4_movielib.pdf](Taller4_movielib.pdf)


- ViewModel
Es el encargado de guardar y manejar la información que muestra la pantalla.
Si giras el celular o cambias de pantalla, el ViewModel no pierde los datos.

- LiveData
Es un contenedor de datos que avisa automáticamente a la pantalla cuando algo cambia.
sola sin necesidad de recargar manualmente.

- Repository
Es el intermediario entre el ViewModel y la base de datos.
El ViewModel no habla directamente con Room, sino que le pide los datos al Repository
y este se encarga de buscarlos o guardarlos.


- Navigation Component, NavController y Safe Args
Navigation Component es el sistema que maneja el movimiento entre pantallas.
NavController es el que ejecuta esa navegación.
Safe Args permite pasar datos entre pantallas de forma segura,
evitando errores por tipos de datos incorrectos.

- Room (Entity, DAO, Database)
Room es la base de datos local de la app.
La Entity define qué campos tiene cada película.
El DAO tiene las funciones para consultar, insertar, actualizar y eliminar datos.
La Database es la conexión principal que une todo y permite acceder
a los datos guardados.