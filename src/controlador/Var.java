/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import clases.Multilista;

/**
 *
 * @author Alfred
 */

public class Var
{
    private static Multilista multilista = new Multilista();
    /**
     * @return the multilista
     */
    public static Multilista getMultilista()
    {
        return multilista;
    }

    /**
     * @param aMultilista the multilista to set
     */
    public static void setMultilista(Multilista aMultilista)
    {
        multilista = aMultilista;
    }    
}
