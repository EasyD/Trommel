/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ASampleDataTrommelScript extends PTrommelScript
{
    private PSampleDataStatement _sampleDataStatement_;

    public ASampleDataTrommelScript()
    {
        // Constructor
    }

    public ASampleDataTrommelScript(
         PSampleDataStatement _sampleDataStatement_)
    {
        // Constructor
        setSampleDataStatement(_sampleDataStatement_);

    }

    @Override
    public Object clone()
    {
        return new ASampleDataTrommelScript(
            cloneNode(this._sampleDataStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASampleDataTrommelScript(this);
    }

    public PSampleDataStatement getSampleDataStatement()
    {
        return this._sampleDataStatement_;
    }

    public void setSampleDataStatement(PSampleDataStatement node)
    {
        if(this._sampleDataStatement_ != null)
        {
            this._sampleDataStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._sampleDataStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._sampleDataStatement_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._sampleDataStatement_ == child)
        {
            this._sampleDataStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._sampleDataStatement_ == oldChild)
        {
            setSampleDataStatement((PSampleDataStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
