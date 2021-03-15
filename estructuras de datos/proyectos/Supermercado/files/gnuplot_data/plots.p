set terminal png size 1200,600
set output 'plots/analisis/resumen.png'
set title 'Comparacion por numero de cajas rapidas'
set boxwidth 0.9
set key left top
set style fill transparent solid 0.5
set xrange [0:24];
plot  "files/gnuplot_data/media/media_14cajaRapida.txt"  using 1:2 with linespoints title "14 cajas rapidas" lt rgb "#d62121","files/gnuplot_data/media/media_13cajaRapida.txt"  using 1:2 with linespoints  title "13 cajas rapidas" lt rgb "#5dd621","files/gnuplot_data/media/media_12cajaRapida.txt"  using 1:2 with linespoints  title "12 cajas rapidas" lt rgb "#21d694","files/gnuplot_data/media/media_11cajaRapida.txt"  using 1:2 with linespoints  title "11 cajas rapidas" lt rgb "#a62121","files/gnuplot_data/media/media_10cajaRapida.txt"  using 1:2 with linespoints  title "10 cajas rapidas" lt rgb "#2198a6","files/gnuplot_data/media/media_9cajaRapida.txt"  using 1:2 with linespoints  title "9 cajas rapidas" lt rgb "#215fa6","files/gnuplot_data/media/media_8cajaRapida.txt"  using 1:2 with linespoints  title "8 cajas rapidas" lt rgb "#2721a6","files/gnuplot_data/media/media_7cajaRapida.txt"  using 1:2 with linespoints  title "7 cajas rapidas" lt rgb "#5921a6","files/gnuplot_data/media/media_6cajaRapida.txt"  using 1:2 with linespoints  title "6 cajas rapidas" lt rgb "#9221a6","files/gnuplot_data/media/media_5cajaRapida.txt"  using 1:2 with linespoints  title "5 cajas rapidas" lt rgb "#a6218a","files/gnuplot_data/media/media_4cajaRapida.txt"  using 1:2 with linespoints  title "4 cajas rapidas" lt rgb "#a62137","files/gnuplot_data/media/media_3cajaRapida.txt"  using 1:2 with linespoints  title "3 cajas rapidas" lt rgb "#a67621","files/gnuplot_data/media/media_2cajaRapida.txt"  using 1:2 with linespoints  title "2 cajas rapidas" lt rgb "#dd1fa9","files/gnuplot_data/media/media_1cajaRapida.txt"  using 1:2 with linespoints  title "1 cajas rapidas" lt rgb "#d62121"

set output 'plots/analisis/resumen1_10.png'
set terminal png size 1500,600
plot  "files/gnuplot_data/media/media_10cajaRapida.txt"  using 1:2 with linespoints  title "10 cajas rapidas" lt rgb "#2198a6","files/gnuplot_data/media/media_9cajaRapida.txt"  using 1:2 with linespoints  title "9 cajas rapidas" lt rgb "#215fa6","files/gnuplot_data/media/media_8cajaRapida.txt"  using 1:2 with linespoints  title "8 cajas rapidas" lt rgb "#2721a6","files/gnuplot_data/media/media_7cajaRapida.txt"  using 1:2 with linespoints  title "7 cajas rapidas" lt rgb "#5921a6","files/gnuplot_data/media/media_6cajaRapida.txt"  using 1:2 with linespoints  title "6 cajas rapidas" lt rgb "#9221a6","files/gnuplot_data/media/media_5cajaRapida.txt"  using 1:2 with linespoints  title "5 cajas rapidas" lt rgb "#a6218a","files/gnuplot_data/media/media_4cajaRapida.txt"  using 1:2 with linespoints  title "4 cajas rapidas" lt rgb "#a62137","files/gnuplot_data/media/media_3cajaRapida.txt"  using 1:2 with linespoints  title "3 cajas rapidas" lt rgb "#a67621","files/gnuplot_data/media/media_2cajaRapida.txt"  using 1:2 with linespoints  title "2 cajas rapidas" lt rgb "#dd1fa9","files/gnuplot_data/media/media_1cajaRapida.txt"  using 1:2 with linespoints  title "1 cajas rapidas" lt rgb "#d62121",

