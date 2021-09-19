package com.atguigu.mapsort2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 比较器对象
 */
public class FlowComparator  extends WritableComparator {

    public FlowComparator(){
        super(FlowBean.class,true);
    }

    /*
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        //先强制转换
        FlowBean abean = (FlowBean)a ;
        FlowBean bbean = (FlowBean)b ;
        //比较规则  ： 按照总流量进行升序排序
        // 直接利用Integer类型的比较规则进行比较
        return abean.getSumFlow().compareTo(bbean.getSumFlow());
    }
    */
}
