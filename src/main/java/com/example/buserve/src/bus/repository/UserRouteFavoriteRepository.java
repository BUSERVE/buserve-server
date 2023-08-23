package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Route;
import com.example.buserve.src.bus.entity.UserRouteFavorite;
import com.example.buserve.src.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRouteFavoriteRepository extends JpaRepository<UserRouteFavorite, Long> {
    UserRouteFavorite findByUserAndRoute(User user, Route route);

    List<UserRouteFavorite> findAllByUser(User user);
}
