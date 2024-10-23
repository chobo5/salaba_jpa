package salaba.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.BookMark;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.member.exception.AlreadyExistsException;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.member.repository.BookMarkRepository;
import salaba.domain.rentalHome.repository.RentalHomeRepository;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final MemberRepository memberRepository;

    public Long create(Long memberId, Long rentalHomeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, rentalHomeId)));

        if (bookMarkRepository.findByMemberAndRentalHome(member, rentalHome).isPresent()) {
            throw new AlreadyExistsException("이미 추가된 숙소입니다.");
        }

        BookMark bookMark = BookMark.create(member, rentalHome);
        bookMarkRepository.save(bookMark);
        return bookMark.getId();
    }

    public void delete(Long memberId, Long rentalHomeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, rentalHomeId)));

        BookMark bookMark = bookMarkRepository.findByMemberAndRentalHome(member, rentalHome)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessage.entityNotFound(BookMark.class, "rentalHomeId: " + rentalHomeId + " memberId: " + memberId)));

        bookMarkRepository.delete(bookMark);
    }
}
