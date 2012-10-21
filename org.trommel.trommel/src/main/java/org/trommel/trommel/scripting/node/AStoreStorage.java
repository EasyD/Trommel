/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AStoreStorage extends PStorage
{
    private TStore _store_;
    private TInto _into_;
    private PHdfsFilePath _hdfsFilePath_;

    public AStoreStorage()
    {
        // Constructor
    }

    public AStoreStorage(
         TStore _store_,
         TInto _into_,
         PHdfsFilePath _hdfsFilePath_)
    {
        // Constructor
        setStore(_store_);

        setInto(_into_);

        setHdfsFilePath(_hdfsFilePath_);

    }

    @Override
    public Object clone()
    {
        return new AStoreStorage(
            cloneNode(this._store_),
            cloneNode(this._into_),
            cloneNode(this._hdfsFilePath_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStoreStorage(this);
    }

    public TStore getStore()
    {
        return this._store_;
    }

    public void setStore(TStore node)
    {
        if(this._store_ != null)
        {
            this._store_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._store_ = node;
    }

    public TInto getInto()
    {
        return this._into_;
    }

    public void setInto(TInto node)
    {
        if(this._into_ != null)
        {
            this._into_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._into_ = node;
    }

    public PHdfsFilePath getHdfsFilePath()
    {
        return this._hdfsFilePath_;
    }

    public void setHdfsFilePath(PHdfsFilePath node)
    {
        if(this._hdfsFilePath_ != null)
        {
            this._hdfsFilePath_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._hdfsFilePath_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._store_)
            + toString(this._into_)
            + toString(this._hdfsFilePath_);
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._store_ == child)
        {
            this._store_ = null;
            return;
        }

        if(this._into_ == child)
        {
            this._into_ = null;
            return;
        }

        if(this._hdfsFilePath_ == child)
        {
            this._hdfsFilePath_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._store_ == oldChild)
        {
            setStore((TStore) newChild);
            return;
        }

        if(this._into_ == oldChild)
        {
            setInto((TInto) newChild);
            return;
        }

        if(this._hdfsFilePath_ == oldChild)
        {
            setHdfsFilePath((PHdfsFilePath) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
