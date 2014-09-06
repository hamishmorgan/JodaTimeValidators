package com.github.hamishmorgan.validators.jodatime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.hamishmorgan.validators.jodatime.Annotations.getDefaultValueAsInt;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BeforeTest {

    @Test
    public void givenValidBeforeYearOne_whenYear_thenReturnsExpected() throws NoSuchFieldException {
        class TestFixture {
            @Before(year = 2014)
            Object field;
        }

        Before before = TestFixture.class.getDeclaredField("field").getAnnotation(Before.class);
        assertThat(before.year()).isEqualTo(2014);
    }

    @Test
    public void givenValidBeforeMonth_whenYear_thenReturnsExpected() throws NoSuchFieldException {
        class TestFixture {
            @Before(month = 1)
            Object field;
        }

        Before before = TestFixture.class.getDeclaredField("field").getAnnotation(Before.class);
        assertThat(before.month()).isEqualTo(1);
    }

    @Test
    public void givenBeforeNoArg_whenYear_thenReturnsZero() throws NoSuchFieldException, NoSuchMethodException {
        class TestFixture {
            @Before
            Object field;
        }

        Before before = TestFixture.class.getDeclaredField("field").getAnnotation(Before.class);
        assertThat(before.year()).isEqualTo(getDefaultValueAsInt(Before.class, "year"));
        assertThat(before.month()).isEqualTo(getDefaultValueAsInt(Before.class, "month"));
    }

    @Test
    public void givenBeforeList_whenYear_thenReturnsZero() throws NoSuchFieldException {
        class TestFixture {
            @Before.List({
                    @Before(year = 2),
                    @Before(year = 1),
            })
            Object field;
        }

        Before.List before = TestFixture.class.getDeclaredField("field").getAnnotation(Before.List.class);
        assertThat(before.value()[0].year()).isEqualTo(2);
        assertThat(before.value()[1].year()).isEqualTo(1);
    }


}