package com.lzxmusta.blogadmin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName Admin
 * @Description TODO
 * @Author lzxmusta
 * @Date 2022/10/17 22:51
 * @Version 1.0
 **/
@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}
