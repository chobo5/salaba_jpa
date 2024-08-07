package salaba.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.Member;
import salaba.entity.Role;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testClass() {
        //스프링이 내부적으로 해당클래스를 자동으로 생성하는데(AOP 기능), 이 클래스 이름을 확인해보자
        System.out.println("memberRepository 인터페이스의 실제 객체이름: " + memberRepository.getClass().getName());

    }

    @Test
    public void testInsertDummies() {
        for (int i = 2; i <= 100; i++) {
            Member member = Member.builder()
                    .email("test" + i + "@test.com")
                    .name("test" + i)
                    .password("1234")
                    .birthday(LocalDate.of(2024, 06, 29))
                    .nickname("test" + i)
                    .build();
            memberRepository.save(member);
        }


    }

    @Transactional
    @Test
    public void testSelect() {
        Long memberNo = 1L;

        Optional<Member> result = memberRepository.findById(memberNo);
        System.out.println("=========================="); //실행전 이미 SQL이 실행됨
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long memberNo = 1L;

        Member result = memberRepository.getOne(memberNo);
        System.out.println("=========================="); //실행후 SQL 실행 즉, 해당 객체가 필요할때 SQL 실행

        System.out.println(result);

    }

    @Test
    public void testUpdate() {
        Member member = Member.builder()
                .memberNo(6L)
                .email("test2@test.com")
                .name("test2")
                .password("1234")
                .birthday(LocalDate.of(2024, 06, 29))
                .nickname("test2")
                .build();

        memberRepository.save(member);
        // JPA는 엔티티 객체들을 메모리상에 보관
        // 특정한 엔티티가 객체가 존재하는지 확인하는 select
        // -> @Id를 가진 엔티티객체가 있다면 update
        // 없다면 insert
    }

    @Test
    public void testDelete() {
        Long memberNo = 6L;
        memberRepository.deleteById(memberNo);
        //select 이후 delete 실행
    }

    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10); // 페이지 처리는 반드시 0부터 시작
        //Page 타입은 단순히 해당 목록만가져오지 않고 실제 페이지 처리에 필요한 전체 데이터의 개수를 가져온다.
        Page<Member> result = memberRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("==========================================");
        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Page Number: " + result.getNumber());
        System.out.println("Page Size: " + result.getSize());
        System.out.println("has next page?: " + result.hasNext());
        System.out.println("first page?: " + result.isFirst());

        System.out.println("==========================================");

        StringBuilder sb = new StringBuilder();
        result.getContent().forEach(member -> sb.append(member).append("\n"));
        System.out.println(sb);

    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("memberNo").descending().and(Sort.by("email").ascending());

        Pageable pageable = PageRequest.of(1, 10, sort1);

        Page<Member> result = memberRepository.findAll(pageable);

        StringBuilder sb = new StringBuilder();
        result.getContent().forEach(member -> sb.append(member).append("\n"));
        System.out.println("==========================================");
        System.out.println(sb);
    }

    @Test
    public void getUserRoles() {
        Member member = memberRepository.findByEmail("wnsdus1008@gmail.com").get();
        for (Role role : member.getRoleSet()) {
            System.out.println(role);
        }
    }
}
