package com.atguigu.groupcomparator;

import org.apache.hadoop.io.WritableComparable;
import org.eclipse.jetty.util.thread.strategy.ProduceConsume;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean  implements WritableComparable<OrderBean> {

    private String orderId; //订单id

    private String productId ; //商品id

    private Double price ; // 商品价格

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 比较规则:
     *     按照订单id升序排序
     *     按照金额降序排序
     */
    @Override
    public int compareTo(OrderBean o) {
        if(this.getOrderId().equals(o.getOrderId())){
            return  -this.getPrice().compareTo(o.getPrice());  // 金额降序
        }else{
            return this.getOrderId().compareTo(o.getOrderId());  //订单id升序
        }
        //return  this.getOrderId().compareTo(o.getOrderId()) ==0? -this.getPrice().compareTo(o.getPrice()): this.getOrderId().compareTo(o.getOrderId());
    }

    public OrderBean(){ }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeUTF(productId);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.productId = in.readUTF();
        this.price = in.readDouble();
    }

    @Override
    public String toString() {
        return  orderId +"\t" + productId + "\t" + price;
    }
}
