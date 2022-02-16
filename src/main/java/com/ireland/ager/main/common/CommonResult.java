package com.ireland.ager.main.common;


import lombok.Data;

/**
 * @Class : CommonResult
 * @Description : 메인 도메인에 대한 결과 매핑
 **/
@Data
public class CommonResult {

    private boolean success;

    private int code;

    private String msg;
}