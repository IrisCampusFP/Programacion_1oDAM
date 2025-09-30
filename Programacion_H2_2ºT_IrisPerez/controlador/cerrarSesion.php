<?php
// Archivo que destruye la sesión (la cierra) y redirige al usuario a la página de inicio
session_start();
session_destroy();
header("Location: ../index.html");
exit();
?>