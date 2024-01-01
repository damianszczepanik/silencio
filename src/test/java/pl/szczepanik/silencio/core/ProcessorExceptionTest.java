package pl.szczepanik.silencio.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ProcessorExceptionTest extends GenericTest {

    @Test
    void constructorWithMessageReturnsPassedMessage() {

        // given
        final String message = " brrrr";

        // when
        ProcessorException exception = new ProcessorException(message);

        // then
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void constructorWithMessageAndCauseReturnsPassedMessageAndCause() {

        // given
        final Exception cause = new RuntimeException("maybe, maybe");

        // when
        ProcessorException exception = new ProcessorException(cause);

        // then
        assertThat(exception.getCause()).isSameAs(cause);
    }
}
