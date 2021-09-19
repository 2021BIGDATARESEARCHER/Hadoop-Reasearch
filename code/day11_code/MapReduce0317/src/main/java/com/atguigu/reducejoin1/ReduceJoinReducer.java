package com.atguigu.reducejoin1;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ReduceJoinReducer extends Reducer<Order, NullWritable, Order, NullWritable> {

    private List<Order> orders = new ArrayList<>();
    private Order pdOrder = new Order();

    @Override
    protected void reduce(Order key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //取出当前组中的所有的数据， 将来自于order文件的数据维护到一个集合中，来自于pd文件的维护成一个对象
        for (NullWritable value : values) {
            //判断order是来自于哪个文件的
            if("order".equals(key.getFlag())){
                //来自于order文件
               //将当前order对象中的数据拷贝到另一个order对象中
                Order currentOrder = new Order();
                try {
                    BeanUtils.copyProperties(currentOrder,key);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                orders.add(currentOrder);
            }else{
                //来自于pd文件
                try {
                    BeanUtils.copyProperties(pdOrder,key);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        //循环处理数据，进行join操作
        for (Order order : orders) {
           //将pdOrder中的pname取出，设置到当前的order对象中
           order.setPname(pdOrder.getPname());
           //写出
            context.write(order,NullWritable.get());
        }

        //一组数据处理完成后将集合中的数据清空
        orders.clear();
    }
}
