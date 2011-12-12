// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
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
 *
 * <p>
 *   This implementation is identified by {@code <org.jomc.standalone.ri.naming.StandaloneContextFactory>}.
 * </p>
 * <p>
 *   It provides objects named {@code <JOMC Standalone RI StandaloneContextFactory>} of the following specifications:
 *
 *   <ul>
 *     <li>{@code <javax.naming.spi.InitialContextFactory>} at any specification level.</li>
 *   </ul>
 * </p>
 * <dl>
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
@javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
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
                            final InitialContextFactory dataSourceContextFactory = Class.forName(
                                this.getStandaloneEnvironment().getDataSourceContextFactoryName() ).
                                asSubclass( InitialContextFactory.class ).newInstance();

                            dataSourceContextFactory.getInitialContext( env );
                        }
                        if ( this.getStandaloneEnvironment().getJtaContextFactoryName() != null )
                        {
                            final InitialContextFactory jtaContextFactory = Class.forName(
                                this.getStandaloneEnvironment().getJtaContextFactoryName() ).
                                asSubclass( InitialContextFactory.class ).newInstance();

                            jtaContextFactory.getInitialContext( env );
                        }
                        if ( this.getStandaloneEnvironment().getJpaContextFactoryName() != null )
                        {
                            final InitialContextFactory jpaContextFactory = Class.forName(
                                this.getStandaloneEnvironment().getJpaContextFactoryName() ).
                                asSubclass( InitialContextFactory.class ).newInstance();

                            jpaContextFactory.getInitialContext( env );
                        }

                        this.isXAPoolInitialized();
                    }
                }

                return instance;
            }
            catch ( final InstantiationException e )
            {
                throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
            }
            catch ( final IllegalAccessException e )
            {
                throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
            }
            catch ( final ClassNotFoundException e )
            {
                throw (NamingException) new NamingException( getMessage( e ) ).initCause( e );
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

    private static String getMessage( final Throwable t )
    {
        return t != null ? t.getMessage() != null ? t.getMessage() : getMessage( t.getCause() ) : null;
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">
    /** Creates a new {@code StandaloneContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
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
