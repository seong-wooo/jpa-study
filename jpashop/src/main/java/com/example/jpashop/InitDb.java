package com.example.jpashop;

import com.example.jpashop.domain.*;
import com.example.jpashop.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitDb {

    InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class InitService {
        EntityManager em;

        public void dbInit1() {
            final Member member = getMember("userA", "서울", "1", "1111");
            em.persist(member);

            final Book book1 = getBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            final Book book2 = getBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            final OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            final OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            final Delivery delivery = getDelivery(member);
            final Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);

        }

        private Book getBook(String name, int price, int stockQuantity) {
            final Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Member getMember(String name, String city, String street, String zipcode) {
            final Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        public void dbInit2() {
            final Member member = getMember("userB", "부산", "2", "2222");
            em.persist(member);

            final Book book1 = getBook("SPRING1 BOOK", 10000, 200);
            em.persist(book1);

            final Book book2 = getBook("SPRING2 BOOK", 20000, 300);
            em.persist(book2);

            final OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            final OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            final Delivery delivery = getDelivery(member);
            final Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);

        }

        private Delivery getDelivery(Member member) {
            final Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
