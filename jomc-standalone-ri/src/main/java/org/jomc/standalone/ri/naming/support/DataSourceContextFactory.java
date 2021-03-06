// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Java Object Management and Configuration
 *   Copyright (C) Christian Schulte, 2005-206
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
 *   THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 *   INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 *   AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 *   THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *   NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *   THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *   $JOMC$
 *
 */
// </editor-fold>
// SECTION-END
package org.jomc.standalone.ri.naming.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jomc.standalone.ri.StandaloneEnvironment;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone data source context factory.
 *
 * <dl>
 *   <dt><b>Identifier:</b></dt><dd>org.jomc.standalone.ri.naming.support.DataSourceContextFactory</dd>
 *   <dt><b>Name:</b></dt><dd>JOMC Standalone RI DataSourceContextFactory</dd>
 *   <dt><b>Specifications:</b></dt>
 *     <dd>javax.naming.spi.InitialContextFactory</dd>
 *   <dt><b>Abstract:</b></dt><dd>No</dd>
 *   <dt><b>Final:</b></dt><dd>No</dd>
 *   <dt><b>Stateless:</b></dt><dd>No</dd>
 * </dl>
 *
 * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a> 1.0
 * @version 1.0-beta-3-SNAPSHOT
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2" )
// </editor-fold>
// SECTION-END
public class DataSourceContextFactory extends AbstractContextFactory
{
    // SECTION-START[InitialContextFactory]

    public Context getInitialContext( final Hashtable environment ) throws NamingException
    {
        try
        {
            this.bindDataSource();
            return null;
        }
        catch ( final ClassNotFoundException e )
        {
            throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
        }
        catch ( final InstantiationException e )
        {
            throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
        }
        catch ( final IllegalAccessException e )
        {
            throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
        }
        catch ( final InvocationTargetException e )
        {
            throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
        }
    }

    // SECTION-END
    // SECTION-START[DataSourceContextFactory]
    private static final Class<?>[] NO_CLASSES =
    {
    };

    private void bindDataSource()
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, NamingException,
               InvocationTargetException
    {
        final Object dataSource = Class.forName(
            this.getStandaloneEnvironment().getDataSourceClassName() ).newInstance();

        this.setupDataSource( dataSource );
        this.getStandaloneContext().bind( this.getStandaloneEnvironment().getDataSourceJndiName(), dataSource );
        this.getStandaloneContext().bind( this.getStandaloneEnvironment().getJtaDataSourceJndiName(), dataSource );
    }

    private void setupDataSource( final Object dataSource )
        throws NamingException, IllegalAccessException, InvocationTargetException
    {
        for ( Map.Entry<?, ?> p : this.getStandaloneEnvironment().getProperties().entrySet() )
        {
            if ( p.getKey().toString().startsWith( StandaloneEnvironment.DATA_SOURCE_PREFIX )
                 && !p.getKey().toString().equals( StandaloneEnvironment.DATA_SOURCE_JNDI_NAME )
                 && !p.getKey().toString().equals( StandaloneEnvironment.DATA_SOURCE_CLASS_NAME )
                 && !p.getKey().toString().equals( StandaloneEnvironment.DATA_SOURCE_CONTEXT_FACTORY_NAME ) )
            {
                final char[] chars =
                    p.getKey().toString().substring( StandaloneEnvironment.DATA_SOURCE_PREFIX.length() ).toCharArray();

                chars[0] = Character.toUpperCase( chars[0] );
                final String propertyName = String.valueOf( chars );

                Method getter = getMethod( dataSource.getClass(), "get" + propertyName, NO_CLASSES );
                if ( getter == null )
                {
                    getter = getMethod( dataSource.getClass(), "is" + propertyName, NO_CLASSES );
                }

                Class<?> propertyType = String.class;
                if ( getter != null )
                {
                    propertyType = getter.getReturnType();
                }

                final Method setter = getMethod( dataSource.getClass(), "set" + propertyName, new Class<?>[]
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

    private static Method getMethod( final Class<?> clazz, final String name, final Class<?>[] arguments )
        throws NamingException
    {
        try
        {
            return clazz.getMethod( name, arguments );
        }
        catch ( final NoSuchMethodException e )
        {
            return null;
        }
        catch ( final SecurityException e )
        {
            throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
        }
    }

    private static String getMessage( final Throwable t )
    {
        return t != null ? t.getMessage() != null ? t.getMessage() : getMessage( t.getCause() ) : null;
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">
    /** Creates a new {@code DataSourceContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2" )
    public DataSourceContextFactory()
    {
        // SECTION-START[Default Constructor]
        super();
        // SECTION-END
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Dependencies]
    // SECTION-END
    // SECTION-START[Properties]
    // SECTION-END
    // SECTION-START[Messages]
    // SECTION-END
}
