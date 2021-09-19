package com.atguigu.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;


/**
 * 自定义LogRecordWriter对象需要继承RecordWriter类
 *
 * 需求:
 *     将包含"atguigu"的日志数据写到   d:/atguigu.log
 *     将不包含"atguigu"的日志数据写到 d:/other.log
 */
public class LogRecordWriter  extends RecordWriter {

    private String  atguiguPath ="d:/atguigu.log";
    private String  otherPath = "d:/other.log";

    private FSDataOutputStream atguiguOut ;
    private FSDataOutputStream  otherOut;
    private FileSystem fs  ;

    public LogRecordWriter(Configuration conf ){
        try {
            fs = FileSystem.get(conf);
            atguiguOut = fs.create(new Path(atguiguPath));
            otherOut = fs.create(new Path(otherPath)) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void write(Object key, Object value) throws IOException, InterruptedException {
        //将key中的日志数据转换成普通字符串
        String log  = key.toString();
        if(log.contains("atguigu")){
            atguiguOut.writeBytes(log+"\n");
        }else{
            otherOut.writeBytes(log+"\n");
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        //关流
        IOUtils.closeStream(atguiguOut);
        IOUtils.closeStream(otherOut);
    }
}
