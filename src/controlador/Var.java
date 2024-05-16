/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import clases.ListaCircularDoblementeLigada;

/**
 *
 * @author Alfred
 */

public class Var
{
    private static ListaCircularDoblementeLigada lista = new ListaCircularDoblementeLigada();

    /**
     * @return the lista
     */
    public static ListaCircularDoblementeLigada getLista()
    {
        return lista;
    }

    /**
     * @param aLista the lista to set
     */
    public static void setLista(ListaCircularDoblementeLigada aLista)
    {
        lista = aLista;
    }
    
    
    
}
