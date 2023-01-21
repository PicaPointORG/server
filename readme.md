# API del servidor

## ***Peticiones***
---
### - _GET_
---
* **`"/login"`**
* **`"/registro"`** -> `Solo la empresa instaladora (nosotros)`
    * Para el registro será necesaria nuestra intervención, ya que un usuario no debería darse de alta por su cuenta, puesto que nosotros somos los que hacemos la instalación del hardware en cada máquina y por tanto somos los que lo damos de alta
---
* **`"/maquinas"`**
    * Lista de todas las máquinas de los que dispone el usuario junto a la id y ubicación.
    * La MAC de la placa ESP8266 podría actuar como la **id** de la máquina junto a alguna referencia interna que use la empresa.
* **`"/maquinas/{Id}"`**
    * Información de cáda máquina, como su ubicación, alguna descripción o anotación que tenga y los productos de los que dispone actualemnte.
        * _"patatas Lays 30G": 5Uds_
        * _"Coca-Cola 330ml": 4Uds_
---
* **`"/productos"`**
    * El usuario podrá consultar el catalogo de todos sus productos completos, identificados por su id interno, de los que irá reabasteciendo cada máquina.
* **`"/productos/{id}"`**
    * Según la id interna de cada producto perteneciente a la empresa, se podrá consultar su precio, peso, los alergenos si se desea y cualquier información adicional al producto en si.
---
### - _POST_
---
> Se identificará la máquina según la MAC de la placa que envia la petición
* **`"/login"`**
    * Se verifica el login del usuario y se redirige a la página principal
* **`"/registro"`** -> `Solo la empresa instaladora (nosotros)`
---
* **`"/maquinas"`**
    * `"/add"` -> `Solo la empresa instaladora (nosotros)`
    * `"/{id}/update"`
        * En el caso de que se requiera actualizar alguna información relacionada con la máquina como su ubicación.
    * `"/{id}/delete"` -> `Solo la empresa instaladora (nosotros)`
---
* **`"maquinas/{id}/stock"`**
    * `"/add"`
        * Se agrega producto nuevo a una máquina en concreto
    * `"/update"`
        * Cada vez que se realice la venta o abastecimiento de productos pertenecientes a esta máquina, esta ruta se encargará de restar o sumar al stock disponible del producto.
    * `"/delete"`
---
* **`"/productos"`**
    * `"/add"`
        * Únicamente se usará para agregar productos nuevos en la empresa que antes no estaban.
    * `"/{id}/update"`
    * `"/{id}/delete"`
    * En el caso de que la empresa ya no haga uso de algún producto en nínguna de sus máquinas, estas pueden ser borradas.
---