package com.atguigu.wordcount1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * WordCount程序的 Driver类
 */
    public class WordCountDriver {

    public static void main(String[] args) throws Exception {
        //1.获取Job对象
        Configuration conf = new Configuration();
        // 指定提交的队列
        conf.set("mapreduce.job.queuename","hive");
        Job job = Job.getInstance(conf);
        //2.关联jar，设置驱动类
        job.setJarByClass(WordCountDriver.class);
        //3.关联Mapper 和 Reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //4.设置Mapper 输出的key 和 value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终输出的key 和 value的类型
        //  如果有reducer，就写reducer输出的kv类型，如果没有reducer，就写mapper输出的kv类型.
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //6.设置输入和输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //7.提交Job
        job.waitForCompletion(true);
    }
}
