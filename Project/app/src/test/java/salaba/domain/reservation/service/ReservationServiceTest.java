package salaba.domain.reservation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import salaba.domain.common.constants.ProcessStatus;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.service.PointService;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.repository.RentalHomeRepository;
import salaba.domain.reservation.constants.PayMethod;
import salaba.domain.reservation.dto.request.DiscountReqDto;
import salaba.domain.reservation.dto.request.PaymentReqDto;
import salaba.domain.reservation.dto.request.ReservationReqDto;
import salaba.domain.reservation.entity.Discount;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.repository.DiscountRepository;
import salaba.domain.reservation.repository.ReservationRepository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RentalHomeRepository rentalHomeRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private PointService pointService;

    @Test
    void 예약생성() {
        //given
        Long memberId = 1L;
        ReservationReqDto reqDto = new ReservationReqDto(1L, LocalDateTime.of(2024, 9, 21, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0));

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(rentalHomeRepository.findById(reqDto.getRentalHomeId())).thenReturn(Optional.of(rentalHome));

        reservationService.makeReservation(memberId, reqDto);

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(rentalHomeRepository, times(1)).findById(reqDto.getRentalHomeId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));


    }

    @Test
    void 숙소의예약된날짜목록() {
        //given
        Long rentalHomeId = 1L;

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 21, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, member));
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 24, 15, 0),
                LocalDateTime.of(2024, 9, 26, 11, 0), rentalHome, member));
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 28, 15, 0),
                LocalDateTime.of(2024, 9, 30, 11, 0), rentalHome, member));

        when(rentalHomeRepository.findById(rentalHomeId)).thenReturn(Optional.of(rentalHome));
        when(reservationRepository.findByRentalHomeAndStatus(rentalHome, ProcessStatus.COMPLETE)).thenReturn(reservations);

        reservationService.getReservedDate(rentalHomeId);

        //then
        verify(rentalHomeRepository, times(1)).findById(rentalHomeId);
        verify(reservationRepository, times(1))
                .findByRentalHomeAndStatus(rentalHome, ProcessStatus.COMPLETE);


    }

    @Test
    void 숙소의예약목록_호스트용() {
        //given
        Long rentalHomeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 21, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, member));
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 24, 15, 0),
                LocalDateTime.of(2024, 9, 26, 11, 0), rentalHome, member));
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 28, 15, 0),
                LocalDateTime.of(2024, 9, 30, 11, 0), rentalHome, member));
        when(reservationRepository.findWithGuest(rentalHomeId, pageable)).thenReturn(new PageImpl<>(reservations, pageable, 3));

        reservationService.getWithRentalHomeForHost(rentalHomeId, pageable);
        //then
        verify(reservationRepository, times(1)).findWithGuest(rentalHomeId, pageable);


    }

    @Test
    void 숙소의예약목록_게스트용() {
        //given
        Long rentalHomeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 21, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, member));
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 24, 15, 0),
                LocalDateTime.of(2024, 9, 26, 11, 0), rentalHome, member));
        reservations.add(Reservation.create(LocalDateTime.of(2024, 9, 28, 15, 0),
                LocalDateTime.of(2024, 9, 30, 11, 0), rentalHome, member));
        when(reservationRepository.findWithRentalHomeAndHost(rentalHomeId, pageable)).thenReturn(new PageImpl<>(reservations, pageable, 3));

        reservationService.getWithRentalHomeForGuest(rentalHomeId, pageable);
        //then
        verify(reservationRepository, times(1)).findWithRentalHomeAndHost(rentalHomeId, pageable);
    }

    @Test
    void 예약완료() {
        //given
        Long memberId = 1L;
        List<DiscountReqDto> discountReqs = Arrays.asList(new DiscountReqDto(1L, 5000, "포인트"),
                new DiscountReqDto(1L, 3000, "포인트2"));
        PaymentReqDto reqDto = new PaymentReqDto(1L, "paymentconde", 300000,
                ProcessStatus.COMPLETE, PayMethod.CARD, discountReqs);

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 21, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, member);
        List<Discount> discounts = discountReqs.stream().map(dto -> Discount.create(reservation, dto.getAmount(), dto.getContent())).toList();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(reservationRepository.findByIdWithMemberAndRentalHome(reqDto.getReservationId())).thenReturn(Optional.of(reservation));
        doNothing().when(pointService).createPaymentPoint(any(Member.class), any(Integer.class));


        reservationService.completeReservation(memberId, reqDto);
        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(reservationRepository, times(1)).findByIdWithMemberAndRentalHome(reqDto.getReservationId());
        verify(discountRepository, times(1)).saveAll(anyIterable());
        verify(pointService, times(1)).createPaymentPoint(any(Member.class), any(Integer.class));

    }

    @Test
    void 예약취소() {
        //given
        Long memberId = 1L;
        Long reservationId = 1L;

        //when
        Member member = Member.create("test@test.com", "Aa123456!@", "test",
                "testNickname", LocalDate.of(2000, 12, 12));
        Nation nation = new Nation(82, "kor");
        Region region = new Region("서울", nation);
        Address address = new Address("test street", 123412);
        RentalHome rentalHome = RentalHome.create(member, region, "testHome",
                "testHome_explanation", address, 100000, 4, 12.123421, 12.21321,
                "test rule", 10000);
        Reservation reservation = Reservation.create(LocalDateTime.of(2024, 9, 21, 15, 0),
                LocalDateTime.of(2024, 9, 23, 11, 0), rentalHome, member);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(reservationRepository.findByIdWithMemberAndRentalHome(reservationId)).thenReturn(Optional.of(reservation));
        doNothing().when(discountRepository).deleteByReservation(reservation);

        reservationService.cancelReservation(reservationId, memberId);
        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(reservationRepository, times(1)).findByIdWithMemberAndRentalHome(reservationId);
        verify(discountRepository).deleteByReservation(any(Reservation.class));
    }
}