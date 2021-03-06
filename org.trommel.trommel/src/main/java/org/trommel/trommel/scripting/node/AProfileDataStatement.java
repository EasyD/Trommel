/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AProfileDataStatement extends PProfileDataStatement
{
    private TProfile _profile_;
    private PProfiledFields _profiledFields_;
    private TWith _with_;
    private PProfilers _profilers_;
    private PStorage _storage_;
    private TSemicolon _semicolon_;

    public AProfileDataStatement()
    {
        // Constructor
    }

    public AProfileDataStatement(
         TProfile _profile_,
         PProfiledFields _profiledFields_,
         TWith _with_,
         PProfilers _profilers_,
         PStorage _storage_,
         TSemicolon _semicolon_)
    {
        // Constructor
        setProfile(_profile_);

        setProfiledFields(_profiledFields_);

        setWith(_with_);

        setProfilers(_profilers_);

        setStorage(_storage_);

        setSemicolon(_semicolon_);

    }

    @Override
    public Object clone()
    {
        return new AProfileDataStatement(
            cloneNode(this._profile_),
            cloneNode(this._profiledFields_),
            cloneNode(this._with_),
            cloneNode(this._profilers_),
            cloneNode(this._storage_),
            cloneNode(this._semicolon_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAProfileDataStatement(this);
    }

    public TProfile getProfile()
    {
        return this._profile_;
    }

    public void setProfile(TProfile node)
    {
        if(this._profile_ != null)
        {
            this._profile_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._profile_ = node;
    }

    public PProfiledFields getProfiledFields()
    {
        return this._profiledFields_;
    }

    public void setProfiledFields(PProfiledFields node)
    {
        if(this._profiledFields_ != null)
        {
            this._profiledFields_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._profiledFields_ = node;
    }

    public TWith getWith()
    {
        return this._with_;
    }

    public void setWith(TWith node)
    {
        if(this._with_ != null)
        {
            this._with_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._with_ = node;
    }

    public PProfilers getProfilers()
    {
        return this._profilers_;
    }

    public void setProfilers(PProfilers node)
    {
        if(this._profilers_ != null)
        {
            this._profilers_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._profilers_ = node;
    }

    public PStorage getStorage()
    {
        return this._storage_;
    }

    public void setStorage(PStorage node)
    {
        if(this._storage_ != null)
        {
            this._storage_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._storage_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._profile_)
            + toString(this._profiledFields_)
            + toString(this._with_)
            + toString(this._profilers_)
            + toString(this._storage_)
            + toString(this._semicolon_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._profile_ == child)
        {
            this._profile_ = null;
            return;
        }

        if(this._profiledFields_ == child)
        {
            this._profiledFields_ = null;
            return;
        }

        if(this._with_ == child)
        {
            this._with_ = null;
            return;
        }

        if(this._profilers_ == child)
        {
            this._profilers_ = null;
            return;
        }

        if(this._storage_ == child)
        {
            this._storage_ = null;
            return;
        }

        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._profile_ == oldChild)
        {
            setProfile((TProfile) newChild);
            return;
        }

        if(this._profiledFields_ == oldChild)
        {
            setProfiledFields((PProfiledFields) newChild);
            return;
        }

        if(this._with_ == oldChild)
        {
            setWith((TWith) newChild);
            return;
        }

        if(this._profilers_ == oldChild)
        {
            setProfilers((PProfilers) newChild);
            return;
        }

        if(this._storage_ == oldChild)
        {
            setStorage((PStorage) newChild);
            return;
        }

        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
