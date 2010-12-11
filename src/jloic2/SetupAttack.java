/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jloic2;
import java.util.*;
/**
 *
 * @author Administrator
 */
public class SetupAttack {
    AttackInformation AtkInfo;
    List<AttackRunner> activeThreads = new ArrayList<AttackRunner>();
    IsFiring isf;
    SetupAttack(AttackInformation AtkInfo,  IsFiring isf)
    {
        this.isf = isf;
        this.AtkInfo = new AttackInformation(AtkInfo);
    }
    void Start()
    {
        this.Stop();
        for (int x = 0; x < this.AtkInfo.ThreadsN; x++)
        {
            activeThreads.add(new AttackRunner(this.AtkInfo, isf));
            activeThreads.get(x).setPriority(7);
            activeThreads.get(x).start();
        }
    }
    void Stop()
    {
        for (int x = 0; x < activeThreads.size(); x++)
        {
            activeThreads.get(x).interrupt();
        }
        activeThreads.clear();
    }
}
