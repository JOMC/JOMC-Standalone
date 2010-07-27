// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Copyright (c) 2010 The JOMC Project
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
// </editor-fold>
// SECTION-END
package org.jomc.standalone.ri.naming;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.jomc.standalone.ri.StandaloneEnvironment;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone {@code InitialContextFactory} implementation.
 * <p><b>Specifications</b><ul>
 * <li>{@code 'javax.naming.spi.InitialContextFactory'} {@code (javax.naming.spi.InitialContextFactory)} {@code Multiton}</li>
 * </ul></p>
 *
 * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a> 1.0
 * @version $Id$
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.1", comments = "See http://jomc.sourceforge.net/jomc/1.1/jomc-tools-1.1" )
// </editor-fold>
// SECTION-END
public class StandaloneContextFactory implements InitialContextFactory
{
    // SECTION-START[InitialContextFactory]

    private static Context instance;

    public Context getInitialContext( final Hashtable env ) throws NamingException
    {
        synchronized ( StandaloneContextFactory.class )
        {
            try
            {
                if ( instance == null )
                {
                    instance = new StandaloneContext();

                    if ( env != null )
                    {
                        System.getProperties().putAll( env );
                        instance.getEnvironment().putAll( env );

                        if ( this.getStandaloneEnvironment().getDataSourceContextFactoryName() != null )
                        {
                            final InitialContextFactory dataSourceContextFactory = (InitialContextFactory) Class.forName(
                                this.getStandaloneEnvironment().getDataSourceContextFactoryName() ).newInstance();

                            dataSourceContextFactory.getInitialContext( env );
                        }
                        if ( this.getStandaloneEnvironment().getJtaContextFactoryName() != null )
                        {
                            final InitialContextFactory jtaContextFactory = (InitialContextFactory) Class.forName(
                                this.getStandaloneEnvironment().getJtaContextFactoryName() ).newInstance();

                            jtaContextFactory.getInitialContext( env );
                        }
                        if ( this.getStandaloneEnvironment().getJpaContextFactoryName() != null )
                        {
                            final InitialContextFactory jpaContextFactory = (InitialContextFactory) Class.forName(
                                this.getStandaloneEnvironment().getJpaContextFactoryName() ).newInstance();

                            jpaContextFactory.getInitialContext( env );
                        }

                        this.isXAPoolInitialized();
                    }
                }

                return instance;
            }
            catch ( final InstantiationException e )
            {
                throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
            }
            catch ( final IllegalAccessException e )
            {
                throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
            }
            catch ( final ClassNotFoundException e )
            {
                throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
            }
        }
    }

    // SECTION-END
    // SECTION-START[StandaloneContextFactory]
    private StandaloneEnvironment standaloneEnvironment;

    public StandaloneEnvironment getStandaloneEnvironment()
    {
        if ( this.standaloneEnvironment == null )
        {
            this.standaloneEnvironment = new StandaloneEnvironment();
        }

        return this.standaloneEnvironment;
    }

    protected boolean isXAPoolInitialized()
    {
        try
        {
            Class.forName( "org.enhydra.jdbc.standard.StandardXADataSource" );
            final Class<?> helperClass = Class.forName( "org.jomc.standalone.ri.naming.support.XAPoolHelper" );
            final Method helperMethod = helperClass.getDeclaredMethod(
                "initializeXAPool", StandaloneEnvironment.class, Context.class );

            helperMethod.invoke( null, this.getStandaloneEnvironment(), instance );
            return true;
        }
        catch ( final IllegalAccessException e )
        {
            return false;
        }
        catch ( final InvocationTargetException e )
        {
            return false;
        }
        catch ( final NoSuchMethodException e )
        {
            return false;
        }
        catch ( final ClassNotFoundException e )
        {
            return false;
        }
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code StandaloneContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.1", comments = "See http://jomc.sourceforge.net/jomc/1.1/jomc-tools-1.1" )
    public StandaloneContextFactory()
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
