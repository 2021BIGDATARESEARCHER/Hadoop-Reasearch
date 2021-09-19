package com.atguigu.reducejoin1;


import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Order  implements WritableComparable<Order> {

    private String orderId ;

    private String pid ;

    private Integer amount ;

    private String  pname ;

    private String  flag ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Order(){}

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeUTF(pid);
        out.writeInt(amount);
        out.writeUTF(pname);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF() ;
        this.pid = in.readUTF() ;
        this.amount = in.readInt();
        this.pname = in.readUTF();
        this.flag  = in.readUTF();
    }

    @Override
    public String toString() {
        return  this.orderId + "\t" + this.pname +"\t" + this.amount;
    }

    /**
     * 排序:    按照pid升序，
     *         按照orderid升序
     */
    @Override
    public int compareTo(Order o) {
        if(this.getPid().compareTo(o.getPid())==0){
            return this.getOrderId().compareTo(o.getOrderId());
        }else{
            return this.getPid().compareTo(o.getPid());
        }
    }

    /**
     *   整体思路:
     *     map端处理数据的时候排序时 通过pid和order排序，这样的话，排好序的数据会是pid升序，pid相同的时候，orderId升序.
     *
     *     数据进入到reduce的时候, 要按照pid进行分组操作. 提供了分组比较器，只比较pid,用于分组操作.
     */
}
