// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Copyright (c) 2010 The JOMC Project
 *   Copyright (c) 2005 Christian Schulte <cs@jomc.org>
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     o Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     o Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE JOMC PROJECT AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *   THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *   PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE JOMC PROJECT OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *   OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *   WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *   OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *   ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *   $Id$
 *
 */
// </editor-fold>
// SECTION-END
package org.jomc.standalone.naming;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.naming.OperationNotSupportedException;
import javax.naming.spi.NamingManager;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone {@code Context} implementation.
 * <p><b>Dependencies</b><ul>
 * <li>"{@link #getLocale Locale}"<blockquote>
 * Dependency on {@code java.util.Locale} at specification level 1.1 bound to an instance.</blockquote></li>
 * <li>"{@link #getLogger Logger}"<blockquote>
 * Dependency on {@code org.jomc.logging.Logger} at specification level 1.0 bound to an instance.</blockquote></li>
 * </ul></p>
 * <p><b>Messages</b><ul>
 * <li>"{@link #getBindInfoMessage bindInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * </table>
 * <li>"{@link #getCreateSubcontextInfoMessage createSubcontextInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}''</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}''</pre></td></tr>
 * </table>
 * <li>"{@link #getDestroySubcontextInfoMessage destroySubcontextInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}''</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}''</pre></td></tr>
 * </table>
 * <li>"{@link #getRebindInfoMessage rebindInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * </table>
 * <li>"{@link #getRenameInfoMessage renameInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * </table>
 * <li>"{@link #getUnbindInfoMessage unbindInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
 * </table>
 * </ul></p>
 *
 * @author <a href="mailto:cs@jomc.org">Christian Schulte</a> 1.0
 * @version $Id$
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                             comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
// </editor-fold>
// SECTION-END
public class StandaloneContext implements Context
{
    // SECTION-START[Context]

    public Object lookup( final Name name ) throws NamingException
    {
        if ( name.isEmpty() )
        {
            final StandaloneContext shared = new StandaloneContext( this.getObjectMap() );
            shared.getEnvironment().putAll( this.getEnvironment() );
            return shared;
        }

        try
        {
            return NamingManager.getObjectInstance( this.getObjectMap().get( name ), name, this, this.getEnvironment() );
        }
        catch ( final Exception e )
        {
            final NamingException n = new NamingException( e.getMessage() );
            n.setRootCause( e );
            throw n;
        }
    }

    public Object lookup( final String name ) throws NamingException
    {
        return this.lookup( new CompositeName( name ) );
    }

    public void bind( final Name name, final Object obj ) throws NamingException
    {
        if ( this.getObjectMap().containsKey( name ) )
        {
            throw new NameAlreadyBoundException( name.toString() );
        }

        this.getObjectMap().put( name, NamingManager.getStateToBind( obj, name, this, this.getEnvironment() ) );
        this.getLogger().info( this.getBindInfoMessage( this.getLocale(), name.toString(),
                                                        obj != null ? obj.getClass().getName() : null ) );

    }

    public void bind( final String name, final Object obj ) throws NamingException
    {
        this.bind( new CompositeName( name ), obj );
    }

    public void rebind( final Name name, final Object obj ) throws NamingException
    {
        if ( name.isEmpty() )
        {
            throw new NamingException( name.toString() );
        }

        final Object oldObject = this.getObjectMap().get( name );
        this.getObjectMap().put( name, NamingManager.getStateToBind( obj, name, this, this.getEnvironment() ) );
        this.getLogger().info( this.getRebindInfoMessage( this.getLocale(), name.toString(),
                                                          oldObject != null ? oldObject.getClass().getName() : null,
                                                          obj != null ? obj.getClass().getName() : null ) );

    }

    public void rebind( final String name, final Object obj ) throws NamingException
    {
        this.rebind( new CompositeName( name ), obj );
    }

    public void unbind( final Name name ) throws NamingException
    {
        this.getObjectMap().remove( name );
        this.getLogger().info( this.getUnbindInfoMessage( this.getLocale(), name.toString() ) );
    }

