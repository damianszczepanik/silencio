package silencio;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import silencio.processors.AbstractConverter;
import silencio.processors.AbstractFormatter;

/**
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 * 
 */
public final class Builder {

    private List<AbstractConverter> converters = new ArrayList<>();

    @Setter
    private AbstractFormatter formatter;

    public Builder(AbstractConverter... converters) {
        this(null, converters);
    }

    public Builder(AbstractFormatter formatter) {
        this(formatter, (AbstractConverter[]) null);
    }

    public Builder(AbstractFormatter formatter, AbstractConverter... converters) {
        this.formatter = formatter;

        if (converters != null) {
            for (AbstractConverter converter : converters) {
                this.addConverter(converter);
            }
        }
    }
    public void addConverter(AbstractConverter converter) {
        this.converters.add(converter);
    }

    public void parse(Map content, Writer writter) {
        for (AbstractConverter converter : converters) {
            // TODO:
        }
        formatter.process(content, writter);
    }
}
