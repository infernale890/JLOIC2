/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jloic2;

/**
 *
 * @author Administrator
 */

public class AttackInformation {
    String Target;
    short  Port;
    short  ThreadsN;
    int    Speed;
    Methodl4 M;
    Payload p;
    boolean Delay;
    boolean ThroughPut;
    boolean Reliability;
    int SendBuffer;
    AttackInformation()
    {
    }
    AttackInformation(String hostname, short Port, short Threads, int Speed, Methodl4 AttkType, Payload p, boolean D, boolean T, boolean R, int Buffer)
    {
        this.Target = new String(hostname);
        this.Port = Port;
        this.ThreadsN = Threads;
        this.Speed = Speed;
        this.M = new Methodl4(AttkType);
        this.p = new Payload(p);
        this.Delay = D;
        this.ThroughPut  = T;
        this.Reliability = R;
        this.SendBuffer = Buffer;
    }
    AttackInformation(AttackInformation AI)
    {
        this.Target = new String(AI.Target);
        this.Port = AI.Port;
        this.ThreadsN = AI.ThreadsN;
        this.Speed = AI.Speed;
        this.M = new Methodl4(AI.M);
        this.p = new Payload(AI.p);
        this.Delay = AI.Delay;
        this.ThroughPut = AI.ThroughPut;
        this.Reliability = AI.Reliability;
        this.SendBuffer = AI.SendBuffer;
    }
}
