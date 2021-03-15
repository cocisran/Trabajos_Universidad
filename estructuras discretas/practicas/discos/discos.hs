torre:: Int->Int 
torre 1 = 1
torre m = if m>= 1
          then (2* torre(m-1))+1
	  else error "numero de discos no valido "

