// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Copyright (C) Christian Schulte, 2005-07-25
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
package org.jomc.standalone.ri.naming.java;

import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import org.jomc.standalone.ri.naming.StandaloneContext;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone context factory for the ''java'' URL scheme.
 *
 * <p>
 *   This implementation is identified by identifier {@code <org.jomc.standalone.ri.naming.java.javaURLContextFactory>}.
 *   It provides objects named {@code <JOMC Standalone RI javaURLContextFactory>} of the following specifications:
 *
 *   <ul>
 *     <li>{@code <javax.naming.spi.ObjectFactory>} at any specification level.</li>
 *   </ul>
 *
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
public class javaURLContextFactory implements ObjectFactory
{
    // SECTION-START[ObjectFactory]

    private static Context javaContext;

    public Object getObjectInstance( final Object obj, final Name name, final Context nameCtx,
                                     final Hashtable environment ) throws Exception
    {
        synchronized ( javaURLContextFactory.class )
        {
            if ( javaContext == null )
            {
                javaContext = new StandaloneContext();
                javaContext.getEnvironment().putAll( environment );
            }

            if ( obj == null )
            {
                return javaContext;
            }
            if ( obj instanceof String && ( (String) obj ).startsWith( "java:" ) )
            {
                return javaContext.lookup( new CompositeName( obj.toString() ) );
            }
            if ( obj instanceof String[] && ( (String[]) obj ).length > 0 )
            {
                final String url = ( (String[]) obj )[0];
                if ( url.startsWith( "java:" ) )
                {
                    return javaContext.lookup( new CompositeName( ( (String[]) obj )[0] ) );
                }
            }

            return null;
        }
    }

    // SECTION-END
    // SECTION-START[javaURLContextFactory]
    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code javaURLContextFactory} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    public javaURLContextFactory()
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
