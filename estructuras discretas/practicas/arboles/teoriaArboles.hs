data ArbolBinario a = Hoja | Nodo a (ArbolBinario a) (ArbolBinario a)

nHojas :: ArbolBinario a -> Int
nHojas Hoja = 1
nHojas (Nodo x xs ys) = nHojas xs + nHojas ys

nElementos:: ArbolBinario a -> Int
nElementos Hoja =0
nElementos (Nodo x der izq) = 1+ nElementos der + nElementos izq 

preOrden :: ArbolBinario a  -> [a]
preOrden Hoja = []
preOrden (Nodo x der izq) = (x: (preOrden der ++ preOrden izq))