    public void unbind( final String name ) throws NamingException
    {
        this.unbind( new CompositeName( name ) );
    }

    public synchronized void rename( final Name oldName, final Name newName ) throws NamingException
    {
        if ( oldName.isEmpty() )
        {
            throw new NamingException( oldName.toString() );
        }
        if ( newName.isEmpty() )
        {
            throw new NamingException( newName.toString() );
        }

        this.bind( newName, this.lookup( oldName ) );
        this.unbind( oldName );
        this.getLogger().info( this.getRenameInfoMessage( this.getLocale(), oldName.toString(), newName.toString() ) );
    }

    public void rename( final String oldName, final String newName ) throws NamingException
    {
        this.rename( new CompositeName( oldName ), new CompositeName( newName ) );
    }

    public NamingEnumeration<NameClassPair> list( final Name name ) throws NamingException
    {
        return new NamingEnumeration<NameClassPair>()
        {

            private Object next = lookup( name );

            public NameClassPair next() throws NamingException
            {
                if ( this.next == null )
                {
                    throw new NamingException();
                }

                final NameClassPair nameClassPair =
                    new NameClassPair( name.toString(), this.next.getClass().getName() );

                this.next = null;
                return nameClassPair;
            }

            public boolean hasMore() throws NamingException
            {
                return this.next != null;
            }

            public void close() throws NamingException
            {
                this.next = null;
            }

            public boolean hasMoreElements()
            {
                try
                {
                    return this.hasMore();
                }
                catch ( final NamingException e )
                {
                    throw new AssertionError( e );
                }
            }

            public NameClassPair nextElement()
            {
                try
                {
                    return this.next();
                }
                catch ( final NamingException e )
                {
                    throw new AssertionError( e );
                }
            }

        };
    }

    public NamingEnumeration<NameClassPair> list( final String name ) throws NamingException
    {
        return this.list( new CompositeName( name ) );
    }

    public NamingEnumeration<Binding> listBindings( final Name name ) throws NamingException
    {
        return new NamingEnumeration<Binding>()
        {

            private Object next = lookup( name );

            public Binding next() throws NamingException
            {
                if ( this.next == null )
                {
                    throw new NamingException();
                }

                final Binding binding = new Binding( name.toString(), this.next );
                this.next = null;
                return binding;
            }

            public boolean hasMore() throws NamingException
            {
                return this.next != null;
            }

            public void close() throws NamingException
            {
                this.next = null;
            }

            public boolean hasMoreElements()
            {
                try
                {
                    return this.hasMore();
                }
                catch ( final NamingException e )
                {
                    throw new AssertionError( e );
                }
            }

            public Binding nextElement()
            {
                try
                {
                    return this.next();
                }
                catch ( final NamingException e )
                {
                    throw new AssertionError( e );
                }
            }

        };
    }

    public NamingEnumeration<Binding> listBindings( final String name ) throws NamingException
    {
        return this.listBindings( new CompositeName( name ) );
    }

    public synchronized void destroySubcontext( final Name name ) throws NamingException
    {
        if ( name.isEmpty() )
        {
            throw new NamingException( name.toString() );
        }

        final Object o = this.getContextMap().get( name );
        if ( o == null )
        {
            throw new NameNotFoundException( name.toString() );
        }
        if ( !( o instanceof StandaloneContext ) )
        {
            throw new NotContextException( o.toString() );
        }
        if ( !( (StandaloneContext) o ).getObjectMap().isEmpty() )
        {
            throw new ContextNotEmptyException( name.toString() );
        }

        this.getContextMap().remove( name );
        this.getLogger().info( this.getDestroySubcontextInfoMessage( this.getLocale(), name.toString() ) );
    }

    public void destroySubcontext( final String name ) throws NamingException
    {
        this.destroySubcontext( new CompositeName( name ) );
    }

