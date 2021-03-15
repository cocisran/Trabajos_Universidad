binarioA:: Int->String
binarioA 0 = "0"
binarioA 1 = "1"
binarioA n = binarioA (div n 2) ++ binarioA (n`mod` 2)

binarioC:: Int->String
binarioC n = if n>=0
             then "0"++binarioA n 
	     else "1"++binarioA (n*(-1))

longitud:: [a]->Int
longitud [] = 0
longitud (x:xs) = 1 + longitud xs

decimalA:: String -> Int
decimalA [] = 0
decimalA (x:xs) = if x=='0'
		  then decimalA xs
		  else (longitud xs) ^ 2 + decimalA xs

decimalC:: String->Int
decimalC (x:xs) = if x=='0'
		  then decimalA xs
		  else (-1)*decimalA xs
	 
