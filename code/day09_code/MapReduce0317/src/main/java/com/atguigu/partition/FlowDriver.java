package com.atguigu.partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowDriver {
    public static void main(String[] args) throws Exception {
        //1. 获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2. 关联jar
        job.setJarByClass(FlowDriver.class);
        //3. 关联Mapper 和 Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        //4. 设置Map输出的key 和 value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //5. 设置最终输出的key 和 value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //设置分区器类
        job.setPartitionerClass(PhoneNumPartitioner.class);
        //设置reduce的个数  按照分区器的业务逻辑来进行设置
        job.setNumReduceTasks(6);

        //6. 设置输入和输出路径
        FileInputFormat.setInputPaths(job,new Path("D:\\input\\inputflow"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\output3"));
        //7. 提交Job
        job.waitForCompletion(true);
    }
}
