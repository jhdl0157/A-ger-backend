package com.ireland.ager.main.common.service;

import com.ireland.ager.main.common.*;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class : ResponseService
 * @Description : 메인 도메인에 대한 서비스
 **/
@Service
public class ResponseService {

    /**
     * @Method : getSingleResult
     * @Description : 단일건 결과 처리
     * @Parameter : [data]
     * @Return : SingleResult<T>
     **/
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    /**
     * @Method : getListResult
     * @Description : 복수건 결과 처리
     * @Parameter : [list]
     * @Return : ListResult<T>
     **/
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    /**
     * @Method : getSliceResult
     * @Description : 페이징 결과 처리
     * @Parameter : [list]
     * @Return : SliceResult<T>
     **/
    public <T> SliceResult<T> getSliceResult(Slice<T> list) {
        SliceResult<T> result = new SliceResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    /**
     * @Method : getSuccessResult
     * @Description : 성공 결과만 처리
     * @Parameter : []
     * @Return : CommonResult
     **/
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    /**
     * @Method : getFailResult
     * @Description : 실패 처리
     * @Parameter : [commonResponse]
     * @Return : CommonResult
     **/
    public CommonResult getFailResult(CommonResponse commonResponse) {
        CommonResult result = new CommonResult();
        setFailResult(commonResponse, result);
        return result;
    }

    /**
     * @Method : setFailResult
     * @Description : 실패 결과 바인딩
     * @Parameter : [commonResponse, result]
     * @Return : null
     **/
    private void setFailResult(CommonResponse commonResponse, CommonResult result) {
        result.setSuccess(false);
        result.setMsg(commonResponse.getMsg());
        result.setCode(commonResponse.getCode());
    }

    /**
     * @Method : setSuccessResult
     * @Description : 결과에 api 요청 성공 데이터 세팅
     * @Parameter : [result]
     * @Return : null
     **/
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}