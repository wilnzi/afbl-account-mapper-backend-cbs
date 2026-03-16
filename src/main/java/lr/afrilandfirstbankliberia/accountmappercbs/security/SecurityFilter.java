package lr.afrilandfirstbankliberia.accountmappercbs.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SecurityFilter implements Filter {

    @Value("${security.listOfAcceptedAddress}")
    private String listOfAcceptedAddress ;

    private List<String> allowedIps;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedIps = Arrays.asList(listOfAcceptedAddress.split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String remoteIp = httpRequest.getRemoteAddr();

        if (allowedIps.contains(remoteIp)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Forbidden");
            httpResponse.getWriter().flush();

        }
    }

    @Override
    public void destroy() {
    }
}
