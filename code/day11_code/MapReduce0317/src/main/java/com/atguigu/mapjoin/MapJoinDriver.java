package com.atguigu.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MapJoinDriver {
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job  = Job.getInstance(conf);
        //在driver中设置缓存数据,这么写更加灵活，可以通过main方法的参数动态传值。
        //可设置多个缓存文件
        job.addCacheFile(new URI("file:///d:/input/inputcache/pd.txt"));
        // job.addCacheFile(new URI("file://"+args[0]));
        job.setJarByClass(MapJoinDriver.class);
        job.setMapperClass(MapJoinMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置Reduce的个数为0
        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job,new Path("D:\\input\\inputtable2"));
        FileOutputFormat.setOutputPath(job,new Path("D:/output1"));

        job.waitForCompletion(true);
    }
}
