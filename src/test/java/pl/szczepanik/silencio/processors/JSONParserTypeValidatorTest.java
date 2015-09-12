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

import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.stubs.StubProcessable;
import pl.szczepanik.silencio.utils.ReflectionUtils;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
@RunWith(Parameterized.class)
public class JSONParserTypeValidatorTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { new String(), true },
            { new Integer(55), true },
            { new Long(32), true },
            { BigInteger.ZERO, true },
            { BigDecimal.TEN, true },
            { new Double(-88), true },
            { Boolean.FALSE, true },
            { null, true },
            { new Object(), false },
            { new Value(null), false },
            });
    }

    @Parameter(value = 0)
    public /* NOT private */ Object type;

    @Parameter(value = 1)
    public /* NOT private */ boolean isBasic;

    @Test
    public void shouldValidateBasicObject() throws Exception {

        // given
        JSONVisitor parser = new JSONVisitor(new StubProcessable());

        // when
        boolean isType = ReflectionUtils.invokeMethod(parser, "isBasicType", Boolean.class, type);

        // then
        assertThat(isType).isEqualTo(isBasic);
    }
}