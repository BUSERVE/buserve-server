package com.example.buserve.src.bus.entity;

import com.example.buserve.src.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class UserRouteFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public void setUserAndRoute(User user, Route route) {
        this.user = user;
        this.route = route;

        user.addFavoriteRoute(this);
    }
}
