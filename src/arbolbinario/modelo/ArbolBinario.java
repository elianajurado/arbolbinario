/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbinario.modelo;

import arbolbinario.modelo.excepciones.ArbolBinarioException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carloaiza
 */
public class ArbolBinario {
    
    private Nodo raiz;
    int cant;
    int altura;

    //public void adicionarNodo()
    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public void isLleno() throws ArbolBinarioException {
        if (raiz == null) {
            throw new ArbolBinarioException("El árbol está vacío");
        }
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String[] getNiveles() {
        return niveles;
    }

    public void setNiveles(String[] niveles) {
        this.niveles = niveles;
    }
    
    
    /**
     * Método que permite adicionar un nuevo dato al arbol
     * @param dato número
     * @param ubicacion posición donde se encontrará el dato
     * @throws ArbolBinarioException 
     */
    public void adicionarNodo(int dato, Nodo ubicacion) throws ArbolBinarioException {
        if (raiz == null) {
            raiz = new Nodo(dato);

        } else {
            if (dato < ubicacion.getDato()) {
                if (ubicacion.getIzquierda() == null) {
                    ubicacion.setIzquierda(new Nodo(dato));
                } else {
                    adicionarNodo(dato, ubicacion.getIzquierda());
                }
            } else if (dato > ubicacion.getDato()) {
                if (ubicacion.getDerecha() == null) {
                    ubicacion.setDerecha(new Nodo(dato));
                } else {
                    adicionarNodo(dato, ubicacion.getDerecha());
                }
            } else {
                throw new ArbolBinarioException("No se puede repetir");
            }
        }
    }
    
    //cantidad de nodos del arbol
    public String cantidadNodos() {
        cant = 0;
        cantidadNodos(raiz);
        return ""+cant;
    }
    
    //altura del arbol
    // Se mostrará cual es la altura de todo el arbol
      public String retornarAltura() {
        altura = 0;
        retornarAltura(raiz, 1);
        return ""+altura;
    }
    
    /**
     * 
     * @param temp Ayudante que toma referencia en un nodo
     * @param nivel parametro que indica a que nivel se encuentran ciertos datos del arbol, 
     */
    //Para mostrar la altura del arbol 
    private void retornarAltura(Nodo temp, int nivel) {
        if (temp != null) {
            retornarAltura(temp.getIzquierda(), nivel + 1);
            //si el nivel es mayor que la altura
            if (nivel > altura) {
                //entonces la altura será será igual al nivel 
                altura = nivel;
            }
            //retorna el valor de la altura
            retornarAltura(temp.getDerecha(), nivel + 1);
        }
    }
    

    /**
     * Método para contar la cantidad de nodos de todo el árbol
     * @param temp parametro que le indica al nodo por donde debe pasar, el recorrido que va a hacer
     */
    private void cantidadNodos(Nodo temp) {
        if (temp != null) {
            cant++;
            cantidadNodos(temp.getIzquierda());
            cantidadNodos(temp.getDerecha());
        }
    }
    
    //cantidad nodos hoja
   
    public String cantidadNodosHoja() {
        cant = 0;
        cantidadNodosHoja(raiz);
        return ""+cant;
    }

     /**
      * Método para contar la cantidad de nodos 
      * @param temp Ayudante que toma referencia en un nodo
      */ 
    private void cantidadNodosHoja(Nodo temp) {
        if (temp != null) {
            if (temp.getIzquierda()== null && temp.getDerecha()== null) {
                cant++;
            }
            cantidadNodosHoja(temp.getIzquierda());
            cantidadNodosHoja(temp.getDerecha());
        }
    }
      
    //Valor menor
     public String menorValor() {
         Nodo temp = raiz;
        if (raiz != null) {
            
            while (temp.getIzquierda()!= null) {
                temp = temp.getIzquierda();
            }
        }
        return ("" + temp.getDato());
    }
    //mayor Valor
    public String mayorValor() {
        Nodo temp = raiz;
        if (raiz != null) {
            while (temp.getDerecha()!= null) {
                temp = temp.getDerecha();
            }
        }
        return ("" + temp.getDato());
    }
    
    //Balance
    int subizq = 0;
    int subder = 0;

    public String imprimirBalance() {
         subizq = 0;
         subder = 0;

        Balance(this.raiz, true, 0);
        //System.out.println("lado Izquierdo " + subizq + " Lado Derecho " + subder);
        switch (subizq - subder) {
            case 0:
                return ("El balance es: 0 ");
            case -1:
                return("El balance es -1, derecha");
            case 1:
                return("El balance 1, izquierda");
            default:
                return("No es balanceado.."
                        + "porque es mas grande el lado "
                        + ((subizq > subder) ? "Izquierdo" : "Derecho"));
        }

    }
    
    
    public void Balance(Nodo temp, boolean lado, int i) {

        if (temp != null) {

            if (temp.getDerecha()== null && temp.getIzquierda()== null) {
                if (lado) {
                    subder = (i > subder) ? i : subder;
                } else {
                    subizq = (i > subizq) ? i : subizq;
                }
            }

            Balance(temp.getDerecha(), lado, i + 1);
            if (i == 0) {
                lado = false;
            }
            Balance(temp.getIzquierda(), lado, i + 1);
        }

    }
    
    //Borrar el que sea
    public int borrar(int x) {
        if (!this.buscar(x)) {
            return 0;
        }

        Nodo z = borrar(this.raiz, x);
        this.setRaiz(z);
        return x;

    }

    private Nodo borrar(Nodo r, int x) {
        if (r == null) {
            return null;//<--Dato no encontrado		
        }
        int compara = ((Comparable) r.getDato()).compareTo(x);
        if (compara > 0) {
            r.setIzquierda(borrar(r.getIzquierda(), x));
        } else if (compara < 0) {
            r.setDerecha(borrar(r.getDerecha(), x));
        } else {
            System.out.println("Encontro el dato:" + x);
            if (r.getIzquierda()!= null && r.getDerecha()!= null) {
                /*
                 *	Buscar el menor de los derechos y lo intercambia por el dato
                 *	que desea borrar. La idea del algoritmo es que el dato a borrar 
                 *	se coloque en una hoja o en un nodo que no tenga una de sus ramas.
                 **/
                Nodo cambiar = buscarMin(r.getDerecha());
                int aux = cambiar.getDato();
                cambiar.setDato(r.getDato());
                r.setDato(aux);
                r.setDerecha(borrar(r.getDerecha(), x));
                System.out.println("2 ramas");
            } else {
                r = (r.getIzquierda()!= null) ? r.getIzquierda(): r.getDerecha();
                System.out.println("Entro cuando le faltan ramas ");
            }
        }
        return r;
    }
    
    //Borrar menor
    public String borrarMenor() {
        Nodo temp=raiz.getIzquierda();
        if (raiz != null) {
            if (raiz.getIzquierda()== null) {
                raiz = raiz.getDerecha();
            } else {
                Nodo anterior = raiz;
                temp = raiz.getIzquierda();
                while (temp.getIzquierda()!= null) {
                    anterior = temp;
                    temp = temp.getIzquierda();
                }
                
                anterior.setIzquierda(temp.getDerecha());
            }
        }
        return ("Valor eliminado: " + temp.getDato());
    }

    
    /**
     * Método que elimina el número mayor
     * @return El valor eliminado
     */
    //borrar mayor
    public String borrarMayor() {
        Nodo temp=raiz.getIzquierda();
        if (raiz != null) {
            if (raiz.getDerecha()== null) {
                raiz = raiz.getIzquierda();
            } else {
                Nodo anterior = raiz;
                temp = raiz.getDerecha();
                while (temp.getDerecha()!= null) {
                    anterior = temp;
                    temp = temp.getDerecha();
                }
                
                anterior.setDerecha(temp.getIzquierda());
            }
        }
        return ("Valor eliminado: " + temp.getDato());
    }
    
    /**
     * Método que retorna un arreglo de enteros con los datos de recorrer el árbol en inOrden
     * @return Listado
     * @throws ArbolBinarioException 
     */ 
    public ArrayList inOrden() throws ArbolBinarioException{
        isLleno();
        ArrayList l=new ArrayList();
        if(raiz != null)
        {
            inOrden(raiz,l);
        }        
        return l;
    }

    private void inOrden(Nodo temp,ArrayList listado) {
        if (temp!= null) {
            inOrden(temp.getIzquierda(),listado);
            listado.add(temp.getDato() + " ");
            inOrden(temp.getDerecha(),listado);
        }
    }
    
    
    /**
     * Método recursivo que recorre el árbol en preorden
     * @param temp Ayudante que toma referencia en un nodo
     * @param listado acumulador para registrar el dato del nodo visitado
     */
    
    private void preOrden(Nodo temp, ArrayList listado)
    {
        //Condición que garantiza que el método finalice
        if(temp!=null)
        {
            listado.add(temp.getDato());
            preOrden(temp.getIzquierda(), listado);
            preOrden(temp.getDerecha(), listado);
        }
    }
    
    /**
     * Método que retorna un arreglo de enteros con los datos de recorrer el árbol en preorden
     * @return Arraylist
     * @throws ArbolBinarioException 
     */
    
    //imprimir lista de números en preorden
    public ArrayList preOrden() throws ArbolBinarioException{
        ArrayList l=new ArrayList();
        if (raiz != null) 
        {
            preOrden(raiz, l);
        }
        return l;
    } 
    /**
     * Método recursivo que recorre el árbol en posorden
     * @param temp Ayudante que toma referencia en un nodo
     * @param listado acumulador para registrar el dato del nodo visitado
     */
    private void posOrden(Nodo temp, ArrayList listado) {
        if (temp != null) {
            posOrden(temp.getIzquierda(),listado);
            posOrden(temp.getDerecha(),listado);
            listado.add(temp.getDato() + " ");
        }
    }
    
    /**
     * Método recursivo que recorre el árbol en posorden
     * @return Arraylist
     * @throws ArbolBinarioException 
     */
    public ArrayList posOrden() throws ArbolBinarioException {
        ArrayList l=new ArrayList();
        if(raiz !=null)
        {
            posOrden(raiz,l);
        }
        return l;
    }
    
    /**
     * 
     * @param reco ayudante que toma la referencia en un nodo
     * @param nivel posición por niveles
     * @param l acumulador para registrar el dato del nodo visitado.
     */

    String[] niveles;

    public int alturaArbol() {
        altura = 0;
        alturaArbol(raiz, 0);
        return altura;
    }

    private void alturaArbol(Nodo pivote, int nivel) {
        if (pivote != null) {
            alturaArbol(pivote.getIzquierda(), nivel + 1);
            if (nivel > altura) {
                altura = nivel;
            }
            alturaArbol(pivote.getDerecha(), nivel + 1);
        }
    }
    
    /**
     * Método que imprime los niveles
     * @param reco ayudante que toma la referencia de un nodo
     * @param nivel posición en el arbol
     * @param l Arrylist
     */
    private void impNiveles(Nodo reco, int nivel,ArrayList l) {
        if (reco != null) {
            impNiveles(reco.getIzquierda(), nivel + 1, l);
            l.add(reco.getDato() + " Nivel: (" + nivel + ") ");
            impNiveles(reco.getDerecha(), nivel + 1, l);
        }
    }
   
     //con nivel
    public ArrayList impNiveles() {
        ArrayList l=new ArrayList();
        impNiveles(raiz, 1,l);
        return l;
    }
    
    public void borrarNivel(){
        
    }
    
    public void llenarArbol(String datos) throws ArbolBinarioException
    {
        String[] arrayDatos= datos.split(",");
        for(String cadena: arrayDatos)
        {
            adicionarNodo(Integer.parseInt(cadena), raiz);
        }
        
    }
    
    //Obtener el numero de ramas
    int numeroRamas = 0;
    public ArrayList<String>ObtenerRamamayor(){
    obtenernumeroRamas(this.raiz, 0);
    return ObtenerRamamayor(this.raiz, 0, "", new ArrayList<String>());
    }
    public void obtenernumeroRamas(Nodo pivote, int contador) {
        if (pivote != null) {
            contador++;
            obtenernumeroRamas(pivote.getIzquierda(), contador);
            obtenernumeroRamas(pivote.getDerecha(), contador);
        }
        if (contador > this.numeroRamas) {
            this.numeroRamas = contador;
        }
    }

     public ArrayList<String> ObtenerRamamayor(Nodo pivote, int contador, String dato, ArrayList lista){
        if (pivote != null ) {
            dato+=pivote.getDato()+",";
            contador ++;
            lista=ObtenerRamamayor(pivote.getIzquierda(), contador, dato, lista);
            lista=ObtenerRamamayor(pivote.getDerecha(), contador, dato, lista);
            
            if (contador == this.numeroRamas) {
                lista.add(dato);
            }
        }
        return lista;
    }
     
    /**
     * Método que muestra las hojas del arbol
     * @return ArrayList
     */
    //hojas
    public ArrayList getHojas()  throws ArbolBinarioException{
        ArrayList l = new ArrayList();
        if(raiz !=null)
        {
            getHojas(this.raiz, l);
        }      
        return (l);
    }

    private void getHojas(Nodo r, ArrayList l) {
        if (r != null) {
            if (this.esHoja(r)) {
                l.add(r.getDato());
            }
            getHojas(r.getIzquierda(), l);
            getHojas(r.getDerecha(), l);
        }

    }
    protected boolean esHoja(Nodo x) {
        return (x != null && x.getIzquierda()== null && x.getDerecha()== null);
    }
    
    //eliminar hojas
     public void podar() {
        podar(this.raiz);
    }

    private void podar(Nodo x) {
        if (x == null) {
            return;
        }
        if (this.esHoja(x.getIzquierda())) {
            x.setIzquierda(null);
        }
        if (this.esHoja(x.getDerecha())) {
            x.setDerecha(null);
        }
        podar(x.getIzquierda());
        podar(x.getDerecha());
    }
    
    //buscar
    public boolean buscar(int x) {
        return (buscar(this.raiz, x));


    }

    private boolean buscar(Nodo r, int x) {
        if (r == null) {
            return (false);
        }
        int compara = ((Comparable) r.getDato()).compareTo(x);
        if (compara > 0) {
            return (buscar(r.getIzquierda(), x));
        } else if (compara < 0) {
            return (buscar(r.getDerecha(), x));
        } else {
            return (true);
        }
    }
    
    //buscar min
    private Nodo buscarMin(Nodo r) {
        for (; r.getIzquierda()!= null; r = r.getIzquierda());
        return (r);
    }
    
    /**
     * Método que imprime niveles por orden
     * @return Listado de niveles en orden
     */
    public ArrayList imprimirNivel() {
        niveles = new String[altura + 1];
        ArrayList l=new ArrayList();
        imprimirNivel(raiz, 0);
        for (int i = 0; i < niveles.length; i++) {
            l.add(niveles[i] + " ");
            //System.out.println(niveles[i] + " ");
        }
        return l;
    }
      public void imprimirNivel(Nodo pivote, int nivel2) {
        if (pivote != null) {
            niveles[nivel2] = pivote.getDato() + ", " + ((niveles[nivel2] != null) ? niveles[nivel2] : "");
            imprimirNivel(pivote.getDerecha(), nivel2 + 1);
            imprimirNivel(pivote.getIzquierda(), nivel2 + 1);
        }
    }
    //Insertar el numero del nivel que se quiere eliminar
    public void borrarNivel(int datoNivel, datoNivel ubicacion){
        niveles = new String[altura + 1];
        // Si el nivel no existe enviar mensaje "El nivel no se encuentra en el árbol"
        if(datoNivel != imprimirNivel() ){
            System.out.println("El nivel no se encuentra en el arbol");
        }
        // Si el nivel si existe 
        else{
            //Raiz debe preguntar a temp si algun nodo en el nivel a eliminar tiene hijos
        }
                //Si no, eliminar todos los nodos con el nivel igual al número ingresado y enviar 
                //mensaje "El nivel ha sido eliminado"
                // Si sí, 
                    //recorrer los niveles hasta llegar al nivel ingresado
                    //llamar a temp para que conecte el nodo menor del nivel mayor al ingresado
                    // y lo conecte con el nodo que contenia el nodo del nivel eliminado
                    // de esta manera los nodos del nivel seleccionado será eliminado                    
                    // al eliminar, enviar mensaje "El nivel ha sido eliminado"
                    
    public void multiplicarArbol(){
      //preguntar si el arbol esta vacio 
      //si esta vacio entonces enviar mensaje "El arbol no tiene datos"
      //si si tiene datos 
      //Ingresar el numero que desea multiplicar
      // llamar a temp para que recorra todos los nodos y los multiplique por el dato ingresado 
      // actualizar el grafico 
      //mostrar los nuevos datos 
    }
    }

}
