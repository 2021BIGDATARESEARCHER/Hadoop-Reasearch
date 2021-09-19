package com.atguigu.partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer  extends Reducer<Text, FlowBean,Text, FlowBean> {

    private FlowBean outV  = new FlowBean() ;

    /**
     * 相同key的多个kv对会进入到一个reduce方法
     */
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        int totalUpFlow =0 ;
        int totalDownFlow = 0 ;
        //1. 迭代values,计算当前key的总上行，总下行，总流量
        for (FlowBean value : values) {
            totalUpFlow += value.getUpFlow();
            totalDownFlow += value.getDownFlow();
        }
        //2. 封装输出的value
        outV.setUpFlow(totalUpFlow);
        outV.setDownFlow(totalDownFlow);
        outV.setSumFlow();

        //3. 写出
        context.write(key, outV);
    }
}
