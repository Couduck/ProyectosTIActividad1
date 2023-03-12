import java.util.ArrayList;

public class Sorter {
    //(MÉTODO RECUPERADO Y MODIFICADO DE UNA ENTREGA DE UNA MATERIA ANTERIOR) Inserta la palabra en la lista del diccionario completo, está basado en el método de busqueda binaria
    public void ordenarPalabra(String palabraNueva, ArrayList<String> lista)
    {
        boolean colocada = false;   //Evalua si la palabra ya encontró su sitio
        int limInf = 0, limSup = lista.size() - 1;  //Se generan los limites inferiores y superiores, empezando siempre con el primer valor de la lista y con el último asignados respectivamente
        int puntoMedio = 0;     //El punto medio sobre el cual partir

        while (!colocada)    //Mientras el programa no haya notificado que la palabra no fue insertada en la lista
        {
            puntoMedio = (limInf + limSup) / 2;   //Se define el punto medio

            String aComparar = lista.get(puntoMedio); //Se obtiene el elemento del punto medio en base a los límites,
            int decision = palabraNueva.compareToIgnoreCase(aComparar);   //La palabra del punto medio se compara con la palabra a meter para comprobar si la nueva va antes o despues que la del punto medio en un sentido alfabético

            if (puntoMedio - 1 < limInf || puntoMedio + 1 > limSup)  //En caso de que el punto medio no se encuentre entre 2 valores (Ya sea el borde derecho o izquiero o incluso, el único valor en la lista. Esto es para casos en los que la lista se ha reducido tanto que ya solo queda un valor a evaluar)
            {
                if (puntoMedio - 1 < limInf && puntoMedio + 1 > limSup)  //Si el punto medio no esta rodeado de nada
                {
                    if (decision < 0)    //Si la palabra nueva va antes del punto medio, se coloca detrás sin preguntar mas
                    {
                        //Un if hecho para manejar el comportamiento del add dependiendo del valor del punto medio
                        if (puntoMedio == 0) {
                            lista.add(0, palabraNueva);
                        } else {
                            lista.add(puntoMedio, palabraNueva);
                        }

                        colocada = true;    //La palabra se ha colocado, de modo que el ciclo no vuelve a repetirse
                    } else    //Si la palabra nueva va despues del punto medio, se coloca delante sin preguntar mas
                    {
                        //Un if hecho para manejar el comportamiento del add dependiendo del valor del punto medio
                        if (puntoMedio == lista.size() - 1) {
                            lista.add(palabraNueva);
                        } else {
                            lista.add(puntoMedio + 1, palabraNueva);
                        }

                        colocada = true;//La palabra se ha colocado, de modo que el ciclo no vuelve a repetirse
                    }
                } else    //Si el punto medio posee mas valores pero solo de un lado
                {
                    if (puntoMedio - 1 < limInf)   //Si el punto medio es el extremo izquierdo de acuerdo a los límites
                    {
                        if (decision < 0)    //Debido a que no hay nada detrás del punto medio, si la palabra nueva va antes, se coloca sin preguntar
                        {
                            //Procedimiento de inserción visto arriba
                            if (puntoMedio == 0) {
                                lista.add(0, palabraNueva);
                            } else {
                                lista.add(puntoMedio, palabraNueva);
                            }

                            colocada = true;
                        } else    //Va despues del punto medio, sin embargo, se compara también la palabra que le sigue. Puede que encontremos el lugar final de la nueva palabra
                        {
                            aComparar = lista.get(puntoMedio + 1); //Se compara la nueva palabra con la palabra siguiente al punto medio

                            //¿Va despues del punto medio, pero antes de la palabra siguiente? Hemos encontrado su lugar
                            if (palabraNueva.compareToIgnoreCase(aComparar) < 0) {
                                lista.add(puntoMedio + 1, palabraNueva);
                                colocada = true;
                            }

                            //¿Va despues del punto medio y tambien despues de la palabra siguiente? Repitamos de nuevo con un nuevo límite inferior (y por obviedad, un nuevo punto medio)
                            else {
                                limInf = puntoMedio + 1;
                            }
                        }
                    } else    //Si el punto medio es el extremo derecho de acuerdo a los límites
                    {
                        if (decision < 0)    //Va antes del punto medio, sin embargo, se compara también la palabra que le antecede. Puede que encontremos el lugar final de la nueva palabra
                        {
                            aComparar = lista.get(puntoMedio - 1); //Se compara la nueva palabra con la palabra siguiente al punto medio

                            //¿Va antes del punto medio, pero despues de la palabra anterior? Hemos encontrado su lugar
                            if (palabraNueva.compareToIgnoreCase(aComparar) > 0) {
                                lista.add(puntoMedio, palabraNueva);
                                colocada = true;
                            }

                            //¿Va antes del punto medio y tambien antes de la palabra anterior? Repitamos de nuevo con un nuevo límite superior (y por obviedad, un nuevo punto medio)
                            else {
                                limSup = puntoMedio - 1;
                            }
                        } else    //Debido a que no hay nada después del punto medio, si la palabra nueva va despues, se coloca sin preguntar
                        {
                            //Procedimiento de inserción visto arriba
                            if (puntoMedio == lista.size() - 1) {
                                lista.add(palabraNueva);
                            } else {
                                lista.add(puntoMedio + 1, palabraNueva);
                            }

                            colocada = true;
                        }
                    }
                }
            } else    //El punto medio se encuentra entre 2 valores si o si
            {
                if (decision < 0)    //Si la palabra nueva va antes del punto medio
                {
                    //La palabra nueva se compara con la palabra anterior al punto medio
                    aComparar = lista.get(puntoMedio - 1);
                    decision = palabraNueva.compareToIgnoreCase(aComparar);

                    //¿Va antes del punto medio y tambien antes de la palabra anterior? Repitamos de nuevo con un nuevo límite superior (y por obviedad, un nuevo punto medio)
                    if (decision < 0) {
                        limSup = puntoMedio - 1;
                    }

                    //¿Va antes del punto medio, pero despues de la palabra anterior? Hemos encontrado su lugar
                    if (decision >= 0) {
                        lista.add(puntoMedio, palabraNueva);
                        colocada = true;
                        decision = 0;
                    }
                } else    //Si la palabra nueva va despues del punto medio
                {
                    //La palabra nueva se compara con la palabra siguiente al punto medio
                    aComparar = lista.get(puntoMedio + 1);
                    decision = palabraNueva.compareToIgnoreCase(aComparar);

                    //¿Va despues del punto medio, pero antes de la palabra siguiente? Hemos encontrado su lugar
                    if (decision < 0) {
                        lista.add(puntoMedio + 1, palabraNueva);
                        colocada = true;
                    }

                    //¿Va despues del punto medio y tambien despues de la palabra siguiente? Repitamos de nuevo con un nuevo límite inferior (y por obviedad, un nuevo punto medio)
                    if (decision > 0) {
                        limInf = puntoMedio + 1;
                    }
                }
            }
        }
    }

