/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.teatromoroventas;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class TeatroMoroVentas {

        //bloque de varibles para reservar los asientos
        static String opcionDeReserva; 
        static int asientoSel;
        static int filaSel;
        static boolean[] filaElegida;
        static int indiceAsiento;
        
        //Bloque de variables y listas que arman el plano
        static int filas = 5;
        static int asientos = 10;
        static boolean[] fila1 = new boolean[asientos];
        static boolean[] fila2 = new boolean[asientos];
        static boolean[] fila3 = new boolean[asientos];
        static boolean[] fila4 = new boolean[asientos];
        static boolean[] fila5 = new boolean[asientos];
        
        //variables con los costos para agregarlos a la boleta
        static int valorVip = 30000;
        static int valorPreferencial = 15000;
        static int valorGeneral = 10000;
        static int compraEjecutada = 0;
        static int condicionUsuario;
        static int descuentoAplicado;
        


        //bloque inicializa el timer
        static Timer timer = new Timer();
        static TimerTask tareaDeCancelacion = new TimerTask() {
        @Override
        public void run() {
            filaElegida[indiceAsiento] = false ;
            System.out.println("Su reserva  se a anulado debido a que no a la a confirmado");

        }
        };//final del timertask
        
        static String tipoDeEntradaUsuario;
        static int precioEntradaUsuario;
        
        
        
        
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        class contenedor{
        String tipoDeEntrada;
        int precio;
        int descuento;
        int costoTotal;
        
        contenedor(String tipoDeEntrada, int precio, int descuento, int costoTotal){
            this.tipoDeEntrada = tipoDeEntrada;
            this.precio = precio;
            this.descuento = descuento;
            this.costoTotal = costoTotal;
        }
    }
        
        ArrayList<contenedor> listaResumenDeVenta = new ArrayList<>();
        
        
            
    
    
    
    for(;;){
            System.out.println("...::: Bievenido al teatro Moro :::...");
            System.out.println("Por favor seleccione una de las siguientes opciones para comenzar");
            System.out.println(" ");
            System.out.println("1. Reservar entrada");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Modificar una venta existente");
            System.out.println("4. Emitir boleta");
            System.out.println("5. Salir");

            int opcion = pedirNumeroValido(sc, "Seleccione un numero del 1 al 5", 1, 5);

            switch (opcion) {
                case 1://reserva de entradas

                    //metodo que crea el plano
                    imprimirPlano(fila1, fila2, fila3, fila4, fila5, filas, asientos);
                    
                    //metodo que reserva el asiento
                    reservaAsiento(sc);
                    //metodo que recibe el numero de filaSel
                    
                    if (filaElegida[indiceAsiento]) {
                        System.out.println("El asiento ya esta reservado");
                        continue;
                    } else {
                        filaElegida[indiceAsiento] = true;
                        System.out.println("Fila seleccionada: " + filaSel + " numero de asiento: " + asientoSel + " reservado exitosamente.");


                    }
                    imprimirPlano(fila1, fila2, fila3, fila4, fila5, filas, asientos);//reimprime el plano con la reserva
                    
                    System.out.println("Por favor indique si cumple con alguna de las siguientes condiciones");
                    System.out.println("1. Estudiante");
                    System.out.println("2. Adulto mayor");
                    System.out.println("0. Ninguna de las anteriores");
                    condicionUsuario = pedirNumeroValido(sc, "Por favor ingrese una de las opciones indicadas", 0, 2);
                    switch (condicionUsuario) {
                        case 1:
                            descuentoAplicado = 10;
                            break;
                        case 2:
                            descuentoAplicado = 15;
                            break;
                        case 0:
                            break;
                    }
                    

                    //bloque de confirmacion de reserva
                    opcionDeReserva = pedirConfirmacion(sc, "Desea confirmar su reserva, indique (s) para confirmar (n) para volver al menu inicial");
                    if (opcionDeReserva.equalsIgnoreCase("s")) {
                        System.out.println("Su reserva fue hecha satisfatoriamente.");                       
                    } else {
                        System.out.println("Su reserva no a sido confirmada, se procedera a anular la reserva sino la confirma");
                        timer.schedule(tareaDeCancelacion, 60000);
                        continue;
 
                    }
                    continue;

                case 2://compra de entradas
                    
                    if (filaElegida == null) {
                        System.out.println("No hay ninguna reserva activa. Por favor haga una reserva antes de comprar.");
                        continue;
                        }
                    
                    if (filaElegida[indiceAsiento] == true) {
                        System.out.println("Usted posee la siguiente reserva");
                        contenedor nuevoContenedor = new contenedor(tipoDeEntradaUsuario, precioEntradaUsuario, descuentoAplicado, costoTotal(descuentoAplicado, precioEntradaUsuario));
                        listaResumenDeVenta.add(nuevoContenedor);
                        System.out.println("Fila seleccionada: " + filaSel + " numero de asiento: " + asientoSel);
                        for(contenedor elemento : listaResumenDeVenta){
                            System.out.println("Tipo de entrada: " + elemento.tipoDeEntrada + " precio: " + elemento.precio + "$ descuento: " + elemento.descuento + "% " + elemento.costoTotal + "$");
                        }
       
                        System.out.println("Indique el medio de pago por favor, ingresando el numero correspondiente");
                        System.out.println("1. Debito");
                        System.out.println("2. Credito");
                        System.out.println("0. Volver al menu inicial");
                        int opcionDePago = pedirNumeroValido(sc, "Seleccione un numero entre 0 y 2", 0, 2);
                        
                        
                        switch (opcionDePago) {
                            case 1, 2:
                            System.out.println("Su pago a sido procesado satisfactoriamente");
                            System.out.println("Disfrute del espectaculo");
                            timer.cancel();
                            compraEjecutada++;
                                continue;
                            case 0:
                                System.out.println("El pago no a sido procesado");
                            continue;
                        }//final del switch 
                        
                        }//final del condicional
                    
                    break;//salida del case 2
                    
                case 3: // Modificar reserva existente
                        if (filaElegida == null) {
                            System.out.println("No hay ninguna reserva aun, por favor realice una reserva");
                            continue;
                        }

                        // Mostrar reserva actual
                        System.out.println("Su reserva actual es:");
                        System.out.println("Fila: " + filaSel + ", Asiento: " + asientoSel);
                        imprimirPlano(fila1, fila2, fila3, fila4, fila5, filas, asientos);

                        // Confirmación
                        System.out.println("Desea modificar su reserva, indique (s) para confirmar (n) para volver al menu inicial");
                        String respuesta = sc.next();
                        if (!respuesta.equalsIgnoreCase("s")) {
                            System.out.println("No se realizó ninguna modificación.");
                            continue;
                        }

                        // Liberar asiento anterior
                        filaElegida[indiceAsiento] = false;

                        // Mostrar plano actualizado
                        System.out.println("Seleccione una nueva ubicación:");
                        imprimirPlano(fila1, fila2, fila3, fila4, fila5, filas, asientos);

                        // Reservar nueva ubicación
                        reservaAsiento(sc);

                        if (filaElegida[indiceAsiento]) {
                            System.out.println("El asiento seleccionado ya está ocupado. Intente nuevamente.");
                            continue;
                        }

                        filaElegida[indiceAsiento] = true;
                        System.out.println("Reserva modificada exitosamente:");
                        System.out.println("Nueva fila: " + filaSel + ", Nuevo asiento: " + asientoSel);
                        imprimirPlano(fila1, fila2, fila3, fila4, fila5, filas, asientos);
                        continue;
                        
                case 4://emitir boleta
                        //Aqui hay que modificar la forma en como se presenta la boleta
                    if (filaElegida == null) {
                            System.out.println("No a realizado ninguna compra");
                            continue;
                        }
                    
                    
                    
                    if (filaElegida[indiceAsiento] == true) {
                        if(compraEjecutada > 0){
                        System.out.println("---------------------------------");
                        System.out.println("           Teatro Moro           ");
                        System.out.println("---------------------------------");
                        System.out.println("Ubicacion: " + tipoDeEntradaUsuario + " fila: " + filaSel + " asiento numero: " + asientoSel);
                        System.out.println("Costo base: " + precioEntradaUsuario);
                        System.out.println("Descuento: " + descuentoAplicado + "%");
                        System.out.println("Total: " + costoTotal(descuentoAplicado, precioEntradaUsuario) + "$");
                        System.out.println("---------------------------------");
                        System.out.println("   Gracias por su visita al teatro Moro  ");
                        System.out.println("---------------------------------");
                        
                        }
                        break;
                        
                    }
                   
                case 5://salir
                    System.out.println("Gracias por visitar la web del Teatro Moro");
                 }//final del switch
            break;//salida del bucle for
         }//final del bucle for
            
    }//final del main
    
    
    //funcion que imprime el plano del teatro

    
    
    
    
    
