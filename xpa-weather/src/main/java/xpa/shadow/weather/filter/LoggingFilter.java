package xpa.shadow.weather.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ThreadContext.put("id", UUID.randomUUID().toString().substring(0, 8));
        ThreadContext.put("ip", request.getRemoteAddr());
        filterChain.doFilter(request, response);
        ThreadContext.remove("ip");
        ThreadContext.remove("id");
    }
}
