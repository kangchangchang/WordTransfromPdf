package com.pdf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pc
 * @date Create in  2022/10/26
 */
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class SubjectResult {
    /**
     * 课程
     */
    private String  subject;
    /**
     * 老师
     */
    private String  teacher;
    /**
     * 分数
     */
    private int score;
}
