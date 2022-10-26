package com.lzxmusta.blogadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzxmusta.blogadmin.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName AdminMapper
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:54
 * @Version 1.0
 **/
@Mapper
public interface AdminMapper  extends BaseMapper<Admin> {
}
