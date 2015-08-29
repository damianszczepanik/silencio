package pl.szczepanik.silencio.integration;

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

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.utils.ReflectionUtil;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
@RunWith(Parameterized.class)
public class JSONProcessorTypeValidatorTest {

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
        JSONProcessor tested = new JSONProcessor(new Converter[] { new StubConverter() });
        
        // when
        boolean isType = ReflectionUtil.invokeMethod(tested, "isBasicType", Boolean.class, type);

        // then
        assertThat(isType).isEqualTo(isBasic);
    }
}