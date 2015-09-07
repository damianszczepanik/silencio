package pl.szczepanik.silencio.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public class JSONorXMLtoMap {

    public static final String xml = "<A><B>b</B><B>b</B><C>c</C></A>";

    @JacksonXmlRootElement(localName = "A")
    public static class POJO {

        private final Map<String, String> map = new TreeMap<String, String>();

        @JsonAnyGetter
        public Map<String, String> get() {
            return map;
        }

        @JsonAnySetter
        public void set(String key, String value) {
            map.put(key, value);
        }
    }

    @Test
    public final void test() throws JsonProcessingException, IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlmapper = new XmlMapper(module);

        POJO p2 = xmlmapper.readValue(xml, POJO.class);
        System.out.println(xmlmapper.writeValueAsString(p2));
        assertEquals(xmlmapper.writeValueAsString(p2), xml);

    }

}