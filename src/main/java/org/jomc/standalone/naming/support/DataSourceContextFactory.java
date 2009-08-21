// SECTION-START[License Header]
/*
 *   Copyright (c) 2009 The JOMC Project
 *   Copyright (c) 2005 Christian Schulte <schulte2005@users.sourceforge.net>
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
// SECTION-END
package org.jomc.standalone.naming.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.jomc.standalone.Environment;

// SECTION-START[Documentation]
/**
 * Standalone data source context factory.
 * <p><b>Specifications</b><ul>
 * <li>{@code javax.naming.spi.InitialContextFactory}</li>
 * </ul></p>
 * <p><b>Dependencies</b><ul>
 * <li>"{@link #getLocale Locale}"<blockquote>
 * Dependency on {@code java.util.Locale} at specification level 1.1 bound to an instance.</blockquote></li>
 * <li>"{@link #getLogger Logger}"<blockquote>
 * Dependency on {@code org.jomc.logging.Logger} at specification level 1.0 bound to an instance.</blockquote></li>
 * </ul></p>
 * <p><b>Messages</b><ul>
 * <li>"{@link #getImplementationInfoMessage implementationInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>DataSourceContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>DataSourceContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
 * </table>
 * </ul></p>
 *
 * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a> 1.0
 * @version $Id$
 */
// SECTION-END
// SECTION-START[Annotations]
@javax.annotation.Generated
(
    value = "org.jomc.tools.JavaSources",
    comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-1-SNAPSHOT/jomc-tools"
)
// SECTION-END
public class DataSourceContextFactory extends AbstractContextFactory implements InitialContextFactory
{
    // SECTION-START[InitialContextFactory]

    public Context getInitialContext( final Hashtable environment ) throws NamingException
    {
        try
        {
            this.bindDataSource();
            return null;
        }
        catch ( ClassNotFoundException e )
        {
            this.getLogger().fatal( e );
            throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
        }
        catch ( InstantiationException e )
        {
            this.getLogger().fatal( e );
            throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
        }
        catch ( IllegalAccessException e )
        {
            this.getLogger().fatal( e );
            throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
        }
        catch ( InvocationTargetException e )
        {
            this.getLogger().fatal( e );
            throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
        }
    }

    // SECTION-END
    // SECTION-START[DataSourceContextFactory]
    private static final Class[] NO_CLASSES =
    {
    };

    private void bindDataSource()
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, NamingException,
               InvocationTargetException
    {
        final Object dataSource = Class.forName( this.getEnvironment().getDataSourceClassName() ).newInstance();
        this.setupDataSource( dataSource );
        this.getContext().bind( this.getEnvironment().getDataSourceJndiName(), dataSource );
        this.getContext().bind( this.getEnvironment().getJtaDataSourceJndiName(), dataSource );
    }

