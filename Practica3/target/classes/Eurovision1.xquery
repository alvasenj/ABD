xquery version "3.0";

for $b in doc("Eurovision.xml")//concurso/ediciones
return $b/edicion/anyo

