package com.atguigu.reduceJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;


public class ReduceJoinMapper extends Mapper<LongWritable, Text , Text,Order> {

    private FileSplit currentSplit;

    private Text outK = new Text();
    private Order outV= new  Order();


    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取当前的切片对象
        InputSplit inputSplit = context.getInputSplit();
        //转换成FileSplit
        currentSplit = (FileSplit)inputSplit ;
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取一条数据
        String line = value.toString() ;
        // 切割数据
        String[] split = line.split("\t");
        // 明确当前数据来自于哪个文件
        // 直观的方式: 判断split数组的长度. 但是如果两个文件的字段数一样的情况判断不了
        // 正确的方式: 通过当前的切片对象来处理.
        // 封装Order对象
        if(currentSplit.getPath().getName().contains("order")){
            //来自于order.txt
            //1001	01	1
            outK.set(split[1]);
            outV.setOrderId(split[0]);
            outV.setPid(split[1]);
            outV.setAmount(Integer.parseInt(split[2]));
            outV.setPname("");
            outV.setFlag("order");
        }else{
            //来自于pd.txt
            // 01	小米
            outK.set(split[0]);
            outV.setOrderId("");
            outV.setPid(split[0]);
            outV.setAmount(0);
            outV.setPname(split[1]);
            outV.setFlag("pd");
        }
        //写出
        context.write(outK,outV);

    }
}