    protected void setupDataSource( final Object dataSource )
        throws NamingException, IllegalAccessException, InvocationTargetException
    {
        for ( Map.Entry p : this.getEnvironment().getProperties().entrySet() )
        {
            if ( p.getKey().toString().startsWith( Environment.DATA_SOURCE_PREFIX ) &&
                 !p.getKey().toString().equals( Environment.DATA_SOURCE_JNDI_NAME ) &&
                 !p.getKey().toString().equals( Environment.DATA_SOURCE_CLASS_NAME ) &&
                 !p.getKey().toString().equals( Environment.DATA_SOURCE_CONTEXT_FACTORY_NAME ) )
            {
                final char[] chars =
                    p.getKey().toString().substring( Environment.DATA_SOURCE_PREFIX.length() ).toCharArray();

                chars[0] = Character.toUpperCase( chars[0] );
                final String propertyName = String.valueOf( chars );

                Method getter = this.getMethod( dataSource.getClass(), "get" + propertyName, NO_CLASSES );
                if ( getter == null )
                {
                    getter = this.getMethod( dataSource.getClass(), "is" + propertyName, NO_CLASSES );
                }

                Class propertyType = String.class;
                if ( getter != null )
                {
                    propertyType = getter.getReturnType();
                }

                Method setter = this.getMethod( dataSource.getClass(), "set" + propertyName, new Class[]
                    {
                        propertyType
                    } );

                if ( setter != null )
                {
                    if ( propertyType == Boolean.class || propertyType == Boolean.TYPE )
                    {
                        setter.invoke( dataSource, Boolean.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == Byte.class || propertyType == Byte.TYPE )
                    {
                        setter.invoke( dataSource, Byte.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == Character.class || propertyType == Character.TYPE )
                    {
                        setter.invoke( dataSource, Character.valueOf( p.getValue().toString().charAt( 0 ) ) );
                    }
                    else if ( propertyType == Double.class || propertyType == Double.TYPE )
                    {
                        setter.invoke( dataSource, Double.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == Float.class || propertyType == Float.TYPE )
                    {
                        setter.invoke( dataSource, Float.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == Integer.class || propertyType == Integer.TYPE )
                    {
                        setter.invoke( dataSource, Integer.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == Long.class || propertyType == Long.TYPE )
                    {
                        setter.invoke( dataSource, Long.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == Short.class || propertyType == Short.TYPE )
                    {
                        setter.invoke( dataSource, Short.valueOf( p.getValue().toString() ) );
                    }
                    else if ( propertyType == BigInteger.class )
                    {
                        setter.invoke( dataSource, new BigInteger( p.getValue().toString() ) );
                    }
                    else if ( propertyType == BigDecimal.class )
                    {
                        setter.invoke( dataSource, new BigDecimal( p.getValue().toString() ) );
                    }
                    else if ( propertyType == String.class )
                    {
                        setter.invoke( dataSource, p.getValue().toString() );
                    }
                }
            }
        }
    }

    private Method getMethod( final Class clazz, final String name, final Class[] arguments ) throws NamingException
    {
        try
        {
            return clazz.getMethod( name, arguments );
        }
        catch ( NoSuchMethodException e )
        {
            return null;
        }
        catch ( SecurityException e )
        {
            this.getLogger().fatal( e );
            throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
        }
    }

    // SECTION-END
    // SECTION-START[Constructors]

    /** Creates a new {@code DataSourceContextFactory} instance. */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-1-SNAPSHOT/jomc-tools"
    )
    public DataSourceContextFactory()
    {
        // SECTION-START[Default Constructor]
        super();
        this.getLogger().info( this.getImplementationInfoMessage( this.getLocale() ) );
        // SECTION-END
    }
    // SECTION-END
    // SECTION-START[Dependencies]

    /**
     * Gets the {@code Locale} dependency.
     * <p>This method returns the "{@code default}" object of the {@code java.util.Locale} specification at specification level 1.1.</p>
     * @return The {@code Locale} dependency.
     * @throws org.jomc.ObjectManagementException if getting the dependency instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-1-SNAPSHOT/jomc-tools"
    )
    private java.util.Locale getLocale() throws org.jomc.ObjectManagementException
    {
        return (java.util.Locale) org.jomc.ObjectManagerFactory.getObjectManager().getDependency( this, "Locale" );
    }

    /**
     * Gets the {@code Logger} dependency.
     * <p>This method returns any available object of the {@code org.jomc.logging.Logger} specification at specification level 1.0.</p>
     * <p><b>Properties</b><dl>
     * <dt>"{@code name}"</dt>
     * <dd>Property of type {@code java.lang.String} with value "org.jomc.standalone.naming.support.DataSourceContextFactory".
     * </dd>
     * </dl>
     * @return The {@code Logger} dependency.
     * @throws org.jomc.ObjectManagementException if getting the dependency instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-1-SNAPSHOT/jomc-tools"
    )
    private org.jomc.logging.Logger getLogger() throws org.jomc.ObjectManagementException
    {
        return (org.jomc.logging.Logger) org.jomc.ObjectManagerFactory.getObjectManager().getDependency( this, "Logger" );
    }
    // SECTION-END
    // SECTION-START[Properties]
    // SECTION-END
    // SECTION-START[Messages]

    /**
     * Gets the text of the {@code implementationInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>DataSourceContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>DataSourceContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @return The text of the {@code implementationInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-1-SNAPSHOT/jomc-tools"
    )
    private String getImplementationInfoMessage( final java.util.Locale locale ) throws org.jomc.ObjectManagementException
    {
        return org.jomc.ObjectManagerFactory.getObjectManager().getMessage( this, "implementationInfo", locale,  null );
    }
    // SECTION-END
}
