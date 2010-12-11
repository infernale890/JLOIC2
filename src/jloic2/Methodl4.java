/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jloic2;

/**
 *
 * @author Administrator
 */
public class Methodl4 {
    public enum Method
    {
        TCP, UDP
    };
    public Method m;
    Methodl4(String str)
    {
        if (str.toLowerCase().compareTo("udp") == 0)
            m = Method.UDP;
        else
            m = Method.TCP;
    }
    Methodl4(Methodl4 ml)
    {
        this.m = ml.m;
    }
}
