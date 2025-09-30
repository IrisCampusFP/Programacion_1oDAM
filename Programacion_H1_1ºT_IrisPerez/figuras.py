#Inicializamos las variables.
respuesta = 0
lado = 0
areaCuadrado = 0
perimetroCuadrado = 0
areaRectangulo = 0
perimetroRectangulo = 0
base = 0
altura = 0

# Mostramos el menú al usuario y le pedimos respuesta hasta que seleccione una opción.
while respuesta < 1 or respuesta > 3:
    print(f"----------------------------------------") # Esto es un separador con finalidad únicamente estética.
    print(f"MENÚ:\n1-Cuadrado\n2-Rectángulo\n3-Salir")
    respuesta = int(input("Selecciona una opción del menú escribiendo el número correspondiente: "))
    # Mostramos al usuario la opción seleccionada.
    if respuesta > 0 and respuesta < 4:
        if respuesta != 3:
            if respuesta == 1:
                print(f"Has seleccionado cuadrado.")
                # Solicitamos al usuario la medida del lado
                lado = int(input("Selecciona la medida del lado: "))
                # Hacemos los cálculos del área y el perímetro del cuadrado aplicando sus fórmulas.
                areaCuadrado = lado**2
                perimetroCuadrado = lado * 4
                # Imprimimos el cuadrado con las medidas insertadas por el usuario.
                for i in range(lado):
                        print(f"☻" * lado)
                # Mostramos al usuario el resultado del área y el perímetro de la figura.
                print(f"El área del cuadrado es {areaCuadrado}")
                print(f"El perímetro del cuadrado es {perimetroCuadrado}")
                # Devolvemos al usuario al menú reiniciando la variable respuesta.
                respuesta = 0
            else:
                print(f"Has seleccionado rectángulo")
                # Solicitamos al usuario las medidas de la base y de la altura.
                base = int(input("Introduce la medida de la base: "))
                altura = int(input("Introduce la altura: "))
                # Hacemos los cálculos del área y el perímetro del rectangulo aplicando sus fórmulas.
                areaRectangulo = base * altura
                perimetroRectangulo = 2 * base + 2 * altura
                # Imprimimos el rectángulo con las medidas insertadas por el usuario.
                for i in range(altura):
                        print(f"☻" * base)
                # Mostramos al usuario el resultado del área y el perímetro de la figura.
                print(f"El área del rectángulo es {areaRectangulo}")
                print(f"El perímmetro del rectángulo es {perimetroRectangulo}")
                # Devolvemos al usuario al menú reiniciando la variable respuesta.
                respuesta = 0
        else:
            print(f"¡Hasta pronto!")
            break #Rompemos el bucle con un break para salir del programa.
    else:
        print(f"Error, introduce un número entero del 1 al 3.")