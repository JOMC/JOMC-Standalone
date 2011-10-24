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
package org.jomc.standalone.ri;

import java.util.Stack;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
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
import org.jomc.ObjectManagementException;
import org.jomc.model.Instance;
import org.jomc.ri.DefaultInvocation;
import org.jomc.ri.DefaultInvoker;
import org.jomc.spi.Invocation;
import org.jomc.standalone.model.ExceptionType;
import org.jomc.standalone.model.ExceptionsType;
import org.jomc.standalone.model.MethodType;
import org.jomc.standalone.model.MethodsType;
import org.jomc.standalone.model.ParameterType;
import org.jomc.standalone.model.ParametersType;
import org.jomc.standalone.model.TransactionType;
import static org.jomc.standalone.model.TransactionAttributeType.*;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone {@code Invoker}.
 *
 * <p>
 *   This implementation is identified by identifier {@code <JOMC Standalone RI Invoker>}.
 *   It provides objects named {@code <JOMC Standalone RI Invoker>} of the following specifications:
 *
 *   <ul>
 *     <li>{@code <org.jomc.spi.Invoker>} at specification level 1.0.</li>
 *   </ul>
 *
 *   No state is retained across operations due to flag {@code <stateless>}.
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
public class StandaloneInvoker extends DefaultInvoker
{
    // SECTION-START[Invoker]
    // SECTION-END
    // SECTION-START[StandaloneInvoker]

    /**
     * State of a frame.
     *
     * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a>
     * @version $JOMC$
     */
    public static class FrameState
    {

        /** Model of the method associated with the frame. */
        private final MethodType methodType;

        /** Transaction suspended due to the frame or {@code null}. */
        private Transaction suspendedTransaction;

        private boolean suspendedTransactionSet;

        /** Flag indicating the frame initiated the transaction associated with the frame. */
        private boolean transactionInitiator;

        private boolean transactionInitiatorSet;

        /** Flag indicating the transaction associated with this frame to be rolled back. */
        private boolean rollback;

        /**
         * Creates a new {@code FrameState} instance.
         *
         * @param method The model of the method to associate with the new frame.
         *
         * @throws NullPointerException if {@code method} is {@code null}.
         */
        public FrameState( final MethodType method )
        {
            super();

            if ( method == null )
            {
                throw new NullPointerException( "method" );
            }

            this.methodType = method;
        }

        /**
         * Gets the model of the method associated with this frame.
         *
         * @return The model of the method associated with this frame.
         */
        public final MethodType getMethodType()
        {
            return this.methodType;
        }

        /**
         * Gets the transaction suspended due to this frame.
         *
         * @return The transaction suspended due to this frame or {@code null} if no transaction got suspended due to
         * this frame.
         */
        public final Transaction getSuspendedTransaction()
        {
            return this.suspendedTransaction;
        }

        /**
         * Sets the transaction suspended due to this frame.
         *
         * @param value The new transaction suspended due to this frame or {@code null}.
         *
         * @throws IllegalStateException if a suspended transaction already has been set.
         */
        public final void setSuspendedTransaction( final Transaction value )
        {
            if ( this.suspendedTransactionSet )
            {
                throw new IllegalStateException();
            }

            this.suspendedTransaction = value;
            this.suspendedTransactionSet = true;
        }

        /**
         * Gets a flag indicating this frame initiated the transaction associated with this frame.
         *
         * @return {@code true} if this frame initiated the transaction associated with this frame; {@code false} if
         * this frame did not initiate the transaction associated with this frame.
         */
        public final boolean isTransactionInitiator()
        {
            return this.transactionInitiator;
        }

        /**
         * Sets the flag indicating this frame initiated the transaction associated with this frame.
         *
         * @param value {@code true} if this frame initiated the transaction associated with this frame; {@code false}
         * if this frame did not initiate the transaction associated with this frame.
         *
         * @throws IllegalStateException if the flag indicating this frame initiated the transaction associated with
         * this frame already has been set.
         */
        public final void setTransactionInitiator( final boolean value )
        {
            if ( this.transactionInitiatorSet )
            {
                throw new IllegalStateException();
            }

            this.transactionInitiator = value;
            this.transactionInitiatorSet = true;
        }

        /**
         * Gets a flag indicating the transaction associated with this frame to be rolled back.
         *
         * @return {@code true} if the transaction associated with this frame is to be rolled back; {@code false} if
         * the transaction associated with this frame is to be committed.
         */
        public final boolean isRollback()
        {
            return this.rollback;
        }

