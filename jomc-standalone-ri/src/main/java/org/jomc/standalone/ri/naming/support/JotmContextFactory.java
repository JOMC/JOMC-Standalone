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

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import org.objectweb.jotm.Jotm;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone Jonas JTA context factory.
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
@javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor",
                             comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-19/jomc-tools" )
// </editor-fold>
// SECTION-END
public class JotmContextFactory extends AbstractContextFactory
{
    // SECTION-START[InitialContextFactory]

    public Context getInitialContext( final Hashtable<?, ?> environment ) throws NamingException
    {
        final Jotm jotm = new Jotm( true, false );
        this.getStandaloneContext().bind( this.getStandaloneEnvironment().getTransactionManagerJndiName(),
                                          jotm.getTransactionManager() );

        this.getStandaloneContext().bind( this.getStandaloneEnvironment().getUserTransactionJndiName(),
                                          jotm.getUserTransaction() );

        this.getStandaloneContext().bind(
            this.getStandaloneEnvironment().getTransactionSynchronizationRegistryJndiName(),
            jotm.getTransactionSynchronizationRegistry() );

        return null;
    }

    // SECTION-END
    // SECTION-START[JotmContextFactory]
    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code JotmContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-19/jomc-tools" )
    public JotmContextFactory()
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
