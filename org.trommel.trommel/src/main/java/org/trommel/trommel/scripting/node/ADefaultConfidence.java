/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ADefaultConfidence extends PConfidence
{
    private TConf _conf_;

    public ADefaultConfidence()
    {
        // Constructor
    }

    public ADefaultConfidence(
        @SuppressWarnings("hiding") TConf _conf_)
    {
        // Constructor
        setConf(_conf_);

    }

    @Override
    public Object clone()
    {
        return new ADefaultConfidence(
            cloneNode(this._conf_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADefaultConfidence(this);
    }

    public TConf getConf()
    {
        return this._conf_;
    }

    public void setConf(TConf node)
    {
        if(this._conf_ != null)
        {
            this._conf_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._conf_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._conf_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._conf_ == child)
        {
            this._conf_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._conf_ == oldChild)
        {
            setConf((TConf) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}