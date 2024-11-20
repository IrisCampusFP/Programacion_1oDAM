# Importo la librería random para poder generar números aleatorios.
import random

# Menú que aparece al inicio de la aplicación.
def menuInicial():
    while True:  # Utilizo un bucle while True para que el menú le vuelva a aparecer al usuario hasta que seleccione una opción válida.
        print("---------------------------------------")
        print("¡Hola! bienvenid@, ¿Qué quieres hacer?:\n1- Iniciar Sesión\n2- Registrarme\n3- Salir")
        try:  
            opcion = int(input("Selecciona una opción: "))
            if opcion == 1:
                iniciarSesion()
                break  # Si la opción seleccionada es válida, rompo el bucle while True con un break.
            elif opcion == 2:
                registrarse()
                break
            elif opcion == 3:
                salir()  # Llamo a la función salir para cerrar el programa.
                break
            else:
                print("ERROR, elige una de las opciones introduciendo el número correspondiente.")
        except ValueError: # Uso try except para que mi programa no se rompa si el usuario introduce una letra o un símbolo en vez de número (ValueError).
            print("ERROR, elige una de las opciones introduciendo el número correspondiente.")


# Función para registrar un cliente, solo si no está ya registrado.
# NOTA: utilizo el DNI como el campo único de cada cliente.
def registrarse():
    try:
        nombre = input("Introduce tu nombre: ").title() # .title() para que la primera letra sea mayúscula.
        DNI = input("Introduce tu DNI: ").upper() # .upper para que la letra del DNI sea mayúscula. 
        while len(DNI) != 9:  # Hago un bucle while para asegurarme de que el DNI introducido tiene 9 dígitos.
            print("El DNI debe tener 9 caracteres.")
            DNI = input("Introduce tu DNI: ").upper()
        # Antes de registrar al cliente compruebo que no exista ningún usuario registrado con el mismo DNI (campo único).
        with open("registros.txt", "r") as archivo:  
            for linea in archivo:
                if DNI in linea:
                    print("---------------------------------------")
                    print("Ya hay un cliente registrado con ese DNI, por favor, prueba a iniciar sesión.")
                    menuInicial()
        # Si el DNI no está registrado (no aparece en el archivo registros), registro al nuevo cliente. 
        registrarCliente(nombre, DNI)
    except FileNotFoundError:  # Si es el primer cliente en registrarse no existe el archivo registros, por lo que daría error al intentar leerlo.
        registrarCliente(nombre, DNI)

# Función que registra a los nuevos clientes (almacena los clientes registrados en el archivo registros).
# Y los envía al menú de la aplicación, guardando su nombre y DNI.
def registrarCliente(nombre, DNI):
    with open("registros.txt", "a") as archivo:
        archivo.write(f"{nombre}:{DNI}\n")
    print("-----------------------------")
    print(f"¡Gracias por registrarte, {nombre}!")
    menu(nombre, DNI)


# Función para iniciar sesión. Concede acceso a la aplicación solo a los clientes registrados.
def iniciarSesion():
    try:
        nombre = input("Introduce tu nombre: ").title()  
        DNI = input("Introduce tu DNI: ").upper()  
        with open("registros.txt", "r") as archivo:  # Abro el archivo registros para comprobar si el usuario está registrado.
            for linea in archivo:
                nombreArchivo, DNIarchivo = linea.strip().split(":")  # .split para separar cada campo en el archivo de texto.
                if DNIarchivo == DNI and nombreArchivo == nombre:  # Si el DNI y el nombre están registrados:
                    print("-----------------------------")
                    print(f"Bienvenid@ de nuevo, {nombre}")
                    menu(nombre, DNI)  # Concedo el acceso a la aplicación al usuario, guardando su nombre y DNI.
                else:  # Sinó, devuelvo al usuario al menú inicial para que se registre.
                    print("---------------------------------------")
                    print("Nombre o DNI incorrectos, prueba a registrarte")
                    menuInicial()
    except FileNotFoundError:  # Si no se ha registrado aun ningún usuario no existe el archivo registros, por lo que daría error al leerlo.
        print("ERROR, no hay ningún usuario registrado con ese DNI.\nPor favor, prueba a registrarte.")
        menuInicial()


