package com.atguigu.compression;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import java.io.*;

public class TestCompress {

    /**
     * 压缩:将数据通过支持压缩的流写出.
     */
    @Test
    public void testCompress() throws IOException, ClassNotFoundException {
        //待压缩的文件 D:\input\inputWord\JaneEyre.txt
        //压缩后的文件: D:\input\inputWord\JaneEyre.txt.deflate
        //输入流读取待压缩的数据
        FileInputStream in =
                new FileInputStream(new File("D:\\input\\inputWord\\JaneEyre.txt"));
        //获取压缩对应的编解码器
        String codecClassName  = "org.apache.hadoop.io.compress.DefaultCodec" ;
        Class<?> codecClass = Class.forName(codecClassName);
        Configuration conf = new Configuration();
        CompressionCodec codec  = (CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);

        //输出流写出数据(压缩)
        FileOutputStream out = 
                new FileOutputStream(new File("D:\\input\\inputWord\\JaneEyre.txt" + codec.getDefaultExtension()));
        CompressionOutputStream codecOut  = codec.createOutputStream(out);

        //流的对拷
        IOUtils.copyBytes(in,codecOut,conf);

        //流的关闭
        IOUtils.closeStream(in);
        IOUtils.closeStream(codecOut);

    }


    /**
     * 解压缩: 将压缩的数据通过支持解压缩的流读入.
     */
    @Test
    public void testDeCompress() throws IOException {
        //待解压的文件: D:\input\inputWord\JaneEyre.txt.deflate
        //解压后的文件: D:\input\inputWord\JaneEyre.txt

        //获取编解码器
        Configuration conf  = new Configuration( );
        CompressionCodec codec =
                new CompressionCodecFactory(conf).getCodec(new Path("D:\\input\\inputWord\\JaneEyre.txt.deflate"));
        //输入流读取数据(解压缩)
        FileInputStream in =
                new FileInputStream(new File("D:\\input\\inputWord\\JaneEyre.txt.deflate"));
        CompressionInputStream codecIn = codec.createInputStream(in);

        //输出流写出数据
        FileOutputStream out =
                new FileOutputStream(new File("D:\\input\\inputWord\\JaneEyre.txt"));
        //流的拷贝
        IOUtils.copyBytes(codecIn,out,conf);
        //关闭
        IOUtils.closeStream(codecIn);
        IOUtils.closeStream(out);
    }
}
