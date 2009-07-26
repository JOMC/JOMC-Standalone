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

import com.arjuna.ats.arjuna.coordinator.TransactionReaper;
import com.arjuna.ats.arjuna.recovery.RecoveryManager;
import com.arjuna.ats.jdbc.TransactionalDriver;
import com.arjuna.ats.jdbc.common.jdbcPropertyManager;
import com.arjuna.ats.jta.common.jtaPropertyManager;
import com.arjuna.ats.jta.utils.JNDIManager;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

// SECTION-START[Implementation Comment]
/**
 * Standalone JBoss JTA context factory.
 * <p><b>Specifications</b><ul>
 * <li>{@code javax.naming.spi.InitialContextFactory}<blockquote>
 * Object applies to Multiton scope.</blockquote></li>
 * </ul></p>
 * <p><b>Properties</b><ul>
 * <li>"{@link #isTransactionalDriverEnabled transactionalDriverEnabled}"<blockquote>
 * Property of type {@code boolean} with value "true".</blockquote></li>
 * </ul></p>
 * <p><b>Dependencies</b><ul>
 * <li>"{@link #getLogger Logger}"<blockquote>
 * Dependency on {@code org.jomc.logging.Logger} at specification level 1.0 applying to Multiton scope bound to an instance.</blockquote></li>
 * <li>"{@link #getLocale Locale}"<blockquote>
 * Dependency on {@code java.util.Locale} at specification level 1.1 applying to Multiton scope bound to an instance.</blockquote></li>
 * </ul></p>
 * <p><b>Messages</b><ul>
 * <li>"{@link #getImplementationInfoMessage implementationInfo}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>JBossJTAContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>JBossJTAContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
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
public class JBossJTAContextFactory extends AbstractContextFactory
{
    // SECTION-START[InitialContextFactory]

    public Context getInitialContext( final Hashtable<?, ?> environment ) throws NamingException
    {
        jdbcPropertyManager.getPropertyManager().setProperty(
            "Context.INITIAL_CONTEXT_FACTORY", (String) environment.get( Context.INITIAL_CONTEXT_FACTORY ) );

        jdbcPropertyManager.getPropertyManager().setProperty(
            "Context.URL_PKG_PREFIXES", (String) environment.get( Context.URL_PKG_PREFIXES ) );

        jtaPropertyManager.getPropertyManager().setProperty( com.arjuna.ats.jta.common.Environment.TM_JNDI_CONTEXT,
                                                             this.getEnvironment().getTransactionManagerJndiName() );

        jtaPropertyManager.getPropertyManager().setProperty( com.arjuna.ats.jta.common.Environment.UT_JNDI_CONTEXT,
                                                             this.getEnvironment().getUserTransactionJndiName() );

        jtaPropertyManager.getPropertyManager().setProperty(
            com.arjuna.ats.jta.common.Environment.TSR_JNDI_CONTEXT,
            this.getEnvironment().getTransactionSynchronizationRegistryJndiName() );

        JNDIManager.bindJTAImplementation();

        TransactionReaper.create();
        RecoveryManager.manager().startRecoveryManagerThread();

        if ( this.isTransactionalDriverEnabled() )
        {
            this.getContext().rebind( this.getEnvironment().getJtaDataSourceJndiName(),
                                      this.getTransactionalDataSource() );

        }

        return null;
    }

    public DataSource getTransactionalDataSource()
    {
        return new DataSource()
        {

            private final TransactionalDriver driver = new TransactionalDriver();

            private PrintWriter logWriter;

            private boolean logWriterSet;

            private int loginTimeout;

            public Connection getConnection() throws SQLException
            {
                return this.getConnection( getEnvironment().getDataSourceUser(),
                                           getEnvironment().getDataSourcePassword() );

            }

            public Connection getConnection( final String username, final String password ) throws SQLException
            {
                final String url = TransactionalDriver.arjunaDriver + getEnvironment().getDataSourceJndiName();
                final Properties properties = new Properties();
                properties.setProperty( TransactionalDriver.userName, username );
                properties.setProperty( TransactionalDriver.password, password );
                return this.driver.connect( url, properties );
            }

            public PrintWriter getLogWriter() throws SQLException
            {
                if ( this.logWriter == null && !this.logWriterSet )
                {
                    this.logWriter = new PrintWriter( System.out );
                }

                return this.logWriter;
            }

            public void setLogWriter( final PrintWriter out ) throws SQLException
            {
                this.logWriter = out;
                this.logWriterSet = true;
            }

            public void setLoginTimeout( final int seconds ) throws SQLException
            {
                this.loginTimeout = seconds;
            }

            public int getLoginTimeout() throws SQLException
            {
                return this.loginTimeout;
            }

            public <T> T unwrap( final Class<T> iface ) throws SQLException
            {
                return (T) ( this.isWrapperFor( iface ) ? this : null );
            }

            public boolean isWrapperFor( final Class<?> iface ) throws SQLException
            {
                return iface.isAssignableFrom( DataSource.class );
            }

        };
    }

    // SECTION-END
    // SECTION-START[JBossJTAContextFactory]
    // SECTION-END
    // SECTION-START[Constructors]

    /** Default implementation constructor. */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc-tools"
    )
    public JBossJTAContextFactory()
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
     * <dd>Property of type {@code java.lang.String} with value "org.jomc.standalone.naming.support.JBossJTAContextFactory".
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

    /**
     * Gets the value of the {@code transactionalDriverEnabled} property.
     * @return {@code true} to enable ArjunaJTA's transactional JDBC driver (no JDBC3 support); {@code false} to disable ArjunaJTA's transactional JDBC driver.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated
    (
        value = "org.jomc.tools.JavaSources",
        comments = "See http://jomc.sourceforge.net/jomc-tools"
    )
    private boolean isTransactionalDriverEnabled() throws org.jomc.ObjectManagementException
    {
        return ( (java.lang.Boolean) org.jomc.ObjectManagerFactory.getObjectManager().getProperty( this, "transactionalDriverEnabled" ) ).booleanValue();
    }
    // SECTION-END
    // SECTION-START[Messages]

    /**
     * Gets the text of the {@code implementationInfo} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>JBossJTAContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>JBossJTAContextFactory 1.0-alpha-1-SNAPSHOT</pre></td></tr>
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