# Función que muestra el menú de la aplicación (con el nombre y DNI del usuario guardados).
# NOTA: incluyo en el menú la opción ver clientes como como pide el enunciado, aunque eso haga que la aplicación deje de ser realista.
def menu(nombre, DNI):
    while True:  # Utilizo un bucle while True para devolver al usuario al menú siempre, hasta que elija salir (break).
        print("-----------------------------")
        print("MENÚ:\n1- Realizar una compra\n2- Buscar una compra\n3- Ver clientes\n4- Cerrar sesión\n5- Salir")
        try:
            opcion = int(input("Selecciona una opción: "))
            if opcion == 1:
                realizarCompra(nombre, DNI)
            elif opcion == 2:
                buscarCompra()
            elif opcion == 3:
                verClientes()
            elif opcion == 4:
                print(f"Has cerrado sesión, hasta pronto {nombre}.")
                menuInicial()
            elif opcion == 5:
                salir()  # Llamo a la función salir para cerrar el programa.
                break
            else: 
                print("ERROR, elige una de las opciones introduciendo el número correspondiente.")
        except ValueError:
            print("ERROR, elige una de las opciones introduciendo el número correspondiente.")


# Función para hacer una compra.
def realizarCompra(nombre, DNI):
    compra = False  # Inicializo la variable compra como False para no registrar la compra ni generar un ticket si el usuario no selecciona ningún producto antes de terminar.
    cesta = []  # Inicializo la lista cesta en la que se almacenan los productos que selecciona el usuario.
    productos = {1: "Altavoces", 2: "Auriculares", 3: "Teclado", 4: "Raton", 5: "Alfombrilla", 6: "Monitor"}  # Creo el diccionario productos en el que cada número corresponde a un producto.
    while True:  # Inicio un bucle while True para pedir al usuario qué producto quiere comprar hasta que seleccione 7 (TERMINAR).
        print(f"\n¿Qué producto quieres comprar?\n{productos}")
        try:
            producto = int(input("Introduce el número de un producto para añadirlo a la cesta\n(Introduce 7 PARA TERMINAR la compra): "))
            if producto == 7:  # Si el usuario selecciona 7, salgo del bucle (break).
                break
            if producto >= 1 and producto <= 6:  # Si el usuario selecciona algún producto.
                compra = True  # Cambio la variable compra a True.
                cesta.append(productos[producto])  # Añado el producto seleccionado a la cesta.
                print("-----------------------------")
                print(f"Has comprado: {productos[producto]}")  # Muestro al usuario qué producto ha comprado.
                print("-----------------------------")
            else:
                print("ERROR, este número no corresponde a ningún producto.")
                continue
        except ValueError:  # Por si el usuario introduce un caracter que no sea un número.
            print("ERROR")
            continue  # Para seguir con la compra.
    if compra == True:  # Si el usuario ha comprado al menos un producto, registro la compra y genero el ticket.
        print("-----------------------------")
        print("¡Gracias por tu compra!")
        asignarNumeroCompra(nombre, DNI, cesta)

# Asignamos un número de compra único, comprobando que no esté ya creado en el archivo compras.
def asignarNumeroCompra(nombre, DNI, cesta):
    numeroCompra = random.randint(1,999)
    try:
        with open("compras.txt", "r") as archivo:  # Compruebo que no exista una compra registrada con el mismo número de compra generado.
            for linea in archivo:
                numeroCompraArchivo, _, _, _ = linea.strip().split("|")
                numeroCompraArchivo = int(numeroCompraArchivo)  # Convierto de nuevo el número de compra a número (int) al sacarlo del archivo.
                while numeroCompra == numeroCompraArchivo:  # Mientras el número de compra esté registrado en el archivo compras, le sumo 1. De esta manera el número de compra será un campo único y no podrá haber diferentes compras con el mismo número de compra.
                    numeroCompra = numeroCompra + 1
        print(f"Número de compra: {numeroCompra}")  # Muestro el número de pedido.
        registrarCompra(numeroCompra, nombre, DNI, cesta)  # Registro la compra en el archivo compras.
    except FileNotFoundError:  # Si es la primera compra en registrarse no existirá el archivo compras.
        registrarCompra(numeroCompra, nombre, DNI, cesta)