public static void reservaAsiento(Scanner sc){
    //solicitud de la fila al usuario
    filaSel = pedirNumeroValido(sc, "Seleccione un numero del 1 al 5", 1, 5);

    //solicitud del asiento al usuario
    asientoSel = pedirNumeroValido(sc, "Seleccione un numero del 1 al 10", 1, 10);
    
    //lista que almacena la fila elegida por el usuario

    switch (filaSel) {
        case 1:
            filaElegida = fila1;
            tipoDeEntradaUsuario = "VIP";
            precioEntradaUsuario = 30000;
            break;
        case 2:
            filaElegida = fila2;
            tipoDeEntradaUsuario = "VIP";
            precioEntradaUsuario = 30000;
            break;
        case 3:
            filaElegida = fila3;
            tipoDeEntradaUsuario = "Preferencial";
            precioEntradaUsuario = 15000;
            break;
        case 4:
            filaElegida = fila4;
            tipoDeEntradaUsuario = "Preferencial";
            precioEntradaUsuario = 15000;
            break;
        case 5:
            filaElegida = fila5;
            tipoDeEntradaUsuario = "General";
            precioEntradaUsuario = 10000;
            break;
        default:
            System.out.println("Por favor elija un numero del 1 al 5");

    }//cierre del switch
    
}//cierre del metodo reservaAsientos
public static void imprimirPlano(boolean[] fila1, boolean[] fila2, boolean[] fila3, boolean[] fila4, boolean[] fila5, int numeroDeFilas, int numeroDeAsientos){
        
                    System.out.println("Por favor seleccione el asiento que desea reservar");
                    System.out.println("Fila 1 y 2 VIP: 30000$");
                    System.out.println("Fila 3 y 4 Preferencial: 20000$");
                    System.out.println("Fila 5 General: 15000$");
                    System.out.println("      ---Plano del teatro---");
                    System.out.println("_____________Escenario_____________");
                    System.out.println("Asientos disponibles: (0) Asientos ocupados: (X)");
                    
                        for(int f = 1; f <= numeroDeFilas;f++){
                            boolean[] filaTemp;
                            
                            if (f == 1) {
                                filaTemp = fila1;
                            }else if(f==2){
                                filaTemp = fila2;
                            }else if(f==3){
                                filaTemp = fila3;
                            }else if(f==4){
                                filaTemp = fila4;
                            }else{
                                filaTemp = fila5;
                            }
                            
                            System.out.println("Fila " + f + " : ");
                            
                            for (int a = 0; a < numeroDeAsientos; a++){
                                if(filaTemp[a]) System.out.print("[X]");
                                else System.out.print("[0]");
                            }
                            System.out.println(" ");
                            
                            
                            
                        }
}//cierre del metodo imprimir plano

