/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class Start extends Node
{
    private PTrommelScript _pTrommelScript_;
    private EOF _eof_;

    public Start()
    {
        // Empty body
    }

    public Start(
         PTrommelScript _pTrommelScript_,
         EOF _eof_)
    {
        setPTrommelScript(_pTrommelScript_);
        setEOF(_eof_);
    }

    @Override
    public Object clone()
    {
        return new Start(
            cloneNode(this._pTrommelScript_),
            cloneNode(this._eof_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseStart(this);
    }

    public PTrommelScript getPTrommelScript()
    {
        return this._pTrommelScript_;
    }

    public void setPTrommelScript(PTrommelScript node)
    {
        if(this._pTrommelScript_ != null)
        {
            this._pTrommelScript_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._pTrommelScript_ = node;
    }

    public EOF getEOF()
    {
        return this._eof_;
    }

    public void setEOF(EOF node)
    {
        if(this._eof_ != null)
        {
            this._eof_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eof_ = node;
    }

    @Override
    void removeChild(Node child)
    {
        if(this._pTrommelScript_ == child)
        {
            this._pTrommelScript_ = null;
            return;
        }

        if(this._eof_ == child)
        {
            this._eof_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        if(this._pTrommelScript_ == oldChild)
        {
            setPTrommelScript((PTrommelScript) newChild);
            return;
        }

        if(this._eof_ == oldChild)
        {
            setEOF((EOF) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    public String toString()
    {
        return "" +
            toString(this._pTrommelScript_) +
            toString(this._eof_);
    }
}
