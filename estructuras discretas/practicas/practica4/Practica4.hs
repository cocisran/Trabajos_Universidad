--DEL MORAL MORALES FRANCISCO EMMANUEL


-- prueba ([1,2,3,4],[(1,2,1),(2,4,2),(4,3,3),(1,4,4),(2,3,5)])
-- kruskal ([0,1,2],[(1,1,2),(2,2,4),(0,2,6),(2,0,3),(1,0,5),(0,1,1),(0,0,4),(2,1,6),(1,2,3)])


--              Vertices    
type Grafica = ([Integer],[Arista])
--             Salida  Llegada Ponderación
type Arista = (Integer,Integer,Float)


--{{{{{{{{{{{{{{{{{{{{3.EJERCIOS DE GRAFICAS}}}}}}}}}}}}}}}}}}}}}}

--[incidencias] Lista de vértices que tienen una arista que incide al vértice parámetro.
incidencias :: Grafica -> Integer -> [Integer]
incidencias (xs , [] ) n = []
incidencias  (xs, ((s,e,v):ys))  n = if n == e 
				     then [s] ++ incidencias (xs,ys) n
				     else incidencias(xs,ys) n

--[adyacencias] Lista de vértices que tienen una arista incidente desde el vértice parámetro.
adyacencias :: Grafica -> Integer -> [Integer]
adyacencias (xs, []) n = []
adyacencias (xs, ((s,e,v):ys)) n = if s == n 
		          	   then [e] ++ adyacencias (xs,ys) n
				  	   else adyacencias (xs,ys) n 

--[ingrado] Una función que nos da el ingrado de un vértice.
ingrado :: Grafica -> Integer -> Integer
ingrado g n = longitud (incidencias  g n)

--[exgrado] Una función que nos da el exgrado de un vértice.
exgrado :: Grafica -> Integer -> Integer
exgrado g n = longitud (adyacencias g n)

--[trayectorias] Una funcion que nos regresa una lista con las aristas que inciden desde un vertice parametro

trayectorias :: Grafica -> Integer -> [(Integer,Integer,Float)]
trayectorias (xs,[]) n= []
trayectorias (xs, ((s,e,v):ys)) n = if s == n 
				    then [(s,e,v)] ++ trayectorias (xs,ys) n 
				    else trayectorias (xs,ys) n

--[kruskal] Una funcion que nos devuelve el árbol generador de peso mı́nimo de la grafica recibida como parametro

kruskal :: Grafica -> Grafica
kruskal g  = auxKruskal g ([],[])

--[aux]
auxKruskal:: Grafica->Grafica->Grafica
auxKruskal (xs, []) (v,ar) = ( aConjunto(dominio ([],ar) ++ imagen ([],ar)),ar)
auxKruskal gx gy = if agregar (menorPeso gx) gy	
		   then auxKruskal (rmA gx  (menorPeso gx) ) (addA gy (menorPeso gx) )
		   else auxKruskal (rmA gx  (menorPeso gx) ) gy
		
agregar:: Arista->Grafica->Bool
agregar  (s,e,v) (n,a) = 	if  (ruta (s,e) (n,a) )  || s==e
		           			then False
			    			else True
		
addA:: Grafica->Arista->Grafica
addA (vx,ex) (v,a,p) = (vx++[v],ex++[(v,a,p)])

rmA :: Grafica->Arista->Grafica
rmA (vx,ex) (v,a,p) = (remove v vx , remove (v,a,p) ex )


ruta:: (Integer,Integer) -> Grafica ->Bool
ruta (x,y) g = encuentra x (caminos y g)
 
caminos:: Integer->Grafica->[Integer]
caminos n g = remove n (acaminos [n] [] g)

	    --baneo      busqueda
acaminos::[Integer]->[Integer]->Grafica->[Integer]
acaminos [n] [] g = if relaciones n g == []
					then []
					else acaminos ([n]++relaciones n g) (relaciones n g) g
acaminos xs  [] g = xs 
acaminos xs ys g = acaminos (xs ++ (removeList xs ys)) (removeList xs (rlist ys g )) g

rlist:: [Integer]-> Grafica -> [Integer]
rlist xs g = aConjunto (arlist xs g)

