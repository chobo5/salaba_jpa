package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.BookMark;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.exception.AlreadyExistsException;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.repository.BookMarkRepository;
import salaba.domain.rentalHome.repository.RentalHomeRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final MemberRepository memberRepository;
    public Long mark(Long memberId, Long rentalHomeId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);

        if (bookMarkRepository.findByMemberAndRentalHome(member, rentalHome) != null) {
            throw new AlreadyExistsException("이미 추가된 숙소입니다.");
        }

        BookMark bookMark = BookMark.create(member, rentalHome);
        bookMarkRepository.save(bookMark);
        return bookMark.getId();
    }

    public void deleteMark(Long memberId, Long rentalHomeId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        BookMark bookMark = bookMarkRepository.findByMemberAndRentalHome(member, rentalHome).orElseThrow(NoSuchElementException::new);
        bookMarkRepository.delete(bookMark);
    }
}
