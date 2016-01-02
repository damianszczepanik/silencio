package pl.szczepanik.silencio.core;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.converters.BlankConverter;
import pl.szczepanik.silencio.decisions.NegativeDecision;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.stubs.StubConverter;
import pl.szczepanik.silencio.stubs.StubFormat;
import pl.szczepanik.silencio.utils.ReflectionUtility;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class BuilderTest extends GenericTest {

    @Test
    public void shouldHoldPassedFormat() {

        // given
        Format format = new StubFormat("builderFormat");

        // when
        Builder builder = new Builder(format);

        // then
        Format retFormat = ReflectionUtility.getField(builder, "format", Format.class);
        assertThat(format).isEqualTo(retFormat);
    }

    @Test
    public void shouldAppendPassedValues_NxN() {

        // given
        Format format = Format.JSON;
        Converter[] converters = { Builder.NUMBER_SEQUENCE, new StubConverter() };
        Decision[] decisions = { new PositiveDecision(), new NegativeDecision() };

        // when
        Builder builder = new Builder(format).with(decisions, converters);

        // then
        List<Execution> executions = ReflectionUtility.getField(builder, "executions", List.class);
        assertThat(executions.get(0).getDecisions().length).isEqualTo(decisions.length);
        assertThat(executions.get(0).getDecisions()).isEqualTo(decisions);
        assertThat(executions.get(0).getConverters().length).isEqualTo(converters.length);
        assertThat(executions.get(0).getConverters()).isEqualTo(converters);
    }

    @Test
    public void shouldAppendPassedValues_1xN() {

        // given
        Format format = Format.JSON;
        Converter[] converters = { Builder.NUMBER_SEQUENCE, new StubConverter() };
        Decision decision = new NegativeDecision();

        // when
        Builder builder = new Builder(format).with(decision, converters[0], converters[1]);

        // then
        List<Execution> executions = ReflectionUtility.getField(builder, "executions", List.class);
        assertThat(executions.get(0).getDecisions().length).isEqualTo(1);
        assertThat(executions.get(0).getDecisions()[0]).isEqualTo(decision);
        assertThat(executions.get(0).getConverters().length).isEqualTo(converters.length);
        assertThat(executions.get(0).getConverters()).isEqualTo(converters);
    }

    @Test
    public void shouldAppendPassedValues_1x1() {

        // given
        Format format = Format.JSON;
        Decision decision = new NegativeDecision();
        Converter converter = new StubConverter();
        Builder builder = new Builder(format).with(new PositiveDecision(), new BlankConverter());

        // when
        builder.with(decision, converter);

        // then
        List<Execution> executions = ReflectionUtility.getField(builder, "executions", List.class);
        assertThat(executions).hasSize(2);

        Execution appendExecution = executions.get(1);
        assertThat(appendExecution.getDecisions().length).isEqualTo(1);
        assertThat(appendExecution.getDecisions()[0]).isEqualTo(decision);
        assertThat(appendExecution.getConverters().length).isEqualTo(1);
        assertThat(appendExecution.getConverters()[0]).isEqualTo(converter);
    }

    @Test
    public void shouldAppendPassedValues_0xN() {

        // given
        Format format = Format.JSON;
        Converter[] converters = { Builder.NUMBER_SEQUENCE, new StubConverter() };
        Builder builder = new Builder(format);

        // when
        builder.with(converters[0], converters[1]);

        // then
        List<Execution> executions = ReflectionUtility.getField(builder, "executions", List.class);
        assertThat(executions.get(0).getDecisions().length).isEqualTo(1);
        assertThat(executions.get(0).getDecisions()[0]).isInstanceOf(PositiveDecision.class);
        assertThat(executions.get(0).getConverters().length).isEqualTo(converters.length);
        assertThat(executions.get(0).getConverters()).isEqualTo(converters);
    }

    @Test
    public void shouldAppendPassedValues_0x1() {

        // given
        Format format = Format.JSON;
        Converter converter = new StubConverter();
        Builder builder = new Builder(format);

        // when
        builder.with(converter);

        // then
        List<Execution> executions = ReflectionUtility.getField(builder, "executions", List.class);
        assertThat(executions.get(0).getDecisions().length).isEqualTo(1);
        assertThat(executions.get(0).getDecisions()[0]).isInstanceOf(PositiveDecision.class);
        assertThat(executions.get(0).getConverters().length).isEqualTo(1);
        assertThat(executions.get(0).getConverters()[0]).isEqualTo(converter);
    }

    @Test
    public void shouldFailWhenPassingInvalidFormat() {

        // when
        Format format = new StubFormat("tr!cky");

        // when
        Builder builder = new Builder(format);

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage("Unsupported format: " + format.getName());
        builder.build();
    }

    @Test
    public void shouldClearExecutions() {
        
        // given
        Builder builder = new Builder(Format.XML);
        builder.with(new NegativeDecision(), Builder.BLANK);

        // when
        builder.build();
        builder.clearExecutions();

        // then
        List<Execution> executions = ReflectionUtility.getField(builder, "executions", List.class);
        assertThat(executions).isEmpty();
    }
}