set output 'plots/analisis/resumen1_7.png'
set terminal png size 1500,600
plot "files/gnuplot_data/media/media_7cajaRapida.txt"  using 1:2 with linespoints  title "7 cajas rapidas" lt rgb "#e51313","files/gnuplot_data/media/media_6cajaRapida.txt"  using 1:2 with linespoints  title "6 cajas rapidas" lt rgb "#e5bc13","files/gnuplot_data/media/media_5cajaRapida.txt"  using 1:2 with linespoints  title "5 cajas rapidas" lt rgb "#065602","files/gnuplot_data/media/media_4cajaRapida.txt"  using 1:2 with linespoints  title "4 cajas rapidas" lt rgb "#17dc41","files/gnuplot_data/media/media_3cajaRapida.txt"  using 1:2 with linespoints  title "3 cajas rapidas" lt rgb "#17dcb2","files/gnuplot_data/media/media_2cajaRapida.txt"  using 1:2 with linespoints  title "2 cajas rapidas" lt rgb "#172fdc","files/gnuplot_data/media/media_1cajaRapida.txt"  using 1:2 with linespoints  title "1 cajas rapidas" lt rgb "#dc17d0",

set output 'plots/analisis/resumen1_5.png'
set terminal png size 1500,600
plot "files/gnuplot_data/media/media_5cajaRapida.txt"  using 1:2 with linespoints  title "5 cajas rapidas" lt rgb "#065602","files/gnuplot_data/media/media_4cajaRapida.txt"  using 1:2 with linespoints  title "4 cajas rapidas" lt rgb "#17dc41","files/gnuplot_data/media/media_3cajaRapida.txt"  using 1:2 with linespoints  title "3 cajas rapidas" lt rgb "#17dcb2","files/gnuplot_data/media/media_2cajaRapida.txt"  using 1:2 with linespoints  title "2 cajas rapidas" lt rgb "#172fdc","files/gnuplot_data/media/media_1cajaRapida.txt"  using 1:2 with linespoints  title "1 cajas rapidas" lt rgb "#dc17d0"

set output 'plots/analisis/resumen135.png'
set terminal png size 1500,600
plot "files/gnuplot_data/media/media_5cajaRapida.txt"  using 1:2 with linespoints  title "5 cajas rapidas" lt rgb "#065602","files/gnuplot_data/media/media_3cajaRapida.txt"  using 1:2 with linespoints  title "3 cajas rapidas" lt rgb "#17dcb2","files/gnuplot_data/media/media_1cajaRapida.txt"  using 1:2 with linespoints  title "1 cajas rapidas" lt rgb "#dc17d0"

set terminal png size 1200,500
set boxwidth 0.9
set style fill solid
unset key
set xrange [6:24];
set title 'Tiempo de espera medio por hora con 1 caja rapida'
set output 'plots/barra/barra1.png'
plot "files/gnuplot_data/media/media_1cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 2 cajas rapida'
set output 'plots/barra/barra2.png'
plot "files/gnuplot_data/media/media_2cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 3 cajas rapida'
set output 'plots/barra/barra3.png'
plot "files/gnuplot_data/media/media_3cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 4 cajas rapida'
set output 'plots/barra/barra4.png'
plot "files/gnuplot_data/media/media_4cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 5 cajas rapida'
set output 'plots/barra/barra5.png'
plot "files/gnuplot_data/media/media_5cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 6 cajas rapida'
set output 'plots/barra/barra6.png'
plot "files/gnuplot_data/media/media_6cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 7 cajas rapida'
set output 'plots/barra/barra7.png'
plot "files/gnuplot_data/media/media_7cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 8 cajas rapida'
set output 'plots/barra/barra8.png'
plot "files/gnuplot_data/media/media_8cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 9 cajas rapida'
set output 'plots/barra/barra9.png'
plot "files/gnuplot_data/media/media_9cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 10 cajas rapida'
set output 'plots/barra/barra10.png'
plot "files/gnuplot_data/media/media_10cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 11 cajas rapida'
set output 'plots/barra/barra11.png'
plot "files/gnuplot_data/media/media_11cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 12 cajas rapida'
set output 'plots/barra/barra12.png'
plot "files/gnuplot_data/media/media_12cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 13 cajas rapida'
set output 'plots/barra/barra13.png'
plot "files/gnuplot_data/media/media_13cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"

set title 'Tiempo de espera medio por hora con 14 cajas rapida'
set output 'plots/barra/barra14.png'
plot "files/gnuplot_data/media/media_14cajaRapida.txt" using 1:2 with boxes lt rgb "#b890d5"



