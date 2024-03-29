package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
class IntegrityExceptionTest extends GenericTest {

    @Test
    void constructorWithMessageReturnsPassedMessage() {

        // given
        final String message = " brrrr";

        // when
        IntegrityException exception = new IntegrityException(message);

        // then
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void constructorWithMessageAndCauseReturnsPassedMessageAndCause() {

        // given
        final String message = " brrrr";
        final Exception cause = new RuntimeException("maybe, maybe");

        // when
        IntegrityException exception = new IntegrityException(message, cause);

        // then
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isSameAs(cause);
    }

}
