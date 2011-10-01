// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Copyright (C) 2005 - 2011 Christian Schulte <schulte2005@users.sourceforge.net>
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
package org.jomc.standalone.ri;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone environment.
 *
 * <p>
 *   This implementation is identified by identifier {@code <JOMC Standalone RI Environment>}.
 *   It does not provide any specified objects.
 * </p>
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
public class StandaloneEnvironment
{
    // SECTION-START[StandaloneEnvironment]

    /**
     * Constant for the name of the system property holding the JNDI name the standalone {@code EntityManager}
     * implementation is bound to.
     */
    public static final String ENTITY_MANAGER_JNDI_NAME = "jomc.standalone.entityManagerJndiName";

    /**
     * Constant for the name of the system property holding the JNDI name the standalone {@code EntityManagerFactory}
     * implementation is bound to.
     */
    public static final String ENTITY_MANAGER_FACTORY_JNDI_NAME = "jomc.standalone.entityManagerFactoryJndiName";

    /**
     * Constant for the name of the system property holding the JNDI name the standalone {@code TransactionManager}
     * implementation is bound to.
     */
    public static final String TRANSACTION_MANAGER_JNDI_NAME = "jomc.standalone.transactionManagerJndiName";

    /**
     * Constant for the name of the system property holding the JNDI name the standalone {@code UserTransaction}
     * implementation is bound to.
     */
    public static final String USER_TRANSACTION_JNDI_NAME = "jomc.standalone.userTransactionJndiName";

    /**
     * Constant for the name of the system property holding the JNDI name the standalone
     * {@code TransactionSynchronizationRegistry} implementation is bound to.
     */
    public static final String TRANSACTION_SYNCHRONIZATION_REGISTRY_JNDI_NAME =
        "jomc.standalone.transactionSynchronizationRegistryJndiName";

    /**
     * Constant for the name of the system property holding the class name of the {@code InitialContextFactory}
     * providing the JPA implementation backing the standalone environment.
     */
    public static final String JPA_CONTEXT_FACTORY_NAME = "jomc.standalone.jpaContextFactoryName";

    /**
     * Constant for the name of the system property holding the root URL of the persistence unit backing the
     * standalone environment.
     */
    public static final String JPA_ROOT_URL = "jomc.standalone.jpaRootUrl";

    /**
     * Constant for the name of the system property holding the class name of the {@code InitialContextFactory}
     * providing the JTA implementation backing the standalone environment.
     */
    public static final String JTA_CONTEXT_FACTORY_NAME = "jomc.standalone.jtaContextFactoryName";

    /**
     * Constant for the name of the system property holding the JNDI name of the JTA aware {@code DataSource} backing
     * the standalone environment.
     */
    public static final String JTA_DATA_SOURCE_JNDI_NAME = "jomc.standalone.jtaDataSourceJndiName";

    /** Constant for the prefix of data source properties of the standalone environment. */
    public static final String DATA_SOURCE_PREFIX = "jomc.standalone.dataSource.";

    /**
     * Constant for the name of the system property holding the class name of the {@code InitialContextFactory}
     * providing the {@code DataSource} implementation backing the standalone environment.
     */
    public static final String DATA_SOURCE_CONTEXT_FACTORY_NAME = DATA_SOURCE_PREFIX + "contextFactoryName";

    /**
     * Constant for the name of the system property holding the JNDI name of the {@code DataSource} backing the
     * standalone environment.
     */
    public static final String DATA_SOURCE_JNDI_NAME = DATA_SOURCE_PREFIX + "jndiName";

    /**
     * Constant for the name of the system property holding the class name of the {@code DataSource} backing the
     * standalone environment.
     */
    public static final String DATA_SOURCE_CLASS_NAME = DATA_SOURCE_PREFIX + "className";

    /**
     * Constant for the name of the system property holding the user of the {@code DataSource} backing the
     * standalone environment.
     */
    public static final String DATA_SOURCE_USER = DATA_SOURCE_PREFIX + "user";

    /**
     * Constant for the name of the system property holding the password of the {@code DataSource} backing the
     * standalone environment.
     */
    public static final String DATA_SOURCE_PASSWORD = DATA_SOURCE_PREFIX + "password";

    /**
     * Gets the JNDI name the standalone {@code EntityManager} implementation is bound to.
     *
     * @return The the JNDI name the standalone {@code EntityManager} implementation is bound to.
     */
    public String getEntityManagerJndiName()
    {
        return this.getProperties().getProperty( ENTITY_MANAGER_JNDI_NAME, this.getDefaultEntityManagerJndiName() );
    }

