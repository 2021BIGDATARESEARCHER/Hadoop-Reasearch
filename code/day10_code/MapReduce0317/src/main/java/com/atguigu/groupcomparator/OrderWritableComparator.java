package com.atguigu.groupcomparator;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OrderWritableComparator  extends WritableComparator {

    public OrderWritableComparator(){
        super(OrderBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean aBean = (OrderBean)a ;
        OrderBean bBean = (OrderBean)b ;

        return aBean.getOrderId().compareTo(bBean.getOrderId()) ;
    }
}