    public synchronized Context createSubcontext( final Name name ) throws NamingException
    {
        if ( name.isEmpty() )
        {
            throw new NamingException( name.toString() );
        }
        if ( this.getObjectMap().containsKey( name ) )
        {
            throw new NameAlreadyBoundException( name.toString() );
        }

        final StandaloneContext subcontext = new StandaloneContext();
        subcontext.getEnvironment().putAll( this.getEnvironment() );
        this.getContextMap().put( name, subcontext );
        this.getLogger().info( this.getCreateSubcontextInfoMessage( this.getLocale(), name.toString() ) );
        return subcontext;
    }

    public Context createSubcontext( final String name ) throws NamingException
    {
        return this.createSubcontext( new CompositeName( name ) );
    }

    public Object lookupLink( final Name name ) throws NamingException
    {
        return this.lookup( name );
    }

    public Object lookupLink( final String name ) throws NamingException
    {
        return this.lookupLink( new CompositeName( name ) );
    }

    public NameParser getNameParser( final Name name ) throws NamingException
    {
        return new NameParser()
        {

            public Name parse( final String name ) throws NamingException
            {
                return new CompositeName( name );
            }

        };
    }

    public NameParser getNameParser( final String name ) throws NamingException
    {
        return this.getNameParser( new CompositeName( name ) );
    }

    public Name composeName( final Name name, final Name prefix ) throws NamingException
    {
        return new CompositeName( prefix.toString() ).add( name.toString() );
    }

    public String composeName( final String name, final String prefix ) throws NamingException
    {
        return this.composeName( new CompositeName( name ), new CompositeName( prefix ) ).toString();
    }

    public Object addToEnvironment( final String propName, final Object propVal ) throws NamingException
    {
        if ( propName == null )
        {
            throw new NamingException();
        }
        if ( propVal == null )
        {
            throw new NamingException();
        }

        return this.getEnvironment().put( propName, propVal );
    }

    public Object removeFromEnvironment( final String propName ) throws NamingException
    {
        if ( propName == null )
        {
            throw new NamingException();
        }
        return this.getEnvironment().remove( propName );
    }

    public Hashtable getEnvironment() throws NamingException
    {
        if ( this.environment == null )
        {
            this.environment = new Hashtable();
        }

        return this.environment;
    }

    public void close() throws NamingException
    {
    }

    public String getNameInNamespace() throws NamingException
    {
        throw new OperationNotSupportedException();
    }

    // SECTION-END
    // SECTION-START[StandaloneContext]
    /** Sub-contexts of the instance. */
    private Map<Name, Context> contextMap;

    /** Objects of the instance. */
    private Map<Name, Object> objectMap;

    /** Environment of the instance. */
    private Hashtable<?, ?> environment;

    /**
     * Creates a new {@code StandaloneContext} taking a map backing the instance.
     *
     * @param objectMap The map backing the instance.
     */
    public StandaloneContext( final Map<Name, Object> objectMap )
    {
        super();
        this.objectMap = objectMap;
    }

    /**
     * Gets the object map backing the instance.
     *
     * @return The object map backing the instance.
     */
    protected Map<Name, Object> getObjectMap()
    {
        if ( this.objectMap == null )
        {
            this.objectMap = Collections.synchronizedMap( new HashMap<Name, Object>() );
        }

        return this.objectMap;
    }

    /**
     * Gets the context map backing the instance.
     *
     * @return The context map backing the instance.
     */
    protected Map<Name, Context> getContextMap()
    {
        if ( this.contextMap == null )
        {
            this.contextMap = Collections.synchronizedMap( new HashMap<Name, Context>() );
        }

        return this.contextMap;
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code StandaloneContext} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    public StandaloneContext()
    {
        // SECTION-START[Default Constructor]
        this( null );
        // SECTION-END
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Dependencies]
    // <editor-fold defaultstate="collapsed" desc=" Generated Dependencies ">

