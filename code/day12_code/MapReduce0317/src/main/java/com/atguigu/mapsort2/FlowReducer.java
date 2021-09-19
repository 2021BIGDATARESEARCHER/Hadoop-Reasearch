package com.atguigu.mapsort2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer  extends Reducer<FlowBean,Text, Text, FlowBean> {

    /**
     * 相同key的多个kv对会进入到一个reduce方法
     */
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //迭代直接将数据写出
        for (Text value : values) {

            context.write(value,key);
        }
    }
}
