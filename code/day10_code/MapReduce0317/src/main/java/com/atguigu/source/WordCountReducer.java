package com.atguigu.source;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * WordCount程序的 Reducer类
 *
 * 自定义的xxxReducer类需要继承Hadoop提供的Reducer类，重写reduce方法
 *
 * 按照当前wordCount程序来分析:
 * 4个泛型 : 两组kv对
 * 输入数据的kv类型:  Reducer输入数据的kv对象要对应Mapper输出数据的kv类型
 *   KEYIN   :  Text    表示一个单词
 *   VALUEIN :  IntWritable 表示该单词出现了1次
 * 输出数据的kv类型:
 *   KEYOUT  :  Text    表示一个单词
 *   VALUEOUT:  IntWritable  表示该单词总共出现的次数
 *
 */
public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    //定义输出的v
    private IntWritable outv = new IntWritable();

    /**
     * Reduce阶段的业务逻辑处理
     * @param key   KEYIN , 表示一个单词
     * @param values  迭代器对象, 表示一个相同的单词(相同key)出现的次数的封装. 能够表明当前的单词出现了多少次.
     * @param context 上下文对象， 负责Reducer类执行过程的调度
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum  = 0 ;
        // 1. 将当前的单词出现的次数进行汇总.
        for (IntWritable value : values) {
            sum += value.get();
        }
        //2. 封装 k 和 v
        outv.set(sum);
        //3. 写出
        context.write(key,outv);
    }
}
