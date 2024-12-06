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
@Table(name = "point")
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

//    public boolean calculate() {
//        // TODO update calculate to new form
//        this.hitTime = new Date();
//        // 1st quarter (circle)
//        if (x > 0 && y > 0
//                && x * x + y * y <= r * r) {
//            return true;
//        }
//        // 3rd quarter (triangle)
//        if (x < 0 && y < 0
//                && y >= -x - r) {
//            return true;
//        }
//        // 4th quarter (circle)
//        if (x < 0 && y > 0
//                && x <= r && y >= -r/2) {
//            return true;
//        }
//        // critical points
//        if (x == 0 && y >= -r && y <= r) {
//            return true;
//        }
//        if (y == 0 && x >= -r && x <= r) {
//            return true;
//        }
//
//        return false;
//    }
    public void calculate() {
        // TODO update calculate to new form
        this.hitTime = new Date();
        // 1st quarter (circle)
        if (x > 0 && y > 0
                && x * x + y * y <= r * r) {
//            return true;
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
        // critical points
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
}
