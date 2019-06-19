package com.wolf.dearcc.manager.core.utils;

import com.wolf.dearcc.common.model.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult errorHandler(HttpServletRequest request, Exception e) {

        //UnknownSessionException
//        MethodArgumentNotValidException mothodEx = (MethodArgumentNotValidException)e;
//        if(mothodEx != null)
//        {
//            BindingResult bindingResult = mothodEx.getBindingResult();
//            if (bindingResult.hasErrors()) {
//                List<ObjectError> objectErrors = bindingResult.getAllErrors();
//                StringBuffer errBuff = new StringBuffer();
//                for (ObjectError err : objectErrors) {
//                    errBuff.append(err.getDefaultMessage()+"\n");
//                }
//
//
//
//                return ApiResult.Fail(errBuff.toString());
//            }
//        }

        return ApiResult.Fail(e.getMessage());
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult MethodArgumentNotValidHandler(HttpServletRequest request,
                                                   MethodArgumentNotValidException e) throws Exception
    {
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> objectErrors = bindingResult.getAllErrors();
            StringBuffer errBuff = new StringBuffer();
            for (ObjectError err : objectErrors) {
                errBuff.append(err.getDefaultMessage()+"\n");
            }
            return ApiResult.Fail(errBuff.toString());
        }
        return ApiResult.Fail("全局异常");
    }

//    @ExceptionHandler(value =BindException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiResult validateErrorHandler(BindException e) {
//
//        BindingResult bindingResult = e.getBindingResult();
//        if (bindingResult.hasErrors()) {
//            List<ObjectError> objectErrors = bindingResult.getAllErrors();
//            StringBuffer errBuff = new StringBuffer();
//            for (ObjectError err : objectErrors) {
//                errBuff.append(err.getDefaultMessage()+"\n");
//            }
//            return ApiResult.Fail(errBuff.toString());
//        }
//        return ApiResult.Fail("全局异常");
//    }
}
