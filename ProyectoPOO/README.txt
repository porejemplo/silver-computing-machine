Especificaciones sobre el codigo.

Como la inteligencia artificial de algunos de los NPCs no les hace buscar activamente la consecuion de sus objetivos en ocasiones la partida se
alarga de manera indefinida. Para que la partida se de por acabada, todos los personajes tienen que decidir no hacer nada. Asi, cuando un NPC
completa sus objetivos, decide siempre no hacer nada. Por lo que la partida se da por terminada cuando los NPCs cumplen sus objetivos y el jugador
decide no hacer nada, o cuando aleatoriamente todos los personajes no hacen nada una ronda. Teniendo esto en cuenta, para poder comprobar que el
final del juego se ejecuta correctamente, hemos implementado un sistema de rondas. Existe un limitador de rondas en la linea 47 de la clase 
GestorJuego que permite acabar el juego tras la ronda 10 (u otro valor numerico). El limitador esta comentado para ceñirse a las especificaciones
del enunciado, para habilitar el limitador es necesario descomentarlo.