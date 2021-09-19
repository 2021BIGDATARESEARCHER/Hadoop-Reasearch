package com.atguigu.source;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * WordCount程序 Mapper类
 *
 * 自定义的xxxMapper类,需要继承Hadoop提供的Mapper类,并重写map方法
 *
 * 按照当前wordCount程序来分析:
 * 四个泛型: 两组kv对
 * 输入数据的kv类型:
 *  KEYIN   ： LongWritable   表示文件读取数据的偏移量,简单理解为每次从文件的哪个位置开始读取数据.
 *  VALUEIN ： Text   实际从文件中读取的一行数据
 * 输出数据的kv类型:
 *  KEYOUT   : Text, 表示一个单词
 *  VALUEOUT : IntWritable, 表示这个单词出现了1次.
 */
public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    //定义输出的k 和 v
    private Text outk = new Text();
    private IntWritable outv = new IntWritable(1);

    /**
     * Map阶段处理的业务逻辑
     * @param key   KEYIN  输入数据的key
     * @param value VALUEIN  输入数据的value
     * @param context 上下文对象，负责整个Mapper类指定过程的调度.
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1. 获取到输入的一行数据
        // atguigu atguigu hadoop hive
        String line = value.toString();
        //2. 切割
        String[] words  = line.split(" ");
        //3. 将每个单词拼成kv写出
        for (String word : words) {
            //封装k 和 v
            outk.set(word);
            //写出
            context.write(outk,outv);
        }
    }
}