# Función para registrar la compra en el archivo compras.
def registrarCompra(numeroCompra, nombre, DNI, cesta):
    with open("compras.txt", "a") as archivo:
        compra = (f"{numeroCompra}|{nombre}|{DNI}|{cesta}\n")
        archivo.write(compra)
    # Una vez registrada la compra, muestro el ticket al usuario.
    imprimirTicket(numeroCompra)

# Función para imprimir un ticket mediante el número de compra.
def imprimirTicket(numeroCompra):
    compraEncontrada = False
    try:
        with open("compras.txt", "r") as archivo:
            for linea in archivo:
                numeroCompraArchivo, nombreArchivo, DNIarchivo, cestaArchivo = linea.strip().split("|")
                numeroCompraArchivo = int(numeroCompraArchivo)  # Convierto el número de compra a int al sacarlo del archivo.
                if numeroCompra == numeroCompraArchivo:  # Si el numero de compra coincide con uno registrado, imprimo el ticket de esa compra.
                    compraEncontrada = True
                    print("Aquí tienes el ticket de la compra ;).")
                    print("-----------------------------")
                    print(f"TICKET:\nNúmero de compra: {numeroCompra}\nNombre: {nombreArchivo}\nDNI: {DNIarchivo}\nProductos Comprados: {cestaArchivo}")
        if compraEncontrada == False:
            print("Lo siento, no existe ninguna compra con ese número asociado.")
    except FileNotFoundError:  # Si aun no se ha registrado ninguna compra y no existe el archivo compras.
        print("Lo siento, no existe ninguna compra con ese número asociado.")


# Función para buscar una compra mediante el número de compra.
def buscarCompra():
    numeroCompra = int(input("\nIntroduce el número de la compra que deseas buscar: "))
    print(f"Buscando la compra nº {numeroCompra}...")
    imprimirTicket(numeroCompra)  # Llamo a la función imprimirTicket para mostrar el ticket de la compra buscada.


# Función para visualizar los clientes registrados y realizar búsquedas de clientes mediante su campo único (DNI).
def verClientes():
    try:
        print("\nElige qué quieres consultar:\n1- Ver todos los clientes registrados\n2- Buscar un cliente")
        opcion = int(input("Selecciona una opción: "))
        if opcion == 1:
            with open("registros.txt", "r") as archivo: # Asumo que existe el archivo registros ya que el cliente que consulta esta opción ha tenido que registrarse antes.
                contenido = archivo.read()
                print(contenido)
        else:
            buscarCliente()
    except ValueError:  # Por si el cliente introduce un caracter que no sea un número.
        print("ERROR, elige una de las opciones introduciendo el número correspondiente.")
        verClientes()

# Función para buscar un cliente registrado a través de su DNI.
def buscarCliente():
    encontrado = False
    DNIbuscado = input("Introduce el DNI del cliente que quieres buscar: ").upper()
    with open("registros.txt", "r") as archivo:  # Busco el DNI en el archivo registros.
        for linea in archivo:
            nombreArchivo, DNIarchivo = linea.strip().split(":")
            if DNIbuscado == DNIarchivo:  # Si el DNI buscado coincide con uno registrado, muestro los datos del cliente.
                encontrado = True
                print("-----------------------------")
                print(f"Cliente encontrado:\nNombre del cliente: {nombreArchivo} (DNI: {DNIarchivo})")
    if encontrado == False:  # Si el DNI buscado no está registrado.
        print("-----------------------------")
        print("Lo siento, no existe ningún cliente registrado con ese DNI.")


# Función para cerrar el programa.
def salir():
    print("Gracias por todo, ¡Hasta pronto! :)")
    exit()  # La función exit cierra directamente el programa.


##################################################
# Ejecución del menú inicial como función principal.
# El resto de funciones se ejecutarán en cadena dependiendo de lo que seleccione el usuario.
menuInicial()