# API del servidor

> Se identificará la máquina según la MAC de la placa ESPxx que envia la petición

## ***Peticiones***

### - _GET_
---
* **`"/login"`**
* **`"/registro"`** -> `Solo la empresa instaladora (nosotros)`
    * Para el registro será necesaria nuestra intervención, ya que un usuario no debería darse de alta por su cuenta, puesto que nosotros somos los que hacemos la instalación del hardware en cada máquina y por tanto somos los que lo damos de alta
* **`"/"`** Página principal de la aplicación. 
* **`"/maquinas"`**
    * Lista de todas las máquinas de los que dispone el usuario junto a la id y ubicación.
    * La MAC de la placa ESP8266 podría actuar como la **id** de la máquina junto a alguna referencia interna que use la empresa.
* **`"/maquinas/{idMaquina}"`**
    * Información de cáda máquina, como su ubicación, alguna descripción o anotación que tenga y el stock disponible.
        * _"patatas Lays 30G": 5Uds_
        * _"Coca-Cola 330ml": 4Uds_
* **`"/productos"`**
    * El usuario podrá consultar el catalogo de todos sus productos completos, identificados por su id interno, de los que irá reabasteciendo cada máquina.
* **`"/productos/{idProducto}"`**
    * Según la id interna de cada producto perteneciente a la empresa, se podrá consultar su precio, peso, los alergenos si se desea y cualquier información adicional al producto en si.

---

### - _POST_
---
* **`"/login"`**
    * Se verifica el login del usuario y se redirige a la página principal
* **`"/registro"`** -> `Solo la empresa instaladora (nosotros)`
    * Solo nosotros podemos hacer peticiones aqui para dar de alta a nuevos usuarios
* **`"/maquinas/add"`** -> `Solo la empresa instaladora (nosotros)`
    * Agregar nueva máquina a la empresa
    * Esto lo debemos hacerlo nosotros ya que es necesaria la instalación de la placa ESP8266 en la máquina. No se debe tener ninguna máquina en la base de datos sin la placa.
* **`"/stock/{idMaquina}/add"`**
    * Se agrega un producto de la tabla de productos al stock de una máquina en concreto
    * Cuando se vaya a incluir un producto que antes no estaba en el stock de una máquina.
* **`"/productos/add"`**
    * Se crea un nuevo producto que la empresa no disponía anteriormente, para poder ser añadida en el stock de alguna máquina.
---

### - _PUT_
---
* **`"/productos/{idProducto}/update"`**
    * En el caso de que se requiera actualizar alguna información relacionada con el producto como su precio o peso.
* **`"/maquinas/{idMaquina}/update"`**
    * En el caso de que se requiera actualizar alguna información relacionada con la máquina como su ubicación.
* **`"/stock/{idMaquina}/update"`**
    * Cada vez que se realice la venta o reabastecimiento de productos pertenecientes a una máquina en concreto, esta ruta se encargará de actualizar el stock disponible del producto en dicha máquina.
---

### - _DELETE_
---
* **`"/productos/{idProducto}/delete"`**
    * En el caso de que la empresa ya no haga uso de algún producto en nínguna de sus máquinas, estas pueden ser borradas de la base de datos completamente.
* **`"/maquinas/{idMaquina}/delete"`** -> `Solo la empresa instaladora (nosotros)`
    * Borrar una máquina del catalogo de la empresa
* **`"/stock/{idMaquina}/delete"`**
    * Se borra un producto de una máquina en concreto