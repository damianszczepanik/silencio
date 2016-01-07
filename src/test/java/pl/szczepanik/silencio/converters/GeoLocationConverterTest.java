package pl.szczepanik.silencio.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.IntegrityException;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.stubs.StubObjectMapper;
import pl.szczepanik.silencio.utils.IOUtility;
import pl.szczepanik.silencio.utils.ReflectionUtility;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { IOUtility.class, Random.class })
public class GeoLocationConverterTest extends GenericTest {

    private static final String URL_ADDRESS_FORMAT = "http://maps.googleapis.com/maps/api/geocode/json?language=en&components=locality&latlng=%s,%s";

    @Test
    public void shouldWaitBetweenRequests() throws Exception {

        final long minDelay = 1000 / 10; // at least 1/10 secs

        // given
        GeoLocationConverter converter = new GeoLocationConverter();
        long startTime = System.currentTimeMillis();

        // when
        ReflectionUtility.invokeMethod(converter, "waitForNextLocation", Void.class);

        // then
        long endTime = System.currentTimeMillis();
        assertThat(endTime - startTime).isGreaterThan(minDelay);
    }

    @Test
    public void shouldGenerateValidUSLatitude() throws Exception {

        // given
        GeoLocationConverter converter = new GeoLocationConverter();

        // when
        String latitude = ReflectionUtility.invokeMethod(converter, "generateNextUSLatitude", String.class);

        // then
        String[] latitudeParts = latitude.split("\\.");
        int latitudeDegree = Integer.parseInt(latitudeParts[0]);
        int latitudeMiniute = Integer.parseInt(latitudeParts[1]);

        assertThat(latitudeDegree).isBetween(32, 50);
        assertThat(latitudeMiniute).isBetween(0, 100);
    }

    @Test
    public void shouldGenerateValidUSLongitude() throws Exception {

        // given
        GeoLocationConverter converter = new GeoLocationConverter();

        // when
        String longitude = ReflectionUtility.invokeMethod(converter, "generateNextUSLongitude", String.class);

        // then
        String[] longitudeParts = longitude.split("\\.");
        int longitudeDegree = Integer.parseInt(longitudeParts[0]);
        int longitudeMiniute = Integer.parseInt(longitudeParts[1]);

        assertThat(longitudeDegree).isBetween(-119, -83);
        assertThat(longitudeMiniute).isBetween(0, 100);
    }

    @Test
    public void shouldClearDataOnInit() {

        // given
        Converter converter = new GeoLocationConverter();
        Map<Object, Integer> values = new HashMap<>();
        values.put(this, 0);
        Set<String> words = new HashSet<>();
        words.add(null);

        ReflectionUtility.setField(converter, "values", values);
        ReflectionUtility.setField(converter, "words", words);

        // when
        converter.init();

        // then
        Map<Object, Integer> retValues = ReflectionUtility.getField(converter, "values", Map.class);
        assertThat(retValues).isEmpty();
        Set<String> retWords = ReflectionUtility.getField(converter, "words", Set.class);
        assertThat(retWords).isEmpty();
    }

    @Test
    public void shouldFailOnInvalidJSONResult() throws Exception {

        // given
        Converter converter = new GeoLocationConverter();

        GeoLocationJSON json = new GeoLocationJSON();
        final String status = "hello!";
        ReflectionUtility.setField(json, "status", status);

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage(String.format("Result status is '%s' but expected 'OK'", status));
        ReflectionUtility.invokeMethod(converter, "assertGeoIsValid", Void.class, json);
    }

    @Test
    public void shouldPassOnValidJSONResult() throws Exception {

        // given
        Converter converter = new GeoLocationConverter();

        GeoLocationJSON json = new GeoLocationJSON();
        final String status = "OK";
        ReflectionUtility.setField(json, "status", status);

        // then
        ReflectionUtility.invokeMethod(converter, "assertGeoIsValid", Void.class, json);
    }