    /**
     * Gets the JNDI name the standalone {@code EntityManagerFactory} implementation is bound to.
     *
     * @return The JNDI name the standalone {@code EntityManagerFactory} implementation is bound to.
     */
    public String getEntityManagerFactoryJndiName()
    {
        return this.getProperties().getProperty( ENTITY_MANAGER_FACTORY_JNDI_NAME,
                                                 this.getDefaultEntityManagerFactoryJndiName() );

    }

    /**
     * Gets the JNDI name the standalone {@code TransactionManager} implementation is bound to.
     *
     * @return The JNDI name the standalone {@code TransactionManager} implementation is bound to.
     */
    public String getTransactionManagerJndiName()
    {
        return this.getProperties().getProperty( TRANSACTION_MANAGER_JNDI_NAME,
                                                 this.getDefaultTransactionManagerJndiName() );

    }

    /**
     * Gets the JNDI name the standalone {@code UserTransaction} implementation is bound to.
     *
     * @return The JNDI name the standalone {@code UserTransaction} implementation is bound to.
     */
    public String getUserTransactionJndiName()
    {
        return this.getProperties().getProperty( USER_TRANSACTION_JNDI_NAME,
                                                 this.getDefaultUserTransactionJndiName() );

    }

    /**
     * Gets the JNDI name the standalone {@code TransactionSynchronizationRegistry} is bound to.
     *
     * @return The JNDI name the standalone {@code TransactionSynchronizationRegistry} is bound to.
     */
    public String getTransactionSynchronizationRegistryJndiName()
    {
        return this.getProperties().getProperty( TRANSACTION_SYNCHRONIZATION_REGISTRY_JNDI_NAME,
                                                 this.getDefaultTransactionSynchronizationRegistryJndiName() );

    }

    /**
     * Gets the class name of the {@code InitialContextFactory} providing the JPA implementation backing the standalone
     * environment.
     *
     * @return The class name of the {@code InitialContextFactory} providing the JPA implementation backing the
     * standalone environment or {@code null}.
     */
    public String getJpaContextFactoryName()
    {
        return this.getProperties().getProperty( JPA_CONTEXT_FACTORY_NAME, this.getDefaultJpaContextFactoryName() );
    }

