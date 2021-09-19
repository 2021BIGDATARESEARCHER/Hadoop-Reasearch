package com.atguigu.wordcount2;

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
        //设置在集群运行的相关参数
        //设置HDFS NameNode的地址
        conf.set("fs.defaultFS", "hdfs://hadoop102:9820");
        // 指定MapReduce运行在Yarn上
        conf.set("mapreduce.framework.name","yarn");
        // 指定mapreduce可以在远程集群运行
        conf.set("mapreduce.app-submission.cross-platform","true");
        //指定Yarn resourcemanager的位置
        conf.set("yarn.resourcemanager.hostname","hadoop103");
        Job job = Job.getInstance(conf);
        //2.关联jar，设置驱动类
        //job.setJarByClass(WordCountDriver.class);
        job.setJar("D:\\IdeaProjects\\BigData200317\\MapReduce0317\\target\\MapReduce0317-1.0-SNAPSHOT.jar");
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
