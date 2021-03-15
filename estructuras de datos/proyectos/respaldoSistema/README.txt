********
Del Moral Morales Francisco Emmanuel
Gomez De La Torre Heidi Lizbeth


*******Como utilizar el sistema :

Para poder usar este sistema de momento tienes que contar con una instalacion de java 8 o superior
la herramienta  que usamos para compilar el proyecto se llama Gradle y te recomendamos que tambien la uses por que es simple
y sencilla de usar.

Si no cuentas con gradle puedes ejecutar los siguientes comandos en la terminal : 

    1) $ sdk install gradle 6.0.1

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

Una vez hecho esto tan solo ejecuta este ultimo comando abriendo una terminal en el directorio sistemaDGP: 

    1) $ gralde run 

¡Y listo! dentro de unos segundos el sistema se abrira en tu terminal

por si estas interesado la estructura de los archivos fuente es la siguiente:

la clase principal es la clase Main.java
.
└── src
    └── main
        ├── java
        │   ├── app
        │   │   └── Main.java
        │   └── sub
        │       ├── libs
        │       │   ├── Listable.java
        │       │   └── Lista.java
        │       └── matriculas
        │           ├── Estudiante.java
        │           └── Materia.java
        └── resources

Esperamos tengas un gran dia y excelente salud :D
