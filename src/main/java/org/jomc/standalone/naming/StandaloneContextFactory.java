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
package org.jomc.standalone.naming;

import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import org.jomc.standalone.Environment;

// SECTION-START[Implementation Comment]
/**
 * Standalone {@code InitialContextFactory} implementation.
 * <p><b>Dependencies</b><ul>
 * <li>"{@link #getLogger Logger}"<blockquote>
 * Dependency on {@code org.jomc.logging.Logger} at specification level 1.0 applying to Multiton scope bound to an instance.</blockquote></li>
 * <li>"{@link #getLocale Locale}"<blockquote>
 * Dependency on {@code java.util.Locale} at specification level 1.1 applying to Multiton scope bound to an instance.</blockquote></li>
 * </ul></p>
 * <p><b>Messages</b><ul>
 * <li>"{@link #getImplementationInfoMessage implementationInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
 * </table>
 * <li>"{@link #getCompletedInitializationMessage completedInitialization}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT - started in {0}ms.</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT - gestartet in {0}ms.</pre></td></tr>
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
    comments = "See http://jomc.sourceforge.net/jomc-tools"
)
// SECTION-END
public class StandaloneContextFactory implements InitialContextFactory
{
    // SECTION-START[InitialContextFactory]

    private static Context instance;

    public synchronized Context getInitialContext( final Hashtable environment ) throws NamingException
    {
        try
        {
            if ( instance == null )
            {
                final long startTime = System.currentTimeMillis();
                this.getLogger().info( this.getImplementationInfoMessage( this.getLocale() ) );
                instance = new StandaloneContext();

                if ( environment != null )
                {
                    System.getProperties().putAll( environment );
                    instance.getEnvironment().putAll( environment );

                    if ( this.getEnvironment().getDataSourceContextFactoryName() != null )
                    {
                        final InitialContextFactory dataSourceContextFactory = (InitialContextFactory) Class.forName(
                            this.getEnvironment().getDataSourceContextFactoryName() ).newInstance();

                        dataSourceContextFactory.getInitialContext( environment );
                    }
                    if ( this.getEnvironment().getJtaContextFactoryName() != null )
                    {
                        final InitialContextFactory jtaContextFactory = (InitialContextFactory) Class.forName(
                            this.getEnvironment().getJtaContextFactoryName() ).newInstance();

                        jtaContextFactory.getInitialContext( environment );
                    }
                    if ( this.getEnvironment().getJpaContextFactoryName() != null )
                    {
                        final InitialContextFactory jpaContextFactory = (InitialContextFactory) Class.forName(
                            this.getEnvironment().getJpaContextFactoryName() ).newInstance();

                        jpaContextFactory.getInitialContext( environment );
                    }

                    try
                    {
                        Class.forName( "org.enhydra.jdbc.standard.StandardXADataSource" );
                        final Class helperClass = Class.forName( "org.jomc.standalone.naming.support.XAPoolHelper" );
                        final Method helperMethod =
                            helperClass.getDeclaredMethod( "initializeXAPool", Environment.class, Context.class );

                        helperMethod.invoke( null, this.getEnvironment(), instance );
                    }
                    catch ( Exception e )
                    {
                        this.getLogger().debug( e );
                    }
                }

                this.getLogger().info( this.getCompletedInitializationMessage(
                    this.getLocale(), System.currentTimeMillis() - startTime ) );

            }

            return instance;
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
        catch ( ClassNotFoundException e )
        {
            this.getLogger().fatal( e );
            throw (NamingException) new NamingException( e.getMessage() ).initCause( e );
        }
    }

    // SECTION-END
    // SECTION-START[StandaloneContextFactory]

    private Environment environment;

    public Environment getEnvironment()
    {
        if ( this.environment == null )
        {
            this.environment = new Environment();
        }

        return this.environment;
    }

    // SECTION-END
    // SECTION-START[Constructors]

    /** Default implementation constructor. */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc-tools"
    )
    public StandaloneContextFactory()
    {
        // SECTION-START[Default Constructor]
        super();
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
        comments = "See http://jomc.sourceforge.net/jomc-tools"
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
     * <dd>Property of type {@code java.lang.String} with value "org.jomc.standalone.naming.StandaloneContextFactory".
     * </dd>
     * </dl>
     * @return The {@code Logger} dependency.
     * @throws org.jomc.ObjectManagementException if getting the dependency instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc-tools"
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
     * Gets the text of the {@code completedInitialization} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT - started in {0}ms.</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT - gestartet in {0}ms.</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param startMillis Format argument.
     * @return The text of the {@code completedInitialization} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc-tools"
    )
    private String getCompletedInitializationMessage( final java.util.Locale locale, final java.lang.Number startMillis ) throws org.jomc.ObjectManagementException
    {
        return org.jomc.ObjectManagerFactory.getObjectManager().getMessage( this, "completedInitialization", locale, new Object[] { startMillis, null } );
    }

    /**
     * Gets the text of the {@code implementationInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>StandaloneContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @return The text of the {@code implementationInfo} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc-tools"
    )
    private String getImplementationInfoMessage( final java.util.Locale locale ) throws org.jomc.ObjectManagementException
    {
        return org.jomc.ObjectManagerFactory.getObjectManager().getMessage( this, "implementationInfo", locale,  null );
    }
    // SECTION-END
}
