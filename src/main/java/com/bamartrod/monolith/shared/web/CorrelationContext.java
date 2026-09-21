package com.bamartrod.monolith.shared.web;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Context holder for CorrelationContext — resolves correlation id from headers.
 *
 * @author Brandon Martinez
 */
public final class CorrelationContext {
    private CorrelationContext(){}
    public static CorrelationId resolve(String header){
        if(header!=null && !header.isBlank()) return CorrelationId.of(header);
        try{
            var attrs=RequestContextHolder.getRequestAttributes();
            if(attrs instanceof ServletRequestAttributes sra){
                HttpServletRequest req=sra.getRequest();
                Object attr=req.getAttribute(com.bamartrod.monolith.platform.web.CorrelationFilter.ATTRIBUTE);
                if(attr instanceof CorrelationId cid) return cid;
                // fallback to legacy filter attribute type
                Object legacy=req.getAttribute("correlationId");
                if(legacy instanceof CorrelationId lc) return lc;
            }
        }catch(Exception ignored){}
        return CorrelationId.generate();
    }
}