    @Test
    public void convertReturnsValidItems() throws IOException {

        // given
        Converter converter = new GeoLocationConverter();
        input = ResourceLoader.loadJsonAsReader("suv.json");
        output = new StringWriter();
        Processor processor = new JSONProcessor();
        Execution execution = new Execution(new PositiveDecision(), converter);
        processor.setConfiguration(new Configuration(execution));
        processor.load(input);

        // when
        Random random = mock(Random.class);
        when(random.nextInt(100)).thenReturn(0);
        ReflectionUtility.setField(converter, "locationGenerator", random);

        mockStatic(IOUtility.class);
        when(IOUtility.urlToString(new URL(String.format(URL_ADDRESS_FORMAT, "32.0", "-83.0"))))
                .thenReturn(loadJson(1))
                .thenReturn(loadJson(2))
                .thenReturn(loadJson(3))
                .thenReturn(loadJson(4))
                .thenReturn(loadJson(5))
                .thenReturn(loadJson(5)) // one more time the same json
                .thenReturn(loadJson(6))
                .thenReturn(loadJson(7))
                .thenReturn(loadJson(8))
                .thenReturn(loadJson(9))
                .thenReturn(loadJson(10))
                .thenReturn(loadJson(11))
                .thenReturn(loadJson(12))
                .thenThrow(new IllegalArgumentException("Trying to parse more elements than expected!"));

        processor.process();

        // then
        processor.write(output);
        String reference = ResourceLoader.loadJsonAsString("suv_Positive_GeoLocation.json");
        assertThat(output.toString()).isEqualTo(reference);
    }

    @Test
    public void generateNextLocationFailsOnEmptyUrl() throws Exception {

        // given
        Converter converter = new GeoLocationConverter();

        final String latitude = "alaska";
        final String longitude = "greenland";
        final String url = String.format(URL_ADDRESS_FORMAT, latitude, longitude);

        // when
        mockStatic(IOUtility.class);
        when(IOUtility.urlToString(new URL(url))).thenReturn(StringUtils.EMPTY);

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage(String.format("URL '%s' returned empty content!", url));
        ReflectionUtility.invokeMethod(converter, "generateNextLocation", GeoLocationJSON.class, latitude, longitude);
    }

    @Test
    public void generateNextLocationFailsOnEmptyJson() throws Exception {

        // given
        Converter converter = new GeoLocationConverter();
        final String latitude = "32.0";
        final String longitude = "-83.0";
        final StubObjectMapper objectMapper = new StubObjectMapper((String) null);
        ReflectionUtility.setField(converter, "mapper", objectMapper);

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(String.format(GeoLocationConverter.EXCEPTION_MESSAGE_EMPTY_JSON, latitude, longitude));
        ReflectionUtility.invokeMethod(converter, "generateNextLocation", GeoLocationJSON.class, latitude, longitude);
    }

    @Test
    public void generateNextLocationFailsOnInvalidURL() throws Exception {

        // given
        Converter converter = new GeoLocationConverter();

        final String latitude = "alaska";
        final String longitude = "greenland";
        final String url = String.format(URL_ADDRESS_FORMAT, latitude, longitude);
        final MalformedURLException exception = new MalformedURLException("ups, something is wrong!");
        final StubObjectMapper objectMapper = new StubObjectMapper(exception);
        ReflectionUtility.setField(converter, "mapper", objectMapper);

        // when
        mockStatic(IOUtility.class);
        when(IOUtility.urlToString(new URL(url))).thenReturn("any string");

        // then
        thrown.expect(IntegrityException.class);
        thrown.expectMessage(exception.getMessage());
        ReflectionUtility.invokeMethod(converter, "generateNextLocation", GeoLocationJSON.class, latitude, longitude);
    }

    private static String loadJson(int index) {
        return ResourceLoader.loadJsonAsString(String.format("google-geo-location-%d.json", index));
    }
}
