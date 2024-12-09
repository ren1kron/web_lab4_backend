package org.ren1kron.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "points")
public class Point implements Serializable {
    private static final long serialVersionUID = 123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float x;
    private float y;
    private float r;

    @Column(name = "hit_time")
    private Date hitTime;

    private boolean hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public void calculate() {
        this.hitTime = new Date();
        // 1st quarter (circle)
        if (x > 0 && y > 0
                && x * x + y * y <= r * r) {
            this.hit = true;
            return;
        }
        // 3rd quarter (triangle)
        if (x < 0 && y < 0
                && y >= -x - r) {
            this.hit = true;
            return;
        }
        // 4th quarter (circle)
        if (x > 0 && y < 0
                && x <= r && y >= -r/2) {
            this.hit = true;
            return;
        }
        // critical lines
        if (x == 0 && y >= -r && y <= r) {
            this.hit = true;
            return;
        }
        if (y == 0 && x >= -r && x <= r) {
            this.hit = true;
            return;
        }

        this.hit = false;
    }
//    public void calculate() {
//        this.hitTime = new Date();
//        if ((x > 0 && y > 0 && x * x + y * y <= r * r) ||
//                (x < 0 && y < 0 && y >= -x - r) ||
//                (x > 0 && y < 0 && x <= r && y >= -r / 2) ||
//                (x == 0 && y >= -r && y <= r) ||
//                (y == 0 && x >= -r && x <= r)) {
//            this.hit = true;
//        } else {
//            this.hit = false;
//        }
//    }

}
