package model.ruoli;

import controller.InfoEsperienza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CiceroneTest {

    Cicerone c;
    InfoEsperienza infoA;
    @BeforeEach
    void setUp() {
        c = new DefaultCicerone("Marco", "marco.rossi@mail.com");
        infoA = new InfoEsperienza("Esperienza A",
                LocalDateTime.parse("2022-02-05T14:00:00"),
                LocalDateTime.parse("2022-02-05T16:00:00"),
                5, 15, 3);
    }

    @Test
    void shouldBeInsideAllEsperienze() {
        c.creaEsperienza(infoA);
        boolean res = c.getAllEsperienze().stream().anyMatch(e -> Objects.equals(e.getName(), "Esperienza A"));
        assertTrue(res);
    }
}