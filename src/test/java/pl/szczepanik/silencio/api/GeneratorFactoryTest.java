package pl.szczepanik.silencio.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import pl.szczepanik.silencio.StubGenerator;

public class GeneratorFactoryTest {

    // unchanged generator used for most methods
    private static final Generator uniqueType = new StubGenerator("thisIsUniqueGeneratorType");

    private static final GeneratorFactory factory = DefaultFactory.getInstance();

    @Test
    public void shouldFilterOutEmptySetForUknownType() {

        // when
        factory.register(uniqueType);

        // then
        Set<Generator> generators = factory.findBy(uniqueType.getType());
        assertThat(generators).hasSize(1);
        assertThat(generators).contains(uniqueType);
    }

    @Test
    public void shouldNotAllowToRegisterGeneratorTwice() {

        // given
        factory.register(uniqueType);

        // when
        factory.register(uniqueType);
    
        // then
        Set<Generator> generators = factory.findBy(uniqueType.getType());
        assertThat(generators).hasSize(1);
    }

    @Test
    public void shouldReturnAllRegisteredGenerators() {

        // given
        Generator g1 = new StubGenerator("uno");
        Generator g2 = new StubGenerator("one");
        Generator g3 = new StubGenerator("ein");

        // when
        factory.register(g1);
        factory.register(g2);
        factory.register(g3);

        // then
        Set<Generator> generators = factory.findAll();
        assertThat(generators).hasSize(3);
        assertThat(generators).contains(g1, g2, g3);
    }

    @Before
    public void before() {
        // keep factory cleared before each test
        factory.unregister(uniqueType);
    }
}
