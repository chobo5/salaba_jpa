package salaba.domain.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Nation;
import salaba.domain.common.entity.Region;
import salaba.domain.common.repository.RegionRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.entity.Role;
import salaba.domain.member.constants.RoleName;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.rentalHome.repository.FacilityRepository;
import salaba.domain.rentalHome.repository.ThemeRepository;
import salaba.domain.reply.entity.Reply;
import salaba.domain.reservation.entity.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init() {

//        Nation southKorea = new Nation(82, "South Korea");
//        Nation japan = new Nation(81, "Japan");
//        Nation usa = new Nation(1, "USA");
//        Nation china = new Nation(86, "China");
//        Nation germany = new Nation(49, "Germany");
//        Nation france = new Nation(33, "France");
//        Nation uk = new Nation(44, "United Kingdom");
//        Nation australia = new Nation(61, "Australia");
//        Nation india = new Nation(91, "India");
//        Nation brazil = new Nation(55, "Brazil");
//        Nation russia = new Nation(7, "Russia");
//        Nation italy = new Nation(39, "Italy");
//        Nation spain = new Nation(34, "Spain");
//        Nation mexico = new Nation(52, "Mexico");
//        Nation southAfrica = new Nation(27, "South Africa");
//        Nation saudiArabia = new Nation(966, "Saudi Arabia");
//        Nation sweden = new Nation(46, "Sweden");
//        Nation netherlands = new Nation(31, "Netherlands");
//        Nation argentina = new Nation(54, "Argentina");
//        Nation turkey = new Nation(90, "Turkey");
//        Nation egypt = new Nation(20, "Egypt");
//        Nation iran = new Nation(98, "Iran");
//        Nation indonesia = new Nation(62, "Indonesia");
//        Nation pakistan = new Nation(92, "Pakistan");
//        Nation bangladesh = new Nation(880, "Bangladesh");
//        Nation nigeria = new Nation(234, "Nigeria");
//        Nation vietnam = new Nation(84, "Vietnam");
//        Nation philippines = new Nation(63, "Philippines");
//        Nation malaysia = new Nation(60, "Malaysia");
//        Nation thailand = new Nation(66, "Thailand");
//        Nation singapore = new Nation(65, "Singapore");
//        Nation uae = new Nation(971, "United Arab Emirates");
//        Nation norway = new Nation(47, "Norway");
//        Nation denmark = new Nation(45, "Denmark");
//        Nation finland = new Nation(358, "Finland");
//        Nation poland = new Nation(48, "Poland");
//        Nation belgium = new Nation(32, "Belgium");
//        Nation switzerland = new Nation(41, "Switzerland");
//        Nation greece = new Nation(30, "Greece");
//        Nation austria = new Nation(43, "Austria");
//        Nation israel = new Nation(972, "Israel");
//        Nation chile = new Nation(56, "Chile");
//        Nation peru = new Nation(51, "Peru");
//        Nation colombia = new Nation(57, "Colombia");
//        Nation venezuela = new Nation(58, "Venezuela");
//        Nation ukraine = new Nation(380, "Ukraine");
//
//        em.persist(southKorea);
//        em.persist(japan);
//        em.persist(usa);
//        em.persist(china);
//        em.persist(germany);
//        em.persist(france);
//        em.persist(uk);
//        em.persist(australia);
//        em.persist(india);
//        em.persist(brazil);
//        em.persist(russia);
//        em.persist(italy);
//        em.persist(spain);
//        em.persist(mexico);
//        em.persist(southAfrica);
//        em.persist(saudiArabia);
//        em.persist(sweden);
//        em.persist(netherlands);
//        em.persist(argentina);
//        em.persist(turkey);
//        em.persist(egypt);
//        em.persist(iran);
//        em.persist(indonesia);
//        em.persist(pakistan);
//        em.persist(bangladesh);
//        em.persist(nigeria);
//        em.persist(vietnam);
//        em.persist(philippines);
//        em.persist(malaysia);
//        em.persist(thailand);
//        em.persist(singapore);
//        em.persist(uae);
//        em.persist(norway);
//        em.persist(denmark);
//        em.persist(finland);
//        em.persist(poland);
//        em.persist(belgium);
//        em.persist(switzerland);
//        em.persist(greece);
//        em.persist(austria);
//        em.persist(israel);
//        em.persist(chile);
//        em.persist(peru);
//        em.persist(colombia);
//        em.persist(venezuela);
//        em.persist(ukraine);
//
//        Nation findKorea = em.find(Nation.class, 82);
//        Region seoul = new Region("seoul", findKorea);
//        Region busan = new Region("Busan", findKorea);
//        Region daegu = new Region("Daegu", findKorea);
//        Region incheon = new Region("Incheon", findKorea);
//        Region gwangju = new Region("Gwangju", findKorea);
//        Region daegeon = new Region("Daegeon", findKorea);
//        Region ulsan = new Region("Ulsan", findKorea);
//        Region sejong = new Region("Sejong", findKorea);
//        Region koyang = new Region("Koyang", findKorea);
//        Region sungnam = new Region("Sungnam", findKorea);
//        Region jeonju = new Region("Jeonju", findKorea);
//        Region jeju = new Region("Jeju", findKorea);
//        Region suwon = new Region("suwon", findKorea);
//        Region hwaseong = new Region("hwaseong", findKorea);
//        Region yongin = new Region("yongin", findKorea);
//
//        em.persist(seoul);
//        em.persist(suwon);
//        em.persist(hwaseong);
//        em.persist(yongin);
//        em.persist(busan);
//        em.persist(daegu);
//        em.persist(incheon);
//        em.persist(gwangju);
//        em.persist(daegeon);
//        em.persist(ulsan);
//        em.persist(sejong);
//        em.persist(koyang);
//        em.persist(sungnam);
//        em.persist(jeonju);
//        em.persist(jeju);
//
//
//        Role roleAdmin = new Role(RoleName.ADMIN.getId(), RoleName.ADMIN);
//        Role roleManager = new Role(RoleName.MANAGER.getId(), RoleName.MANAGER);
//        Role roleMember = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
//        em.persist(roleAdmin);
//        em.persist(roleManager);
//        em.persist(roleMember);
//
//
//        Facility facility1 = new Facility("Air Conditioner");
//        Facility facility2 = new Facility("wi-fi");
//        Facility facility3 = new Facility("Washing Machine");
//        Facility facility4 = new Facility("Dryer");
//        Facility facility5 = new Facility("TV");
//        Facility facility6 = new Facility("Heating");
//        Facility facility7 = new Facility("Barbeque");
//        Facility facility8 = new Facility("Spa");
//        Facility facility10 = new Facility("Breakfast");
//        Facility facility11 = new Facility("Bag Storage Possible");
//        Facility facility12 = new Facility("Camp Fire");
//        Facility facility13 = new Facility("Garden");
//        Facility facility14 = new Facility("Swimming Pool");
//
//        em.persist(facility1);
//        em.persist(facility2);
//        em.persist(facility3);
//        em.persist(facility4);
//        em.persist(facility5);
//        em.persist(facility6);
//        em.persist(facility7);
//        em.persist(facility8);
//        em.persist(facility10);
//        em.persist(facility11);
//        em.persist(facility12);
//        em.persist(facility13);
//        em.persist(facility14);
//
//        Theme theme1 = new Theme("Country");
//        Theme theme2 = new Theme("Beach");
//        Theme theme3 = new Theme("Mountain");
//        Theme theme4 = new Theme("City");
//        Theme theme5 = new Theme("Park");
//        Theme theme6 = new Theme("Camping");
//        Theme theme8 = new Theme("Hotel");
//        Theme theme9 = new Theme("Outbuilding");
//        Theme theme10 = new Theme("Pension");
//        Theme theme11 = new Theme("Motel");
//        em.persist(theme1);
//        em.persist(theme2);
//        em.persist(theme3);
//        em.persist(theme4);
//        em.persist(theme5);
//        em.persist(theme6);
//        em.persist(theme8);
//        em.persist(theme9);
//        em.persist(theme10);
//        em.persist(theme11);

        //회원 생성
//        Member member1 = Member.createMember("john.smith0@gmail.com", "Tt12241509!@", "John", "john_123", LocalDate.of(1975, 5, 12));
//        em.persist(member1);
//
//        Member member2 = Member.createMember("jane.johnson1@yahoo.com", "Tt12241509!@", "Jane", "jane_456", LocalDate.of(1983, 3, 8));
//        em.persist(member2);
//
//        Member member3 = Member.createMember("alex.williams2@outlook.com", "Tt12241509!@", "Alex", "alex_789", LocalDate.of(1990, 11, 22));
//        em.persist(member3);
//
//        Member member4 = Member.createMember("laura.moore98@gmail.com", "Tt12241509!@", "Laura", "laura_523", LocalDate.of(1985, 8, 17));
//        em.persist(member4);
//
//        Member member5 = Member.createMember("chris.taylor99@yahoo.com", "Tt12241509!@", "Chris", "chris_842", LocalDate.of(1993, 12, 5));
//        em.persist(member5);
//
//        Member member6 = Member.createMember("anna.wilson100@outlook.com", "Tt12241509!@", "Anna", "anna_351", LocalDate.of(1979, 7, 21));
//        em.persist(member6);
//
//        Member member7 = Member.createMember("david.smith101@gmail.com", "Tt12241509!@", "David", "david_101", LocalDate.of(1972, 2, 15));
//        em.persist(member7);
//
//        Member member8 = Member.createMember("sarah.johnson102@yahoo.com", "Tt12241509!@", "Sarah", "sarah_102", LocalDate.of(1980, 9, 30));
//        em.persist(member8);
//
//        Member member9 = Member.createMember("michael.williams103@outlook.com", "Tt12241509!@", "Michael", "michael_103", LocalDate.of(1995, 6, 5));
//        em.persist(member9);
//
//        Member member10 = Member.createMember("laura.jones104@gmail.com", "Tt12241509!@", "Laura", "laura_104", LocalDate.of(1987, 1, 10));
//        em.persist(member10);
//
//        Member member11 = Member.createMember("chris.brown105@yahoo.com", "Tt12241509!@", "Chris", "chris_105", LocalDate.of(1974, 12, 25));
//        em.persist(member11);
//
//        Member member12 = Member.createMember("anna.davis106@outlook.com", "Tt12241509!@", "Anna", "anna_106", LocalDate.of(1991, 4, 18));
//        em.persist(member12);
//
//        Member member13 = Member.createMember("john.miller107@gmail.com", "Tt12241509!@", "John", "john_107", LocalDate.of(1984, 7, 13));
//        em.persist(member13);
//
//        Member member14 = Member.createMember("jane.wilson108@yahoo.com", "Tt12241509!@", "Jane", "jane_108", LocalDate.of(1978, 10, 22));
//        em.persist(member14);
//
//        Member member15 = Member.createMember("alex.moore109@outlook.com", "Tt12241509!@", "Alex", "alex_109", LocalDate.of(1993, 8, 2));
//        em.persist(member15);
//
//        Member member16 = Member.createMember("emily.taylor110@gmail.com", "Tt12241509!@", "Emily", "emily_110", LocalDate.of(1989, 11, 27));
//        em.persist(member16);
//
//        Member member17 = Member.createMember("david.smith199@gmail.com", "Tt12241509!@", "David", "david_199", LocalDate.of(1997, 3, 7));
//        em.persist(member17);
//
//        Member member18 = Member.createMember("sarah.johnson200@yahoo.com", "Tt12241509!@", "Sarah", "sarah_200", LocalDate.of(1982, 12, 14));
//        em.persist(member18);
//
//        for (int i = 1; i <= 100000; i++) {
//            Member member = Member.createMember("test" + i + "@yahoo.com", "Tt12241509!@", "testName" + i, "testNick" + i, LocalDate.of(1982, 12, 14));
//            em.persist(member);
//        }

//        List<Region> regions = em.createQuery("select r from Region r", Region.class).getResultList();

//        for (int i = 0; i <= 10000; i++) {
//            Address seoul = new Address("seoulStreet" + i, 11111 + i);
//            RentalHome rentalHome = RentalHome.createRentalHome(members.get(0), regions.get(i % 15), "seoulHotel" + i, "it's seoulHotel" + i, seoul, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome);
//
//            Address suwon = new Address("suwonStreet" + i, 22222 + i);
//            RentalHome rentalHome1 = RentalHome.createRentalHome(members.get(1), regions.get(i % 15), "suwonHotel" + i, "it's suwonHotel" + i, suwon, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome1);
//
//            Address hwaseong = new Address("hwaseongStreet" + i, 33333 + i);
//            RentalHome rentalHome2 = RentalHome.createRentalHome(members.get(2), regions.get(i % 15), "hwaseongHotel" + i, "it's hwaseongHotel" + i, hwaseong, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome2);
//
//            Address yongin = new Address("yonginStreet" + i, 44444 + i);
//            RentalHome rentalHome3 = RentalHome.createRentalHome(members.get(3), regions.get(i % 15), "yonginHotel" + i, "it's yonginHotel" + i, yongin, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome3);
//
//            Address daegu = new Address("daeguStreet" + i, 55555 + i);
//            RentalHome rentalHome4 = RentalHome.createRentalHome(members.get(4), regions.get(i % 15), "deaguHotel" + i, "it's deaguHotel" + i, daegu, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome4);
//
//            Address busan = new Address("busanStreet" + i, 66666 + i);
//            RentalHome rentalHome5 = RentalHome.createRentalHome(members.get(5), regions.get(i % 15), "busanHotel" + i, "it's busanHotel" + i, busan, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome5);
//
//            Address incheon = new Address("incheonStreet" + i, 77777 + i);
//            RentalHome rentalHome6 = RentalHome.createRentalHome(members.get(6), regions.get(i % 15), "incheonHotel" + i, "it's incheonHotel" + i, incheon, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome6);
//
//            Address gwangju = new Address("gwangjuStreet" + i, 88888 + i);
//            RentalHome rentalHome7 = RentalHome.createRentalHome(members.get(7), regions.get(i % 15), "gwangjuHotel" + i, "it's gwangjuHotel" + i, gwangju, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome7);
//
//            Address daegeon = new Address("daegeonStreet" + i, 99999 + i);
//            RentalHome rentalHome8 = RentalHome.createRentalHome(members.get(8), regions.get(i % 15), "daegeonHotel" + i, "it's deageonHotel" + i, daegeon, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome8);
//
//            Address ulsan = new Address("ulsanStreet" + i, 101010 + i);
//            RentalHome rentalHome9 = RentalHome.createRentalHome(members.get(9), regions.get(i % 15), "ulsanHotel" + i, "it's ulsanHotel" + i, ulsan, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome9);
//
//            Address sejong = new Address("sejongStreet" + i, 11111 + i);
//            RentalHome rentalHome10 = RentalHome.createRentalHome(members.get(10), regions.get(i % 15), "sejongHotel" + i, "it's sejongHotel" + i, sejong, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome10);
//
//            Address koyang = new Address("koyangStreet" + i, 121212 + i);
//            RentalHome rentalHome11 = RentalHome.createRentalHome(members.get(11), regions.get(i % 15), "koyangHotel" + i, "it's koyangHotel" + i, koyang, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome11);
//
//            Address sungnam = new Address("sungnamStreet" + i, 131313 + i);
//            RentalHome rentalHome12 = RentalHome.createRentalHome(members.get(12), regions.get(i % 15), "sungnamHotel" + i, "it's sungnamHotel" + i, sungnam, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome12);
//
//            Address jeonju = new Address("jeonjuStreet" + i, 141414 + i);
//            RentalHome rentalHome13 = RentalHome.createRentalHome(members.get(13), regions.get(i % 15), "jeonjuHotel" + i, "it's jeonjuHotel" + i, jeonju, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome13);
//
//
//            Address jeju1 = new Address("jejuStreet" + i, 151515 + i);
//            RentalHome rentalHome14 = RentalHome.createRentalHome(members.get(14), regions.get(i % 15), "jejuHotel" + i, "it's jejuHotel" + i, jeju1, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome14);
//
//
//            Address jeju2 = new Address("jejuStreet" + i, 161616 + i);
//            RentalHome rentalHome15 = RentalHome.createRentalHome(members.get(15), regions.get(i % 15), "jejuHotel" + i, "it's jejuHotel" + i, jeju2, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome15);
//
//            Address jeju3 = new Address("jejuStreet" + i, 171717 + i);
//            RentalHome rentalHome16 = RentalHome.createRentalHome(members.get(16), regions.get(i % 15), "jejuHotel" + i, "it's jejuHotel" + i, jeju3, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
//            em.persist(rentalHome16);
//        }
//        List<Member> members = em.createQuery("select m from Member m", Member.class).setMaxResults(1000).getResultList();
//        List<RentalHome> rentalHomes = em.createQuery("select r from RentalHome r", RentalHome.class).setMaxResults(1000).getResultList();
//        List<Theme> themes = em.createQuery("select t from Theme t", Theme.class).getResultList();
//        List<Facility> facilities = em.createQuery("select f from Facility f", Facility.class).getResultList();
//        Random random = new Random();
//        for (int i = 0; i < rentalHomes.size(); i++) {
//            for (int j = 0; j < random.nextInt(10); j++) {
//                RentalHomeTheme rht = RentalHomeTheme.createRentalHomeTheme(rentalHomes.get(i), themes.get(j));
//                em.persist(rht);
//            }

//            for (int j = 0; j < random.nextInt(13); j++) {
//                RentalHomeFacility rhf = RentalHomeFacility.createRentalHomeFacility(rentalHomes.get(i), facilities.get(j));
//                em.persist(rhf);
//            }

//            for (int j = 0; j < random.nextInt(1000) + 1; j++) {
//                Reservation reservation = Reservation.createReservation(LocalDateTime.of(2021, 9, 10, 15, 00), LocalDateTime.of(2021, 9, 12, 11, 00), rentalHome, members.get(j));
//                em.persist(reservation);
//                Review review = Review.createReview(reservation, random.nextInt(5) + 1, "review " + j);
//                em.persist(review);
//            }
//        }



    }


}
