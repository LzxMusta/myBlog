package com.lzxmusta.blogadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzxmusta.blogadmin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName PermissionMapper
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:27
 * @Version 1.0
 **/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionsByAdminId(Long adminId);
}
