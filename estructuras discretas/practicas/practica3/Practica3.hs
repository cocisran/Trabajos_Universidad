--Del Moral Morales Francisco Emmanuel

data Var = A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z deriving (Show, Eq, Ord)

data Formula = Prop Var| Neg Formula| Formula :&: Formula | Formula :|: Formula | Formula :=>: Formula | Formula :<=>: Formula deriving (Show, Eq, Ord)
infixl 9 :&:
infixl 9 :|:
infixr 7 :=>:
infixl 8 :<=>:


--Funcion auxiliar de varlist obtiene la lista con repeticiones
pvarList:: Formula->[Var]
pvarList (Prop x) = [x]
pvarList (Neg xs) = pvarList xs 
pvarList (xs :&: ys) = (pvarList xs) ++ (pvarList ys) 
pvarList (xs :|: ys) = (pvarList xs) ++ (pvarList ys)  
pvarList (xs :=>: ys) = (pvarList xs) ++ (pvarList ys)  
pvarList (xs :<=>: ys) = (pvarList xs) ++ (pvarList ys)   

--[varList]: Función que recibe una fórmula y devuelve el conjunto (lista sin repeticiones) de variables que hay en la fórmula.
varList:: Formula->[Var]
varList x = aConjunto(pvarList x)

--[negar]: Una función recibe una fórmula y devuelve su negación.
negar :: Formula->Formula
negar (Prop x) = Neg (Prop x)	
negar (Neg x) = equivalencia x
negar (xs :&: ys) = (negar xs :|: negar ys) 
negar (xs :|: ys) = (negar xs :&: negar ys)  
negar (xs :=>: ys) = negar (equivalencia (xs :=>: ys))
negar (xs :<=>: ys) = negar (equivalencia(xs :<=>: ys))

--[equivalencia]: Una función que recibe una fórmula y devuelve su equivalencia 0lógica, sin implicaciones y bicondicionales, además la negación se encuentra únicamente frente a variables proposicionales.
equivalencia :: Formula -> Formula
equivalencia (Prop x) = (Prop x)
equivalencia (Neg xs) = negar(equivalencia xs	)
equivalencia (xs :&: ys) = if ys /= xs
			   then ((equivalencia xs ):&: (equivalencia ys))
			   else equivalencia ys
equivalencia (xs :|: ys) = if ys /= xs
			   then ((equivalencia xs ):|: (equivalencia ys))
			   else equivalencia ys
equivalencia (xs :=>: ys) = (negar(equivalencia xs ):|: (equivalencia ys))
equivalencia (xs :<=>: ys) = ( equivalencia(xs :=>: ys):&:( equivalencia(ys:=>:xs) ) )

--[interpretación]: evalua una formula a partir de una lista de parejas ordenadas que indican su valor de verdad : [(var,estado(True/False))]
interp :: Formula -> [(Var,Bool)] -> Bool
interp (Prop x) ((y,v):xs) = if x==y
			     then v
			     else interp (Prop x) xs
interp ((Neg (Prop x)):&:(Prop y)) ls = if x == y
				       then False
				       else interp (Neg(Prop x)) ls && interp (Prop y) ls
interp (( Prop x):&:(Neg (Prop y))) ls = if x == y
				       then False
				       else interp (Prop x) ls && interp (Neg (Prop y)) ls
interp ((Neg (Prop x)):|:(Prop y)) ls = if x == y
				       then True
				       else interp (Neg (Prop x)) ls || interp (Prop y) ls
interp (( Prop x):|:(Neg (Prop y))) ls = if x == y
				       then True
				       else interp (Prop x) ls || interp (Neg (Prop y)) ls
interp f [] = error "No todas la variables estan definidas"
interp (Neg x)       ls  = nel (interp  x ls)
interp (xs :&: ys)   ls  = (interp xs ls) && (interp ys ls)
interp (xs :|: ys)   ls  = (interp xs ls) || (interp ys ls)
interp (xs :=>: ys)  ls  = implicacion (interp xs ls) (interp ys ls)
interp (xs :<=>: ys) ls  = dobleImplica (interp xs ls) (interp ys ls)
--Funciones auxiliarees de combinaciones
hCombinaciones::[Var]->[Bool]->[(Var,Bool)]
hCombinaciones [] xs = []
hCombinaciones (v:vs) (t:ts) =[(v,t)]++hCombinaciones vs ts

preCombinaciones::[Var]->[[Bool]]->[[(Var,Bool)]]
preCombinaciones xs [] = []
preCombinaciones xs (b:bs) = [hCombinaciones xs b]++preCombinaciones xs bs 

--[combinaciones]: Una función que recibe una fórmula y devuelve una lista con los posibles combinaciones de las variables de la fórmula, con sus posibles valores de verdad
combinaciones :: Formula -> [[(Var,Bool)]]
combinaciones fx = preCombinaciones (varList fx) (booleanTabla (longitud(varList fx )) ) --booleantabla

--Funciones auxiliares de tablaDeVerdad
preTab::Formula->[(Var,Bool)]->([(Var,Bool)],Bool)
preTab fx val= (val,(interp fx val))

auxtablaDeVerdad::Formula->[[(Var,Bool)]]->[ ([(Var,Bool)],Bool) ]
auxtablaDeVerdad fx [] = []
auxtablaDeVerdad fx (v:vs) = [(preTab fx v)]++ auxtablaDeVerdad fx vs

--[tablaDeVerdad]

tablaDeVerdad::Formula->[ ([(Var,Bool)],Bool)]
tablaDeVerdad fx = auxtablaDeVerdad fx (combinaciones fx)


--Utilidades
booleanTabla:: Int->[[Bool]]
booleanTabla 0 = error "La tabla de verdad mas pequeña es de al menos una proposición " 
booleanTabla n = relleno n (listBool ((2^n)-1) )

relleno:: Int->[[Bool]]->[[Bool]]
relleno f [] = []
relleno f (l:ls) = if (longitud l )>f
		   then error "La longitud actual de los elementos ya excede el formato" 
		   else if(longitud l)>0 && (longitud l)<f
			then relleno f (([False]++l):ls)		 
			else [l] ++ (relleno f ls)
			

binarioBool:: Int->[Bool]
binarioBool 0 = [False]
binarioBool 1 = [True] 
binarioBool n = binarioBool (div n 2) ++ binarioBool (n`mod` 2)

listBool:: Int->[[Bool]]
listBool (-1) = []
listBool n = [binarioBool n ] ++ listBool (n-1)

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

longitud::[a]->Int
longitud [] = 0
longitud (x:xs) = longitud xs +1
nel:: Bool->Bool

nel True  = False
nel False = True

implicacion:: Bool->Bool->Bool
implicacion True False = False
implicacion a b = True


dobleImplica:: Bool->Bool->Bool
dobleImplica True True = True
dobleImplica False False = True
dobleImplica a b = False

--Fin de utilidades
