package com.jeeplus.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeeplus.common.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Controller
public class GlobalErrorController extends AbstractErrorController {

    Log log = LogFactory.getLog(ErrorController.class);
    @Autowired
    ObjectMapper objectMapper;

    public GlobalErrorController() {
        super(new DefaultErrorAttributes());
    }

    @ExceptionHandler(RuntimeException.class) //拦截所有运行时的全局异常
    @RequestMapping("/error")
    public ModelAndView getErrorPath(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        Map<String, Object> model =
                Collections.unmodifiableMap(getErrorAttributes(
                        request, false));
        //获取异常，有可能为空
        Throwable cause = e.getCause();
        int status = (Integer) model.get("status"); //错误信 息
        String message = (String) model.get("message"); //友好提示
        String errorMessage = getErrorMessage(status, cause); //后 台打印日志信息 方便查错
        if(status == 999){
            status = 500;
        }
        if(status != 404){
            e.printStackTrace();
        }
        if(StringUtils.isNotBlank(message) && !"No message available".equals(message)){
            errorMessage = message;
        }

        if(errorMessage.toUpperCase().startsWith("SUBJECT DOES NOT")){
            errorMessage = "没有权限，你可以尝试退出重新登录或者清理浏览器cookie,错误信息如下:<br/>" + errorMessage;
        }

        response.setStatus(status);
        if (!isJsonRequest(request)) {
            ModelAndView view = new ModelAndView("/error/exception");
            view.addAllObjects(model);
            view.addObject("errorMessage", errorMessage);
            view.addObject("status", status);
            view.addObject("cause", cause);
            return view;
        } else {
            Map error = new HashMap();
            error.put("success", false);
            error.put("errorMessage", errorMessage);
            error.put("message", e);
            writeJson(response,status, error);
            return null;
        }
    }


    protected void writeJson(HttpServletResponse response, int status, Map error) throws Exception {
        response.reset();
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(error);
    }

    protected Throwable getCause(HttpServletRequest request) {
        Throwable error =
                (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (error != null) {
        //阳C 有可能会封装异常成 ServletExcept工on，需妥调用 getCause 获取真正的异 常
            while (error instanceof ServletException && error.getCause() != null) {
                error = ((ServletException) error).getCause();
            }
        }
        return error;
    }


    protected String getErrorMessage(int status,Throwable ex) {
        if(status == 404){
            return "页面好像去火星了~";
        }else {
             if(ex!=null  && ex.getMessage()!=null ){
                return "服务器错误:<br/>"+ex.getMessage();
            }else {
                return "服务器错误:<br/>未知错误!";
            }

        }

    }


    protected boolean isJsonRequest(HttpServletRequest request) {
         String header =request.getHeader("x-requested-with");
        if (header != null && header.equals("XMLHttpRequest")) {
            return true;
        } else {
            //也可以通过获取 HTTP 头，根据 Accept 字段是否包含 JSON 来进一步判断， 比如 II request.getHeader(”Accept”).contains(”application/json”)
            return false;
        }
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}