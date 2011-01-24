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
package org.jomc.standalone.ri.naming.support;

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

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone JBoss JTA context factory.
 * <p><b>Specifications</b><ul>
 * <li>{@code 'javax.naming.spi.InitialContextFactory'} {@code (javax.naming.spi.InitialContextFactory)} {@code Multiton}</li>
 * </ul></p>
 * <p><b>Properties</b><ul>
 * <li>"{@link #isTransactionalDriverEnabled transactionalDriverEnabled}"
 * <blockquote>Property of type {@code boolean}.
 * <p>{@code true} to enable ArjunaJTA's transactional JDBC driver (no JDBC3 support); {@code false} to disable ArjunaJTA's transactional JDBC driver.</p>
 * </blockquote></li>
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
public class JBossJTAContextFactory extends AbstractContextFactory
{
    // SECTION-START[InitialContextFactory]

    public Context getInitialContext( final Hashtable<?, ?> environment ) throws NamingException
    {
        jdbcPropertyManager.getPropertyManager().setProperty(
            "Context.INITIAL_CONTEXT_FACTORY", (String) environment.get( Context.INITIAL_CONTEXT_FACTORY ) );

        jdbcPropertyManager.getPropertyManager().setProperty(
            "Context.URL_PKG_PREFIXES", (String) environment.get( Context.URL_PKG_PREFIXES ) );

        jtaPropertyManager.getPropertyManager().setProperty(
            com.arjuna.ats.jta.common.Environment.TM_JNDI_CONTEXT,
            this.getStandaloneEnvironment().getTransactionManagerJndiName() );

        jtaPropertyManager.getPropertyManager().setProperty(
            com.arjuna.ats.jta.common.Environment.UT_JNDI_CONTEXT,
            this.getStandaloneEnvironment().getUserTransactionJndiName() );

        jtaPropertyManager.getPropertyManager().setProperty(
            com.arjuna.ats.jta.common.Environment.TSR_JNDI_CONTEXT,
            this.getStandaloneEnvironment().getTransactionSynchronizationRegistryJndiName() );

        JNDIManager.bindJTAImplementation();

        TransactionReaper.create();

        final RecoveryManager recoveryManager = RecoveryManager.manager();
        recoveryManager.startRecoveryManagerThread();

        if ( this.isTransactionalDriverEnabled() )
        {
            this.getStandaloneContext().rebind( this.getStandaloneEnvironment().getJtaDataSourceJndiName(),
                                                this.getTransactionalDataSource() );

        }

        return null;
    }

    private DataSource getTransactionalDataSource()
    {
        return new DataSource()
        {

            private final TransactionalDriver driver = new TransactionalDriver();

            private PrintWriter logWriter;

            private boolean logWriterSet;

            private int loginTimeout;

            public Connection getConnection() throws SQLException
            {
                return this.getConnection( getStandaloneEnvironment().getDataSourceUser(),
                                           getStandaloneEnvironment().getDataSourcePassword() );

            }

            public Connection getConnection( final String username, final String password ) throws SQLException
            {
                final String url =
                    TransactionalDriver.arjunaDriver + getStandaloneEnvironment().getDataSourceJndiName();

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
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code JBossJTAContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.1", comments = "See http://jomc.sourceforge.net/jomc/1.1/jomc-tools-1.1" )
    public JBossJTAContextFactory()
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
    // <editor-fold defaultstate="collapsed" desc=" Generated Properties ">

    /**
     * Gets the value of the {@code transactionalDriverEnabled} property.
     * @return {@code true} to enable ArjunaJTA's transactional JDBC driver (no JDBC3 support); {@code false} to disable ArjunaJTA's transactional JDBC driver.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.1", comments = "See http://jomc.sourceforge.net/jomc/1.1/jomc-tools-1.1" )
    private boolean isTransactionalDriverEnabled()
    {
        final java.lang.Boolean _p = (java.lang.Boolean) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "transactionalDriverEnabled" );
        assert _p != null : "'transactionalDriverEnabled' property not found.";
        return _p.booleanValue();
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Messages]
    // SECTION-END
}
