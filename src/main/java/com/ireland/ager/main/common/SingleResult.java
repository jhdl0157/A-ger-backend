package com.ireland.ager.main.common;

import lombok.Data;

/**
 * @Class : SingleResult
 * @Description : 메인 도메인에 대한 단일 결과값 매핑
 **/
@Data
public class SingleResult<T> extends CommonResult {
    private T data;
}