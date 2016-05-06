package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.processors.visitors.JSONVisitor;
import pl.szczepanik.silencio.utils.ReflectionUtility;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(Parameterized.class)
public class JSONParser_isBasicTypeTest extends GenericTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { new String(),    true },
            { new Integer(55), true },
            { new Long(32),    true },
            { BigInteger.ZERO, true },
            { BigDecimal.TEN,  true },
            { new Double(-88), true },
            { Boolean.FALSE,   true },
            { null,            true },
            { new Object(),    false },
            { new Value(null), false },
        });
    }

    @Parameter(value = 0)
    public Object type;

    @Parameter(value = 1)
    public boolean isBasic;

    @Test
    public void shouldValidateBasicObject() throws Exception {

        // given
        JSONVisitor parser = new JSONVisitor();

        // when
        boolean isType = ReflectionUtility.invokeMethod(parser, "isBasicType", type);

        // then
        assertThat(isType).isEqualTo(isBasic);
    }
}