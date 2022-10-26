package com.lzxmusta.blogadmin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:22
 * @Version 1.0
 **/
@Data
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String description;
}
