package com.dhsr.wx.cp.config;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * 统一返回相应参数
 *
 * @author liuxiaogang
 */
public class ResponseHelper {

    public ResponseHelper() {
    }

    public static <T> ResponseModel<T> notFound(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> internalServerError(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> validationFailure(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    /**
     * token验证
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> validationToken(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(405);
        response.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> buildResponseModel(T result) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.OK.value());
        response.setCode(HttpStatus.OK.getReasonPhrase());
        response.setMessage("操作成功");
        response.setResult(result);
        return response;
    }

    public static <T> ResponseModel<T> buildResponseModelList(List<T> result) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.OK.value());
        response.setCode(HttpStatus.OK.getReasonPhrase());
        response.setMessage("操作成功");
        response.setResult(result);
        return response;
    }

    /**
     * 返回数据总数
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseModelListPage<T> buildResponseModelPageList(List<T> result, int total) {
        ResponseModelListPage response = new ResponseModelListPage();
        response.setStatus(HttpStatus.OK.value());
        response.setCode(HttpStatus.OK.getReasonPhrase());
        response.setMessage("操作成功");
        response.setResult(result);
        response.setTotal(total);
        return response;
    }

    public static <T> ResponseModel<T> buildResponseModelInsert(Map<String, Object> mso) {
        ResponseModel response = new ResponseModel();


        // if(mso.containsKey("state")&&Integer.valueOf(mso.get("state").toString()) > 0){

        response.setStatus(HttpStatus.OK.value());
        response.setCode(HttpStatus.OK.getReasonPhrase());
        response.setMessage("操作成功");
      /*  }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setMessage(mso.get("msg").toString());
        }*/
        return response;
    }
}
