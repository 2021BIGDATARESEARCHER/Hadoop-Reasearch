package com.atguigu.groupcomparator;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OrderReducer extends Reducer<OrderBean , NullWritable,OrderBean,NullWritable> {

    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //去当前kv组中的第一条数据即可
        //context.write(key,NullWritable.get());

        double currentPrice = 0;
        int i = 0 ;
        //如果考虑到一个订单中有多个相同金额的商品
        for (NullWritable value : values) {
            if(i == 0){
                i++ ;
                //先记录第一个商品的金额
                currentPrice = key.getPrice() ;
                System.err.println("第一条数据:" + key);
                context.write(key,NullWritable.get());
            }else{
                if(key.getPrice()!=currentPrice){
                    return ;
                }else{
                    System.err.println("后续的数据:" + key);
                    context.write(key,NullWritable.get());
                }
            }
        }
    }
}
