package salaba.domain.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.common.entity.Address;
import salaba.domain.common.entity.Region;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.reservation.entity.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
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
//
//
//        //회원 생성
//        Member member1 = Member.create("john.smith0@gmail.com", "Tt12241509!@", "John", "john_123", LocalDate.of(1975, 5, 12));
//        em.persist(member1);
//
//        Member member2 = Member.create("jane.johnson1@yahoo.com", "Tt12241509!@", "Jane", "jane_456", LocalDate.of(1983, 3, 8));
//        em.persist(member2);
//
//        Member member3 = Member.create("alex.williams2@outlook.com", "Tt12241509!@", "Alex", "alex_789", LocalDate.of(1990, 11, 22));
//        em.persist(member3);
//
//        Member member4 = Member.create("laura.moore98@gmail.com", "Tt12241509!@", "Laura", "laura_523", LocalDate.of(1985, 8, 17));
//        em.persist(member4);
//
//        Member member5 = Member.create("chris.taylor99@yahoo.com", "Tt12241509!@", "Chris", "chris_842", LocalDate.of(1993, 12, 5));
//        em.persist(member5);
//
//        Member member6 = Member.create("anna.wilson100@outlook.com", "Tt12241509!@", "Anna", "anna_351", LocalDate.of(1979, 7, 21));
//        em.persist(member6);
//
//        Member member7 = Member.create("david.smith101@gmail.com", "Tt12241509!@", "David", "david_101", LocalDate.of(1972, 2, 15));
//        em.persist(member7);
//
//        Member member8 = Member.create("sarah.johnson102@yahoo.com", "Tt12241509!@", "Sarah", "sarah_102", LocalDate.of(1980, 9, 30));
//        em.persist(member8);
//
//        Member member9 = Member.create("michael.williams103@outlook.com", "Tt12241509!@", "Michael", "michael_103", LocalDate.of(1995, 6, 5));
//        em.persist(member9);
//
//        Member member10 = Member.create("laura.jones104@gmail.com", "Tt12241509!@", "Laura", "laura_104", LocalDate.of(1987, 1, 10));
//        em.persist(member10);
//
//        Member member11 = Member.create("chris.brown105@yahoo.com", "Tt12241509!@", "Chris", "chris_105", LocalDate.of(1974, 12, 25));
//        em.persist(member11);
//
//        Member member12 = Member.create("anna.davis106@outlook.com", "Tt12241509!@", "Anna", "anna_106", LocalDate.of(1991, 4, 18));
//        em.persist(member12);
//
//        Member member13 = Member.create("john.miller107@gmail.com", "Tt12241509!@", "John", "john_107", LocalDate.of(1984, 7, 13));
//        em.persist(member13);
//
//        Member member14 = Member.create("jane.wilson108@yahoo.com", "Tt12241509!@", "Jane", "jane_108", LocalDate.of(1978, 10, 22));
//        em.persist(member14);
//
//        Member member15 = Member.create("alex.moore109@outlook.com", "Tt12241509!@", "Alex", "alex_109", LocalDate.of(1993, 8, 2));
//        em.persist(member15);
//
//        Member member16 = Member.create("emily.taylor110@gmail.com", "Tt12241509!@", "Emily", "emily_110", LocalDate.of(1989, 11, 27));
//        em.persist(member16);
//
//        Member member17 = Member.create("david.smith199@gmail.com", "Tt12241509!@", "David", "david_199", LocalDate.of(1997, 3, 7));
//        em.persist(member17);
//
//        Member member18 = Member.create("sarah.johnson200@yahoo.com", "Tt12241509!@", "Sarah", "sarah_200", LocalDate.of(1982, 12, 14));
//        em.persist(member18);
//        for (int i = 1; i <= 100; i++) {
//            Member member = Member.create("test" + i + "@yahoo.com", "Tt12241509!@", "testName" + i, "testNick" + i, LocalDate.of(1982, 12, 14));
//            em.persist(member);
//        }

        Random random = new Random();
        for (int i = 625; i < 6250; i++) {
            List<Region> regions = em.createQuery("select r from Region r", Region.class).getResultList();
            List<Theme> themes = em.createQuery("select t from Theme t", Theme.class).getResultList();
            List<Facility> facilities = em.createQuery("select f from Facility f", Facility.class).getResultList();
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

            Address seoulAd = new Address("seoulStreet" + i, 11111 + i);
            RentalHome rentalHome = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "seoulHotel" + i, "it's seoulHotel" + i, seoulAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome);
            rentalHomeHelper(rentalHome, themes, facilities, members);
            

            Address suwonAd = new Address("suwonStreet" + i, 22222 + i);
            RentalHome rentalHome1 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "suwonHotel" + i, "it's suwonHotel" + i, suwonAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome1);

            Address hwaseongAd = new Address("hwaseongStreet" + i, 33333 + i);
            RentalHome rentalHome2 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "hwaseongHotel" + i, "it's hwaseongHotel" + i, hwaseongAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome2);
            rentalHomeHelper(rentalHome2, themes, facilities, members);

            Address yonginAd = new Address("yonginStreet" + i, 44444 + i);
            RentalHome rentalHome3 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "yonginHotel" + i, "it's yonginHotel" + i, yonginAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome3);
            rentalHomeHelper(rentalHome3, themes, facilities, members);

            Address daeguAd = new Address("daeguStreet" + i, 55555 + i);
            RentalHome rentalHome4 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "deaguHotel" + i, "it's deaguHotel" + i, daeguAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome4);
            rentalHomeHelper(rentalHome4, themes, facilities, members);

            Address busanAd = new Address("busanStreet" + i, 66666 + i);
            RentalHome rentalHome5 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "busanHotel" + i, "it's busanHotel" + i, busanAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome5);
            rentalHomeHelper(rentalHome5, themes, facilities, members);

            Address incheonAd = new Address("incheonStreet" + i, 77777 + i);
            RentalHome rentalHome6 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "incheonHotel" + i, "it's incheonHotel" + i, incheonAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome6);
            rentalHomeHelper(rentalHome6, themes, facilities, members);

            Address gwangjuAd = new Address("gwangjuStreet" + i, 88888 + i);
            RentalHome rentalHome7 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "gwangjuHotel" + i, "it's gwangjuHotel" + i, gwangjuAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome7);
            rentalHomeHelper(rentalHome7, themes, facilities, members);

            Address daegeonAd = new Address("daegeonStreet" + i, 99999 + i);
            RentalHome rentalHome8 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "daegeonHotel" + i, "it's deageonHotel" + i, daegeonAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome8);
            rentalHomeHelper(rentalHome8, themes, facilities, members);

            Address ulsanAd = new Address("ulsanStreet" + i, 101010 + i);
            RentalHome rentalHome9 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "ulsanHotel" + i, "it's ulsanHotel" + i, ulsanAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome9);
            rentalHomeHelper(rentalHome9, themes, facilities, members);

            Address sejongAd = new Address("sejongStreet" + i, 11111 + i);
            RentalHome rentalHome10 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "sejongHotel" + i, "it's sejongHotel" + i, sejongAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome10);
            rentalHomeHelper(rentalHome10, themes, facilities, members);

            Address koyangAd = new Address("koyangStreet" + i, 121212 + i);
            RentalHome rentalHome11 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "koyangHotel" + i, "it's koyangHotel" + i, koyangAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome11);
            rentalHomeHelper(rentalHome11, themes, facilities, members);

            Address sungnamAd = new Address("sungnamStreet" + i, 131313 + i);
            RentalHome rentalHome12 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "sungnamHotel" + i, "it's sungnamHotel" + i, sungnamAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome12);
            rentalHomeHelper(rentalHome12, themes, facilities, members);

            Address jeonjuAd = new Address("jeonjuStreet" + i, 141414 + i);
            RentalHome rentalHome13 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "jeonjuHotel" + i, "it's jeonjuHotel" + i, jeonjuAd, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome13);
            rentalHomeHelper(rentalHome13, themes, facilities, members);


            Address jeju1 = new Address("jejuStreet" + i, 151515 + i);
            RentalHome rentalHome14 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "jejuHotel" + i, "it's jejuHotel" + i, jeju1, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome14);
            rentalHomeHelper(rentalHome14, themes, facilities, members);


            Address jeju2 = new Address("jejuStreet" + i, 161616 + i);
            RentalHome rentalHome15 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "jejuHotel" + i, "it's jejuHotel" + i, jeju2, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome15);
            rentalHomeHelper(rentalHome15, themes, facilities, members);

            Address jeju3 = new Address("jejuStreet" + i, 171717 + i);
            RentalHome rentalHome16 = RentalHome.create(members.get(random.nextInt(members.size())), regions.get(i % regions.size()), "jejuHotel" + i, "it's jejuHotel" + i, jeju3, 50000 + 10 * i, 4, 123.123123, 111.111111, "seoulHotel" + i + "rule", 10000);
            em.persist(rentalHome16);
            rentalHomeHelper(rentalHome16, themes, facilities, members);

            em.flush();
            em.clear();
        }
        
    }
    
    private void rentalHomeHelper(RentalHome rentalHome, List<Theme> themes, List<Facility> facilities, List<Member> members) {
        Random random = new Random();
        for (int j = 0; j < random.nextInt(themes.size()); j++) {
            RentalHomeTheme rht = RentalHomeTheme.create(rentalHome, themes.get(j));
            em.persist(rht);
        }

        for (int j = 0; j < random.nextInt(facilities.size()); j++) {
            RentalHomeFacility rhf = RentalHomeFacility.create(rentalHome, facilities.get(j));
            em.persist(rhf);
        }

        for (int j = 0; j < random.nextInt(members.size()); j++) {
            Reservation reservation = Reservation.create(LocalDateTime.of(2021, 9, 10, 15, 00), LocalDateTime.of(2021, 9, 12, 11, 00), rentalHome, members.get(random.nextInt(members.size())));
            em.persist(reservation);
            Review review = Review.createReview(reservation, random.nextInt(5) + 1, "review " + j);
            em.persist(review);
        }
    }
}
