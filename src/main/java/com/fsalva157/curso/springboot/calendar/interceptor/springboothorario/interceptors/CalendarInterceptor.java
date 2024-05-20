package com.fsalva157.curso.springboot.calendar.interceptor.springboothorario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("calendarInterceptor")
public class CalendarInterceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Calendar calendar = Calendar.getInstance();
        Integer hora_actual = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println(hora_actual);
        System.out.println(open);
        System.out.println(close);

        if(hora_actual < open || hora_actual > close){     
            System.out.println("no estoy dentro del horario");      
            StringBuilder message = new StringBuilder();
            Map<String, String> data = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            message.append("El horario de atención es de ");
            message.append(open);
            message.append(" a ");
            message.append(close);
            message.append(" horas. Regrese dentro del horario establecido");
            // request.setAttribute("message", message.toString());
            data.put("message", message.toString());
            data.put("date", new Date().toString());


            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(mapper.writeValueAsString(data));


             return false;
         }
         System.out.println("estoy dentro del horario");

        StringBuilder message = new StringBuilder();
        message.append("El horario de atención es de ");
        message.append(open);
        message.append(" a ");
        message.append(close);
        message.append(" horas. Enseguida sera atedido");
        request.setAttribute("message", message.toString());            
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        
    }

   
    
}
