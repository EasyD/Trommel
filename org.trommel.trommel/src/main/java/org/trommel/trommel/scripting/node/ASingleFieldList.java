/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ASingleFieldList extends PFieldList
{
    private PField _field_;

    public ASingleFieldList()
    {
        // Constructor
    }

    public ASingleFieldList(
         PField _field_)
    {
        // Constructor
        setField(_field_);

    }

    @Override
    public Object clone()
    {
        return new ASingleFieldList(
            cloneNode(this._field_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASingleFieldList(this);
    }

    public PField getField()
    {
        return this._field_;
    }

    public void setField(PField node)
    {
        if(this._field_ != null)
        {
            this._field_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._field_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._field_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._field_ == child)
        {
            this._field_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._field_ == oldChild)
        {
            setField((PField) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
