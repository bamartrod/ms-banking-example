package com.bamartrod.monolith.platform.web;

import com.bamartrod.monolith.platform.kernel.CorrelationId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * Servlet filter for CorrelationFilter — handles cross-cutting web concerns (correlation id).
 *
 * @author Brandon Martinez
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class CorrelationFilter extends OncePerRequestFilter {
    public static final String HEADER="X-Correlation-Id";
    public static final String ATTRIBUTE="correlationId";
    @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String h=req.getHeader(HEADER);
        CorrelationId cid=(h!=null && !h.isBlank())?CorrelationId.of(h):CorrelationId.generate();
        req.setAttribute(ATTRIBUTE,cid);
        res.setHeader(HEADER,cid.value());
        long start=System.nanoTime();
        try{ chain.doFilter(req,res);} finally {
            long ms=(System.nanoTime()-start)/1_000_000;
            if(res.getStatus()>=400) org.slf4j.LoggerFactory.getLogger(CorrelationFilter.class).warn("request status={} correlation={} ms={} path={}",res.getStatus(),cid.value(),ms,req.getRequestURI());
        }
    }
}
