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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Base JPA context factory.
 * <p><b>Specifications</b><ul>
 * <li>{@code javax.naming.spi.InitialContextFactory} {@code Multiton}</li>
 * </ul></p>
 *
 * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a> 1.0
 * @version $Id$
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                             comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-17-SNAPSHOT/jomc-tools" )
// </editor-fold>
// SECTION-END
public abstract class AbstractJPAContextFactory extends AbstractContextFactory
{
    // SECTION-START[InitialContextFactory]

    public final Context getInitialContext( final Hashtable<?, ?> environment ) throws NamingException
    {
        final EntityManagerFactory entityManagerFactory =
            this.getPersistenceProvider().createContainerEntityManagerFactory(
            this.getPersistenceUnitInfo(), environment );

        this.getStandaloneContext().bind( this.getStandaloneEnvironment().getEntityManagerFactoryJndiName(),
                                          entityManagerFactory );

        this.getStandaloneContext().bind( this.getStandaloneEnvironment().getEntityManagerJndiName(),
                                          entityManagerFactory.createEntityManager() );

        return null;
    }

    // SECTION-END
    // SECTION-START[AbstractJPAContextFactory]
    private static final String PERSISTENCE_NS = "http://java.sun.com/xml/ns/persistence";

    private PersistenceUnitInfo persistenceUnitInfo;

    protected abstract PersistenceProvider getPersistenceProvider();