//en este metodo se encapsula la validacion de entradas numericas
public static int pedirNumeroValido(Scanner sc, String mensaje, int min, int max) {
        int numero;
        while (true) {
            if (!mensaje.isEmpty()) System.out.println(mensaje);
            if (sc.hasNextInt()) {
                numero = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
                if (numero >= min && numero <= max) {
                    return numero;
                }
            } else {
                sc.nextLine(); // Limpiar entrada inválida
            }
            System.out.println("Entrada inválida. Por favor, ingrese un número entre " + min + " y " + max + ".");
        }
    }//cierre de la validacion de las entradas numericas

//metodo que valida las entradas con caracteres tipo S o N
 public static String pedirConfirmacion(Scanner sc, String mensaje) {
        String respuesta;
        while (true) {
            System.out.println(mensaje);
            respuesta = sc.next().toLowerCase();
            if (respuesta.equals("s") || respuesta.equals("n")) {
                sc.nextLine(); // Limpia el scanner
                return respuesta;
            }
            System.out.println("Entrada inválida. Ingrese 's' para confirmar o 'n' para volver al menu inicial.");
        }
    }//cierre del metodo para validar entradas de confirmacion tipo S o N
 
 //metodo para calcular descuento de adultos mayores
   public static int costoTotal(int descuentoAplicado, int precioEntradaUsuario){
        
        int descuento = descuentoAplicado * precioEntradaUsuario / 100;
        int costoTotal = precioEntradaUsuario - descuento;
        return costoTotal;

    }//cierre del metodo de costo total
   
   
    
}//final de la clase
