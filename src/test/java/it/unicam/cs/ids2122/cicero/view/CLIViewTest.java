package it.unicam.cs.ids2122.cicero.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CLIViewTest {

    static IView<String> view;

    @BeforeAll
    static void setUp() {
        view = new CLIView();
    }

    private static final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;

    @AfterAll
    static void resetSystemIn() {
        System.setIn(systemIn);
    }

    private void inputData(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void test_fetchInt() {
        inputData("3");
        int actual = view.fetchInt();
        assertEquals(3, actual);
    }
}