    /**
     * Gets the {@code Locale} dependency.
     * <p>This method returns the "{@code default}" object of the {@code java.util.Locale} specification at specification level 1.1.</p>
     * <p>That specification does not apply to any scope. A new object is returned whenever requested and bound to this instance.</p>
     * @return The {@code Locale} dependency.
     * @throws org.jomc.ObjectManagementException if getting the dependency instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private java.util.Locale getLocale()
    {
        final java.util.Locale _d = (java.util.Locale) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getDependency( this, "Locale" );
        assert _d != null : "'Locale' dependency not found.";
        return _d;
    }

    /**
     * Gets the {@code Logger} dependency.
     * <p>This method returns any available object of the {@code org.jomc.logging.Logger} specification at specification level 1.0.</p>
     * <p>That specification does not apply to any scope. A new object is returned whenever requested and bound to this instance.</p>
     * <p><b>Properties</b><dl>
     * <dt>"{@code name}"</dt>
     * <dd>Property of type {@code java.lang.String}.
     * </dd>
     * </dl>
     * @return The {@code Logger} dependency.
     * @throws org.jomc.ObjectManagementException if getting the dependency instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private org.jomc.logging.Logger getLogger()
    {
        final org.jomc.logging.Logger _d = (org.jomc.logging.Logger) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getDependency( this, "Logger" );
        assert _d != null : "'Logger' dependency not found.";
        return _d;
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Properties]
    // SECTION-END
    // SECTION-START[Messages]
    // <editor-fold defaultstate="collapsed" desc=" Generated Messages ">

    /**
     * Gets the text of the {@code bindInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param name Format argument.
     * @param object Format argument.
     * @return The text of the {@code bindInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private String getBindInfoMessage( final java.util.Locale locale, final java.lang.String name, final java.lang.String object )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "bindInfo", locale, name, object );
        assert _m != null : "'bindInfo' message not found.";
        return _m;
    }

    /**
     * Gets the text of the {@code createSubcontextInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}''</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}''</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param contextName Format argument.
     * @return The text of the {@code createSubcontextInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private String getCreateSubcontextInfoMessage( final java.util.Locale locale, final java.lang.String contextName )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "createSubcontextInfo", locale, contextName );
        assert _m != null : "'createSubcontextInfo' message not found.";
        return _m;
    }

    /**
     * Gets the text of the {@code destroySubcontextInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}''</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}''</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param contextName Format argument.
     * @return The text of the {@code destroySubcontextInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private String getDestroySubcontextInfoMessage( final java.util.Locale locale, final java.lang.String contextName )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "destroySubcontextInfo", locale, contextName );
        assert _m != null : "'destroySubcontextInfo' message not found.";
        return _m;
    }

    /**
     * Gets the text of the {@code rebindInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param name Format argument.
     * @param oldObject Format argument.
     * @param newObject Format argument.
     * @return The text of the {@code rebindInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private String getRebindInfoMessage( final java.util.Locale locale, final java.lang.String name, final java.lang.String oldObject, final java.lang.String newObject )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "rebindInfo", locale, name, oldObject, newObject );
        assert _m != null : "'rebindInfo' message not found.";
        return _m;
    }

    /**
     * Gets the text of the {@code renameInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param oldName Format argument.
     * @param newName Format argument.
     * @return The text of the {@code renameInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private String getRenameInfoMessage( final java.util.Locale locale, final java.lang.String oldName, final java.lang.String newName )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "renameInfo", locale, oldName, newName );
        assert _m != null : "'renameInfo' message not found.";
        return _m;
    }

    /**
     * Gets the text of the {@code unbindInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>''{0}'', ''{1}''</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param name Format argument.
     * @return The text of the {@code unbindInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-14-SNAPSHOT/jomc-tools" )
    private String getUnbindInfoMessage( final java.util.Locale locale, final java.lang.String name )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "unbindInfo", locale, name );
        assert _m != null : "'unbindInfo' message not found.";
        return _m;
    }
    // </editor-fold>
    // SECTION-END
}
