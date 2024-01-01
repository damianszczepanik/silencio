package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.powermock.reflect.Whitebox;
import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.core.Value;
import pl.szczepanik.silencio.processors.visitors.JSONVisitor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class JSONParser_isBasicTypeTest extends GenericTest {

    public Object type;

    public boolean isBasic;

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new String(), true },
                { Integer.valueOf(55), true },
                { Long.valueOf(32), true },
                { BigInteger.ZERO, true },
                { BigDecimal.TEN, true },
                { Double.valueOf(-88), true },
                { Boolean.FALSE, true },
                { new Object(), false },
                { new Value(null), false },
        });
    }

    @MethodSource("data")
    @ParameterizedTest(name = "\"{0}\" with \"{1}\"")
    void shouldValidateBasicObject(Object type, boolean isBasic) throws Exception {

        initDataWithNameTest(type, isBasic);
        // given
        JSONVisitor parser = new JSONVisitor();

        // when
        boolean isType = Whitebox.invokeMethod(parser, "isBasicType", type);

        // then
        assertThat(isType).isEqualTo(isBasic);
    }

    private void initDataWithNameTest(Object type, boolean isBasic) {
        this.type = type;
        this.isBasic = isBasic;
    }
}
