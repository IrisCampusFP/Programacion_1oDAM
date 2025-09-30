# Inicializamos las variables
saldo = -1
opcion = 0
dinero_ingresado = 0
dinero_retirado = 0
contador_ingresos = 0
contador_retiradas = 0

# Solicitamos al usuario el saldo de la cuenta
while saldo < 0:
    saldo = float(input("Introduce el saldo de la cuenta: "))
    if saldo < 0:
        print(f"ERROR, el saldo no puede ser negativo.")

# Mostramos el menú al usuario para que seleccione una opción hasta que elija 5- Salir.
# Realizamos el procedimiento correspondiente a cada opción.
while opcion < 1 or opcion > 5:
    print(f"----------------------------------------") # Esto es un separador con finalidad únicamente estética.    
    print(f"MENÚ:\n1- Ingresar dinero\n2- Retirar dinero\n3- Mostrar saldo\n4- Estadísticas\n5- Salir")
    opcion = int(input("Selecciona una opción del menú escribiendo el número correspondiente: "))
    # Mostramos al usuario la opción seleccionada.
    if opcion >= 1 and opcion <= 5:
        if opcion != 5:
            if opcion == 1:
                print(f"Has seleccionado la opción 1, 'Ingresar dinero'.")
                # Solicitamos al usuario la cantidad de dinero que quiere ingresar.
                dinero_ingresado = float(input("Introduce la cantidad de dinero que deseas ingresar: "))
                # Comprobamos que el saldo ingresado sea positivo.
                if dinero_ingresado > 0:
                    # Aumentamos en 1 el contador de ingresos para las estadísticas.
                    contador_ingresos = contador_ingresos + 1
                    # Actualizamos el saldo sumando el dinero ingresado
                    saldo = saldo + dinero_ingresado
                    print(f"Tu nuevo saldo es {saldo} €")
                    # Reestablecemos la variable opción en 0 para devolver al usuario al menú.
                    opcion = 0
                else:
                    print(f"ERROR, el saldo ingresado no puede ser negativo.")
                    opcion = 0
            elif opcion == 2:
                print(f"Has seleccionado la opción 2, 'Retirar dinero'.")
                # Solicitamos al usuario la cantidad de dinero que quiere retirar.
                dinero_retirado = float(input("Introduce la cantidad de dinero que deseas retirar: "))
                # Aumentamos en 1 el contador de retiradas para las estadísticas.
                contador_retiradas = contador_retiradas + 1
                # Actualizamos el saldo restando el dinero retirado.
                saldo = saldo - dinero_retirado
                # Si al retirar el dinero el saldo se queda en negativo, mostramos ERROR y cancelamos la operación.
                if saldo < 0:
                    saldo = saldo + dinero_retirado
                    print(f"ERROR, no puedes retirar el dinero porque no tienes suficiente saldo disponible.")
                    print(f"Saldo actual: {saldo} €")
                    opcion = 0
                else:
                    print(f"Tu nuevo saldo es {saldo} €")
                    opcion = 0
            elif opcion == 3:
                print(f"Has seleccionado la opción 3, 'Mostrar saldo'.")
                print(f"Tu saldo actual es {saldo} €")
                opcion = 0
            else:
                print(f"Has seleccionado la opción 4, 'Estadísticas'.")
                print(f"- Se han realizado {contador_ingresos} ingresos.") 
                print(f"- Se han realizado {contador_retiradas} retiradas.")
                opcion = 0
        else:
            print(f"¡Gracias, hasta pronto!")
            break #Utilizamos un break para salir del bucle y cerrar el programa.
    else:
        print(f"ERROR, escribe un número entero del 1 al 5 para seleccionar una opción del menú.")
