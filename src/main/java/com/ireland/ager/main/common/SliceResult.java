package com.ireland.ager.main.common;


import lombok.Data;
import org.springframework.data.domain.Slice;

/**
 * @Class : SliceResult
 * @Description : 메인 도메인에 대한 다중 결과값 매핑
 **/
@Data
public class SliceResult<T> extends CommonResult {
    private Slice<T> data;
}