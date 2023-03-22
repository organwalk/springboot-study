package com.example.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootstudy.entity.Order;
import com.example.springbootstudy.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from t_order where uid = #{uid}")
    @Results(
            {
                    @Result(column = "id", property = "id"),
                    @Result(column = "order_time", property = "order_time", jdbcType = JdbcType.TIMESTAMP),
                    @Result(column = "total", property = "total")
            }
    )//不写这一步会导致该方法查询出的order_time为null
    List<Order> selectByUid(int uid);

    @Select("select * from t_order")
    @Results(
            {
                    @Result(column = "id",property = "id"),
                    @Result(column = "order_time",property = "order_time"),
                    @Result(column = "total",property = "total"),
                    @Result(column = "uid",property = "user" ,javaType = User.class,
                            one = @One(select = "com.example.springbootstudy.mapper.UserMapper.selectById"))
            }
    )
    List<Order> selectAllOrdersAndUser();
}
