package club.codeqi.project.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Aspect
@Component
public class ControllerAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger("Request请求记录");

    @Pointcut("execution(* club.codeqi.project.Controller.*Controller*.*(..))")
    public void webLogProject(){}

    @Before("webLogProject()")
    public void beginRequest(JoinPoint joinPoint){
        LOGGER.info("--------------------请求开始--------------------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("请求路径:"  + " " + request.getRequestURL().toString() + "  " + request.getMethod() + "\n");
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> paramMap = new HashMap<>();
            parameterMap.forEach((key, value) -> paramMap.put(key, Arrays.stream(value).collect(joining(","))));
            sb.append("请求内容:" + paramMap);
        } else if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(args).forEach(object -> stringBuilder.append(object.toString().replace("=", ":")));
            if (stringBuilder.length() == 0) {
                stringBuilder.append("{}");
            }
            sb.append("请求内容:" + stringBuilder.toString());
        }
        else if (request.getMethod().equalsIgnoreCase(RequestMethod.PUT.name())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(args).forEach(object -> stringBuilder.append(object.toString().replace("=", ":")));
            if (stringBuilder.length() == 0) {
                stringBuilder.append("{}");
            }
            sb.append("请求内容:" + stringBuilder.toString());
        }
        else if (request.getMethod().equalsIgnoreCase(RequestMethod.DELETE.name())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(args).forEach(object -> stringBuilder.append(object.toString().replace("=", ":")));
            if (stringBuilder.length() == 0) {
                stringBuilder.append("{}");
            }
            sb.append("请求内容:" + stringBuilder.toString());
        }
        LOGGER.info(String.valueOf(sb));
    }

    @AfterReturning(pointcut = "webLogProject()", returning = "ret")
    public void endRequest(Object ret) {
        LOGGER.info("请求结果：" + ret.toString());
        LOGGER.info("--------------------请求结束--------------------");
    }

    @Around("webLogProject()")
    public Object serviceAOP(ProceedingJoinPoint pjp) throws Throwable {
        //1.开始
        try {
            // 2、执行时
            Object obj =  pjp.proceed();
//            LOGGER.info("请求结果：" + obj.toString());
//            LOGGER.info("--------------------请求结束--------------------");
            return obj;
        } catch (Throwable e) {
            LOGGER.error(e.toString());// 3、发生异常时
            LOGGER.error("--------------------请求结束--------------------");
            throw e;
        }
    }
}
