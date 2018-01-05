package pl.szczepanik.silencio.utils;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import pl.szczepanik.silencio.GenericTest;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class CommonUtility_saveWaitTest extends GenericTest {

    @Parameter(value = 0)
    public Long timeout;

    @Parameterized.Parameters
    public static Collection<Long> timeout() {
        return Arrays.asList(new Long[] { -1L, 10L });
    }

    @Test
    public void waitNeverFails() {

        // when
        CommonUtility.saveWait(timeout);

        // then
        // no exception
    }
}
