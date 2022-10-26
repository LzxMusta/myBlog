package com.lzxmusta.myblog.dao.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * do 对象 数据库 查询出来的对象但是不需要持久化，由于do是关键字所以加了个s成为dos
 */
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Archives {
    private Integer year;
    private Integer month;
    private Long count;
}
