import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManejadorArchivos      //Clase para manejar los archivos de HTML
{
    private ArrayList<String> DiccionarioLista = new ArrayList<String>();
    public void generarDiccionario() throws IOException {


        long inicioPrograma = System.currentTimeMillis();

        FileWriter salida = new FileWriter("Salidas\\Salida3.txt"), diccionario = new FileWriter("Diccionario.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCreacionDiccionario = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(int i = 2; i < 504; i++)
        {
            //Se genera el link del archivo cuyas etiquetas sevan a remover en la iteración
            String linkActual = "Limpios\\" + this.numeroAString(i) + ".txt";

            //Empieza el conteo del tiempo que se tarda en remover las etiquetas, se hace, se genera el archivo puro y se termina el conteo
            long inicioLeerPagina = System.currentTimeMillis();
            extraerPalabrasArchivo(linkActual,i);
            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }


        }

        diccionario.write(listaAString(DiccionarioLista));

        //Termina el proceso de borrado de etiquetas
        long finCreacionDiccionario = System.currentTimeMillis();

        //Se obtiene la duración total del borrado de todas las etiquetas
        long duracionCreacionDiccionario = finCreacionDiccionario - inicioCreacionDiccionario;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para elaborar el diccionario completo fue de " + duracionCreacionDiccionario/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - inicioPrograma;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
        diccionario.close();
    }



    //Quita todas als etiquetas de la actividaad
    public void removerTodasLasEtiquetas() throws IOException
    {
        long inicioPrograma = System.currentTimeMillis();

        FileWriter salida = new FileWriter("Salidas\\Salida2.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioBorradoEtiquetas = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(int i = 2; i < 504; i++)
        {
            //Se genera el link del archivo cuyas etiquetas sevan a remover en la iteración
            String linkActual = "Files\\" + this.numeroAString(i) + ".html";

            //Empieza el conteo del tiempo que se tarda en remover las etiquetas, se hace, se genera el archivo puro y se termina el conteo
            long inicioLeerPagina = System.currentTimeMillis();
            this.quitarEtiquetas(linkActual,i);
            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }
        }

        //Termina el proceso de borrado de etiquetas
        long finBorradoEtiquetas = System.currentTimeMillis();

        //Se obtiene la duración total del borrado de todas las etiquetas
        long duracionBorradoEtiqutas = finBorradoEtiquetas - inicioBorradoEtiquetas;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para retirar todas las etiquetas de todas las paginas fue de " + duracionBorradoEtiqutas/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - inicioPrograma;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
    }

    //Abre todas las páginas de la actividad
    public void abrirTodaslasPaginas() throws IOException
    {
        //Empieza el conteo de los milisegundos que pasan en el programa completo
        long inicioPrograma = System.currentTimeMillis();

        FileWriter salida = new FileWriter("Salidas\\Salida1.txt");       //Se crea el archivo de salida de la información

        boolean primeraVez = true;      //Variable booleana que indica si se realizará la primera escritura en el archivo de salida

        long inicioCargaPaginas = System.currentTimeMillis();   //Empieza el conteo que indica el tiempo que el sistema tarda en cargar todas las páginas

        //Desde el archivo 002 hasta el 503 realiza este ciclo
        for(int i = 2; i < 504; i++)
        {
            //Se genera el link del archivo a leer en la iteración
            String linkActual = "Files\\" + this.numeroAString(i) + ".html";

            //Empieza el conteo del tiempo que se tarda en abrir la pagina, se realiza la apertura y se termina el conteo
            long inicioLeerPagina = System.currentTimeMillis();
            this.abrirPagina(linkActual);
            long finLeerPagina = System.currentTimeMillis();

            //Se obtiene el tiempo total de duración del conteo
            long duracionLeerPagina = finLeerPagina - inicioLeerPagina;

            //Se añade al archivo de salida el nombre de la página junto con su duración en segundos
            String entradaPagina = linkActual + "\t" + duracionLeerPagina/1000.0;

            //Dependiendo de si es la primera escritura, se escrbe o se anexa en el documento de texto
            if(primeraVez)
            {
                salida.write(entradaPagina + "\n");
                primeraVez = false;
            }

            else
            {
                salida.append(entradaPagina + "\n");
            }

        }

        //Termina el conteo de la carga de páginas
        long finCargaPaginas = System.currentTimeMillis();

        //Se obtiene la duración total del conteo
        long duracionCargaPaginas = finCargaPaginas - inicioCargaPaginas;

        //Se añade el tiempo al documento de salida
        salida.append("\nEl tiempo requerido para cargar todas las paginas fue de fue de " + duracionCargaPaginas/1000.0 + " segundos");

        //Mismo proceso de arriba pero con la duración total del programa
        long finPrograma = System.currentTimeMillis();

        long duracionPrograma = finPrograma - inicioPrograma;

        salida.append("\nLa duración del programa fue de " + duracionPrograma/1000.0 + " segundos");

        //Se cierra el documento
        salida.close();
    }

    public void extraerPalabrasArchivo(String link, int num) throws IOException {
        /**
         * 1° Abrir N archivo
         * 2° Pasa filtro de solo cosas que pueda haber en palabras: [^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'0-9-]
         * 3° Quitar lineas nuevas por espacios
         * 4° Substrings del archivo completo por espacios
         * 5° Filtrar cada palabra con
         *      5.1° ¿Empieza con numeros?
         *      5.2° ¿Ya existe en la lista?
         * 6° Comparar palabras de la lista con palabras del diccionario completo
         *      6.1° ¿Ya existe en diccionario?
         *      6.2° ¿Donde va?
         * 7° Pasar a siguiente archivo
         * 8° FIN
         */

        FileReader fileLectura = new FileReader(link);  //Se guarda el lector del archivo en cuestión
        BufferedReader bufred = new BufferedReader(fileLectura); // BufferedReader para el análisis de linea
        StringBuilder temporal = new StringBuilder(); // En esta se almacenará poco a poco el texto del archivo
        String archivoCompleto, linea;  //Almacenará el archivo completo con el texto completo

        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            if(linea.equals(""))    //Si la linea está vacía, se deja un salto de linea, si no lo está, se pone la linea en cuestión y se deja un salto de linea
            {
                temporal.append('\n');
            }

            else
            {
                temporal.append(linea + '\n');
            }
        }

        archivoCompleto = temporal.toString().replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'0-9-\n ]", "");
        archivoCompleto = archivoCompleto.replaceAll("\n"," ");
        String[] palabras = archivoCompleto.split(" ");
        List<String> arrayAlista = Arrays.asList(palabras);
        ArrayList<String> listapalabras = new ArrayList<>(arrayAlista), listaFiltrada = new ArrayList<String>();

        //Ciclo para sacar palabras del archivo
        for (String palabra:listapalabras)
        {
            if(!palabra.isBlank())
            {
                if(filtradoPalabras(palabra))
                {
                    if(!listaFiltrada.isEmpty())
                    {
                        if(!palabraEnLista(palabra, listaFiltrada))
                        {
                            listaFiltrada.add(palabra);
                        }
                    }

                    else
                    {
                        listaFiltrada.add(palabra);
                    }
                }
            }
        }

        //Ciclo para meter palabras de lista filtrada a diccionario
        for(String palabra : listaFiltrada)
        {
            if(!DiccionarioLista.isEmpty())
            {
                if(!palabraEnLista(palabra, DiccionarioLista))
                {
                    ordenarPalabra(palabra);
                }
            }

            else
            {
                DiccionarioLista.add(palabra);
            }
        }
    }

    public boolean palabraEnLista(String palabraNueva, ArrayList<String> lista)
    {
        boolean palabraEnLista = false;

        for(String palabra : lista)
        {
            if(palabraNueva.equalsIgnoreCase(palabra))
            {
                palabraEnLista = true;
                break;
            }
        }

        return palabraEnLista;
    }

    //Quita las etiquetas de la página del link correspondiente
    public void quitarEtiquetas(String link, int num) throws IOException {
        FileWriter fileEscritura = new FileWriter("Limpios\\" + this.numeroAString(num) + ".txt");  //Se crea el archivo de salida del texto filtrado
        FileReader fileLectura = new FileReader(link);  //Se guarda el lector del archivo en cuestión
        BufferedReader bufred = new BufferedReader(fileLectura); // BufferedReader para el análisis de linea
        StringBuilder temporal = new StringBuilder(); // En esta se almacenará poco a poco el texto del archivo
        String archivoCompleto, linea;  //Almacenará el archivo completo con el texto completo

        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            if(linea.equals(""))    //Si la linea está vacía, se deja un salto de linea, si no lo está, se pone la linea en cuestión y se deja un salto de linea
            {
                temporal.append('\n');
            }

            else
            {
                temporal.append(linea + '\n');
            }
        }

        //Se borran todas las etiquetas de la string que contiene el archivo completo
        archivoCompleto = temporal.toString().replaceAll("<.*?>|<.*?(?:\n.*?)+>"," ");

        //Se reemplazan los caracteres especiales como letras acentuadas y ñ's
        archivoCompleto = reemplazarCaracteresEspeciales(archivoCompleto);

        //archivoCompleto = temporal.toString();

        //Se guarda en el archivo de escritura el texto filtrado
        fileEscritura.write(archivoCompleto);

        //Cierre de archivos
        fileEscritura.close();
        fileLectura.close();

    }

    public String reemplazarCaracteresEspeciales(String archivoCompleto)
    {
        //Reemplaza minusculas acentuadas y ñ's
        archivoCompleto = archivoCompleto.replaceAll("&aacute;", "á");
        archivoCompleto = archivoCompleto.replaceAll("&eacute;", "é");
        archivoCompleto = archivoCompleto.replaceAll("&iacute;", "í");
        archivoCompleto = archivoCompleto.replaceAll("&oacute;", "ó");
        archivoCompleto = archivoCompleto.replaceAll("&uacute;", "ú");
        archivoCompleto = archivoCompleto.replaceAll("&uuml;", "ü");
        archivoCompleto = archivoCompleto.replaceAll("&nacute;", "ñ");

        //Reemplaza mayusculas acentuadas y ñ's
        archivoCompleto = archivoCompleto.replaceAll("&Aacute;", "Á");
        archivoCompleto = archivoCompleto.replaceAll("&Eacute;", "É");
        archivoCompleto = archivoCompleto.replaceAll("&Iacute;", "Í");
        archivoCompleto = archivoCompleto.replaceAll("&Oacute;", "Ó");
        archivoCompleto = archivoCompleto.replaceAll("&Uacute;", "Ú");
        archivoCompleto = archivoCompleto.replaceAll("&Uuml;", "Ü");
        archivoCompleto = archivoCompleto.replaceAll("&Nacute;", "Ñ");

        //Reemplaza referencias a ASCII por nada
        archivoCompleto = archivoCompleto.replaceAll("&#[0-9]+;", "");
        return archivoCompleto;
    }

    //Abre con el buscador predeterminado la página con el link que recibe de parametro
    public void abrirPagina(String link) throws IOException
    {
        File file = new File (link);
        Desktop.getDesktop().browse(file.toURI());
    }

    public String numeroAString(int numero)     //Se usa para crear la String que cambie el numero de la iteración por el nombre real de la página de HTML
    {
        String regresar="";

        if(numero < 10)
        {
            regresar = "00";
            regresar += numero;
        }

        else
        {
            if(numero < 100)
            {
                regresar = "0";
                regresar += numero;
            }

            else
            {
                regresar = String.valueOf(numero);
            }
        }

        return regresar;
    }

    public void ordenarPalabra(String palabraNueva)    //Inserta la palabra en la lista que accede al archivo, está basado en el método de busqueda binaria
    {
        boolean colocada = false;   //Evalua si la palabra ya encontró su sitio
        int limInf = 0, limSup = DiccionarioLista.size()-1;  //Se generan los limites inferiores y superiores, empezando siempre con el primer valor de la lista y con el último asignados respectivamente
        int puntoMedio = 0;     //El punto medio sobre el cual partir

        while(!colocada)    //Mientras el programa no haya notificado que la palabra no fue insertada en la lista
        {
            puntoMedio = (limInf + limSup)/2;   //Se define el punto medio

            String aComparar = DiccionarioLista.get(puntoMedio); //Se obtiene el elemento del punto medio en base a los límites,
            int decision = palabraNueva.compareToIgnoreCase(aComparar);   //La palabra del nodo obtenido se compara con la palabra a meter, para comprobar si la nueva va antes o despues que la del punto medio en un sentido alfabético

            if(puntoMedio-1 < limInf || puntoMedio+1 > limSup)  //En caso de que el punto medio no se encuentre entre 2 valores (Ya sea el borde derecho o izquiero o incluso, el único valor en la lista)
            {
                if(puntoMedio-1 < limInf && puntoMedio+1 > limSup)  //Si el punto medio no esta rodeado de nada
                {
                    if(decision < 0)    //Si la palabra nueva va antes del punto medio, se coloca detrás sin preguntar mas
                    {
                        //Un if hecho para manejar el comportamiento del add dependiendo del valor del punto medio
                        if(puntoMedio == 0)
                        {
                            DiccionarioLista.add(0, palabraNueva);
                        }

                        else
                        {
                            DiccionarioLista.add(puntoMedio, palabraNueva);
                        }

                        colocada = true;    //La palabra se ha colocado, de modo que el ciclo no vuelve a repetirse
                    }

                    else    //Si la palabra nueva va despues del punto medio, se coloca delante sin preguntar mas
                    {
                        //Un if hecho para manejar el comportamiento del add dependiendo del valor del punto medio
                        if(puntoMedio == DiccionarioLista.size()-1)
                        {
                            DiccionarioLista.add(palabraNueva);
                        }

                        else
                        {
                            DiccionarioLista.add(puntoMedio+1, palabraNueva);
                        }

                        colocada = true;//La palabra se ha colocado, de modo que el ciclo no vuelve a repetirse
                    }
                }

                else    //Si el punto medio posee mas valores pero solo de un lado
                {
                    if(puntoMedio-1 < limInf)   //Si el punto medio es el extremo izquierdo de acuerdo a los límites
                    {
                        if(decision < 0)    //Debido a que no hay nada detrás del punto medio, si la palabra nueva va antes, se coloca sin preguntar
                        {
                            //Procedimiento de inserción visto arriba
                            if(puntoMedio == 0)
                            {
                                DiccionarioLista.add(0, palabraNueva);
                            }

                            else
                            {
                                DiccionarioLista.add(puntoMedio, palabraNueva);
                            }

                            colocada = true;
                        }

                        else    //Va despues del punto medio, sin embargo, se compara también la palabra que le sigue. Puede que encontremos el lugar final de la nueva palabra
                        {
                            aComparar = DiccionarioLista.get(puntoMedio+1); //Se compara la nueva con la palabra siguiente al punto medio

                            //¿Va despues del punto medio, pero antes de la palabra siguiente? Hemos encontrado su lugar
                            if(palabraNueva.compareToIgnoreCase(aComparar) < 0)
                            {
                                DiccionarioLista.add(puntoMedio+1, palabraNueva);
                                colocada = true;
                            }

                            //¿Va despues del punto medio y tambien despues de la palabra siguiente? Repitamos de nuevo con un nuevo límite inferior (y por obviedad, un nuevo punto medio)
                            else
                            {
                                limInf = puntoMedio+1;
                            }
                        }
                    }

                    else    //Si el punto medio es el extremo derecho de acuerdo a los límites
                    {
                        if(decision < 0)    //Va antes del punto medio, sin embargo, se compara también la palabra que le antecede. Puede que encontremos el lugar final de la nueva palabra
                        {
                            aComparar = DiccionarioLista.get(puntoMedio-1); //Se compara la nueva con la palabra siguiente al punto medio

                            //¿Va antes del punto medio, pero despues de la palabra anterior? Hemos encontrado su lugar
                            if(palabraNueva.compareToIgnoreCase(aComparar) > 0)
                            {
                                DiccionarioLista.add(puntoMedio, palabraNueva);
                                colocada = true;
                            }

                            //¿Va antes del punto medio y tambien antes de la palabra anterior? Repitamos de nuevo con un nuevo límite superior (y por obviedad, un nuevo punto medio)
                            else
                            {
                                limSup = puntoMedio-1;
                            }
                        }

                        else    //Debido a que no hay nada después del punto medio, si la palabra nueva va despues, se coloca sin preguntar
                        {
                            //Procedimiento de inserción visto arriba
                            if(puntoMedio == DiccionarioLista.size()-1)
                            {
                                DiccionarioLista.add(palabraNueva);
                            }

                            else
                            {
                                DiccionarioLista.add(puntoMedio+1, palabraNueva);
                            }

                            colocada = true;
                        }
                    }
                }
            }

            else    //El punto medio se encuentra entre 2 valores si o si
            {
                if(decision < 0)    //Si la palabra nueva va antes del punto medio
                {
                    //La palabra nueva se compara con la palabra anterior al punto medio
                    aComparar = DiccionarioLista.get(puntoMedio-1);
                    decision = palabraNueva.compareToIgnoreCase(aComparar);

                    //¿Va antes del punto medio y tambien antes de la palabra anterior? Repitamos de nuevo con un nuevo límite superior (y por obviedad, un nuevo punto medio)
                    if(decision < 0)
                    {
                        limSup = puntoMedio-1;
                    }

                    //¿Va antes del punto medio, pero despues de la palabra anterior? Hemos encontrado su lugar
                    if(decision > 0)
                    {
                        DiccionarioLista.add(puntoMedio, palabraNueva);
                        colocada = true;
                        decision = 0;
                    }
                }

                else    //Si la palabra nueva va despues del punto medio
                {
                    //La palabra nueva se compara con la palabra siguiente al punto medio
                    aComparar = DiccionarioLista.get(puntoMedio+1);
                    decision = palabraNueva.compareToIgnoreCase(aComparar);

                    //¿Va despues del punto medio, pero antes de la palabra siguiente? Hemos encontrado su lugar
                    if(decision < 0)
                    {
                        DiccionarioLista.add(puntoMedio+1, palabraNueva);
                        colocada = true;
                    }

                    //¿Va despues del punto medio y tambien despues de la palabra siguiente? Repitamos de nuevo con un nuevo límite inferior (y por obviedad, un nuevo punto medio)
                    if(decision > 0)
                    {
                        limInf = puntoMedio+1;
                    }
                }
            }
        }
    }

    String listaAString(ArrayList<String> lista)
    {
        String listaStrings="";

        for(String palabra : lista)
        {
            listaStrings += palabra + "\n";
        }

        return listaStrings;
    }

    public boolean filtradoPalabras(String palabra)
    {
        boolean pasaFiltro = true;

        /**
         * 1° Tiene letras
         * 2° No tiene numeros
         * 3° Empieza con una letra
         * 4° todo: ¿Hay que añadir mas filtros?
         */

        String[] regexes = {"[a-zA-Z]", "[0-9]", "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ]"};
        boolean[] resultadosEsperados = {true, false, true};

        for(int i = 0; i < regexes.length; i++)
        {
            Pattern strPat = Pattern.compile(regexes[i]);
            Matcher m = strPat.matcher(palabra);

            if(m.find() != resultadosEsperados[i])
            {
                pasaFiltro = false;
                break;
            }
        }

        return  pasaFiltro;
    }
}
