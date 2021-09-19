package com.atguigu.reducejoin1;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OrderWritableComparator extends WritableComparator {

    public OrderWritableComparator(){
        super(Order.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Order aOrder =(Order)a;
        Order bOrder =(Order)b;
        //分组只需要按照pid进行分组即可.
        return aOrder.getPid().compareTo(bOrder.getPid());
    }
}
