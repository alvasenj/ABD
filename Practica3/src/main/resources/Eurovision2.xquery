xquery version "3.0";

declare variable $anyo as xs:integer external;


let $b :=  doc("Eurovision.xml")//concurso/ediciones/edicion[anyo = $anyo]


for $c in $b/participantes
let $pais := data($c//pais/@id)
let $cancion := data($c//cancion)
let $representante := data($c//representante/@id)

let $puntuacion := sum(data($c/votos/voto/@puntuacion))


where $b/anyo = $anyo
order by $puntuacion descending

return <clasificacion>
   
    <ClasificacionPais>{$pais}</ClasificacionPais>
    <Cancion>{$cancion}</Cancion>
    <Artista>{$representante}</Artista>
    <Puntuacion>
       { $puntuacion
       }
    </Puntuacion>
</clasificacion>
