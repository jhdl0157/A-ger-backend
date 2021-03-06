package com.ireland.ager.main.common;

import lombok.Data;

import java.util.List;

/**
 * @Class : ListResult
 * @Description : 메인 도메인에 대한 리스트 결과값 매핑
 **/
@Data
public class ListResult<T> extends CommonResult {
    private List<T> data;
}
