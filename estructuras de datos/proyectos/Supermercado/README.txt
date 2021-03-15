Para compilar el proyecto preparamos un script de bash, solo es necesario darle permisos de ejecucion y escribir
	$ ./compilar.sh 
en una ventana de la consola
Posteriormente se puede correr el programa mediante el script run.sh, tras darle permisos de ejecucion escribe en tu consola
	$./run

Si seleccionas la opcion dos de analisis en la consola, puedes generar graficas que se generan a partir de la informacion generada por el programa mediante el script plots.sh, dale permisos de ejecucion al archivo y despues solo escribe en consola
	$./plots.sh

Si tienes cualquier problema durante la ejecucion comprueba que tu directorio se vea asi:
.
├── build
├── compilar.sh
├── files
│   ├── almacen
│   ├── gnuplot_data
│   │   ├── brute
│   │   ├── media
│   │   └── plots.p
│   └── registroCajas
├── plots
│   ├── analisis
│   │   └── resumen.png
│   └── barra
├── plots.sh
├── README.txt
├── Reporte_Supermercado.pdf
├── run.sh
├── src
│   ├── almacen
│   │   ├── GeneradorInventario.java
│   │   ├── Inventario.java
│   │   └── Producto.java
│   ├── cliente
│   │   ├── Carrito.java
│   │   └── Cliente.java
│   ├── estructuras
│   │   ├── ArbolAVL.java
│   │   ├── ArbolBinarioBusqueda.java
│   │   ├── ArbolBinario.java
│   │   ├── Cola.java
│   │   ├── Coleccionable.java
│   │   ├── Heap.java
│   │   ├── Listable.java
│   │   ├── Lista.java
│   │   ├── MaxHeap.java
│   │   ├── MinHeap.java
│   │   └── Pila.java
│   ├── funcionalidades
│   │   ├── ControlEjecucion.java
│   │   ├── Escritor.java
│   │   └── Lector.java
│   ├── gnuplotProcess
│   │   └── Interprete.java
│   ├── Main.java
│   ├── menus
│   │   ├── MenuInventario.java
│   │   ├── MenuResetear.java
│   │   └── MenuSimulacion.java
│   └── supermercado
│       ├── Caja.java
│       ├── Cajero.java
│       └── Simulacion.java
└── systemFiles
    └── control

Breve recorrido por las opciones:

**Menu principal**
1) Menu de simulaciones  [abre el menu para realizar simulaciones]
2) Menu del almacen      [menu de tareas administrativas del programa]
3) Ajustes  		 [abre los ajustes del programa]
4) SALIR	         [termina la ejecucion]

**Menu de simulaciones**
1) Realizar simulacion optima [simulacion prestablecida de lo mejor para el supermercado usuario friendly]
2) Analisis completo          [simulacion de analisis genera archivos para graficas]
3) VOLVER		      [regresa al menu principal]	
		


**Sistema de administracion de inventario*
1) Consultar producto 				[consulta el estado de un producto mediante su id]
2) Modificar existencias 			[aumenta las existencias de un producto]
3) Agregar Producto				[agrega un nuevo producto al inventario]
4)  Generar informe de productos con  pocas existencias [crear un archivo en la carpeta/files/almacen con los productos con pocas existencias ]
5) VOLVER						[regresa al menu principal]
opcion: 

**Ajustes del programa*
1) Reiniciar  programa   [reinicia el estado del prograama, borra el inventario y los datos recopilados y regresa el estado del programa al de primera ejecucion]
2) VOLVER		 [regresa al menu principal]
opcion: 






 




