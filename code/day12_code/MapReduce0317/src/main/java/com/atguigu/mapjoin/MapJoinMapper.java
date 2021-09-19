package com.atguigu.mapjoin;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 大概思想:  将小表的数据提前维护到内存中，
 *           Mapper正常读取大表的数据，每读取一条就跟内存中小表的数据进行join，
 *           join后直接将数据写到磁盘即可。
 */
public class MapJoinMapper  extends Mapper<LongWritable, Text,Text, NullWritable> {


    private Map<String,String> pdMap = new HashMap<>();

    private Text outK   = new Text();

    /**
     * 在处理大表数据之前，将小表的数据维护到内存中
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        // 计数器
        context.getCounter("MapJoin","setup").increment(1); //步长

        // 简单实现:  通过流直接读取小表的数据   new FileInputStream(new File("d:/input/inputcache/pd.txt"));
        // 加载缓存文件
        URI[] cacheFiles = context.getCacheFiles();
        // 从数据中获取到缓存文件
        URI cacheFile = cacheFiles[0];
        // 获取输入流
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream in = fs.open(new Path(cacheFile));
        // 按行读取文件内容
        //in.readLine();  //方法过时
        //构造一个高级流
        BufferedReader br = new BufferedReader(
                new InputStreamReader(in,"utf8")
        );
        String line ;
        while((line = br.readLine())!=null){
            //切割一行数据
            //01	小米
            String[] split = line.split("\t");
            pdMap.put(split[0],split[1]);
        }
        // pdMap :  01-小米   02-华为   03-格力
        //关闭
        IOUtils.closeStream(br);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //计数器
        context.getCounter("MapJoin","map").increment(1);

        //读取大表的数据
        // 1001	01	1
        String line = value.toString();
        //切割
        String[] split = line.split("\t");
        //到pdMap中获取Pname
        split[1] = pdMap.get(split[1]);
        //将数组中的数据转换成字符串
        String result = split[0] + "\t" + split[1]+ "\t"+split[2] ;
        //封装outK
        outK.set(result);
        //写出
        context.write(outK,NullWritable.get());
    }
}
