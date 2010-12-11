/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jloic2;
import java.io.*;
/**
 *
 * @author Administrator
 */
public class Payload {
    String FileHeaderPayload;
    String FileDataPayload;
    String TextPayload;
    byte[] FHPData, FDPData;
    Payload(String FileHeader, String FileData, String Text)
    {
        this.FileHeaderPayload = FileHeader;
        this.FileDataPayload = FileData;
        this.TextPayload = Text;
        FHPData = this.LoadFiles(FileHeaderPayload);
        FDPData = this.LoadFiles(FileDataPayload);
    }
    Payload(Payload pl)
    {
        this.FileHeaderPayload = new String(pl.FileHeaderPayload);
        this.FileDataPayload  = new String(pl.FileDataPayload);
        this.TextPayload = new String(pl.TextPayload);
        FHPData = this.LoadFiles(FileHeaderPayload);
        FDPData = this.LoadFiles(FileDataPayload);
    }
    byte[] LoadFiles(String str)
    {
        byte[] Data = null;
        FileInputStream file_input = null;
        try
        {
            file_input = new FileInputStream (FileHeaderPayload);
        }catch (FileNotFoundException e) {
            return Data;
        }
        DataInputStream data_in    = new DataInputStream (file_input);
        try
        {
            int length = data_in.available();
            Data = new byte[length];
            data_in.read(Data);
            data_in.close();
            file_input.close();
        } catch(IOException e){
        }
        return Data;
    }
}
