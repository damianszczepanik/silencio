package pl.szczepanik.silencio.processors.visitors;

import pl.szczepanik.silencio.core.Processable;

/**
 * Collects common methods and attributes used by visitors that goes over passed model and visitis all nodes.
 * 
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public abstract class AbstractVisitor {

    protected final Processable processable;

    public AbstractVisitor(Processable processable) {
        this.processable = processable;
    }
}
