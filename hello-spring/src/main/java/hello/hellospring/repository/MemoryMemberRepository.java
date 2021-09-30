package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    public static Map<Long, Member> store = new HashMap<>(); // 실무에선 ConcurrentHashMap
    public static long sequence = 0L; // 동시성 문제를 생각해서 atomic long을 해야함

    @Override
    public Member save(Member member) {
        // name은 이미 member에 들어 있음
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이어도 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        ArrayList<Member> members = new ArrayList<>(store.values());
        return members;
    }

    public void clearStore() {
        store.clear();
    }
}
