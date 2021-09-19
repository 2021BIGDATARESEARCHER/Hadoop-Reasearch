package com.atguigu.partition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text, Text , FlowBean> {

    //输出的key
    private   Text outK = new Text();
    //输出的value
    private FlowBean outV = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1. 获取一行数据
        //  1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
        String line = value.toString();

        //2.切割数据
        String[] splits = line.split("\t");

        //3. 封装输出的key
        outK.set(splits[1]);

        //4. 封装输出的value
        outV.setUpFlow(Integer.parseInt(splits[splits.length-3]));  //上行
        outV.setDownFlow(Integer.parseInt(splits[splits.length-2]));//下行
        outV.setSumFlow();

        //5. 写出
        context.write(outK,outV);

    }
    
}
