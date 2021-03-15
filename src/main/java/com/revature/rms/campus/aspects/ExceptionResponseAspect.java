package com.revature.rms.campus.aspects;

import com.revature.rms.core.aspects.CoreExceptionResponseAspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class ExceptionResponseAspect extends CoreExceptionResponseAspect {
}
