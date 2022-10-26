package com.pdf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pc
 * @date Create in  2022/10/26
 */
@Data
@AllArgsConstructor
public class User {

    private String userName;

    private String seqId;


    private String grade;
}