    public ArrayList<String> quickSort(ArrayList<String> enProceso, int limite1, int limite2)   ///Metodo para ordenar el arreglo con el metodo Quick, recibe la lista actual a ordenar al igual que los limites finales de la misma (donde empieza y donde termina)
    {
        ArrayList<String> resultado = new ArrayList<String>();    //Lista a devolver

        int i = limite1, j = limite2;   //Los límites auxiliares, estos cambiaran y utilizaran los limites de la lista a modo de referencia para su comportamiento

        if (limite1 == limite2)  //Caso base: Si los limites de inicio son iguales
        {
            resultado = enProceso;
        }

        else    //Caso recursivo, los limites iniciales son diferentes
        {
            String pivote = enProceso.get(limite1);    //Se genera el pivote, siempre empezando con el valor que se encuentra en la primer casilla

            while (i != j)   //Mientras ambos limites auxiliares no se alinen, significa que el pivote aun no ha encontrado su lugar final en el arreglo
            {
                while (j > limite1 && i != j)    //Comprueba que el limite auxiliar a la derecha del pivote no haya recorrido el arreglo completo, al igual que se comprueba que ambos limites auxiliares no sean iguales (Lo que significa que el pivote ya tiene una posición asignada)
                {
                    if (enProceso.get(j).compareToIgnoreCase(pivote) < 0)   //Si el elemento a analizar es menor al pivote, estos intercambian posiciones, de lo contrario, el auxiliar j avanza a la izquierda
                    {
                        String aux = enProceso.get(j);
                        enProceso.set(j, pivote);
                        enProceso.set(i, aux);
                        break;
                    }

                    else
                    {
                        j--;
                    }
                }

                while (i < limite2 && i != j)    //Comprueba que el limite auxiliar a la izquierda del pivote no haya recorrido el arreglo completo, al igual que se comprueba que ambos limites auxiliares no sean iguales (Lo que significa que el pivote ya tiene una posición asignada)
                {

                    if (enProceso.get(i).compareToIgnoreCase(pivote) >= 0)   //Si el elemento a analizar es mayor al pivote, estos intercambian posiciones, de lo contrario, el auxiliar i avanza a la derecha
                    {
                        String aux = enProceso.get(i);
                        enProceso.set(i, pivote);
                        enProceso.set(j, aux);
                        break;
                    }

                    else
                    {
                        i++;
                    }
                }
            }

            //Una vez el`pivote tenga un lugar final, se pueden dar 3 casos:

            if (i - 1 < limite1)   //En caso de que el pivote no tenga nada a la izquierda, se recursea solamente usando el lado derecho del arreglo
            {
                resultado = quickSort(enProceso, i + 1, limite2);
            }

            else
            {
                if (i + 1 > limite2)   //En caso de que el pivote no tenga nada a la derecha, se recursea solamente usando el lado izquierdo del arreglo
                {
                    resultado = quickSort(enProceso, limite1, i - 1);
                } else    //Si el pivote tiene valores tanto a la izquierda como a la derecha, se recursan ambos lados
                {
                    resultado = quickSort(enProceso, limite1, i - 1);
                    resultado = quickSort(enProceso, i + 1, limite2);
                }
            }
        }

        return resultado;
    }
}