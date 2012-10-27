/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AParmConfidence extends PConfidence
{
    private TConf _conf_;
    private TLeftParen _leftParen_;
    private TInteger _integer_;
    private TRightParen _rightParen_;

    public AParmConfidence()
    {
        // Constructor
    }

    public AParmConfidence(
         TConf _conf_,
         TLeftParen _leftParen_,
         TInteger _integer_,
         TRightParen _rightParen_)
    {
        // Constructor
        setConf(_conf_);

        setLeftParen(_leftParen_);

        setInteger(_integer_);

        setRightParen(_rightParen_);

    }

    @Override
    public Object clone()
    {
        return new AParmConfidence(
            cloneNode(this._conf_),
            cloneNode(this._leftParen_),
            cloneNode(this._integer_),
            cloneNode(this._rightParen_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAParmConfidence(this);
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

    public TInteger getInteger()
    {
        return this._integer_;
    }

    public void setInteger(TInteger node)
    {
        if(this._integer_ != null)
        {
            this._integer_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._integer_ = node;
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
            + toString(this._conf_)
            + toString(this._leftParen_)
            + toString(this._integer_)
            + toString(this._rightParen_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._conf_ == child)
        {
            this._conf_ = null;
            return;
        }

        if(this._leftParen_ == child)
        {
            this._leftParen_ = null;
            return;
        }

        if(this._integer_ == child)
        {
            this._integer_ = null;
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
        if(this._conf_ == oldChild)
        {
            setConf((TConf) newChild);
            return;
        }

        if(this._leftParen_ == oldChild)
        {
            setLeftParen((TLeftParen) newChild);
            return;
        }

        if(this._integer_ == oldChild)
        {
            setInteger((TInteger) newChild);
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
