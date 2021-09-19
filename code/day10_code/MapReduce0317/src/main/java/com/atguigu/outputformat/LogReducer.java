package com.atguigu.outputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LogReducer extends Reducer<Text, NullWritable,Text,NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //迭代写出，考虑到会有相同的key(有相同的网址信息)
        for (NullWritable value : values) {
            context.write(key,NullWritable.get());
        }
    }
}
