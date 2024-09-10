package salaba.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeLoggerAspect {
    @Pointcut("execution(* salaba.domain.rentalHome.service.RentalHomeService.searchRentalHomesOrderByReview(..))")
    public void searchRentalHomesOrderByReview() {}

    @Pointcut("execution(* salaba.domain.rentalHome.service.RentalHomeService.searchRentalHomesOrderBySalesCount(..))")
    public void searchRentalHomesOrderBySalesCount() {}


    // 특정 패키지와 클래스의 메서드들에 적용
    @Around("searchRentalHomesOrderByReview() || searchRentalHomesOrderBySalesCount()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        log.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

}
