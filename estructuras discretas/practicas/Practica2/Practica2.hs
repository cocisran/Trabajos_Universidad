--Del Moral Morales Francisco Emmanuel


conjuncion:: Bool->Bool->Bool
conjuncion True True = True
conjuncion a b = False


disyuncion:: Bool->Bool->Bool
disyuncion False False = False
disyuncion a b = True


implicacion:: Bool->Bool->Bool
implicacion True False = False
implicacion a b = True


dobleImplica:: Bool->Bool->Bool
dobleImplica True True = True
dobleImplica False False = True
dobleImplica a b = False


longitud::[a]->Int
longitud [] = 0
longitud (x:xs) = longitud xs +1


sumaNumeros:: Num a=>[a]->a
sumaNumeros [] = 0
sumaNumeros (x:xs) =x+ sumaNumeros (xs)


maximo:: Ord a => [a] -> a
maximo [x] = x
maximo (x:xs) = if x> maximo xs
		then x
		else maximo xs


indiceDe:: Int->[a]->a
indiceDe i (x:xs) = if i < 0 || i>longitud(x:xs)
		    then error "Disculpeme, pero se paso de lanza no hay tantos elementos en su lista"
		    else if i==0
		    then x
		    else indiceDe (i-1) xs


insertarElemento :: a -> [a] -> Bool -> [a]
insertarElemento e (xs) True = (e:xs) 
insertarElemento e (x:xs) False = xs++ [e] 

 
reversa::  [a] -> [a]
reversa [] = []
reversa (x:xs) = reversa xs  ++ [x]


esPalindromo::Eq a=> [a] -> Bool
esPalindromo (x:xs) = (x:xs) == reversa (x:xs)


encuentra:: Eq a=>a->[a]->Bool
encuentra e []= False
encuentra e (x:xs)= if e==x
		     then True
		     else encuentra e xs


aConjunto:: Eq a=>[a] -> [a]
aConjunto [] = []
aConjunto (x:xs) = if encuentra x xs 
		   then	aConjunto xs 
		   else	[x] ++ aConjunto xs 


union::Eq a=>[a] -> [a] -> [a]
union xs ys = aConjunto (xs++ys)


interseccionP:: Eq a=>[a] -> [a] -> [a]
interseccionP [] [] = []
interseccionP [] ys = []
interseccionP (x:xs) ys = if encuentra x (ys)
			     then [x] ++ interseccion (xs) ys		 
			     else interseccion (xs) (ys)

			
interseccion::Eq a=>[a] -> [a] -> [a]
interseccion [] xs = []
interseccion (x:xs) ys = aConjunto(interseccionP (x:xs) ys)


productoCruz:: Eq a=>[a] -> [a] -> [(a,a)]
productoCruz xs ys =aConjunto([(x,y)|x<-xs,y<-ys])

			
diferenciaC:: Eq a=>[a] -> [a] -> [a]
diferenciaC [] [] = []
diferenciaC [] ys = []
diferenciaC (x:xs) ys = if encuentra x ys
			    then diferenciaC xs ys		
		            else [x]++diferenciaC xs ys


diferenciaSimetrica::Eq a=>[a] -> [a] -> [a]
diferenciaSimetrica xs ys = diferenciaC (union xs ys) (interseccion xs ys)  


modulo:: Int->Int->Int
modulo a b  = if b==0 || b>a 
              then error "mod  no definido"
              else if a-(quot a b)*b <0 
	           then (-1)*(a-(quot a b)*b)
		   else a-(quot a b)*b		
divisores:: Int -> [Int]
divisores n = [x|x<-[1..n],modulo n x == 0] 		


--Inicio de Conjunto Potencia
subConjunto:: Eq a=>[a]->[a]->Bool
subConjunto [] [] = True 
subConjunto [] xs  = True
subConjunto (y:ys) xs = if encuentra y xs 
			 then subConjunto ys xs
			 else False	 	
igualdadConjuntos:: Eq a=>[a]->[a]->Bool
igualdadConjuntos a b = (subConjunto a b) && (subConjunto b a)

encuentraConjunto::Eq a=>[a]->[[a]]->Bool
encuentraConjunto e [[]] = False
encuentraConjunto e [] = False
encuentraConjunto e (x:xs) = if igualdadConjuntos e x
			      then True
			      else encuentraConjunto e xs	

superAconjunto::Eq a=>[[a]]->[[a]]
superAconjunto [] = []
superAconjunto (a:as) = if encuentraConjunto a as
			  then	superAconjunto as
			  else [a]++superAconjunto as

mueveLista::[a]->[a]
mueveLista  (x:xs)= (xs++[x])

superMM::Eq a=>  Int->[a]->[[a]]
superMM i xs = if i==0
		then [[]]
		else superMM (i-1) (mueveLista xs) ++ pconjuntoPotencia xs

pconjuntoPotencia::Eq a=> [a] -> [[a]]
pconjuntoPotencia [] = [[]]
pconjuntoPotencia (x:xs) = ([x]:(x:xs) :pconjuntoPotencia (xs))			  



mconjuntoPotencia::Eq a=> [a] -> [[a]]
mconjuntoPotencia xs = aConjunto(superMM (longitud xs) xs)

conjuntoPotencia::Eq a=> [a] -> [[a]]
conjuntoPotencia xs = superAconjunto (mconjuntoPotencia xs )

--fin