    /**
     * Gets the root URL of the persistence unit backing the standalone environment.
     *
     * @return The root URL of the persistence unit backing the standalone environment.
     */
    public URL getJpaRootUrl()
    {
        try
        {
            final String sysRootUrl = this.getProperties().getProperty( JPA_ROOT_URL );

            if ( sysRootUrl != null )
            {
                final File f = new File( sysRootUrl );
                if ( !f.exists() )
                {
                    f.mkdirs();
                }

                return f.toURI().toURL();
            }
            else
            {
                final File defaultRootUrl = new File( System.getProperty( "java.io.tmpdir" ),
                                                      this.getDefaultTemporaryDirectoryName() );

                if ( !defaultRootUrl.exists() )
                {
                    defaultRootUrl.mkdirs();
                }

                return defaultRootUrl.toURI().toURL();
            }
        }
        catch ( final MalformedURLException e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Gets the class name of the {@code InitialContextFactory} providing the JTA implementation backing the standalone
     * environment.
     *
     * @return The class name of the {@code InitialContextFactory} providing the JTA implementation backing the
     * standalone environment or {@code null}.
     */
    public String getJtaContextFactoryName()
    {
        return this.getProperties().getProperty( JTA_CONTEXT_FACTORY_NAME, this.getDefaultJtaContextFactoryName() );
    }

    /**
     * Gets the JNDI name of the JTA aware {@code DataSource} backing the standalone environment.
     *
     * @return The JNDI name of the JTA aware {@code DataSource} backing the standalone environment or {@code null}.
     */
    public String getJtaDataSourceJndiName()
    {
        return this.getProperties().getProperty( JTA_DATA_SOURCE_JNDI_NAME, this.getDefaultJtaDataSourceJndiName() );
    }

    /**
     * Gets the class name of the {@code InitialContextFactory} providing the {@code DataSource} backing the standalone
     * environment.
     *
     * @return The class name of the {@code InitialContextFactory} providing the {@code DataSource} backing the
     * standalone environment.
     */
    public String getDataSourceContextFactoryName()
    {
        return this.getProperties().getProperty( DATA_SOURCE_CONTEXT_FACTORY_NAME,
                                                 this.getDefaultDataSourceContextFactoryName() );

    }

    /**
     * Gets the class name of the {@code DataSource} backing the standalone environment.
     *
     * @return The class name of the {@code DataSource} backing the standalone environment or {@code null}.
     */
    public String getDataSourceClassName()
    {
        return this.getProperties().getProperty( DATA_SOURCE_CLASS_NAME, this.getDefaultDataSourceClassName() );
    }

    /**
     * Gets the JNDI name of the {@code DataSource} backing the standalone environment.
     *
     * @return The JNDI name of the {@code DataSource} backing the standalone environment.
     */
    public String getDataSourceJndiName()
    {
        return this.getProperties().getProperty( DATA_SOURCE_JNDI_NAME, this.getDefaultDataSourceJndiName() );
    }

    /**
     * Gets the user of the {@code DataSource} backing the standalone environment.
     *
     * @return The user of the {@code DataSource} backing the standalone environment.
     */
    public String getDataSourceUser()
    {
        return this.getProperties().getProperty( DATA_SOURCE_USER );
    }

    /**
     * Gets the password of the {@code DataSource} backing the standalone environment.
     *
     * @return The password of the {@code DataSource} backing the standalone environment.
     */
    public String getDataSourcePassword()
    {
        return this.getProperties().getProperty( DATA_SOURCE_PASSWORD );
    }

    /**
     * Gets the properties of the instance.
     *
     * @return The properties of the instance.
     */
    public Properties getProperties()
    {
        return System.getProperties();
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code StandaloneEnvironment} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    public StandaloneEnvironment()
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
     * Gets the value of the {@code <defaultDataSourceClassName>} property.
     * @return The default class name of the {@code DataSource} backing the standalone environment.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultDataSourceClassName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultDataSourceClassName" );
        assert _p != null : "'defaultDataSourceClassName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultDataSourceContextFactoryName>} property.
     * @return The default class name of the {@code InitialContextFactory} providing the standalone {@code DataSource}.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultDataSourceContextFactoryName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultDataSourceContextFactoryName" );
        assert _p != null : "'defaultDataSourceContextFactoryName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultDataSourceJndiName>} property.
     * @return The default JNDI name of the data source backing the standalone JPA implementation.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultDataSourceJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultDataSourceJndiName" );
        assert _p != null : "'defaultDataSourceJndiName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultEntityManagerFactoryJndiName>} property.
     * @return The default JNDI name the standalone {@code EntityManagerFactory} is bound to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultEntityManagerFactoryJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultEntityManagerFactoryJndiName" );
        assert _p != null : "'defaultEntityManagerFactoryJndiName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultEntityManagerJndiName>} property.
     * @return The default JNDI name the standalone {@code EntityManager} is bound to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultEntityManagerJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultEntityManagerJndiName" );
        assert _p != null : "'defaultEntityManagerJndiName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultJpaContextFactoryName>} property.
     * @return The default class name of the {@code InitialContextFactory} providing the JPA implementation backing the standalone environment.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultJpaContextFactoryName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultJpaContextFactoryName" );
        assert _p != null : "'defaultJpaContextFactoryName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultJtaContextFactoryName>} property.
     * @return The default class name of the of the {@code InitialContextFactory} providing the JTA implementation backing the standalone environment.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultJtaContextFactoryName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultJtaContextFactoryName" );
        assert _p != null : "'defaultJtaContextFactoryName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultJtaDataSourceJndiName>} property.
     * @return The default JNDI name of the data source backing the standalone JTA implementation.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultJtaDataSourceJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultJtaDataSourceJndiName" );
        assert _p != null : "'defaultJtaDataSourceJndiName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultTemporaryDirectoryName>} property.
     * @return The default name of the temporary directory backing the standalone environment.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultTemporaryDirectoryName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultTemporaryDirectoryName" );
        assert _p != null : "'defaultTemporaryDirectoryName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultTransactionManagerJndiName>} property.
     * @return The default JNDI name the standalone {@code TransactionManager} is bound to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultTransactionManagerJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultTransactionManagerJndiName" );
        assert _p != null : "'defaultTransactionManagerJndiName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultTransactionSynchronizationRegistryJndiName>} property.
     * @return The default JNDI name the standalone {@code TransactionSynchronizationRegistry} is bound to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultTransactionSynchronizationRegistryJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultTransactionSynchronizationRegistryJndiName" );
        assert _p != null : "'defaultTransactionSynchronizationRegistryJndiName' property not found.";
        return _p;
    }

    /**
     * Gets the value of the {@code <defaultUserTransactionJndiName>} property.
     * @return The default JNDI name the standalone {@code UserTransaction} is bound to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private java.lang.String getDefaultUserTransactionJndiName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "defaultUserTransactionJndiName" );
        assert _p != null : "'defaultUserTransactionJndiName' property not found.";
        return _p;
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Messages]
    // SECTION-END
}