arlist:: [Integer]-> Grafica -> [Integer]
arlist [] g = []
arlist (x:xs) g = (relaciones x g) ++ (rlist xs g) 

relaciones :: Integer-> Grafica -> [Integer]
relaciones n g = remove n (arelaciones n g)

arelaciones:: Integer-> Grafica -> [Integer]
arelaciones n g = aConjunto( (incidencias g n ) ++ (adyacencias g n)) 

--si bueno, ¿quien tiene hambre :C?

--{{{{{{{{{{{{{{{{{{{{4.EJERCIOS DE RELACIONES Y FUNCIONES}}}}}}}}}}}}}}}}}}}}}}

--[dominio] Una función que nos de el dominio de una relación.

dominio :: Grafica -> [Integer]
dominio g = aConjunto (auxDominio g) 
--[aux]

auxDominio :: Grafica -> [Integer]
auxDominio (v,[]) = []
auxDominio (v,((x,y,p):xs)) = [x] ++ auxDominio (v,xs)

--[Imagen] Una función que nos de la imagen de una relación.

imagen :: Grafica -> [Integer]
imagen g = aConjunto (auxImagen g) 
--[aux]

auxImagen :: Grafica -> [Integer]
auxImagen (v,[]) = []
auxImagen (v,((x,y,p):xs)) = [y] ++ auxImagen (v,xs)

--[esFuncion] Una función que nos dice si una relación es función.
esFuncion :: Grafica -> Bool 
esFuncion (v,a) = (aesFuncion (v,a) ) && subConjunto v (dominio (v,a))
--Aux

aesFuncion :: Grafica -> Bool --revisar que todo el dominio este relacionado
aesFuncion (v,[]) = True 
aesFuncion (v,(a:as)) = if unicidad a as
		    		   then esFuncion (v,as)
						else False 
						
unicidad :: Arista->[Arista]->Bool
unicidad a [] = True
unicidad (s,e,v) ((ls,le,lv):as) = if s == ls
				   				   then False
									  else unicidad (s,e,v) as


-- [esReflexiva] Una función que nos dice si una relación es reflexiva
esReflexiva :: Grafica->Bool
esReflexiva ([],ar) = True
esReflexiva ((x:xs),ar) = if espejo x ar
			  		      then esReflexiva (xs,ar)
		          		  else False 


--Aux
espejo:: Integer->[Arista]->Bool
espejo v [] = False
espejo v ((s,e,p):ys) = if v == s && s == e 
		       		    then True
						else espejo v ys

--[esSimetrica] Una función que nos dice si una relación es reflexiva.
esSimetrica :: Grafica -> Bool
esSimetrica (v,[]) = True
esSimetrica (v,((s,e,p):as)) = if pareja (s,e,p) as
			       then esSimetrica (v,softRemove (e,s,p) as)
			       else False

--Aux
pareja :: Arista->[Arista]->Bool
pareja (s,e,v) [] = s == e  
pareja (s,e,v) ((sa,ea,va):xs) = if (s == ea && e == sa) 
				 then True
				 else pareja (s,e,v) xs

--[esAntisimetrica] Una función que nos dice si una relación es antisimétrica.

esAntisimetrica :: Grafica -> Bool
esAntisimetrica (v,[]) = True
esAntisimetrica (v,(a:as)) =  if apareja a as 
			    			  then  esAntisimetrica (v,as)
			      			  else False
--Aux
apareja :: Arista->[Arista]->Bool
apareja (s,e,v) [] = True 
apareja (s,e,v) ((sa,ea,va):xs) = if (s == ea && e == sa)  
				 				  then False
				 	    		  else apareja (s,e,v) xs	

--[composicion] Una función que nos calcula la composición de dos relaciones.

composicion :: Grafica->Grafica->Grafica
composicion gx gy = if subConjunto (imagen gy) (dominio gx)	
		    	    then auxComp gx gy ([],[])
		    	    else error  "Las relaciones no se pueden componer"  	
--Aux
auxComp :: Grafica->Grafica->Grafica->Grafica
auxComp g (v,[]) (yv,ya) = (aConjunto yv, aConjunto  ya)
auxComp (vx,ax) (vy, (a:as)) gy = glue (auxComp (vx,ax) (vy, as) gy ) (fusion a ax gy )


