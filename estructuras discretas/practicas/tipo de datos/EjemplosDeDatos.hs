data Natural = Cero | Suc Natural deriving (Show,Eq,Ord)

suma :: Natural -> Natural -> Natural
suma n Cero = n
suma Cero n = n 
suma (Suc n) m = Suc(suma n m)

multiplicacion:: Natural -> Natural -> Natural
multiplicacion Cero n = Cero
multiplicacion n Cero = Cero
multiplicacion (Suc n) m = suma m (multiplicacion n m)

potencia:: Natural->Natural->Natural
potencia Cero Cero = error "No este molestando crack"
potencia Cero n= Cero
potencia n Cero = Suc Cero
potencia m (Suc n) = multiplicacion m  (potencia m n)
 

traduccion:: Natural->Int
traduccion Cero = 0;
traduccion (Suc m) = 1+traduccion m

interpretacion:: Int->Natural
interpretacion 0 = Cero
interpretacion n = Suc (interpretacion (n-1))

mt:: Natural->Natural->Int
mt n m = traduccion (multiplicacion n m)

st:: Natural->Natural->Int
st n m = traduccion (suma n m)
