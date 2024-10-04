package salaba.domain.global.aspect;

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

    @Pointcut("execution(* salaba.domain.rentalHome.service.RentalHomeService.getRentalHome(..))")
    public void getRentalHome() {}

    @Pointcut("execution(* salaba.domain.reservation.service.ReviewService.getRentalHomeReviews(..))")
    public void getRentalHomeReviews() {}

    @Pointcut("execution(* salaba.domain.rentalHome.service.RentalHomeService.getRentalHomeByHost(..))")
    public void getRentalHomeByHost() {}

    @Pointcut("execution(* salaba.domain.rentalHome.service.RentalHomeService.getRentalHomesByHost(..))")
    public void getRentalHomesByHost() {}

    @Pointcut("execution(* salaba.domain.board.service.BoardService.getBoardList(..))")
    public void getBoardList() {}

    @Pointcut("execution(* salaba.domain.board.service.BoardService.getBoard(..))")
    public void getBoard() {}

    @Pointcut("execution(* salaba.domain.board.service.ReplyService.getRepliesByMember(..))")
    public void getRepliesByMember() {}

    @Pointcut("execution(* salaba.domain.reservation.service.ReservationService.getReservedDate(..))")
    public void getReservedDate() {}

    @Pointcut("execution(* salaba.domain.reservation.service.ReservationService.getWithRentalHomeForHost(..))")
    public void getWithRentalHomeForHost() {}

    @Pointcut("execution(* salaba.domain.reservation.service.ReservationService.getWithRentalHomeForGuest(..))")
    public void getWithRentalHomeForGuest() {}



    // 특정 패키지와 클래스의 메서드들에 적용
    @Around("searchRentalHomesOrderByReview() || searchRentalHomesOrderBySalesCount() || " +
            "getRentalHome() || getRentalHomeReviews() || getRentalHomeByHost() || getRentalHomesByHost() || " +
            "getBoardList() || getBoard() || getRepliesByMember() || " +
            "getReservedDate() || getWithRentalHomeForHost() || getWithRentalHomeForGuest()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        log.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

}
