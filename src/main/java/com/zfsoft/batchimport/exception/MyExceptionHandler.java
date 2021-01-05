package com.zfsoft.batchimport.exception;

import com.zfsoft.batchimport.base.ErrorCode;
import com.zfsoft.batchimport.base.ResModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
    
    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResModel runtimeExceptionHandler(RuntimeException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(2000, null);
    }  

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResModel nullPointerExceptionHandler(NullPointerException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(2001, null);
    }   
    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResModel classCastExceptionHandler(ClassCastException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(2002, null);  
    }  

    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResModel iOExceptionHandler(IOException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(2003, null); 
    }  
    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResModel noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(2004, null);
    }  

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResModel indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(2005, null);
    }
    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResModel requestNotReadable(HttpMessageNotReadableException ex){
        System.out.println("400..requestNotReadable");
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(400, null);
    }
    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResModel requestTypeMismatch(TypeMismatchException ex){
        System.out.println("400..TypeMismatchException");
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(400, null);
    }
    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResModel requestMissingServletRequest(MissingServletRequestParameterException ex){
        System.out.println("400..MissingServletRequest");
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(400, null);
    }
    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResModel request405(HttpRequestMethodNotSupportedException ex){
        System.out.println("405...");
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(405, null);
    }
    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResModel request406(HttpMediaTypeNotAcceptableException ex){
        System.out.println("404...");
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(406, null);
    }
    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class,HttpMessageNotWritableException.class})
    @ResponseBody
    public ResModel server500(RuntimeException ex){
        System.out.println("500...");
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(406, null);
    }
    
    /** 
     * 参数check异常
     * @param ex 异常内容
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResModel baseExceptionHandler(BaseException ex) {
        logger.error(ex.toString(), ex);
        return ErrorCode.retParam(ex.getMessage());
    }
    
    /** 
     * 输出错误信息
     * @param ex 异常内容
     */
    @ExceptionHandler(BaseMsgException.class)
    @ResponseBody
    public ResModel baseMsgExceptionHandler(BaseMsgException ex) {
        logger.error(ex.toString(), ex);
        return ResModel.fail("500",ex.getMessage());
    }
    
    /** 
     * 参数check异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResModel baseExceptionHandler(IllegalArgumentException ex) {
        logger.error(ex.toString(), ex);
        return ResModel.fail(ErrorCode.PARAM_NULL_ERROR, ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResModel handleException(Exception e){
        logger.error(e.toString(), e);
        BindingResult result = null;
        if (e instanceof MethodArgumentNotValidException){
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException){
            result = ((BindException) e).getBindingResult();
        } else if (e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<?> violation : constraintViolations) {
                errorMsg.append(violation.getMessage());
                break;
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return ResModel.fail(ErrorCode.PARAM_NULL_ERROR,errorMsg.toString());
        }
        if (result != null) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(",");
                break;
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return ResModel.fail(ErrorCode.PARAM_NULL_ERROR,errorMsg.toString());
        }
     
        return ResModel.fail(ErrorCode.PARAM_NULL_ERROR, e.getMessage());
    }

}
