********
Del Moral Morales Francisco Emmanuel 420003162
Gomez De La Torre Heidi Lizbeth      317266245


*******Como utilizar el sistema :

Para poder usar este sistema de momento tienes que encontrarte en linux y contar con una instalacion de java 8 o superior
la herramienta  que usamos para compilar el proyecto se llama Gradle y te recomendamos que tambien la uses por que es simple
y sencilla de usar.

Si no cuentas con gradle puedes ejecutar los siguientes comandos en la terminal : 

    1) $ $ sdk install gradle 6.6.1

Y en caso de no tener sdkman instaldo tambien realiza estos pasos : 
En una terminal :

    1) $ curl -s "https://get.sdkman.io" | bash
    2) $ source "$HOME/.sdkman/bin/sdkman-init.sh"
    3) $ sdk version

Este ultimo, para verificar que la instalacion haya sido correcta

Y si eres de aquellos que no confian en nadie no te preocupes siente libre de seguir los pasos por tu cuenta desde las 
fuentes oficiales :

    para gradle https://gradle.org/install/
    para sdkman https://sdkman.io/install

Una vez hecho esto tan solo ejecuta este ultimo comando abriendo una terminal en el directorio weatherMap: 

    1) $ gradle run 

Si deseas ejecutar las pruebas diseñadas para el proyecto solamente ejecuta en la terminal abierta en el directorio weatherMap
el siguiente comando: 

   1) $ gradle test

¡Y listo! dentro de unos segundos se desplegara el programa dentro de tu terminal :D