    protected PersistenceUnitInfo getPersistenceUnitInfo()
    {
        if ( this.persistenceUnitInfo == null )
        {
            this.persistenceUnitInfo = new PersistenceUnitInfo()
            {

                private List<ClassTransformer> transformers;

                private List<String> managedClasses;

                private List<String> mappingFileNames;

                private ClassLoader classLoader;

                public String getPersistenceUnitName()
                {
                    return "jomc-standalone";
                }

                public String getPersistenceProviderClassName()
                {
                    return getPersistenceProvider().getClass().getName();
                }

                public PersistenceUnitTransactionType getTransactionType()
                {
                    return PersistenceUnitTransactionType.JTA;
                }

                public DataSource getJtaDataSource()
                {
                    try
                    {
                        return (DataSource) getStandaloneContext().lookup(
                            getStandaloneEnvironment().getJtaDataSourceJndiName() );

                    }
                    catch ( final NamingException e )
                    {
                        throw new PersistenceException( e );
                    }
                }

                public DataSource getNonJtaDataSource()
                {
                    return null;
                }

                public List<String> getMappingFileNames()
                {
                    try
                    {
                        if ( this.mappingFileNames == null )
                        {
                            this.mappingFileNames = new LinkedList<String>();

                            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            factory.setNamespaceAware( true );
                            factory.setValidating( false );

                            final DocumentBuilder documentBuilder = factory.newDocumentBuilder();

                            for ( final Enumeration<URL> e = this.getNewTempClassLoader().getResources(
                                "META-INF/persistence.xml" ); e.hasMoreElements(); )
                            {
                                final URL url = e.nextElement();
                                final InputStream in = url.openStream();
                                final Document doc = documentBuilder.parse( in );
                                in.close();

                                final NodeList persistenceUnits =
                                    doc.getElementsByTagNameNS( PERSISTENCE_NS, "persistence-unit" );

                                for ( int i = persistenceUnits.getLength() - 1; i >= 0; i-- )
                                {
                                    final Element persistenceUnit = (Element) persistenceUnits.item( i );
                                    final NodeList mappingFiles =
                                        persistenceUnit.getElementsByTagNameNS( PERSISTENCE_NS, "mapping-file" );

                                    for ( int j = mappingFiles.getLength() - 1; j >= 0; j-- )
                                    {
                                        final Element mappingFile = (Element) mappingFiles.item( j );
                                        this.mappingFileNames.add( mappingFile.getFirstChild().getNodeValue() );
                                    }
                                }
                            }
                        }

                        return this.mappingFileNames;
                    }
                    catch ( final SAXException e )
                    {
                        throw new PersistenceException( e );
                    }
                    catch ( final IOException e )
                    {
                        throw new PersistenceException( e );
                    }
                    catch ( final ParserConfigurationException e )
                    {
                        throw new PersistenceException( e );
                    }
                }

                public List<URL> getJarFileUrls()
                {
                    try
                    {
                        final List<URL> jarFileUrls = new LinkedList<URL>();
                        for ( final Enumeration<URL> unitUrls = this.getClassLoader().getResources(
                            "META-INF/persistence.xml" ); unitUrls.hasMoreElements(); )
                        {
                            final URL unitUrl = unitUrls.nextElement();
                            final String externalForm = unitUrl.toExternalForm();
                            final String jarUrl = externalForm.substring( 0, externalForm.indexOf( "META-INF" ) );
                            jarFileUrls.add( new URL( jarUrl ) );
                        }

                        return jarFileUrls;
                    }
                    catch ( final IOException e )
                    {
                        throw new PersistenceException( e );
                    }
                }

                public URL getPersistenceUnitRootUrl()
                {
                    return getStandaloneEnvironment().getJpaRootUrl();
                }

                public List<String> getManagedClassNames()
                {
                    try
                    {
                        if ( this.managedClasses == null )
                        {
                            this.managedClasses = new LinkedList<String>();
                            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            factory.setNamespaceAware( true );
                            factory.setValidating( false );

                            final DocumentBuilder documentBuilder = factory.newDocumentBuilder();

                            for ( final Enumeration<URL> e = this.getNewTempClassLoader().getResources(
                                "META-INF/persistence.xml" ); e.hasMoreElements(); )
                            {
                                final URL url = e.nextElement();
                                final InputStream in = url.openStream();
                                final Document doc = documentBuilder.parse( in );
                                in.close();

                                final NodeList persistenceUnits =
                                    doc.getElementsByTagNameNS( PERSISTENCE_NS, "persistence-unit" );

                                for ( int i = persistenceUnits.getLength() - 1; i >= 0; i-- )
                                {
                                    final Element persistenceUnit = (Element) persistenceUnits.item( i );
                                    final NodeList classes =
                                        persistenceUnit.getElementsByTagNameNS( PERSISTENCE_NS, "class" );

                                    for ( int j = classes.getLength() - 1; j >= 0; j-- )
                                    {
                                        final Element clazz = (Element) classes.item( j );
                                        this.managedClasses.add( clazz.getFirstChild().getNodeValue() );
                                    }
                                }
                            }
                        }

                        return this.managedClasses;
                    }
                    catch ( final SAXException e )
                    {
                        throw new PersistenceException( e );
                    }
                    catch ( final IOException e )
                    {
                        throw new PersistenceException( e );
                    }
                    catch ( final ParserConfigurationException e )
                    {
                        throw new PersistenceException( e );
                    }
                }

                public boolean excludeUnlistedClasses()
                {
                    return true;
                }

                public Properties getProperties()
                {
                    return getStandaloneEnvironment().getProperties();
                }

                public ClassLoader getClassLoader()
                {
                    if ( this.classLoader == null )
                    {
                        this.classLoader = new URLClassLoader( new URL[]
                            {
                                getStandaloneEnvironment().getJpaRootUrl()
                            }, this.getClass().getClassLoader() );

                    }

                    return this.classLoader;
                }

                public void addTransformer( final ClassTransformer transformer )
                {
                    if ( this.transformers == null )
                    {
                        this.transformers = new LinkedList<ClassTransformer>();
                    }

                    this.transformers.add( transformer );
                }

                public ClassLoader getNewTempClassLoader()
                {
                    final List<URL> jarFileUrls = this.getJarFileUrls();
                    jarFileUrls.add( getStandaloneEnvironment().getJpaRootUrl() );

                    return new URLClassLoader( jarFileUrls.toArray( new URL[ jarFileUrls.size() ] ),
                                               this.getClass().getClassLoader() );

                }

            };
        }

        return this.persistenceUnitInfo;
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code AbstractJPAContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-17-SNAPSHOT/jomc-tools" )
    public AbstractJPAContextFactory()
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
