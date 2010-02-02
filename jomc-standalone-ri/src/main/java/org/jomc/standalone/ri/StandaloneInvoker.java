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
package org.jomc.standalone.ri;

import java.util.Stack;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicCommitException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionRequiredException;
import javax.transaction.TransactionRolledbackException;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import org.jomc.ObjectManagementException;
import org.jomc.model.Instance;
import org.jomc.ri.DefaultInvocation;
import org.jomc.ri.DefaultInvoker;
import org.jomc.standalone.model.MethodType;
import org.jomc.standalone.model.MethodsType;
import org.jomc.standalone.model.ParameterType;
import org.jomc.standalone.model.ParametersType;
import static org.jomc.standalone.model.TransactionAttributeType.MANDATORY;
import static org.jomc.standalone.model.TransactionAttributeType.NEVER;
import static org.jomc.standalone.model.TransactionAttributeType.NOT_SUPPORTED;
import static org.jomc.standalone.model.TransactionAttributeType.REQUIRED;
import static org.jomc.standalone.model.TransactionAttributeType.REQUIRES_NEW;
import static org.jomc.standalone.model.TransactionAttributeType.SUPPORTS;
import org.jomc.standalone.model.TransactionType;
import org.jomc.spi.Invocation;
import org.w3c.dom.Element;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * JOMC Standalone RI Invoker
 * <p><b>Specifications</b><ul>
 * <li>{@code org.jomc.spi.Invoker} {@code 1.0} {@code Multiton}</li>
 * </ul></p>
 * <p><b>Dependencies</b><ul>
 * <li>"{@link #getLocale Locale}"<blockquote>
 * Dependency on {@code java.util.Locale} at specification level 1.1 bound to an instance.</blockquote></li>
 * </ul></p>
 * <p><b>Messages</b><ul>
 * <li>"{@link #getIllegalTransactionMessage illegalTransaction}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>Cannot invoke method ''{0}'' with a ''{1}'' transaction context.</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>Die Methode ''{0}'' kann nicht in einem ''{1}'' Transaktions-Kontext ausgef&uuml;hrt werden.</pre></td></tr>
 * </table>
 * <li>"{@link #getUnsupportedTransactionMessage unsupportedTransaction}"<table>
 * <tr><td valign="top">English:</td><td valign="top"><pre>Unsupported transaction attribute ''{1}'' invoking method ''{0}''.</pre></td></tr>
 * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>Ung&uuml;ltiges Transaktions-Attribut ''{1}'' bei der Ausf&uuml;hrung von Methode ''{0}''.</pre></td></tr>
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
                             comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-16/jomc-tools" )
// </editor-fold>
// SECTION-END
public class StandaloneInvoker extends DefaultInvoker
{
    // SECTION-START[StandaloneInvoker]

    /** {@code QName} of the {@code Methods} complex type. */
    private static final QName METHODS_QNAME = new QName( "http://jomc.org/standalone/model", "methods" );

    private static final ThreadLocal<ThreadState> CURRENT = new ThreadLocal<ThreadState>()
    {

        @Override
        public ThreadState initialValue()
        {
            return new ThreadState();
        }

    };

    private StandaloneEnvironment standaloneEnvironment;

    private Context standaloneContext;

    @Override
    public Invocation preInvoke( final Invocation invocation )
    {
        try
        {
            final FrameState frame = CURRENT.get().getFrames().push( this.createFrameState( invocation ) );
            invocation.getContext().put( FrameState.class, frame );

            final int status = this.getTransactionManager().getStatus();

            switch ( frame.getMethodType().getTransaction().getType() )
            {
                case MANDATORY:
                    if ( status != Status.STATUS_ACTIVE )
                    {
                        final TransactionRequiredException e =
                            new TransactionRequiredException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), this.getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }
                    break;

                case NEVER:
                    if ( status != Status.STATUS_NO_TRANSACTION )
                    {
                        final NotSupportedException e =
                            new NotSupportedException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), this.getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }
                    break;

                case NOT_SUPPORTED:
                    if ( status == Status.STATUS_ACTIVE )
                    {
                        frame.setSuspendedTransaction( this.getTransactionManager().suspend() );
                    }

                    break;

                case REQUIRED:
                    if ( status == Status.STATUS_NO_TRANSACTION )
                    {
                        this.getTransactionManager().begin();
                        frame.setTransactionInitiator( true );
                        this.getEntityManager().joinTransaction();
                    }
                    else if ( status != Status.STATUS_ACTIVE )
                    {
                        final SystemException e = new SystemException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), this.getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }

                    break;

                case REQUIRES_NEW:
                    if ( status == Status.STATUS_ACTIVE )
                    {
                        frame.setSuspendedTransaction( this.getTransactionManager().suspend() );
                    }
                    else if ( status != Status.STATUS_NO_TRANSACTION )
                    {
                        final SystemException e = new SystemException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), this.getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }

                    this.getTransactionManager().begin();
                    frame.setTransactionInitiator( true );
                    this.getEntityManager().joinTransaction();
                    break;

                case SUPPORTS:
                    break;

                default:
                    final SystemException e = new SystemException( this.getUnsupportedTransactionMessage(
                        this.getLocale(), invocation.getMethod().getName(),
                        frame.getMethodType().getTransaction().getType().toString() ) );

                    e.fillInStackTrace();
                    invocation.setResult( e );

            }
        }
        catch ( final NamingException e )
        {
            invocation.setResult( e );
        }
        catch ( final NotSupportedException e )
        {
            invocation.setResult( e );
        }
        catch ( final SystemException e )
        {
            invocation.setResult( e );
        }

        return invocation;
    }

    @Override
    public Invocation postInvoke( final Invocation invocation )
    {
        final FrameState frame = CURRENT.get().getFrames().pop();

        try
        {
            if ( frame.isTransactionInitiator() )
            {
                if ( invocation.getResult() instanceof Throwable )
                {
                    this.getTransactionManager().rollback();
                }
                else
                {
                    this.getTransactionManager().commit();
                }
            }
        }
        catch ( final NamingException e )
        {
            invocation.setResult( e );
        }
        catch ( final SystemException e )
        {
            invocation.setResult( e );
        }
        catch ( final RollbackException e )
        {
            invocation.setResult( e );
        }
        catch ( final HeuristicMixedException e )
        {
            invocation.setResult( e );
        }
        catch ( final HeuristicRollbackException e )
        {
            invocation.setResult( e );
        }
        finally
        {
            try
            {
                if ( frame.getSuspendedTransaction() != null )
                {
                    this.getTransactionManager().resume( frame.getSuspendedTransaction() );
                }
            }
            catch ( final NamingException e )
            {
                invocation.setResult( e );
            }
            catch ( final InvalidTransactionException e )
            {
                invocation.setResult( e );
            }
            catch ( final SystemException e )
            {
                invocation.setResult( e );
            }
        }

        return invocation;
    }

    @Override
    public void handleException( final Invocation invocation, final Throwable t )
    {
        super.handleException( invocation, t );

        if ( invocation.getResult() instanceof PersistenceException ||
             invocation.getResult() instanceof HeuristicCommitException ||
             invocation.getResult() instanceof HeuristicMixedException ||
             invocation.getResult() instanceof HeuristicRollbackException ||
             invocation.getResult() instanceof InvalidTransactionException ||
             invocation.getResult() instanceof NotSupportedException ||
             invocation.getResult() instanceof RollbackException || invocation.getResult() instanceof SystemException ||
             invocation.getResult() instanceof TransactionRequiredException ||
             invocation.getResult() instanceof TransactionRolledbackException ||
             invocation.getResult() instanceof NamingException )
        {
            final MethodType methodType =
                ( (FrameState) invocation.getContext().get( FrameState.class ) ).getMethodType();

            if ( methodType.getSystemException() != null )
            {
                try
                {
                    final ClassLoader classLoader =
                        (ClassLoader) invocation.getContext().get( DefaultInvocation.CLASSLOADER_KEY );

                    final Throwable systemException =
                        (Throwable) Class.forName( methodType.getSystemException(), true, classLoader ).newInstance();

                    systemException.initCause( t );
                    systemException.fillInStackTrace();
                    invocation.setResult( systemException );
                }
                catch ( final InstantiationException e )
                {
                    final ObjectManagementException oe = new ObjectManagementException( e );
                    oe.fillInStackTrace();
                    invocation.setResult( oe );
                }
                catch ( final IllegalAccessException e )
                {
                    final ObjectManagementException oe = new ObjectManagementException( e );
                    oe.fillInStackTrace();
                    invocation.setResult( oe );
                }
                catch ( final ClassNotFoundException e )
                {
                    final ObjectManagementException oe = new ObjectManagementException( e );
                    oe.fillInStackTrace();
                    invocation.setResult( oe );
                }
            }
            else
            {
                final ObjectManagementException oe = new ObjectManagementException( t );
                oe.fillInStackTrace();
                invocation.setResult( oe );
            }
        }
    }

    public MethodType getMethodType( final Invocation invocation )
    {
        MethodType methodType = null;
        final Instance instance = (Instance) invocation.getContext().get( DefaultInvocation.INSTANCE_KEY );

        String defaultSystemException = ObjectManagementException.class.getName();
        TransactionType defaultTransaction = new TransactionType();
        defaultTransaction.setType( SUPPORTS );

        if ( instance != null )
        {
            MethodsType methodsType = null;

            for ( Object o : instance.getAny() )
            {
                if ( o instanceof JAXBElement )
                {
                    final JAXBElement e = (JAXBElement) o;

                    if ( METHODS_QNAME.equals( e.getName() ) )
                    {
                        methodsType = (MethodsType) e.getValue();
                    }
                }

                if ( o instanceof Element )
                {
                    final Element e = (Element) o;
                    final QName elementQName = new QName( e.getNamespaceURI(), e.getLocalName() );

                    if ( METHODS_QNAME.equals( elementQName ) )
                    {
                        methodsType = JAXB.unmarshal( new DOMSource( e ), MethodsType.class );
                    }
                }
            }

            if ( methodsType != null )
            {
                if ( methodsType.getSystemException() != null )
                {
                    defaultSystemException = methodsType.getSystemException();
                }
                if ( methodsType.getTransaction() != null )
                {
                    defaultTransaction = methodsType.getTransaction();
                }

                methodType = methodsType.getMethod(
                    invocation.getMethod().getName(), invocation.getMethod().getParameterTypes() );

            }
        }

        if ( methodType == null )
        {
            methodType = new MethodType();
            methodType.setName( invocation.getMethod().getName() );
            methodType.setSystemException( defaultSystemException );
            methodType.setTransaction( defaultTransaction );

            final ParametersType parametersType = new ParametersType();

            for ( Class clazz : invocation.getMethod().getParameterTypes() )
            {
                final ParameterType parameterType = new ParameterType();
                parameterType.setType( clazz.getName() );
                parametersType.getParameter().add( parameterType );
            }

            if ( !parametersType.getParameter().isEmpty() )
            {
                methodType.setParameters( parametersType );
            }
        }

        return methodType;
    }

    private StandaloneEnvironment getStandaloneEnvironment()
    {
        if ( this.standaloneEnvironment == null )
        {
            this.standaloneEnvironment = new StandaloneEnvironment();
        }

        return this.standaloneEnvironment;
    }

    private Context getStandaloneContext() throws NamingException
    {
        if ( this.standaloneContext == null )
        {
            this.standaloneContext = new InitialContext();
        }

        return this.standaloneContext;
    }

    private TransactionManager getTransactionManager() throws NamingException
    {
        return (TransactionManager) this.getStandaloneContext().lookup(
            this.getStandaloneEnvironment().getTransactionManagerJndiName() );

    }

    private EntityManager getEntityManager() throws NamingException
    {
        return (EntityManager) this.getStandaloneContext().lookup(
            this.getStandaloneEnvironment().getEntityManagerJndiName() );

    }

    private FrameState createFrameState( final Invocation invocation )
    {
        final FrameState frame = new FrameState();
        frame.setMethodType( this.getMethodType( invocation ) );
        return frame;
    }

    private String getStatusName( final int status ) throws SystemException, NamingException
    {
        switch ( status )
        {
            case Status.STATUS_ACTIVE:
                return "STATUS_ACTIVE";

            case Status.STATUS_COMMITTED:
                return "STATUS_COMMITTED";

            case Status.STATUS_COMMITTING:
                return "STATUS_COMMITTING";

            case Status.STATUS_MARKED_ROLLBACK:
                return "STATUS_MARKED_ROLLBACK";

            case Status.STATUS_NO_TRANSACTION:
                return "STATUS_NO_TRANSACTION";

            case Status.STATUS_PREPARED:
                return "STATUS_PREPARED";

            case Status.STATUS_PREPARING:
                return "STATUS_PREPARING";

            case Status.STATUS_ROLLEDBACK:
                return "STATUS_ROLLEDBACK";

            case Status.STATUS_ROLLING_BACK:
                return "STATUS_ROLLING_BACK";

            default:
                return "STATUS_UNKNOWN";

        }
    }
    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code StandaloneInvoker} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-16/jomc-tools" )
    public StandaloneInvoker()
    {
        // SECTION-START[Default Constructor]
        super();
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
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-16/jomc-tools" )
    private java.util.Locale getLocale()
    {
        final java.util.Locale _d = (java.util.Locale) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getDependency( this, "Locale" );
        assert _d != null : "'Locale' dependency not found.";
        return _d;
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Properties]
    // SECTION-END
    // SECTION-START[Messages]
    // <editor-fold defaultstate="collapsed" desc=" Generated Messages ">

    /**
     * Gets the text of the {@code illegalTransaction} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>Cannot invoke method ''{0}'' with a ''{1}'' transaction context.</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>Die Methode ''{0}'' kann nicht in einem ''{1}'' Transaktions-Kontext ausgef&uuml;hrt werden.</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param methodName Format argument.
     * @param statusName Format argument.
     * @return The text of the {@code illegalTransaction} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-16/jomc-tools" )
    private String getIllegalTransactionMessage( final java.util.Locale locale, final java.lang.String methodName, final java.lang.String statusName )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "illegalTransaction", locale, methodName, statusName );
        assert _m != null : "'illegalTransaction' message not found.";
        return _m;
    }

    /**
     * Gets the text of the {@code unsupportedTransaction} message.
     * <p><b>Templates</b><br/><table>
     * <tr><td valign="top">English:</td><td valign="top"><pre>Unsupported transaction attribute ''{1}'' invoking method ''{0}''.</pre></td></tr>
     * <tr><td valign="top">Deutsch:</td><td valign="top"><pre>Ung&uuml;ltiges Transaktions-Attribut ''{1}'' bei der Ausf&uuml;hrung von Methode ''{0}''.</pre></td></tr>
     * </table></p>
     * @param locale The locale of the message to return.
     * @param methodName Format argument.
     * @param transactionAttribute Format argument.
     * @return The text of the {@code unsupportedTransaction} message.
     *
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-16/jomc-tools" )
    private String getUnsupportedTransactionMessage( final java.util.Locale locale, final java.lang.String methodName, final java.lang.String transactionAttribute )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "unsupportedTransaction", locale, methodName, transactionAttribute );
        assert _m != null : "'unsupportedTransaction' message not found.";
        return _m;
    }
    // </editor-fold>
    // SECTION-END
}

class ThreadState
{

    private Stack<FrameState> frames;

    public Stack<FrameState> getFrames()
    {
        if ( this.frames == null )
        {
            this.frames = new Stack<FrameState>();
        }

        return this.frames;
    }

}

class FrameState
{

    private MethodType methodType;

    private Transaction suspendedTransaction;

    private boolean transactionInitiator;

    public MethodType getMethodType()
    {
        return this.methodType;
    }

    public void setMethodType( final MethodType value )
    {
        this.methodType = value;
    }

    public Transaction getSuspendedTransaction()
    {
        return this.suspendedTransaction;
    }

    public void setSuspendedTransaction( final Transaction value )
    {
        this.suspendedTransaction = value;
    }

    public boolean isTransactionInitiator()
    {
        return this.transactionInitiator;
    }

    public void setTransactionInitiator( final boolean value )
    {
        this.transactionInitiator = value;
    }

}
