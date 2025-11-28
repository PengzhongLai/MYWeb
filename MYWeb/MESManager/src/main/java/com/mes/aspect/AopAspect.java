//
//package com.mes.aspect;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import javax.servlet.http.HttpServletRequest;
//
//@Aspect //标注切面类
//@Component
//public class AopAspect {
//
//@Before("execution(* com.mes.controller.*.*(..))")
//  public void  aopBefore()throws Exception{//切面方法
//    //获取request属性
//  RequestAttributes requestAttributes=
//          RequestContextHolder.getRequestAttributes();
//  //使用ServletRequestAttributes将requestAttributes转化为request对象
//  HttpServletRequest request=
//          ((ServletRequestAttributes)requestAttributes).getRequest();
//  System.out.println("==已拦截到发起的请求接口===");
//   String method=request.getMethod();
//  System.out.println("==拦截的接口请求方法："+method);
//  String servletPath=request.getServletPath();
//  System.out.println("==拦截到的接口请求路径："+servletPath);
//  String loginId=request.getHeader("loginId");
//  System.out.println("==拦截到Header中封装的loginId参数为："+loginId);
//  if(loginId!=null && !"".equals(loginId)){
//    if("root".equals(loginId)&&"/user/query-user-info".equals(servletPath)){
//      throw  new RuntimeException("账号没有【/user/query-user-info】接口的访问权限");
//    }else {
//      System.out.println(loginId+"账号有权限，跳过拦截， 正常执行！");
//    }
//  }else {
//    throw  new RuntimeException("接口已被拦截，检测到header 中没有封装loginId参数或loginId为空！");
//  }
//
//
//}
//}
