-- G = (V,E)
-- V = { V1,...,Vn}
-- E = {e1,....,ek}
-- ei = (Vj,Vz)


type Grafica = ([Integer],[(Integer,Integer,Float)])

incidencias :: Grafica -> Integer -> [Integer]
incidencias (xs,((a,b,w):ys)) n = if n == b
				  then [a] ++ incidencias (xs,ys) n
				  else incidencias (xs,ys) n


ingrado:: Grafica -> Integer -> Integer 
ingrado g n = size (incidencias g n)

esHamiltoniana:: Grafica -> Bool
esHamiltoniana g = 
