/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ALoadedFields extends PLoadedFields
{
    private TLeftParen _leftParen_;
    private PFieldList _fieldList_;
    private TRightParen _rightParen_;

    public ALoadedFields()
    {
        // Constructor
    }

    public ALoadedFields(
         TLeftParen _leftParen_,
         PFieldList _fieldList_,
         TRightParen _rightParen_)
    {
        // Constructor
        setLeftParen(_leftParen_);

        setFieldList(_fieldList_);

        setRightParen(_rightParen_);

    }

    @Override
    public Object clone()
    {
        return new ALoadedFields(
            cloneNode(this._leftParen_),
            cloneNode(this._fieldList_),
            cloneNode(this._rightParen_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALoadedFields(this);
    }

    public TLeftParen getLeftParen()
    {
        return this._leftParen_;
    }

    public void setLeftParen(TLeftParen node)
    {
        if(this._leftParen_ != null)
        {
            this._leftParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._leftParen_ = node;
    }

    public PFieldList getFieldList()
    {
        return this._fieldList_;
    }

    public void setFieldList(PFieldList node)
    {
        if(this._fieldList_ != null)
        {
            this._fieldList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fieldList_ = node;
    }

    public TRightParen getRightParen()
    {
        return this._rightParen_;
    }

    public void setRightParen(TRightParen node)
    {
        if(this._rightParen_ != null)
        {
            this._rightParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rightParen_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._leftParen_)
            + toString(this._fieldList_)
            + toString(this._rightParen_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._leftParen_ == child)
        {
            this._leftParen_ = null;
            return;
        }

        if(this._fieldList_ == child)
        {
            this._fieldList_ = null;
            return;
        }

        if(this._rightParen_ == child)
        {
            this._rightParen_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._leftParen_ == oldChild)
        {
            setLeftParen((TLeftParen) newChild);
            return;
        }

        if(this._fieldList_ == oldChild)
        {
            setFieldList((PFieldList) newChild);
            return;
        }

        if(this._rightParen_ == oldChild)
        {
            setRightParen((TRightParen) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
