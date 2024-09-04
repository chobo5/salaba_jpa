package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.request.PaymentReqDto;
import salaba.entity.member.Member;
import salaba.entity.member.Point;
import salaba.entity.rental.Discount;
import salaba.entity.rental.Payment;
import salaba.entity.rental.Reservation;
import salaba.exception.NoAuthorityException;
import salaba.repository.jpa.MemberRepository;
import salaba.repository.jpa.PointRepository;
import salaba.repository.jpa.rentalHome.DiscountRepository;
import salaba.repository.jpa.rentalHome.PaymentRepository;
import salaba.repository.jpa.rentalHome.ReservationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

    private final ReservationRepository reservationRepository;

    private final PointRepository pointRepository;
    private final DiscountRepository discountRepository;

    public String completePayment(Long memberId, PaymentReqDto paymentReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Reservation reservation = reservationRepository.findByIdWithMember(paymentReqDto.getReservationId()).orElseThrow(NoSuchElementException::new);
        if (!reservation.getMember().equals(member)) {
            throw new NoAuthorityException("권한이 없습니다.");
        }
        //예약 때 만들어두었던 Payment를 찾는다.
        Payment payment = paymentRepository.findById(reservation.getId()).orElseThrow(NoSuchElementException::new);
        //할인을 만든다.
        List<Discount> discounts = paymentReqDto.getDiscounts().stream().map(reqDto ->
           Discount.createDiscount(payment, reqDto.getAmount(), reqDto.getContent())).toList();

        // 포인트를 사용했다면 포인트를 차감시킨다.
        discounts.forEach(discount -> {
            if (discount.getContent().contains("포인트")) {
                Point usedPoint = Point.createUsedPoint(discount.getContent(),
                        -1 * discount.getAmount(), reservation.getMember());
                pointRepository.save(usedPoint);
            }
        });
        //할인을 저장한다.
        discountRepository.saveAll(discounts);

        // 결제를 완료시킨다.
        payment.completePayment(paymentReqDto.getPaymentId(), discounts ,paymentReqDto.getMethod());

        // 포인트를 적립시킨다.
        Point point = Point.createRentalHomePoint(reservation.getMember(), payment.getFinalPrice());
        pointRepository.save(point);
        return payment.getId();
    }
}
