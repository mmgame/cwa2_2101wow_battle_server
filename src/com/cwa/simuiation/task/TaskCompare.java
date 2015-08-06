package com.cwa.simuiation.task;

import java.util.Comparator;
/**
 * task优先级比较器
 * @author tzy
 *
 */
public class TaskCompare implements Comparator<ITask>{

	@Override
	public int compare(ITask o1, ITask o2) {
	       long numbera = o1.getExcuteTime();
           long numberb = o2.getExcuteTime();
           if(numberb > numbera)  
           {  
               return 1;  
           }  
           else if(numberb<numbera)  
           {  
               return -1;  
           }  
           else  
           {  
               return 0;  
           }
	}
}