fusion :: Arista -> [Arista] ->Grafica-> Grafica
fusion a [] (vE,aE) = ( vE, aE)
fusion (s,e,v) ((ss,se,sv):as) (vE,aE) = if e == ss
										 then fusion (s,e,v) as (vE++[s,e,se],aE ++[(s,se,v+sv)])
					 					 else fusion (s,e,v) as (vE,aE)

--[potencia] Una funcion que nos calcula la potencia de una relacion
 
potencia:: Grafica->Integer->Grafica 
potencia g 0 = g
potencia g n = potencia (composicion g g) (n-1) 

--[cerrReflexiva] Una funcion que nos calcula la cerradura reflexiva de una relacion
cerrReflexiva :: Grafica -> Grafica
cerrReflexiva ([],ys) = ( aConjunto(dominio ([],ys) ++ imagen ([],ys)) ,ys)
cerrReflexiva ((x:xs),ys) = if compa (x,x,0) ys 
							then cerrReflexiva (xs,ys)
							else cerrReflexiva (xs,ys++[(x,x,0)])
--[cerrSimetrica] Una funcion que nos calcula la cerradura simetrica de una relacion
cerrSimetrica :: Grafica -> Grafica
cerrSimetrica (v,ys) = (v,auxCS ys ys) 
 
-- Aux
auxCS :: [Arista] -> [Arista] -> [Arista]
auxCS [] xs = xs 
auxCS ((x,y,v):xs) ys = if compa (y,x,0) ys 
						then auxCS xs ys
				        else auxCS xs (ys ++ [(y,x,0)])

--
--							
-- [Funcionalidades] 



--Une dos graficas
glue::Grafica -> Grafica ->Grafica
glue (v,a) (vs,as) = (aConjunto (v++vs),aConjunto(a++as))
--Devuelve la longitud de una lista
longitud::[a]->Integer
longitud [] = 0
longitud (x:xs) = longitud xs +1

--Nos devuelve la arista de  menor peso en la grafica
menorPeso:: Grafica -> Arista 
menorPeso (xs, [(s,e,v)]) = (s,e,v)
menorPeso (xs,( (s,e,v):(ss,se,sv):ys)) = if v < sv 
										  then menorPeso (xs,((s,e,v):ys))
										  else menorPeso (xs,((ss,se,sv):ys))
 
--Nos dice si cierto elemento se encuentra en una lista
encuentra:: Eq a=>a->[a]->Bool
encuentra e []= False
encuentra e (x:xs)= if e==x
		    	   then True
					  else encuentra e xs
--remueve pares
softRemove :: Arista -> [Arista] -> [Arista]					  
softRemove x [] = []
softRemove (a,b,v) ((m,n,f):xs) = if m == a && b == n
								  then softRemove (a,b,v) xs			
								  else ((m,n,f):softRemove (a,b,v) xs)
--elimina cierto elemento de una lista

removeList:: Eq a=>[a]->[a]->[a]
removeList [] xs = xs
removeList (r:rm) xs = removeList rm (remove r xs) 

remove :: Eq a=>a->[a]->[a]
remove a [] = []
remove a (x:xs) = if x == a 
		  		  then remove a xs
		 		  else (x:(remove a xs)) 

--convierte en un conjunto una lista de elementos
aConjunto:: Eq a=>[a] -> [a]
aConjunto [] = []
aConjunto (x:xs) = (x: aConjunto (remove x xs))
					   
-- indica si un conjunto es subconjunto de otro
subConjunto:: Eq a=>[a]->[a]->Bool
subConjunto [] [] = True 
subConjunto [] xs  = True
subConjunto (y:ys) xs = if encuentra y xs 
			 			then subConjunto ys xs
						 else False
						 
--indica si cierta pareja esta en el conjunto de aristas  (ignora la ponderacion)
compa :: Arista -> [Arista] -> Bool
compa a [] = False 
compa  (x,y,p) ((a,b,v):ys) = if x == a && y == b 
							 then True
						     else compa (x,y,p) ys 

--Fin de funcionalidades
