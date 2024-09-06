package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Point;
import salaba.domain.member.repository.PointRepository;
import salaba.domain.reservation.entity.Discount;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {
    private final PointRepository pointRepository;

    public void createBoardPoint(Member writer) {
        Point boardPoint = Point.createBoardPoint(writer);
        pointRepository.save(boardPoint);
    }

    public void createReplyPoint(Member writer) {
        Point replyPoint = Point.createReplyPoint(writer);
        pointRepository.save(replyPoint);
    }

    public void createReviewPoint(Member writer) {
        Point reviewPoint = Point.createReviewPoint(writer);
        pointRepository.save(reviewPoint);
    }

    public void createPaymentPoint(Member payer, int price) {
        Point paymentPoint = Point.createPaymentPoint(payer, price);
        pointRepository.save(paymentPoint);
    }

    public void createUsedPoint(Member user, Discount discount) {
        Point usedPoint = Point.createUsedPoint(discount.getContent(),
                discount.getAmount(), user);
        pointRepository.save(usedPoint);
    }

    public Page<Point> getPointHistory(Member member, Pageable pageable) {
        return pointRepository.findByMember(member, pageable);
    }

    public int getTotalPoint(Long memberId) {
        return pointRepository.getTotalPoint(memberId);
    }
}