        /**
         * Sets the flag indicating the transaction associated with this frame to be rolled back.
         *
         * @param value {@code true} to roll back the transaction associated with this frame; {@code false} to commit
         * the transaction associated with this frame.
         */
        public final void setRollback( final boolean value )
        {
            if ( !this.rollback )
            {
                this.rollback = value;
            }
        }

    }

    /** Environment of the instance. */
    private StandaloneEnvironment standaloneEnvironment;

    /** Context of the instance. */
    private Context standaloneContext;

    /** Thread local frames. */
    private static final ThreadLocal<Stack<FrameState>> FRAMES = new ThreadLocal<Stack<FrameState>>()
    {

        @Override
        public Stack<FrameState> initialValue()
        {
            return new Stack<FrameState>();
        }

    };

    @Override
    public Invocation preInvoke( final Invocation invocation )
    {
        try
        {
            final FrameState currentFrame = this.getFrameState( invocation );
            invocation.getContext().put( FrameState.class, currentFrame );

            final FrameState previousFrame = FRAMES.get().isEmpty() ? null : FRAMES.get().peek();

            if ( previousFrame != null )
            {
                currentFrame.setRollback( previousFrame.isRollback() );
            }

            FRAMES.get().push( currentFrame );

            final int status = this.getTransactionManager().getStatus();

            switch ( currentFrame.getMethodType().getTransaction().getType() )
            {
                case MANDATORY:
                    if ( status != Status.STATUS_ACTIVE )
                    {
                        final TransactionRequiredException e =
                            new TransactionRequiredException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }
                    break;

                case NEVER:
                    if ( status != Status.STATUS_NO_TRANSACTION )
                    {
                        final NotSupportedException e =
                            new NotSupportedException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }
                    break;

                case NOT_SUPPORTED:
                    if ( status == Status.STATUS_ACTIVE )
                    {
                        currentFrame.setSuspendedTransaction( this.getTransactionManager().suspend() );
                    }

                    break;

                case REQUIRED:
                    if ( status == Status.STATUS_NO_TRANSACTION )
                    {
                        this.getTransactionManager().begin();
                        currentFrame.setTransactionInitiator( true );
                        this.getEntityManager().joinTransaction();
                    }
                    else if ( status != Status.STATUS_ACTIVE )
                    {
                        final SystemException e = new SystemException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }

                    break;

                case REQUIRES_NEW:
                    if ( status == Status.STATUS_ACTIVE )
                    {
                        currentFrame.setSuspendedTransaction( this.getTransactionManager().suspend() );
                    }
                    else if ( status != Status.STATUS_NO_TRANSACTION )
                    {
                        final SystemException e = new SystemException( this.getIllegalTransactionMessage(
                            this.getLocale(), invocation.getMethod().getName(), getStatusName( status ) ) );

                        e.fillInStackTrace();
                        invocation.setResult( e );
                    }

                    this.getTransactionManager().begin();
                    currentFrame.setTransactionInitiator( true );
                    this.getEntityManager().joinTransaction();
                    break;

                case SUPPORTS:
                    break;

                default:
                    final SystemException e = new SystemException( this.getUnsupportedTransactionMessage(
                        this.getLocale(), invocation.getMethod().getName(),
                        currentFrame.getMethodType().getTransaction().getType().toString() ) );

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
        final FrameState frame = FRAMES.get().pop();

        try
        {
            if ( frame.isTransactionInitiator() )
            {
                if ( frame.isRollback() )
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

        final FrameState frameState = (FrameState) invocation.getContext().get( FrameState.class );
        final ClassLoader classLoader = (ClassLoader) invocation.getContext().get( DefaultInvocation.CLASSLOADER_KEY );

        try
        {
            ExceptionType handledException =
                frameState.getMethodType().getExceptions().getException( invocation.getResult().getClass().getName() );

            if ( handledException == null )
            {
                for ( ExceptionType e : frameState.getMethodType().getExceptions().getException() )
                {
                    final Class<?> handledExceptionClass = Class.forName( e.getClazz(), true, classLoader );

                    if ( handledExceptionClass.isAssignableFrom( invocation.getResult().getClass() ) )
                    {
                        handledException = e;
                        break;
                    }
                }
            }

            if ( handledException != null )
            {
                frameState.setRollback( handledException.isRollback() );

                if ( handledException.getTargetClass() != null )
                {
                    final Throwable targetException =
                        Class.forName( handledException.getTargetClass(), true, classLoader ).
                        asSubclass( Throwable.class ).newInstance();

                    targetException.initCause( (Throwable) invocation.getResult() );
                    targetException.fillInStackTrace();
                    invocation.setResult( targetException );
                }
            }
            else if ( frameState.getMethodType().getExceptions().getDefaultException() != null )
            {
                final Throwable defaultException = Class.forName(
                    frameState.getMethodType().getExceptions().getDefaultException().getClazz(), true, classLoader ).
                    asSubclass( Throwable.class ).newInstance();

                defaultException.initCause( (Throwable) invocation.getResult() );
                defaultException.fillInStackTrace();
                invocation.setResult( defaultException );

                frameState.setRollback(
                    frameState.getMethodType().getExceptions().getDefaultException().isRollback() );

            }
        }
        catch ( final InstantiationException e )
        {
            final ObjectManagementException oe = new ObjectManagementException( getMessage( e ), e );
            oe.fillInStackTrace();
            invocation.setResult( oe );
            frameState.setRollback( true );
        }
        catch ( final IllegalAccessException e )
        {
            final ObjectManagementException oe = new ObjectManagementException( getMessage( e ), e );
            oe.fillInStackTrace();
            invocation.setResult( oe );
            frameState.setRollback( true );
        }
        catch ( final ClassNotFoundException e )
        {
            final ObjectManagementException oe = new ObjectManagementException( getMessage( e ), e );
            oe.fillInStackTrace();
            invocation.setResult( oe );
            frameState.setRollback( true );
        }
    }

    /**
     * Creates a new {@code FrameState} instance for a given invocation.
     *
     * @param invocation The invocation to create a new {@code FrameState} instance for.
     *
     * @return A new {@code FrameState} instance for {@code invocation}.
     *
     * @throws NullPointerException if {@code invocation} is {@code null}.
     */
    protected FrameState getFrameState( final Invocation invocation )
    {
        if ( invocation == null )
        {
            throw new NullPointerException( "invocation" );
        }

        return new FrameState( this.getMethodType( invocation ) );
    }

    /**
     * Gets the model of the method of a given invocation.
     *
     * @param invocation The invocation to get the model of the associated method of.
     *
     * @return The model of the method of {@code invocation}.
     *
     * @throws NullPointerException if {@code invocation} is {@code null}.
     */
    public MethodType getMethodType( final Invocation invocation )
    {
        if ( invocation == null )
        {
            throw new NullPointerException( "invocation" );
        }

        MethodType methodType = null;
        final Instance instance = (Instance) invocation.getContext().get( DefaultInvocation.INSTANCE_KEY );

        ExceptionsType defaultExceptions = new ExceptionsType();
        TransactionType defaultTransaction = new TransactionType();
        defaultTransaction.setType( SUPPORTS );

        if ( instance != null )
        {
            final MethodsType methodsType = instance.getAnyObject( MethodsType.class );

            if ( methodsType != null )
            {
                if ( methodsType.getTransaction() != null )
                {
                    defaultTransaction = methodsType.getTransaction();
                }
                if ( methodsType.getExceptions() != null )
                {
                    defaultExceptions = methodsType.getExceptions();
                }

                methodType = methodsType.getMethod(
                    invocation.getMethod().getName(), invocation.getMethod().getParameterTypes() );

            }
        }

        if ( methodType != null )
        {
            if ( methodType.getTransaction() == null )
            {
                methodType.setTransaction( defaultTransaction );
            }
            if ( methodType.getExceptions() == null )
            {
                methodType.setExceptions( defaultExceptions );
            }
            else
            {
                for ( ExceptionType e : defaultExceptions.getException() )
                {
                    if ( methodType.getExceptions().getException( e.getClazz() ) == null )
                    {
                        methodType.getExceptions().getException().add( e );
                    }
                }

                if ( methodType.getExceptions().getDefaultException() == null )
                {
                    methodType.getExceptions().setDefaultException( defaultExceptions.getDefaultException() );
                }
            }
        }
        else
        {
            methodType = new MethodType();
            methodType.setName( invocation.getMethod().getName() );
            methodType.setTransaction( defaultTransaction );
            methodType.setExceptions( defaultExceptions );

            final ParametersType parametersType = new ParametersType();

            for ( Class<?> clazz : invocation.getMethod().getParameterTypes() )
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

    /**
     * Gets the environment of the invoker.
     *
     * @return The environment of the invoker.
     */
    protected StandaloneEnvironment getStandaloneEnvironment()
    {
        if ( this.standaloneEnvironment == null )
        {
            this.standaloneEnvironment = new StandaloneEnvironment();
        }

        return this.standaloneEnvironment;
    }

    /**
     * Gets the JNDI context of the invoker.
     *
     * @return The JNDI context of the invoker.
     *
     * @throws NamingException if getting the JNDI context of the invoker fails.
     */
    protected Context getStandaloneContext() throws NamingException
    {
        if ( this.standaloneContext == null )
        {
            this.standaloneContext = new InitialContext();
        }

        return this.standaloneContext;
    }

    /**
     * Gets the transaction manager of the JNDI context of the invoker.
     *
     * @return The transaction manager of the JNDI context of the invoker.
     *
     * @throws NamingException if getting the transaction manager of the JNDI context of the invoker fails.
     *
     * @see #getStandaloneContext()
     * @see #getStandaloneEnvironment()
     * @see StandaloneEnvironment#getTransactionManagerJndiName()
     */
    private TransactionManager getTransactionManager() throws NamingException
    {
        return (TransactionManager) this.getStandaloneContext().lookup(
            this.getStandaloneEnvironment().getTransactionManagerJndiName() );

    }

    /**
     * Gets the entity manager of the JNDI context of the invoker.
     *
     * @return The entity manager of the JNDI context of the invoker.
     *
     * @throws NamingException if getting the entity manager of the JNDI context of the invoker fails.
     *
     * @see #getStandaloneContext()
     * @see #getStandaloneEnvironment()
     * @see StandaloneEnvironment#getEntityManagerJndiName()
     */
    private EntityManager getEntityManager() throws NamingException
    {
        return (EntityManager) this.getStandaloneContext().lookup(
            this.getStandaloneEnvironment().getEntityManagerJndiName() );

    }

    /**
     * Gets a name for a transaction status constant.
     *
     * @param status The status constant to get a name for.
     *
     * @return A name for {@code status}.
     */
    private static String getStatusName( final int status )
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

    /**
     * Gets the message of a given {@code Throwable} recursively.
     *
     * @param t The {@code Throwable} to get the message of.
     *
     * @return The message of {@code t} or {@code null} if no message is found.
     */
    private static String getMessage( final Throwable t )
    {
        return t != null ? t.getMessage() != null ? t.getMessage() : getMessage( t.getCause() ) : null;
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">
    /** Creates a new {@code StandaloneInvoker} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
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
     * Gets the {@code <Locale>} dependency.
     * <p>
     *   This method returns the {@code <default>} object of the {@code <java.util.Locale>} specification at specification level 1.1.
     *   That specification does not apply to any scope. A new object is returned whenever requested and bound to this instance.
     * </p>
     * @return The {@code <Locale>} dependency.
     * @throws org.jomc.ObjectManagementException if getting the dependency instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
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
     * Gets the text of the {@code <illegalTransactionMessage>} message.
     * <p><strong>Languages:</strong>
     *   <ul>
     *     <li>English (default)</li>
     *     <li>Deutsch</li>
     *   </ul>
     * </p>
     *
     * @param locale The locale of the message to return.
     * @param methodName Format argument.
     * @param statusName Format argument.
     * @return The text of the {@code <illegalTransactionMessage>} message for {@code locale}.
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private String getIllegalTransactionMessage( final java.util.Locale locale, final java.lang.String methodName, final java.lang.String statusName )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "illegalTransactionMessage", locale, methodName, statusName );
        assert _m != null : "'illegalTransactionMessage' message not found.";
        return _m;
    }
    /**
     * Gets the text of the {@code <unsupportedTransactionMessage>} message.
     * <p><strong>Languages:</strong>
     *   <ul>
     *     <li>English (default)</li>
     *     <li>Deutsch</li>
     *   </ul>
     * </p>
     *
     * @param locale The locale of the message to return.
     * @param methodName Format argument.
     * @param transactionAttribute Format argument.
     * @return The text of the {@code <unsupportedTransactionMessage>} message for {@code locale}.
     * @throws org.jomc.ObjectManagementException if getting the message instance fails.
     */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    private String getUnsupportedTransactionMessage( final java.util.Locale locale, final java.lang.String methodName, final java.lang.String transactionAttribute )
    {
        final String _m = org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getMessage( this, "unsupportedTransactionMessage", locale, methodName, transactionAttribute );
        assert _m != null : "'unsupportedTransactionMessage' message not found.";
        return _m;
    }
    // </editor-fold>
    // SECTION-END
}
