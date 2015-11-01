package pl.szczepanik.silencio.utils;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class CommonUtility_saveWaitTest {

    @Parameters
    public static Collection<Long> timeout() {
        return Arrays.asList(new Long[] { -1L, 10L });
    }

    @Parameter(value = 0)
    public Long timeout;

    @Test
    public void waitNeverFails() {

        // when
        CommonUtility.saveWait(timeout);

        // then
        // no exception
    }
}
