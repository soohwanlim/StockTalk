package com.mycom.myapp.common;

import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println("LoginInterceptor >> preHandle : " + requestURI);

        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("userDto");

        if (userDto == null) {
            if (isApiRequest(request)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"unauthorized\",\"message\":\"로그인이 필요합니다.\"}");
            } else {
                response.sendRedirect(request.getContextPath() + "/pages/login");
            }
            return false;
        }

        return true;
    }

    private boolean isApiRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String requestedWith = request.getHeader("X-Requested-With");
        String uri = request.getRequestURI();

        return uri.startsWith(request.getContextPath() + "/api/")
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(requestedWith);
    }
}
