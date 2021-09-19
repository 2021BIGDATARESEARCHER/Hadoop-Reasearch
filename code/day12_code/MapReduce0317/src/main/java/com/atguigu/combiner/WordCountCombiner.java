package com.atguigu.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Combiner的父类也是Reducer类. 但是Combiner是运行在MapTask中的。
 */
public class WordCountCombiner extends Reducer<Text, IntWritable,Text, IntWritable> {

    private IntWritable outV = new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0 ;
        for (IntWritable value : values) {
            sum+=value.get();
        }

        //封装value
        outV.set(sum);

        //写出
        context.write(key,outV);
    }
}
