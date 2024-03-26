package com.user.repo;

import com.user.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
                select t from token t inner join User u on t.user.id = u.id
                where u.id = :userId and t.expired = false or t.revoked = false
            """)
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByUserToken(String token);

    @Query(value = """
            update token set expired=:expired and revoked=:revoked where id=:id
            """, nativeQuery = true)
    @Modifying
    boolean updateTokenDetail(@Param("expired") boolean expired, @Param("revoked") boolean revoked, Long id);
}
