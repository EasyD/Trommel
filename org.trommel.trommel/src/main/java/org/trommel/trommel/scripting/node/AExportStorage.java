/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AExportStorage extends PStorage
{
    private TExport _export_;
    private TTo _to_;
    private PLocalFilePath _localFilePath_;

    public AExportStorage()
    {
        // Constructor
    }

    public AExportStorage(
         TExport _export_,
         TTo _to_,
         PLocalFilePath _localFilePath_)
    {
        // Constructor
        setExport(_export_);

        setTo(_to_);

        setLocalFilePath(_localFilePath_);

    }

    @Override
    public Object clone()
    {
        return new AExportStorage(
            cloneNode(this._export_),
            cloneNode(this._to_),
            cloneNode(this._localFilePath_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAExportStorage(this);
    }

    public TExport getExport()
    {
        return this._export_;
    }

    public void setExport(TExport node)
    {
        if(this._export_ != null)
        {
            this._export_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._export_ = node;
    }

    public TTo getTo()
    {
        return this._to_;
    }

    public void setTo(TTo node)
    {
        if(this._to_ != null)
        {
            this._to_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._to_ = node;
    }

    public PLocalFilePath getLocalFilePath()
    {
        return this._localFilePath_;
    }

    public void setLocalFilePath(PLocalFilePath node)
    {
        if(this._localFilePath_ != null)
        {
            this._localFilePath_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._localFilePath_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._export_)
            + toString(this._to_)
            + toString(this._localFilePath_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._export_ == child)
        {
            this._export_ = null;
            return;
        }

        if(this._to_ == child)
        {
            this._to_ = null;
            return;
        }

        if(this._localFilePath_ == child)
        {
            this._localFilePath_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._export_ == oldChild)
        {
            setExport((TExport) newChild);
            return;
        }

        if(this._to_ == oldChild)
        {
            setTo((TTo) newChild);
            return;
        }

        if(this._localFilePath_ == oldChild)
        {
            setLocalFilePath((PLocalFilePath) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
