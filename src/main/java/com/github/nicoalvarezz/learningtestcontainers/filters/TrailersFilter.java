package com.github.nicoalvarezz.learningtestcontainers.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TrailersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse servletResponse) {
            // Proceed with the filter chain
            filterChain.doFilter(request, response);

            // Check if the request attribute is set to add trailer fields
            Boolean shouldAddTrailers = (Boolean) request.getAttribute("addTrailers");
            if (shouldAddTrailers != null && shouldAddTrailers) {
                Supplier<Map<String, String>> supplier = () -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("key1", "value1");
                    map.put("key2", "value2");
                    return map;
                };
                servletResponse.setTrailerFields(supplier);
            }
        }
    }
}
