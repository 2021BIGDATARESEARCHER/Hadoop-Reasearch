package com.atguigu.mapsort3;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 用于封装每个手机号的 上行流量  下行流量  总流量
 */
public class FlowBean implements WritableComparable<FlowBean> {

    private Integer upFlow ;   // 上行流量

    private Integer downFlow ; // 下行流量

    private Integer sumFlow ;  // 总流量

    public Integer getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Integer upFlow) {
        this.upFlow = upFlow;
    }

    public Integer getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Integer downFlow) {
        this.downFlow = downFlow;
    }

    public Integer getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(Integer sumFlow) {
        this.sumFlow = sumFlow;
    }

    public void setSumFlow(){
        setSumFlow(getUpFlow() + getDownFlow());
    }


    public FlowBean(){}

    /**
     * 序列化方法
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(upFlow);
        out.writeInt(downFlow);
        out.writeInt(sumFlow);
    }

    /**
     * 反序列化方法
     *
     * 注意: 反序列的顺序要与序列化的顺序一致.
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.upFlow = in.readInt();
        this.downFlow = in.readInt();
        this.sumFlow = in.readInt();
    }

    @Override
    public String toString() {
        return  this.upFlow + "\t" + this.downFlow +"\t" + this.sumFlow ;
    }

    /**
     * 比较规则
     *
     * 按照总流量倒叙排序
     *
     */
    @Override
    public int compareTo(FlowBean o) {
        return -this.getSumFlow().compareTo(o.getSumFlow()) ;
    }
}
