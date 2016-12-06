xquery version "3.0";

declare variable $anyo as xs:integer external;


let $b :=  doc("Eurovision.xml")//concurso/ediciones/edicion[anyo = $anyo]

let $ciudad := $b/ciudad
let $lugar := $b/lugar
return
    
<body>
    <h1>{string-join(($ciudad, $lugar)," (")})</h1>
    <ol>
{

for $c in $b/participantes

let $pais := data($c//pais/@id)
let $representante := data($c//representante/@id)
let $cancion := data($c//cancion)
let $votos := data($c//votos/voto/@pais)

for $a in doc("Eurovision.xml")//concurso
let $artista := $a/artistas//artista[@id = $representante]
let $descripcion := data($artista/descripcion)
let $imagen := data($artista/imagen)


order by $c/orden ascending

return 
         <li>
            <p>  {concat($pais," - ", $artista/nombre, " - ", $cancion)}</p>
            <p>  {$descripcion} </p>
            <p>  {$imagen} </p>
            <p>  Recibio votos de: {string-join ($votos ,",")}</p>
        </li>
}
    </ol>
</body>

